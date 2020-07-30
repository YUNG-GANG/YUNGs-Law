package com.yungnickyoung.minecraft.yungslaw.event;

import com.yungnickyoung.minecraft.yungslaw.YungsLaw;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventWorld {
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        YungsLaw.configMap.remove(event.getWorld().provider.getDimension());
    }
}
