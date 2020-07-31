package com.yungnickyoung.minecraft.yungslaw.init;

import com.yungnickyoung.minecraft.yungslaw.YungsLaw;
import com.yungnickyoung.minecraft.yungslaw.config.YLSettings;
import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.io.IOException;

public class ModConfig {
    public static void preinit() {
        // Create custom dimension config directory if doesn't already exist
        File parentDir = new File(Loader.instance().getConfigDir().toString(), YLSettings.CUSTOM_CONFIG_PATH);
        YungsLaw.customConfigDir = new File(parentDir, YLSettings.VERSION_PATH);
        try {
            String filePath = YungsLaw.customConfigDir.getCanonicalPath();
            if (YungsLaw.customConfigDir.mkdirs()) {
                YungsLaw.LOGGER.info("Creating directory for dimension-specific YUNG's Law configs at {}", filePath);
            }
        } catch (IOException e) {
            YungsLaw.LOGGER.error("ERROR creating YUNG's Law config directory.");
        }
    }
}
