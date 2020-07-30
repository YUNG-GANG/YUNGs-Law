package com.yungnickyoung.minecraft.yungslaw;

import com.yungnickyoung.minecraft.yungslaw.config.ConfigHolder;
import com.yungnickyoung.minecraft.yungslaw.config.YLSettings;
import com.yungnickyoung.minecraft.yungslaw.event.EventWorld;
import com.yungnickyoung.minecraft.yungslaw.proxy.IProxy;
import com.yungnickyoung.minecraft.yungslaw.world.BlockGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Mod(modid = YLSettings.MOD_ID, name = YLSettings.NAME, version = YLSettings.VERSION, useMetadata = YLSettings.USE_META_DATA, acceptableRemoteVersions = "*")
public class YungsLaw {
    public static final Logger LOGGER = LogManager.getLogger(YLSettings.MOD_ID);

    @SidedProxy(clientSide = YLSettings.CLIENT_PROXY, serverSide = YLSettings.SERVER_PROXY)
    private static IProxy proxy;

    /** File referring to the overarching directory for custom dimension configs **/
    public static File customConfigDir;

    /** Map of dimension ID to config **/
    public static Map<Integer, ConfigHolder> configMap = new HashMap<>();

    public static BlockGenerator generator;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();

        // Register generator
        GameRegistry.registerWorldGenerator(generator = new BlockGenerator(), Integer.MAX_VALUE);

        // Create custom dimension config directory if doesn't already exist
        createDirectory();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventWorld());
    }

    private static void createDirectory() {
        File parentDir = new File(Loader.instance().getConfigDir().toString(), YLSettings.CUSTOM_CONFIG_PATH);
        customConfigDir = new File(parentDir, YLSettings.VERSION_PATH);
        try {
            String filePath = customConfigDir.getCanonicalPath();
            if (customConfigDir.mkdirs()) {
                LOGGER.info("Creating directory for dimension-specific YUNG's Law configs at {}", filePath);
            }
        } catch (IOException e) {
            LOGGER.error("ERROR creating YUNG's Law config directory.");
        }
    }}
