package org.hujohner.skintogglemod.mixin;

import net.minecraft.client.Options;
import org.hujohner.skintogglemod.SkinToggleMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to the {@link Options} class to save additional options to the options file.
 */
@Mixin(Options.class)
public abstract class OptionsMixin {
    @Inject(method = "processOptions", at = @At(value = "TAIL"))
    public void acceptHook(Options.FieldAccess fieldAccess, CallbackInfo ci) {
        SkinToggleMod.announceToggle = fieldAccess.process(
            "skin_toggle_mod.announce_toggles",
            SkinToggleMod.announceToggle,
            SkinToggleMod.AnnounceType::byName,
            SkinToggleMod.AnnounceType::getName
        );
        SkinToggleMod.hud = fieldAccess.process(
                "skin_toggle_mod.hud",
                SkinToggleMod.hud,
                SkinToggleMod.HudType::byName,
                SkinToggleMod.HudType::getName
        );
    }
}
