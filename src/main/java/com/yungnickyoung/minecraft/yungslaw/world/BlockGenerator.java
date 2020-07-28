package com.yungnickyoung.minecraft.yungslaw.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class BlockGenerator implements IWorldGenerator {
    // Constants. Can be extracted into config item.
    private static int RADIUS = 3;

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (!(world instanceof WorldServer)) return;

        // Bounds for the 16x16 area we are actually generating on
        int innerXStart = chunkX * 16 + 8;
        int innerZStart = chunkZ * 16 + 8;
        int innerXEnd = innerXStart + 16;
        int innerZEnd = innerZStart + 16;

        // Bounds for the outer area.
        // Pads the inner 16x16 area by RADIUS blocks in each direction in order to find any target blocks
        // outside the inner area that may impact blocks within the inner area
        int outerXStart = innerXStart - RADIUS;
        int outerZStart = innerZStart - RADIUS;
        int outerXEnd = innerXEnd + RADIUS;
        int outerZEnd = innerZEnd + RADIUS;

        // 3-D array of values we set for each block.
        // 0 = AIR, 1 = Block within range of AIR, 2 = block that should be processed
        int[][][] values = new int[outerXEnd - outerXStart][64 + RADIUS][outerZEnd - outerZStart];

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        // Initialize values
        for (int x = 0; x < outerXEnd - outerXStart; x++) {
            for (int z = 0; z < outerZEnd - outerZStart; z++) {
                for (int y = 0; y < 64 + RADIUS; y++) {
                    pos.setPos(outerXStart + x, y, outerZStart + z);
                    IBlockState state = world.getBlockState(pos);
                    if (state == Blocks.AIR.getDefaultState() || state.getMaterial().isLiquid())
                        values[x][y][z] = 0;
                    else
                        values[x][y][z] = 2;
                }
            }
        }

        // Update blocks around air blocks
        for (int x = outerXStart; x < outerXEnd; x++) {
            for (int z = outerZStart; z < outerZEnd; z++) {
                for (int y = 0; y < 64 + RADIUS; y++) {
                    if (values[x - outerXStart][y][z - outerZStart] == 0) { // if block is air
                        for (int offsetX = x - outerXStart - RADIUS; offsetX <= x - outerXStart + RADIUS; offsetX++) {
                            if (offsetX < RADIUS || offsetX > 15 + RADIUS) continue;

                            for (int offsetZ = z - outerZStart - RADIUS; offsetZ <= z - outerZStart + RADIUS; offsetZ++) {
                                if (offsetZ < RADIUS || offsetZ > 15 + RADIUS) continue;

                                for (int offsetY = y - RADIUS; offsetY <= y + RADIUS; offsetY++) {
                                    if (offsetY < 0 || offsetY > 64) continue;
                                    values[offsetX][offsetY][offsetZ] = Math.min(values[offsetX][offsetY][offsetZ], 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Process marked blocks
        for (int x = RADIUS; x < 16 + RADIUS; x++) {
            for (int z = RADIUS; z < 16 + RADIUS; z++) {
                for (int y = 0; y < 64; y++) {
                    if (values[x][y][z] == 2) {
                        pos.setPos(x + outerXStart, y, z + outerZStart);
                        world.setBlockState(pos, Blocks.LAPIS_BLOCK.getDefaultState());
                    }
                }
            }
        }
    }
}
