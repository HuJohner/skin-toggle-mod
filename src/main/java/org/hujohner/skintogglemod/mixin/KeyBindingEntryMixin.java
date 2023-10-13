package org.hujohner.skintogglemod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.ControlsListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import org.hujohner.skintogglemod.SkinToggleMod;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to the {@link ControlsListWidget.KeyBindingEntry} class to prevent error highlighting
 * on duplicate skin toggle key bindings.
 */
@Mixin(ControlsListWidget.KeyBindingEntry.class)
public class KeyBindingEntryMixin {
    @Final
    @Shadow
    private KeyBinding binding;
    @Final
    @Shadow
    private ButtonWidget editButton;
    @Shadow
    private boolean duplicate;

    @Unique
    private MinecraftClient cachedClient;

    /**
     * Overrides the get duplicate field call to prevent the red rectangle next to a duplicate key
     * bind, if it has only duplicates that belong to the skin toggle category.
     *
     * @param instance this
     * @return modified duplicate field
     */
    @Redirect(method = "render", at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/gui/screen/option/ControlsListWidget$KeyBindingEntry;duplicate:Z",
            opcode = Opcodes.GETFIELD))
    public boolean renderHook(ControlsListWidget.KeyBindingEntry instance) {
        if (isSkinToggleBinding(this.binding)) {
            return isDuplicate();
        }
        return duplicate;
    }

    /**
     * Changes the text on the button to prevent the red brackets around the key,
     * if the key binding has only duplicates that belong to the skin toggle category.
     *
     * @param ci callback info
     */
    @Inject(method = "update", at = @At(value = "TAIL"))
    public void updateHook(CallbackInfo ci) {
        if (isSkinToggleBinding(this.binding) && !isDuplicate()) {
            this.editButton.setMessage(this.binding.getBoundKeyLocalizedText());
        }
    }

    /**
     * @param keyBinding key bind to check
     * @return whether the key binding is in the skin toggle category
     */
    @Unique
    private boolean isSkinToggleBinding(KeyBinding keyBinding) {
        return SkinToggleMod.CATEGORY_ID.equals(keyBinding.getCategory());
    }

    /**
     * @return whether the key binding has a duplicate outside the skin toggle group
     */
    @Unique
    private boolean isDuplicate() {
        if (cachedClient == null) {
            cachedClient = MinecraftClient.getInstance();
        }

        if (!this.binding.isUnbound()) {
            KeyBinding[] allKeys = cachedClient.options.allKeys;
            for (KeyBinding keyBinding : allKeys) {
                if (!isSkinToggleBinding(keyBinding) && this.binding.equals(keyBinding)) {
                    return true;
                }
            }
        }
        return false;
    }
}
