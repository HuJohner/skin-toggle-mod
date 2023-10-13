package org.hujohner.skintogglemod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

/**
 * Client-side entry for SkinToggleMod registering HUD.
 */
@Environment(EnvType.CLIENT)
public class SkinToggleModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new SkinToggleHudOverlay());
    }
}
