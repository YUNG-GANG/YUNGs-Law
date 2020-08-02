package com.yungnickyoung.minecraft.yungslaw.config;

import net.minecraftforge.common.config.Config;

public class ConfigReplacementMode {
    @Config.Name("Hard Block")
    @Config.Comment("The block that will replace the underground in areas far from Safe Blocks. Defaults to obsidian if value provided is invalid.")
    @Config.RequiresWorldRestart
    public String hardBlock = "minecraft:obsidian";
}
