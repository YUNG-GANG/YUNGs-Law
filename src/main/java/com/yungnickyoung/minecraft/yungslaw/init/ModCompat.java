package com.yungnickyoung.minecraft.yungslaw.init;

import com.yungnickyoung.minecraft.yungslaw.integration.Integrations;

public class ModCompat {
    public static void postInit() {
        Integrations.update();
    }
}
