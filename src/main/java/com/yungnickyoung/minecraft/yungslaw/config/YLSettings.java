package com.yungnickyoung.minecraft.yungslaw.config;

/**
 * Settings and information for YUNG's Law.
 * All fields are {@code static}.
 *
 * For the user-facing configuration options, see the Configuration class.
 */
public class YLSettings {
    /** MOD INFORMATION CONSTANTS
     * These will not be used if USE_META_DATA is true. Instead, data will be used from resources/mcmod.info.
     * Strings like {@varName} are set from the gradle build script.
     */
    public static final boolean USE_META_DATA = true;
    public static final String MOD_ID = "{@modid}";
    public static final String NAME = "{@name}";
    public static final String VERSION = "{@version}";

    public static final String SERVER_PROXY = "com.yungnickyoung.minecraft.yungslaw.proxy.ServerProxy";
    public static final String CLIENT_PROXY = "com.yungnickyoung.minecraft.yungslaw.proxy.ClientProxy";

    public static final String CUSTOM_CONFIG_PATH = "yungslaw";
    public static final String BASE_CONFIG_NAME = "yungslaw-1_12_2";
    public static final String VERSION_PATH = "1_12_2";
}
