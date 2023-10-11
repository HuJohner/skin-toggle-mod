package org.hujohner.skintogglemod.mixin;

import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameOptionsScreen.class)
public abstract class GameOptionsScreenMixin extends ScreenMixin {
}
