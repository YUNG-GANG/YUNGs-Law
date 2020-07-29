package com.yungnickyoung.minecraft.yungslaw.proxy;

import com.yungnickyoung.minecraft.yungslaw.event.EventConfig;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy implements IProxy {
    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new EventConfig());
    }

    @Override
    public void init() {
    }

    @Override
    public void postInit() {
    }
}
