package org.hujohner.skintogglemod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.util.InputUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class SkinToggleModClient implements ClientModInitializer {

    public final KeyBinding keyToggleCape;
    public final KeyBinding keyToggleTorso;
    public final KeyBinding keyToggleArmLeft;
    public final KeyBinding keyToggleArmRight;
    public final KeyBinding keyToggleLegLeft;
    public final KeyBinding keyToggleLegRight;
    public final KeyBinding keyToggleHead;
    public final Map<KeyBinding, PlayerModelPart> keysSkinToggle;

    public SkinToggleModClient() {
        keysSkinToggle = new HashMap<>();
        keyToggleCape = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-cape", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
        keysSkinToggle.put(keyToggleCape, PlayerModelPart.CAPE);
        keyToggleTorso = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-torso", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
        keysSkinToggle.put(keyToggleTorso, PlayerModelPart.JACKET);
        keyToggleArmLeft = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-arm-left", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
        keysSkinToggle.put(keyToggleArmLeft, PlayerModelPart.LEFT_SLEEVE);
        keyToggleArmRight = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-arm-right", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
        keysSkinToggle.put(keyToggleArmRight, PlayerModelPart.RIGHT_SLEEVE);
        keyToggleLegLeft = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-leg-left", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
        keysSkinToggle.put(keyToggleLegLeft, PlayerModelPart.LEFT_PANTS_LEG);
        keyToggleLegRight = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-leg-right", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
        keysSkinToggle.put(keyToggleLegRight, PlayerModelPart.RIGHT_PANTS_LEG);
        keyToggleHead = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-head", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
        keysSkinToggle.put(keyToggleHead, PlayerModelPart.HAT);
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            Map<InputUtil.Key, List<KeyBinding>> multiBinding = new HashMap<>();
            for (KeyBinding kb : keysSkinToggle.keySet()) {
                multiBinding.computeIfAbsent(InputUtil.fromTranslationKey(kb.getBoundKeyTranslationKey()), l -> new ArrayList<>()).add(kb);
            }

            for (KeyBinding kb : keysSkinToggle.keySet()) {
                if (kb.wasPressed()) {
                    for (KeyBinding key : multiBinding.get(InputUtil.fromTranslationKey(kb.getBoundKeyTranslationKey()))) {
                        PlayerModelPart part = keysSkinToggle.get(key);
                        boolean isPartEnabled = client.options.isPlayerModelPartEnabled(part);
                        client.options.togglePlayerModelPart(part, !isPartEnabled);
                    }
                }
            }
        });
    }
}
