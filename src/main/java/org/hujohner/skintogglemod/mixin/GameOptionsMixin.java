package org.hujohner.skintogglemod.mixin;

import net.minecraft.client.option.GameOptions;
import org.hujohner.skintogglemod.SkinToggleMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to the {@link GameOptions} class to save additional options to the options file.
 */
@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Inject(method = "accept", at = @At(value = "TAIL"))
    public void acceptHook(GameOptions.Visitor visitor, CallbackInfo ci) {
        SkinToggleMod.announceToggle = visitor.visitObject(
            "skin_toggle_mod.announce_toggles",
            SkinToggleMod.announceToggle,
            SkinToggleMod.AnnounceType::byName,
            SkinToggleMod.AnnounceType::getName
        );
        SkinToggleMod.hud = visitor.visitObject(
                "skin_toggle_mod.hud",
                SkinToggleMod.hud,
                SkinToggleMod.HudType::byName,
                SkinToggleMod.HudType::getName
        );
    }
}
