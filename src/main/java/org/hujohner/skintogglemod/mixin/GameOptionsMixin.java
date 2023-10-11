package org.hujohner.skintogglemod.mixin;

import net.minecraft.client.option.GameOptions;
import org.hujohner.skintogglemod.client.SkinToggleModClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Inject(method = "accept", at = @At(value = "TAIL"))
    public void acceptHook(GameOptions.Visitor visitor, CallbackInfo ci) {
        SkinToggleModClient.announceToggle = visitor.visitObject(
            "skin_toggle_mod.announce_toggles",
            SkinToggleModClient.announceToggle,
            SkinToggleModClient.AnnounceType::byName,
            SkinToggleModClient.AnnounceType::getName
        );
    }
}
