package com.yungnickyoung.minecraft.yungslaw.init;

import com.yungnickyoung.minecraft.yungslaw.YungsLaw;
import com.yungnickyoung.minecraft.yungslaw.config.YLSettings;
import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModConfig {
    public static void preinit() {
        createCustomConfigDir();
        createReadMe();
    }

    private static void createCustomConfigDir() {
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


    private static void createReadMe() {
        Path path = Paths.get(Loader.instance().getConfigDir().toString(), YLSettings.CUSTOM_CONFIG_PATH, "README.txt");
        File readme = new File(path.toString());
        if (!readme.exists()) {
            String readmeText =
                "This directory is for adding YUNG's Law configurations specific to certain dimensions.\n" +
                    "For more information, please see the wiki:\n" +
                    "https://github.com/yungnickyoung/YUNGs-Law/wiki";
            try {
                Files.write(path, readmeText.getBytes());
            } catch (IOException e) {
                YungsLaw.LOGGER.error("Unable to create README file!");
            }
        }
    }
}
