package org.hujohner.skintogglemod.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.util.Formatting;
import org.hujohner.skintogglemod.SkinToggleMod;

/**
 * HUD for visual representation of enabled skin parts.
 */
public class SkinToggleHudOverlay implements HudRenderCallback {
    private final int SIZE_X = 16;
    private final int SIZE_Y = 31;

    /**
     * Draws the silhouette of the player in the selected corner.
     * Each part is coloured red or green based on whether the respective
     * skin part is enabled.
     * @param drawContext the {@link DrawContext} instance
     * @param tickDelta Progress for linearly interpolating between the previous and current game state
     */
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if (SkinToggleMod.hud == SkinToggleMod.HudType.NONE) {
            return;
        }

        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            switch (SkinToggleMod.hud) {
                case TOP_RIGHT -> {
                    x = width - SIZE_X;
                }
                case BOTTOM_LEFT -> {
                    y = height - SIZE_Y;
                }
                case BOTTOM_RIGHT -> {
                    x = width - SIZE_X;
                    y = height - SIZE_Y;
                }
            }

            int red = Formatting.RED.getColorValue() | -16777216;
            int green = Formatting.GREEN.getColorValue() | -16777216;

            // head
            drawContext.fill(
                    x+4, y,
                    x+4 + 8, y + 8,
                    client.options.isPlayerModelPartEnabled(PlayerModelPart.HAT) ? green : red);

            // left arm
            drawContext.fill(
                    x, y+8,
                    x + 4, y+8 + 12,
                    client.options.isPlayerModelPartEnabled(PlayerModelPart.LEFT_SLEEVE) ? green : red);
            // right arm
            drawContext.fill(
                    x+12, y+8,
                    x+12 + 4, y+8 + 12,
                    client.options.isPlayerModelPartEnabled(PlayerModelPart.RIGHT_SLEEVE) ? green : red);

            // torso
            drawContext.fill(
                    x+4, y+8,
                    x+4 + 8, y+8 + 12,
                    client.options.isPlayerModelPartEnabled(PlayerModelPart.JACKET) ? green : red);

            // left leg
            drawContext.fill(
                    x+4, y+20,
                    x+4 + 4, y+20 + 11,
                    client.options.isPlayerModelPartEnabled(PlayerModelPart.LEFT_PANTS_LEG) ? green : red);
            // right leg
            drawContext.fill(
                    x+8, y+20,
                    x+8 + 4, y+20 + 11,
                    client.options.isPlayerModelPartEnabled(PlayerModelPart.RIGHT_PANTS_LEG) ? green : red);
        }
    }
}
