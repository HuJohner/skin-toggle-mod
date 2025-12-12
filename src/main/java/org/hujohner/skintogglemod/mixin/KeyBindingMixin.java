package org.hujohner.skintogglemod.mixin;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.hujohner.skintogglemod.SkinToggleMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

/**
 * Mixin to the {@link KeyBinding} class to hook into the key press event.
 * Also hooks into the update event when keybindings change.
 */
@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin {
    @Inject(method = "updateKeysByCode", at = @At(value = "HEAD"))
    private static void updateHook(CallbackInfo ci) {
        SkinToggleMod.KEY_TO_BINDINGS.clear();
        SkinToggleMod.BINDING_TO_PART.keySet().forEach(keyBinding -> {
            InputUtil.Key key = KeyBindingHelper.getBoundKeyOf(keyBinding);
            List<KeyBinding> keyBindings = SkinToggleMod.KEY_TO_BINDINGS.getOrDefault(key, new ArrayList<>());
            keyBindings.add(keyBinding);
            SkinToggleMod.KEY_TO_BINDINGS.put(key, keyBindings);
        });
    }

    @Inject(method = "onKeyPressed", at = @At(value = "HEAD"))
    private static void onKeyPressedHook(InputUtil.Key key, CallbackInfo ci) {
        if (SkinToggleMod.KEY_TO_BINDINGS.containsKey(key)) {
            MinecraftClient client = MinecraftClient.getInstance();

            // update skin toggles
            MutableText toggled = Text.literal("");
            SkinToggleMod.KEY_TO_BINDINGS.get(key).forEach(keyBinding -> {
                PlayerModelPart part = SkinToggleMod.BINDING_TO_PART.get(keyBinding);
                client.options.setPlayerModelPart(part, !client.options.isPlayerModelPartEnabled(part));

                // announce toggles
                if (!toggled.getString().isEmpty()) {
                    toggled.append(", ");
                }
                TextColor colour = TextColor.fromFormatting(Formatting.RED);
                if (client.options.isPlayerModelPartEnabled(part)) {
                    colour = TextColor.fromFormatting(Formatting.GREEN);
                }
                toggled.append(part.getOptionName().copy()
                        .fillStyle(Style.EMPTY.withColor(colour)));
            });
            if (SkinToggleMod.announceToggle != SkinToggleMod.AnnounceType.NONE && client.player != null) {
                client.player.sendMessage(toggled, SkinToggleMod.announceToggle == SkinToggleMod.AnnounceType.ACTION_BAR);
            }
        }
    }
}
