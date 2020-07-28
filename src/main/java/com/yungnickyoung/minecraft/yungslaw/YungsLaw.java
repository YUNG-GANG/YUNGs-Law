package com.yungnickyoung.minecraft.yungslaw;

import com.yungnickyoung.minecraft.yungslaw.proxy.IProxy;
import com.yungnickyoung.minecraft.yungslaw.world.BlockGenerator;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = YungsLaw.MODID, name = YungsLaw.NAME, version = YungsLaw.VERSION, useMetadata = true, acceptableRemoteVersions = "*")
public class YungsLaw {
    public static final String MODID = "{@modid}";
    public static final String NAME = "{@name}";
    public static final String VERSION = "{@version}";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "com.yungnickyoung.minecraft.yungslaw.proxy.ClientProxy", serverSide = "com.yungnickyoung.minecraft.yungslaw.proxy.ServerProxy")
    private static IProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new BlockGenerator(), Integer.MAX_VALUE);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
