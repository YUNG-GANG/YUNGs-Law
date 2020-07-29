package com.yungnickyoung.minecraft.yungslaw.config;

public class YLConfig {
    public static void bake() {
        genDistance = Configuration.genDistance;
        hardBlock = Configuration.hardBlock;
        maxAltitude = Configuration.maxAltitude;
        safeBlocks = Configuration.safeBlocks;
        untouchableBlocks = Configuration.untouchableBlocks;
        enableLiquidSafety = Configuration.enableLiquidSafety;
        whitelistedDimensionIDs = Configuration.whitelistedDimensionIDs;
        enableGlobalWhitelist = Configuration.enableGlobalWhitelist;
    }

    public static int genDistance = Configuration.genDistance;
    public static String hardBlock = Configuration.hardBlock;
    public static int maxAltitude = Configuration.maxAltitude;
    public static String[] safeBlocks = Configuration.safeBlocks;
    public static String[] untouchableBlocks = Configuration.untouchableBlocks;
    public static boolean enableLiquidSafety = Configuration.enableLiquidSafety;
    public static int[] whitelistedDimensionIDs = Configuration.whitelistedDimensionIDs;
    public static boolean enableGlobalWhitelist = Configuration.enableGlobalWhitelist;
}
