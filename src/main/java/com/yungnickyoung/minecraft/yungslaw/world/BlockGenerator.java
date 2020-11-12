package com.yungnickyoung.minecraft.yungslaw.world;

import com.yungnickyoung.minecraft.yungslaw.YungsLaw;
import com.yungnickyoung.minecraft.yungslaw.config.util.ConfigHolder;
import com.yungnickyoung.minecraft.yungslaw.config.Configuration;
import com.yungnickyoung.minecraft.yungslaw.config.io.ConfigLoader;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockGenerator implements IWorldGenerator {
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (!(world instanceof WorldServer)) return;
        if (!isDimensionWhitelisted(world)) return;

        // Extract vars from config for this dimension
        final ConfigHolder config = YungsLaw.configMap.computeIfAbsent(world.provider.getDimension(), ConfigLoader::loadConfigFromFileForDimension);
        final boolean           enableOreDeletion    = config.enableOreDeletion.get();
        final int               radius               = config.genDistance.get();
        final int               maxAltitude          = config.maxAltitude.get();
        final boolean           enableLiquidSafety   = config.enableLiquidSafety.get();
        final IBlockState       hardBlock            = getHardBlockFromString(config.hardBlock.get());
        final List<IBlockState> whitelistedOreBlocks = getBlockListFromNames(config.oreWhitelist.get());
        final List<IBlockState> safeBlocks           = getBlockListFromNames(config.safeBlocks.get());
        final List<IBlockState> untouchableBlocks    = getBlockListFromNames(config.untouchableBlocks.get());

        // Bounds for the 16x16 area we are actually generating on
        final int innerXStart = chunkX * 16 + 8;
        final int innerZStart = chunkZ * 16 + 8;
        final int innerXEnd   = innerXStart + 16;
        final int innerZEnd   = innerZStart + 16;

        // Bounds for the outer area.
        // Pads the inner 16x16 area by <radius> blocks in each direction in order to find any Safe Blocks
        // outside the inner area that may impact blocks within the inner area
        final int outerXStart = innerXStart - radius;
        final int outerZStart = innerZStart - radius;
        final int outerXEnd   = innerXEnd + radius;
        final int outerZEnd   = innerZEnd + radius;

        // 3-D array of values we set for each block. I don't use an enum here to avoid additional overhead cost
        // -1 = should not be processed, 0 = Safe Block, 1 = Block within range of AIR, 2 = should be processed, 3 = ore (for ore delete mode)
        int[][][] values = new int[outerXEnd - outerXStart][maxAltitude + radius][outerZEnd - outerZStart];

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        // Initialize values
        for (int x = 0; x < outerXEnd - outerXStart; x++) {
            for (int z = 0; z < outerZEnd - outerZStart; z++) {
                for (int y = 0; y < maxAltitude + radius; y++) {
                    pos.setPos(outerXStart + x, y, outerZStart + z);
                    IBlockState state = world.getBlockState(pos);
                    if (safeBlocks.contains(state) || (enableLiquidSafety && state.getMaterial().isLiquid())) values[x][y][z] = 0; //  0 --> Safe Block
                    else if (untouchableBlocks.contains(state)) values[x][y][z] = -1;                                              // -1 --> Untouchable Block
                    else if (enableOreDeletion && whitelistedOreBlocks.contains(state)) values[x][y][z] = 3;                       //  3 --> Ore Block
                    else values[x][y][z] = 2;                                                                                      //  2 --> Can be processed
                }
            }
        }

        // Update blocks around air blocks
        for (int x = outerXStart; x < outerXEnd; x++) {
            for (int z = outerZStart; z < outerZEnd; z++) {
                for (int y = 0; y < maxAltitude + radius; y++) {
                    // Mark blocks within radius distance of AIR blocks as safe from processing (1)
                    if (values[x - outerXStart][y][z - outerZStart] == 0) {
                        for (int offsetX = x - outerXStart - radius; offsetX <= x - outerXStart + radius; offsetX++) {
                            if (offsetX < radius || offsetX > 15 + radius) continue;

                            for (int offsetZ = z - outerZStart - radius; offsetZ <= z - outerZStart + radius; offsetZ++) {
                                if (offsetZ < radius || offsetZ > 15 + radius) continue;

                                for (int offsetY = y - radius; offsetY <= y + radius; offsetY++) {
                                    if (offsetY < 0 || offsetY > maxAltitude) continue;
                                    values[offsetX][offsetY][offsetZ] = Math.min(values[offsetX][offsetY][offsetZ], 1); // 1 --> Cannot be processed
                                }
                            }
                        }
                    }
                }
            }
        }

        // Process marked blocks
        for (int x = radius; x < 16 + radius; x++) {
            for (int z = radius; z < 16 + radius; z++) {
                for (int y = 0; y < maxAltitude; y++) {
                    pos.setPos(x + outerXStart, y, z + outerZStart);
                    // Ore deletion mode
                    if (enableOreDeletion && values[x][y][z] == 3) {
                        // Replace with biome filler block
                        world.setBlockState(pos, world.getBiome(pos).fillerBlock);
                    }
                    // Replacement mode (default)
                    else if (!enableOreDeletion && values[x][y][z] == 2) {
                        world.setBlockState(pos, hardBlock);
                    }
                }
            }
        }
    }

    private boolean isDimensionWhitelisted(World world) {
        return Configuration.enableGlobalWhitelist ||
            Arrays.stream(Configuration.whitelistedDimensionIDs).anyMatch(id -> id == world.provider.getDimension());
    }

    private List<IBlockState> getBlockListFromNames(String[] blockNames) {
        List<IBlockState> blockStateList = new ArrayList<>();

        for (String blockName : blockNames) {
            try {
                if (blockName.indexOf('@') > -1) {
                    String[] nameSplit = blockName.split("@", 2);
                    Block block = Block.getBlockFromName(nameSplit[0]);
                    if (block != null) blockStateList.add(block.getStateFromMeta(Integer.parseInt(nameSplit[1])));
                }
                else {
                    Block block = Block.getBlockFromName(blockName);
                    if (block != null) blockStateList.add(block.getDefaultState());
                }
            } catch (Exception e) {
                YungsLaw.LOGGER.error("ERROR: Unable to find block {}: {}", blockName, e);
            }
        }

        return blockStateList;
    }

    /**
     * Gets the namespaced Hard Block string from the config and returns its BlockState.
     * Defaults to obsidian if its BlockState cannot be found.
     */
    private IBlockState getHardBlockFromString(String hardBlockString) {
        IBlockState hardBlock;

        try {
            hardBlock = Block.getBlockFromName(hardBlockString).getDefaultState();
        } catch (Exception e) {
            YungsLaw.LOGGER.error("ERROR: Unable to use block {}: {}", hardBlockString, e);
            YungsLaw.LOGGER.error("Using obsidian instead...");
            hardBlock = Blocks.OBSIDIAN.getDefaultState();
        }

        if (hardBlock == null) {
            YungsLaw.LOGGER.error("ERROR: Unable to use block {}: null block returned.", hardBlockString);
            YungsLaw.LOGGER.warn("Using obsidian instead...");
            hardBlock = Blocks.OBSIDIAN.getDefaultState();
        }

        return hardBlock;
    }
}
