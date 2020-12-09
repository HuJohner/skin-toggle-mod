package org.hujohner.skintogglemod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.util.InputUtil;

@Environment(EnvType.CLIENT)
public class SkinToggleModClient implements ClientModInitializer {

    public final KeyBinding keyToggleCape;
    public final KeyBinding keyToggleTorso;
    public final KeyBinding keyToggleArmLeft;
    public final KeyBinding keyToggleArmRight;
    public final KeyBinding keyToggleLegLeft;
    public final KeyBinding keyToggleLegRight;
    public final KeyBinding keyToggleHead;

    public SkinToggleModClient() {
        keyToggleCape = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-cape", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
        keyToggleTorso = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-torso", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
        keyToggleArmLeft = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-arm-left", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
        keyToggleArmRight = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-arm-right", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
        keyToggleLegLeft = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-leg-left", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
        keyToggleLegRight = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-leg-right", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
        keyToggleHead = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-head", InputUtil.UNKNOWN_KEY.getCode(), "key.category.skin-customization"));
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyToggleCape.wasPressed()) {
                client.options.togglePlayerModelPart(PlayerModelPart.CAPE);
            }
            while (keyToggleTorso.wasPressed()) {
                client.options.togglePlayerModelPart(PlayerModelPart.JACKET);
            }
            while (keyToggleArmLeft.wasPressed()) {
                client.options.togglePlayerModelPart(PlayerModelPart.LEFT_SLEEVE);
            }
            while (keyToggleArmRight.wasPressed()) {
                client.options.togglePlayerModelPart(PlayerModelPart.RIGHT_SLEEVE);
            }
            while (keyToggleLegLeft.wasPressed()) {
                client.options.togglePlayerModelPart(PlayerModelPart.LEFT_PANTS_LEG);
            }
            while (keyToggleLegRight.wasPressed()) {
                client.options.togglePlayerModelPart(PlayerModelPart.RIGHT_PANTS_LEG);
            }
            while (keyToggleHead.wasPressed()) {
                client.options.togglePlayerModelPart(PlayerModelPart.HAT);
            }
        });
    }
}
