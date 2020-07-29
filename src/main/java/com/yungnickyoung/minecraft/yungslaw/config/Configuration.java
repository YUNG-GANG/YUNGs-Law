package com.yungnickyoung.minecraft.yungslaw.config;

import com.yungnickyoung.minecraft.yungslaw.YungsLaw;
import net.minecraftforge.common.config.Config;

@Config(modid = YungsLaw.MODID, name="yungslaw-1_12_2")
public class Configuration {
    @Config.Name("Generation Distance")
    @Config.Comment("Distance from Safe Blocks (e.g. air) from which your Hard Block will replace underground blocks.")
    @Config.RequiresWorldRestart
    public static int genDistance = 3;

    @Config.Name("Hard Block")
    @Config.Comment("The block that will replace the underground in areas far from Safe Blocks. Defaults to obsidian if value provided is invalid.")
    @Config.RequiresWorldRestart
    public static String hardBlock = "minecraft:obsidian";

    @Config.Name("Safe Blocks")
    @Config.Comment("Blocks that will not be replaced. Any blocks within the Generation Distance of a Safe Block will also not be replaced.")
    @Config.RequiresWorldRestart
    public static String[] safeBlocks = {"minecraft:air"};

    @Config.Name("Untouchable Blocks")
    @Config.Comment("Like Safe Blocks, these blocks will not be replaced. Unlike Safe Blocks, no surrounding blocks will be protected from replacement.")
    @Config.RequiresWorldRestart
    public static String[] untouchableBlocks = {"minecraft:bedrock"};

    @Config.Name("Max Altitude")
    @Config.Comment("Maximum altitude at which blocks will be replaced with the Hard Block.")
    @Config.RequiresWorldRestart
    public static int maxAltitude = 63;

    @Config.Name("Mark Liquids As Safe")
    @Config.Comment("If true, liquids such as water and lava will be considered Safe Blocks, meaning blocks close to them won't be replaced. Recommended: true")
    @Config.RequiresWorldRestart
    public static boolean enableLiquidSafety = true;

    @Config.Name("Whitelisted Dimension IDs")
    @Config.Comment("List of ID's of dimensions that will use YUNG's Law. Ignored if Global Whitelisting is enabled.")
    @Config.RequiresWorldRestart
    public static int[] whitelistedDimensionIDs = {0};

    @Config.Name("Enable Global Whitelist")
    @Config.Comment("Automatically enables YUNG's Law in every possible dimension.")
    @Config.RequiresWorldRestart
    public static boolean enableGlobalWhitelist = false;
}
