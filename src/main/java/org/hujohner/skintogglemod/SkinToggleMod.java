package org.hujohner.skintogglemod;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerModelPart;
import java.util.List;
import java.util.Map;

/**
 * Entry for SkinToggleMod creating keybindings.
 */
public class SkinToggleMod implements ModInitializer {
    public static final String CATEGORY_ID = "key.category.skin-customization";

    public static final Map<KeyMapping, PlayerModelPart> BINDING_TO_PART = Maps.newHashMap();
    public static final Map<InputConstants.Key, List<KeyMapping>> KEY_TO_BINDINGS = Maps.newHashMap();

    public static AnnounceType announceToggle = AnnounceType.ACTION_BAR;
    public static HudType hud = HudType.TOP_LEFT;

    public KeyMapping.Category category;
    public KeyMapping keyToggleCape;
    public KeyMapping keyToggleTorso;
    public KeyMapping keyToggleArmLeft;
    public KeyMapping keyToggleArmRight;
    public KeyMapping keyToggleLegLeft;
    public KeyMapping keyToggleLegRight;
    public KeyMapping keyToggleHead;

    @Override
    public void onInitialize() {
        category = KeyMapping.Category.register(Identifier.parse(CATEGORY_ID));

        keyToggleCape = KeyBindingHelper.registerKeyBinding(
                new KeyMapping("key.skin-toggle-mod.skin-custom-cape", InputConstants.UNKNOWN.getValue(), category));
        BINDING_TO_PART.put(keyToggleCape, PlayerModelPart.CAPE);

        keyToggleTorso = KeyBindingHelper.registerKeyBinding(
                new KeyMapping("key.skin-toggle-mod.skin-custom-torso", InputConstants.UNKNOWN.getValue(), category));
        BINDING_TO_PART.put(keyToggleTorso, PlayerModelPart.JACKET);

        keyToggleArmLeft = KeyBindingHelper.registerKeyBinding(
                new KeyMapping("key.skin-toggle-mod.skin-custom-arm-left", InputConstants.UNKNOWN.getValue(), category));
        BINDING_TO_PART.put(keyToggleArmLeft, PlayerModelPart.LEFT_SLEEVE);

        keyToggleArmRight = KeyBindingHelper.registerKeyBinding(
                new KeyMapping("key.skin-toggle-mod.skin-custom-arm-right", InputConstants.UNKNOWN.getValue(), category));
        BINDING_TO_PART.put(keyToggleArmRight, PlayerModelPart.RIGHT_SLEEVE);

        keyToggleLegLeft = KeyBindingHelper.registerKeyBinding(
                new KeyMapping("key.skin-toggle-mod.skin-custom-leg-left", InputConstants.UNKNOWN.getValue(), category));
        BINDING_TO_PART.put(keyToggleLegLeft, PlayerModelPart.LEFT_PANTS_LEG);

        keyToggleLegRight = KeyBindingHelper.registerKeyBinding(
                new KeyMapping("key.skin-toggle-mod.skin-custom-leg-right", InputConstants.UNKNOWN.getValue(), category));
        BINDING_TO_PART.put(keyToggleLegRight, PlayerModelPart.RIGHT_PANTS_LEG);

        keyToggleHead = KeyBindingHelper.registerKeyBinding(
                new KeyMapping("key.skin-toggle-mod.skin-custom-head", InputConstants.UNKNOWN.getValue(), category));
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

    /**
     * Options for HUD positions.
     * <li>NONE: no HUD</li>
     * <li>TOP_LEFT: top left</li>
     * <li>TOP_RIGHT: top right</li>
     * <li>BOTTOM_LEFT: bottom left</li>
     * <li>BOTTOM_RIGHT: bottom right</li>
     */
    public enum HudType {
        NONE("none"),
        TOP_LEFT("topleft"),
        TOP_RIGHT("topright"),
        BOTTOM_LEFT("bottomleft"),
        BOTTOM_RIGHT("bottomright");

        private final String name;

        HudType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static HudType byName(String name) {
            for (HudType hudType : values()) {
                if (hudType.name.equals(name)) {
                    return hudType;
                }
            }

            return NONE;
        }
    }
}
