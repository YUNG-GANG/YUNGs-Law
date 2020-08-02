package com.yungnickyoung.minecraft.yungslaw.proxy;

import com.yungnickyoung.minecraft.yungslaw.init.ModCompat;
import com.yungnickyoung.minecraft.yungslaw.init.ModConfig;
import com.yungnickyoung.minecraft.yungslaw.init.ModWorld;

public class Proxy {
    public void preInit() {
        ModWorld.preInit();
        ModConfig.preinit();
    }

    public void init() {
        ModWorld.init();
    }

    public  void postInit() {
        ModCompat.postInit();
    }
}
