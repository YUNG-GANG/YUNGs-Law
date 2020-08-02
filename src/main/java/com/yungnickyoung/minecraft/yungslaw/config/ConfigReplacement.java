package com.yungnickyoung.minecraft.yungslaw.config;

import net.minecraftforge.common.config.Config;

public class ConfigReplacement {
    @Config.Name("Generation Distance")
    @Config.Comment(
        "If Ore Deletion Mode is disabled, this is the minimum distance from Safe Blocks (e.g. air) at which your Hard Block will replace underground blocks.\n" +
        "If Ore Deletion Mode is enabled, this is the minimum distance from Safe Blocks at which ores will start to be deleted."
    )
    @Config.RequiresWorldRestart
    public int genDistance = 3;

    @Config.Name("Safe Blocks")
    @Config.Comment("Blocks that will not be replaced. Any blocks within the Generation Distance of a Safe Block will also not be replaced.")
    @Config.RequiresWorldRestart
    public String[] safeBlocks = {"minecraft:air"};

    @Config.Name("Untouchable Blocks")
    @Config.Comment("Like Safe Blocks, these blocks will not be replaced. Unlike Safe Blocks, no surrounding blocks will be protected from replacement.")
    @Config.RequiresWorldRestart
    public String[] untouchableBlocks = {"minecraft:bedrock"};

    @Config.Name("Max Altitude")
    @Config.Comment("Maximum altitude at which block replacement or ore deletion can occur.")
    @Config.RequiresWorldRestart
    public int maxAltitude = 63;

    @Config.Name("Mark Liquids As Safe")
    @Config.Comment(
        "If true, liquids such as water and lava will be considered Safe Blocks, meaning blocks close to them won't be replaced or deleted.\n" +
        "Recommended: true"
    )
    @Config.RequiresWorldRestart
    public boolean enableLiquidSafety = true;

    @Config.Name("Enable Ore Deletion Mode")
    @Config.Comment("If enabled, only ore blocks will be changed. The Hard Block will not be used - instead ores will be replaced with the biome's filler block (usually stone).")
    @Config.RequiresWorldRestart
    public boolean enableOreDeletion = false;

    @Config.Name("Replacement Mode Settings")
    @Config.Comment("Settings for using Replacement Mode (default). These only do anything if Ore Deletion Mode is disabled.")
    @Config.RequiresWorldRestart
    public ConfigReplacementMode replacementMode = new ConfigReplacementMode();

    @Config.Name("Ore Deletion Mode Settings")
    @Config.Comment("Settings for using Ore Deletion Mode. These only do anything if Ore Deletion Mode is enabled.")
    @Config.RequiresWorldRestart
    public ConfigOreMode oreMode = new ConfigOreMode();
}
