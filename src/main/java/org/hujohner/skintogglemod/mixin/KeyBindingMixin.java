package org.hujohner.skintogglemod.mixin;

import net.fabricmc.fabric.mixin.client.keybinding.KeyBindingAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
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
            if (keyBinding instanceof KeyBindingAccessor) {
                InputUtil.Key key = ((KeyBindingAccessor) keyBinding).fabric_getBoundKey();
                List<KeyBinding> keyBindings = SkinToggleMod.KEY_TO_BINDINGS.getOrDefault(key, new ArrayList<>());
                keyBindings.add(keyBinding);
                SkinToggleMod.KEY_TO_BINDINGS.put(key, keyBindings);
            }
        });
    }

    @Inject(method = "onKeyPressed", at = @At(value = "HEAD"))
    private static void onKeyPressedHook(InputUtil.Key key, CallbackInfo ci) {
        if (SkinToggleMod.KEY_TO_BINDINGS.containsKey(key)) {
            MinecraftClient client = MinecraftClient.getInstance();

            // update skin toggles
            SkinToggleMod.KEY_TO_BINDINGS.get(key).forEach(keyBinding -> {
                PlayerModelPart part = SkinToggleMod.BINDING_TO_PART.get(keyBinding);
                client.options.togglePlayerModelPart(part, !client.options.isPlayerModelPartEnabled(part));
            });

            // announce toggles
            if (SkinToggleMod.announceToggle != SkinToggleMod.AnnounceType.NONE && client.player != null) {
                MutableText toggled = MutableText.of(new LiteralTextContent(""));
                for (Map.Entry<KeyBinding, PlayerModelPart> entry : SkinToggleMod.BINDING_TO_PART.entrySet()) {
                    if (!toggled.getString().isEmpty()) {
                        toggled.append(", ");
                    }
                    TextColor colour = TextColor.parse("red");
                    if (client.options.isPlayerModelPartEnabled(entry.getValue())) {
                        colour = TextColor.parse("green");
                    }
                    toggled.append(entry.getValue().getOptionName().copy()
                            .fillStyle(Style.EMPTY.withColor(colour)));
                }
                client.player.sendMessage(toggled, SkinToggleMod.announceToggle == SkinToggleMod.AnnounceType.ACTION_BAR);
            }
        }
    }
}
