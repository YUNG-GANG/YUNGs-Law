package com.yungnickyoung.minecraft.yungslaw.config;

import net.minecraftforge.common.config.Config;

public class ModCompatConfig {
    @Config.Name("Quark")
    @Config.Comment("If enabled, Quark ores will be deleted when using Ore Deletion Mode.")
    @Config.RequiresWorldRestart
    public boolean quark = true;

    @Config.Name("Applied Energistics 2")
    @Config.Comment("If enabled, AE2 ores will be deleted when using Ore Deletion Mode.")
    @Config.RequiresWorldRestart
    public boolean ae2 = true;
}
