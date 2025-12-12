package org.hujohner.skintogglemod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.resources.Identifier;

/**
 * Client-side entry for SkinToggleMod registering HUD.
 */
@Environment(EnvType.CLIENT)
public class SkinToggleModClient implements ClientModInitializer {
    public static final String HUD_ID = "hud.skin-customization";
    @Override
    public void onInitializeClient() {
        HudElementRegistry.addFirst(Identifier.parse(HUD_ID), new SkinToggleHudOverlay());
    }
}
