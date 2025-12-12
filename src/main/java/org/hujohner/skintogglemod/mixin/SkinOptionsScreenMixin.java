package org.hujohner.skintogglemod.mixin;

import org.hujohner.skintogglemod.SkinToggleMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.options.SkinCustomizationScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to the {@link SkinCustomizationScreen} class to add UI elements before other UI elements.
 */
@Mixin(SkinCustomizationScreen.class)
public abstract class SkinOptionsScreenMixin extends OptionsSubScreenMixin {
    @Inject(method = "addOptions", at = @At(value = "HEAD"))
    public void addButtons(CallbackInfo ci) {
        SkinCustomizationScreen instance = (SkinCustomizationScreen) (Object) this;

        List<AbstractWidget> list = new ArrayList<>();
        list.add(
            CycleButton.builder(value -> {
                    switch (value) {
                        case CHAT -> {
                            return Component.translatable("options.skinCustomisation.skin-toggle-mod.announceToggle.chat");
                        }
                        case ACTION_BAR -> {
                            return Component.translatable("options.skinCustomisation.skin-toggle-mod.announceToggle.actionbar");
                        }
                        default -> {
                            return Component.translatable("options.skinCustomisation.skin-toggle-mod.announceToggle.none");
                        }
                    }
                }, SkinToggleMod.announceToggle)
                .withValues(SkinToggleMod.AnnounceType.values())
                .create(
                        instance.width / 2 - 155,
                        instance.height / 6,
                        150,
                        20,
                        Component.translatable("options.skinCustomisation.skin-toggle-mod.announceToggle"),
                        (button, value) -> SkinToggleMod.announceToggle = value
                )
        );
        list.add(
                CycleButton.builder(value -> {
                            switch (value) {
                                case TOP_LEFT -> {
                                    return Component.translatable("options.skinCustomisation.skin-toggle-mod.hud.topleft");
                                }
                                case TOP_RIGHT -> {
                                    return Component.translatable("options.skinCustomisation.skin-toggle-mod.hud.topright");
                                }
                                case BOTTOM_LEFT -> {
                                    return Component.translatable("options.skinCustomisation.skin-toggle-mod.hud.bottomleft");
                                }
                                case BOTTOM_RIGHT -> {
                                    return Component.translatable("options.skinCustomisation.skin-toggle-mod.hud.bottomright");
                                }
                                default -> {
                                    return Component.translatable("options.skinCustomisation.skin-toggle-mod.hud.none");
                                }
                            }
                        }, SkinToggleMod.hud)
                        .withValues(SkinToggleMod.HudType.values())
                        .create(
                                instance.width / 2 - 155 + 160,
                                instance.height / 6,
                                150,
                                20,
                                Component.translatable("options.skinCustomisation.skin-toggle-mod.hud"),
                                (button, value) -> SkinToggleMod.hud = value
                        )
        );

        this.list.addSmall(list);
    }
}