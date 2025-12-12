package org.hujohner.skintogglemod.mixin;

import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Mixin to the {@link OptionsSubScreen} class to access the OptionsList list field.
 */
@Mixin(OptionsSubScreen.class)
public abstract class OptionsSubScreenMixin {
    @Shadow
    protected OptionsList list;
}