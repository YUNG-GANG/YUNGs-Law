package com.yungnickyoung.minecraft.yungslaw.config.util;

import com.yungnickyoung.minecraft.yungslaw.config.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds all the config values specific to a single dimension.
 * By default, a dimension's values are equivalent to the global configuration found in
 * the base config file. This lets users only specify the options they want to override
 * for each dimension.
 */
public class ConfigHolder {
    /** Map of full names to ConfigOptions. Holds all config options.  */
    public Map<String, ConfigOption<?>> properties = new HashMap<>();

    /**
     * Constructor loads in default global values for all vars.
     * If a config file for a specific dimension is present, its values will override the ones loaded
     * in here when the config file is loaded via the ConfigLoader.
     */
    public ConfigHolder() {
        genDistance = new ConfigOption<>("Generation Distance", Configuration.replacementSettings.genDistance)
            .setCategory("general.Replacement Settings")
            .addToMap(properties);
        hardBlock = new ConfigOption<>("Hard Block", Configuration.replacementSettings.replacementMode.hardBlock)
            .setCategory("general.Replacement Settings.Replacement Mode Settings")
            .addToMap(properties);
        safeBlocks = new ConfigOption<>("Safe Blocks", Configuration.replacementSettings.safeBlocks)
            .setCategory("general.Replacement Settings")
            .addToMap(properties);
        untouchableBlocks = new ConfigOption<>("Untouchable Blocks", Configuration.replacementSettings.untouchableBlocks)
            .setCategory("general.Replacement Settings")
            .addToMap(properties);
        maxAltitude = new ConfigOption<>("Max Altitude", Configuration.replacementSettings.maxAltitude)
            .setCategory("general.Replacement Settings")
            .addToMap(properties);
        enableLiquidSafety = new ConfigOption<>("Mark Liquids As Safe", Configuration.replacementSettings.enableLiquidSafety)
            .setCategory("general.Replacement Settings")
            .addToMap(properties);
        enableOreDeletion = new ConfigOption<>("Enable Ore Deletion Mode", Configuration.replacementSettings.enableOreDeletion)
            .setCategory("general.Replacement Settings")
            .addToMap(properties);
        oreWhitelist = new ConfigOption<>("Ore Block Whitelist", Configuration.replacementSettings.oreMode.oreWhitelist)
            .setCategory("general.Replacement Settings.Ore Deletion Mode Settings")
            .addToMap(properties);
    }

    public ConfigOption<Integer>  genDistance;
    public ConfigOption<String>   hardBlock;
    public ConfigOption<String[]> safeBlocks;
    public ConfigOption<String[]> untouchableBlocks;
    public ConfigOption<Integer>  maxAltitude;
    public ConfigOption<Boolean>  enableLiquidSafety;
    public ConfigOption<Boolean>  enableOreDeletion;
    public ConfigOption<String[]> oreWhitelist;
}
