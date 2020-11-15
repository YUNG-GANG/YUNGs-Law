package com.yungnickyoung.minecraft.yungslaw.integration.modules;

import appeng.api.AEApi;
import appeng.api.definitions.IBlocks;
import com.yungnickyoung.minecraft.yungslaw.YungsLaw;
import com.yungnickyoung.minecraft.yungslaw.config.Configuration;

public class AE2Module extends CompatModule {
    public AE2Module() {
        super("appliedenergistics2");
    }

    @Override
    public void enable() {
        super.enable();
        try {
            IBlocks blocks = AEApi.instance().definitions().blocks();
            blocks.quartzOre().maybeBlock().ifPresent(b -> this.addIfAbsent(this.ores, b.getDefaultState()));
            blocks.quartzOreCharged().maybeBlock().ifPresent(b -> this.addIfAbsent(this.ores, b.getDefaultState()));
        } catch (Exception e) {
            YungsLaw.LOGGER.error("Unable to enable AE2 compat for YUNG's Law!");
            YungsLaw.LOGGER.error(e.toString());
        }
    }

    @Override
    public boolean shouldBeEnabled() {
        return super.shouldBeEnabled() && Configuration.modCompat.ae2;
    }
}
