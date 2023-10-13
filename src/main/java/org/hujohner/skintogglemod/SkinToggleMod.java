package org.hujohner.skintogglemod;

import com.google.common.collect.Maps;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.util.InputUtil;

import java.util.List;
import java.util.Map;

/**
 * Entry for SkinToggleMod creating keybindings.
 */
public class SkinToggleMod implements ModInitializer {
    public static final String CATEGORY_ID = "key.category.skin-customization";

    public static final Map<KeyBinding, PlayerModelPart> BINDING_TO_PART = Maps.newHashMap();
    public static final Map<InputUtil.Key, List<KeyBinding>> KEY_TO_BINDINGS = Maps.newHashMap();

    public static AnnounceType announceToggle = AnnounceType.ACTION_BAR;

    public KeyBinding keyToggleCape;
    public KeyBinding keyToggleTorso;
    public KeyBinding keyToggleArmLeft;
    public KeyBinding keyToggleArmRight;
    public KeyBinding keyToggleLegLeft;
    public KeyBinding keyToggleLegRight;
    public KeyBinding keyToggleHead;

    @Override
    public void onInitialize() {
        keyToggleCape = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-cape", InputUtil.UNKNOWN_KEY.getCode(), CATEGORY_ID));
        BINDING_TO_PART.put(keyToggleCape, PlayerModelPart.CAPE);

        keyToggleTorso = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-torso", InputUtil.UNKNOWN_KEY.getCode(), CATEGORY_ID));
        BINDING_TO_PART.put(keyToggleTorso, PlayerModelPart.JACKET);

        keyToggleArmLeft = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-arm-left", InputUtil.UNKNOWN_KEY.getCode(), CATEGORY_ID));
        BINDING_TO_PART.put(keyToggleArmLeft, PlayerModelPart.LEFT_SLEEVE);

        keyToggleArmRight = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-arm-right", InputUtil.UNKNOWN_KEY.getCode(), CATEGORY_ID));
        BINDING_TO_PART.put(keyToggleArmRight, PlayerModelPart.RIGHT_SLEEVE);

        keyToggleLegLeft = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-leg-left", InputUtil.UNKNOWN_KEY.getCode(), CATEGORY_ID));
        BINDING_TO_PART.put(keyToggleLegLeft, PlayerModelPart.LEFT_PANTS_LEG);

        keyToggleLegRight = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-leg-right", InputUtil.UNKNOWN_KEY.getCode(), CATEGORY_ID));
        BINDING_TO_PART.put(keyToggleLegRight, PlayerModelPart.RIGHT_PANTS_LEG);

        keyToggleHead = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.skin-toggle-mod.skin-custom-head", InputUtil.UNKNOWN_KEY.getCode(), CATEGORY_ID));
        BINDING_TO_PART.put(keyToggleHead, PlayerModelPart.HAT);
    }

    /**
     * Options for announcing toggles.
     * <li>NONE: no announcement</li>
     * <li>CHAT: announcement to chat</li>
     * <li>ACTION_BAR: announcement to action bar</li>
     */
    public enum AnnounceType {
        NONE("none"),
        CHAT("chat"),
        ACTION_BAR("actionbar");

        private final String name;

        AnnounceType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static AnnounceType byName(String name) {
            for (AnnounceType announceType : values()) {
                if (announceType.name.equals(name)) {
                    return announceType;
                }
            }

            return NONE;
        }
    }
}
