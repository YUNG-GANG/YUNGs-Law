package com.yungnickyoung.minecraft.yungslaw.integration;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.yungslaw.integration.modules.AE2Module;
import com.yungnickyoung.minecraft.yungslaw.integration.modules.CompatModule;
import com.yungnickyoung.minecraft.yungslaw.integration.modules.QuarkModule;
import net.minecraft.block.state.IBlockState;

import java.util.Collection;
import java.util.List;

public class Integrations {
    // List of supported modules
    public static QuarkModule QUARK = new QuarkModule();
    public static AE2Module AE2 = new AE2Module();

    public static List<CompatModule> MODULES = Lists.newArrayList(
        QUARK,
        AE2
    );

    /** Collection of all ores from enabled and successfully loaded mods **/
    public static List<IBlockState> ORES = Lists.newArrayList();

    /**
     * Update all modules' status and all internal lists (e.g. ores).
     */
    public static void update() {
        // Update mods enabled/disabled status in case config settings have changed
        MODULES.forEach(module -> {
            if (module.shouldBeEnabled() && !module.isEnabled()) module.enable();  // Enable
            if (!module.shouldBeEnabled() && module.isEnabled()) module.disable(); // Disable
        });

        // Add ores for enabled mods
        MODULES.stream().filter(CompatModule::isEnabled).forEach(module -> addIfAbsent(ORES, module.ores));

        // Remove ores for disabled mods
        MODULES.stream().filter(module -> !module.isEnabled()).forEach(module -> ORES.removeAll(module.ores));
    }

    private static <T> void addIfAbsent(List<T> list, T item) {
        if (!list.contains(item)) list.add(item);
    }

    private static <T> void addIfAbsent(List<T> list, Collection<T> items) {
        items.forEach(item -> addIfAbsent(list, item));
    }
}
