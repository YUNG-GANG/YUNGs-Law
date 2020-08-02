package com.yungnickyoung.minecraft.yungslaw.config;

import net.minecraftforge.common.config.Config;

public class ConfigOreMode {
    @Config.Name("Ore Block Whitelist")
    @Config.Comment(
        "Whitelist for designating blocks as ore blocks for Ore Deletion Mode. Useful if some modded ores you're using aren't already supported, \n" +
        "or if you perhaps only want to remove some of a mod's ores, but not all."
    )
    @Config.RequiresWorldRestart
    public String[] oreWhitelist = {
        "minecraft:coal_ore",
        "minecraft:iron_ore",
        "minecraft:gold_ore",
        "minecraft:lapis_ore",
        "minecraft:redstone_ore",
        "minecraft:emerald_ore",
        "minecraft:diamond_ore",
        "minecraft:quartz_ore",
    };
}
