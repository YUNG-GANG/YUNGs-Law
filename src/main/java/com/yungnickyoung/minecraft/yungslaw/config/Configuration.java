package com.yungnickyoung.minecraft.yungslaw.config;

import net.minecraftforge.common.config.Config;

@Config(modid = YLSettings.MOD_ID, name=YLSettings.BASE_CONFIG_NAME)
public class Configuration {
    @Config.Name("Whitelisted Dimension IDs")
    @Config.Comment("List of ID's of dimensions that will use YUNG's Law. Ignored if Global Whitelisting is enabled.")
    @Config.RequiresWorldRestart
    public static int[] whitelistedDimensionIDs = {0};

    @Config.Name("Enable Global Whitelist")
    @Config.Comment("Automatically enables YUNG's Law in every possible dimension.")
    @Config.RequiresWorldRestart
    public static boolean enableGlobalWhitelist = false;

    @Config.Name("Replacement Settings")
    @Config.Comment("Settings for underground block replacement.")
    @Config.RequiresWorldRestart
    public static ConfigReplacement replacementSettings = new ConfigReplacement();

    @Config.Name("Mod Compatibility Settings")
    @Config.Comment("Support for modded ores when using Ore Deletion Mode.")
    @Config.RequiresWorldRestart
    public static ConfigModCompat modCompat = new ConfigModCompat();
}
