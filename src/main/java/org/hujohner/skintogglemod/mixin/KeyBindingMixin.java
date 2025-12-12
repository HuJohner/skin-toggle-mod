package org.hujohner.skintogglemod.mixin;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.hujohner.skintogglemod.SkinToggleMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.*;

/**
 * Mixin to the {@link KeyMapping} class to hook into the key press event.
 * Also hooks into the update event when keybindings change.
 */
@Mixin(KeyMapping.class)
public abstract class KeyBindingMixin {
    @Inject(method = "resetMapping", at = @At(value = "HEAD"))
    private static void updateHook(CallbackInfo ci) {
        SkinToggleMod.KEY_TO_BINDINGS.clear();
        SkinToggleMod.BINDING_TO_PART.keySet().forEach(keyBinding -> {
            InputConstants.Key key = KeyBindingHelper.getBoundKeyOf(keyBinding);
            List<KeyMapping> keyBindings = SkinToggleMod.KEY_TO_BINDINGS.getOrDefault(key, new ArrayList<>());
            keyBindings.add(keyBinding);
            SkinToggleMod.KEY_TO_BINDINGS.put(key, keyBindings);
        });
    }

    @Inject(method = "click", at = @At(value = "HEAD"))
    private static void onKeyPressedHook(InputConstants.Key key, CallbackInfo ci) {
        if (SkinToggleMod.KEY_TO_BINDINGS.containsKey(key)) {
            Minecraft client = Minecraft.getInstance();

            // update skin toggles
            MutableComponent toggled = Component.literal("");
            SkinToggleMod.KEY_TO_BINDINGS.get(key).forEach(keyBinding -> {
                PlayerModelPart part = SkinToggleMod.BINDING_TO_PART.get(keyBinding);
                client.options.setModelPart(part, !client.options.isModelPartEnabled(part));

                // announce toggles
                if (!toggled.getString().isEmpty()) {
                    toggled.append(", ");
                }
                TextColor colour = TextColor.fromLegacyFormat(ChatFormatting.RED);
                if (client.options.isModelPartEnabled(part)) {
                    colour = TextColor.fromLegacyFormat(ChatFormatting.GREEN);
                }
                toggled.append(part.getName().copy()
                        .withStyle(Style.EMPTY.withColor(colour)));
            });
            client.options.broadcastOptions();
            if (SkinToggleMod.announceToggle != SkinToggleMod.AnnounceType.NONE && client.player != null) {
                client.player.displayClientMessage(toggled, SkinToggleMod.announceToggle == SkinToggleMod.AnnounceType.ACTION_BAR);
            }
        }
    }
}
