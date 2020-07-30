package com.yungnickyoung.minecraft.yungslaw.config;

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
        genDistance = new ConfigOption<>("Generation Distance", Configuration.genDistance)
            .setCategory("general")
            .addToMap(properties);
        hardBlock = new ConfigOption<>("Hard Block", Configuration.hardBlock)
            .setCategory("general")
            .addToMap(properties);
        safeBlocks = new ConfigOption<>("Safe Blocks", Configuration.safeBlocks)
            .setCategory("general")
            .addToMap(properties);
        untouchableBlocks = new ConfigOption<>("Untouchable Blocks", Configuration.untouchableBlocks)
            .setCategory("general")
            .addToMap(properties);
        maxAltitude = new ConfigOption<>("Max Altitude", Configuration.maxAltitude)
            .setCategory("general")
            .addToMap(properties);
        enableLiquidSafety = new ConfigOption<>("Mark Liquids As Safe", Configuration.enableLiquidSafety)
            .setCategory("general")
            .addToMap(properties);
    }

    public ConfigOption<Integer>  genDistance;
    public ConfigOption<String>   hardBlock;
    public ConfigOption<String[]> safeBlocks;
    public ConfigOption<String[]> untouchableBlocks;
    public ConfigOption<Integer>  maxAltitude;
    public ConfigOption<Boolean>  enableLiquidSafety;

    public static class ConfigOption<T> {
        public String name;
        public String fullName;
        public Class<?> type;
        private T value;
        private boolean hidden = false;
        private String category = "";

        public ConfigOption(String name, T value) {
            this.name = name;
            this.value = value;
            this.type = value.getClass();
        }

        public T get() {
            return value;
        }

        @SuppressWarnings("unchecked")
        public void set(Object value) {
            this.value = (T)value;
        }

        public ConfigOption<T> hidden() {
            this.hidden = true;
            return this;
        }

        public ConfigOption<T> setCategory(String category) {
            this.category = category;
            this.fullName = category + "." + name;
            return this;
        }

        public ConfigOption<T> addToMap(Map<String, ConfigOption<?>> map) {
            map.put(fullName, this);
            return this;
        }
    }
}
