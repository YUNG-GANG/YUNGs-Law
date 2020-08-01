package com.yungnickyoung.minecraft.yungslaw.integration.modules;

import com.yungnickyoung.minecraft.yungslaw.config.Configuration;
import vazkii.quark.world.feature.Biotite;

public class QuarkModule extends CompatModule {
    public QuarkModule() {
        super("quark");
    }

    @Override
    public void enable() {
        super.enable();
        this.addIfAbsent(this.ores, Biotite.biotite_ore.getDefaultState());
    }

    @Override
    public boolean shouldBeEnabled() {
        return super.shouldBeEnabled() && Configuration.modCompat.quark;
    }
}
