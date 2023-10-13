package org.hujohner.skintogglemod.mixin;

import net.minecraft.client.gui.screen.option.SkinOptionsScreen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;
import org.hujohner.skintogglemod.SkinToggleMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Mixin to the {@link SkinOptionsScreen} class to add UI elements before other UI elements.
 */
@Mixin(SkinOptionsScreen.class)
public abstract class SkinOptionsScreenMixin extends ScreenMixin {
    @ModifyVariable(method = "init", at = @At(value = "STORE"), ordinal = 0)
    public int addButton(int i) {
        SkinOptionsScreen instance = (SkinOptionsScreen) (Object) this;

        // add button in first line
        this.addDrawableChild(
            CyclingButtonWidget.builder(value -> {
                    switch ((SkinToggleMod.AnnounceType)value) {
                        case CHAT -> {
                            return Text.translatable("options.skinCustomisation.skin-toggle-mod.announceToggle.chat");
                        }
                        case ACTION_BAR -> {
                            return Text.translatable("options.skinCustomisation.skin-toggle-mod.announceToggle.actionbar");
                        }
                        default -> {
                            return Text.translatable("options.skinCustomisation.skin-toggle-mod.announceToggle.none");
                        }
                    }
                })
                .values((Object[])SkinToggleMod.AnnounceType.values())
                .initially(SkinToggleMod.announceToggle)
                .build(
                        instance.width / 2 - 100,
                        instance.height / 6,
                        200,
                        20,
                        Text.translatable("options.skinCustomisation.skin-toggle-mod.announceToggle"),
                        (button, value) -> SkinToggleMod.announceToggle = (SkinToggleMod.AnnounceType)value
                )
        );

        return 2; // start i from 2 to skip first line
    }
}