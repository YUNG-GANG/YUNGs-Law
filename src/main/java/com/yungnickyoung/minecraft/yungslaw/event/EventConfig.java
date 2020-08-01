package com.yungnickyoung.minecraft.yungslaw.event;

import com.yungnickyoung.minecraft.yungslaw.YungsLaw;
import com.yungnickyoung.minecraft.yungslaw.config.YLSettings;
import com.yungnickyoung.minecraft.yungslaw.integration.Integrations;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventConfig {
    @SubscribeEvent
    public void onConfigReload(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(YLSettings.MOD_ID)) {
            ConfigManager.sync(YLSettings.MOD_ID, Config.Type.INSTANCE);
            Integrations.update();
            YungsLaw.configMap.clear();
        }
    }
}
