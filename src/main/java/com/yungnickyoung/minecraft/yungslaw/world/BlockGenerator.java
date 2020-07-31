package com.yungnickyoung.minecraft.yungslaw.world;

import com.yungnickyoung.minecraft.yungslaw.YungsLaw;
import com.yungnickyoung.minecraft.yungslaw.config.ConfigHolder;
import com.yungnickyoung.minecraft.yungslaw.config.Configuration;
import com.yungnickyoung.minecraft.yungslaw.config.io.ConfigLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Arrays;
import java.util.Random;

public class BlockGenerator implements IWorldGenerator {
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (!(world instanceof WorldServer)) return;
        if (!isDimensionWhitelisted(world)) return;

        // Get vars from config
        ConfigHolder config = YungsLaw.configMap.computeIfAbsent(world.provider.getDimension(), ConfigLoader::loadConfigFromFileForDimension);
        boolean enableOreDeletion = config.enableOreDeletion.get();
        IBlockState hardBlock = getHardBlock(config.hardBlock.get());
        int radius = config.genDistance.get();
        int maxAltitude = config.maxAltitude.get();
        boolean enableLiquidSafety = config.enableLiquidSafety.get();

        // Bounds for the 16x16 area we are actually generating on
        int innerXStart = chunkX * 16 + 8;
        int innerZStart = chunkZ * 16 + 8;
        int innerXEnd = innerXStart + 16;
        int innerZEnd = innerZStart + 16;

        // Bounds for the outer area.
        // Pads the inner 16x16 area by <radius> blocks in each direction in order to find any Safe Blocks
        // outside the inner area that may impact blocks within the inner area
        int outerXStart = innerXStart - radius;
        int outerZStart = innerZStart - radius;
        int outerXEnd = innerXEnd + radius;
        int outerZEnd = innerZEnd + radius;

        // 3-D array of values we set for each block.
        // 0 = AIR, 1 = Block within range of AIR, 2 = should be processed, -1 = should not be processed, 3 = ore (for ore delete mode)
        int[][][] values = new int[outerXEnd - outerXStart][maxAltitude + radius][outerZEnd - outerZStart];

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        // Initialize values
        for (int x = 0; x < outerXEnd - outerXStart; x++) {
            for (int z = 0; z < outerZEnd - outerZStart; z++) {
                for (int y = 0; y < maxAltitude + radius; y++) {
                    pos.setPos(outerXStart + x, y, outerZStart + z);
                    IBlockState state = world.getBlockState(pos);
                    if (isSafe(state, enableLiquidSafety, config.safeBlocks.get())) values[x][y][z] = 0;
                    else if (isUntouchable(state, config.untouchableBlocks.get())) values[x][y][z] = -1;
                    else if (isOre(state, enableOreDeletion)) values[x][y][z] = 3;
                    else values[x][y][z] = 2;
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
                                    values[offsetX][offsetY][offsetZ] = Math.min(values[offsetX][offsetY][offsetZ], 1);
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
                        world.setBlockState(pos, Blocks.GOLD_BLOCK.getDefaultState());
                    }
                    // Replacement mode (default)
                    else if (!enableOreDeletion && values[x][y][z] == 2) {
                        world.setBlockState(pos, hardBlock);
                    }
                }
            }
        }
    }

    private boolean isSafe(IBlockState state, boolean enableLiquidSafety, String[] safeBlocks) {
        for (String blockName : safeBlocks) {
            try {
                Block block = Block.getBlockFromName(blockName);
                if (block == state.getBlock()) return true;
            } catch (Exception e) {
                YungsLaw.LOGGER.error("ERROR: Unable to find Safe Block {}: {}", blockName, e);
            }
        }
        return enableLiquidSafety && state.getMaterial().isLiquid();
    }

    private boolean isUntouchable(IBlockState state, String[] untouchableBlocks) {
        for (String blockName : untouchableBlocks) {
            try {
                Block block = Block.getBlockFromName(blockName);
                if (block == state.getBlock()) return true;
            } catch (Exception e) {
                YungsLaw.LOGGER.warn("ERROR: Unable to find Untouchable Block {}: {}", blockName, e);
            }
        }
        return false;
    }

    private boolean isOre(IBlockState state, boolean enableOreDeletion) {
        return enableOreDeletion && (
            state.getBlock() instanceof BlockOre
            || state.getBlock() instanceof BlockRedstoneOre
        );
    }

    private boolean isDimensionWhitelisted(World world) {
        return Configuration.enableGlobalWhitelist ||
            Arrays.stream(Configuration.whitelistedDimensionIDs).anyMatch(id -> id == world.provider.getDimension());
    }

    /**
     * Gets the namespaced Hard Block string from the config and returns its BlockState.
     * Defaults to obsidian if its BlockState cannot be found.
     */
    private IBlockState getHardBlock(String hardBlockString) {
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
