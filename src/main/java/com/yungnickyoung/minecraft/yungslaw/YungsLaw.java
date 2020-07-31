package com.yungnickyoung.minecraft.yungslaw;

import com.yungnickyoung.minecraft.yungslaw.config.ConfigHolder;
import com.yungnickyoung.minecraft.yungslaw.config.YLSettings;
import com.yungnickyoung.minecraft.yungslaw.init.ModConfig;
import com.yungnickyoung.minecraft.yungslaw.init.ModWorld;
import com.yungnickyoung.minecraft.yungslaw.proxy.IProxy;
import com.yungnickyoung.minecraft.yungslaw.world.BlockGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Mod(modid = YLSettings.MOD_ID, name = YLSettings.NAME, version = YLSettings.VERSION, useMetadata = YLSettings.USE_META_DATA, acceptableRemoteVersions = "*")
public class YungsLaw {
    public static final Logger LOGGER = LogManager.getLogger(YLSettings.MOD_ID);

    @SidedProxy(clientSide = YLSettings.CLIENT_PROXY, serverSide = YLSettings.SERVER_PROXY)
    private static IProxy proxy;

    /**
     * File referring to the overarching directory for custom dimension configs
     **/
    public static File customConfigDir;

    /**
     * Map of dimension ID to config
     **/
    public static Map<Integer, ConfigHolder> configMap = new HashMap<>();

    public static BlockGenerator generator;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
        ModWorld.preInit();
        ModConfig.preinit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }
}
