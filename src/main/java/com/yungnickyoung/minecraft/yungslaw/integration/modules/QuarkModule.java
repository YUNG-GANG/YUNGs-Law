package com.yungnickyoung.minecraft.yungslaw.integration.modules;

import com.yungnickyoung.minecraft.yungslaw.YungsLaw;
import com.yungnickyoung.minecraft.yungslaw.config.Configuration;
import net.minecraft.block.state.IBlockState;
import vazkii.quark.world.feature.Biotite;

import java.util.Objects;

public class QuarkModule extends CompatModule {
    public QuarkModule() {
        super("quark");
    }

    @Override
    public void enable() {
        super.enable();
        try {
            IBlockState biotite = Objects.requireNonNull(Biotite.biotite_ore.getDefaultState());
            this.addIfAbsentAndAble(this.ores, biotite);
        } catch (Exception e) {
            YungsLaw.LOGGER.error("Unable to register Quark Biotite for YUNG's Law! Is the Quark Biotite module enabled?");
            YungsLaw.LOGGER.error(e.toString());
        }
    }

    @Override
    public boolean shouldBeEnabled() {
        return super.shouldBeEnabled() && Configuration.modCompat.quark;
    }
}
