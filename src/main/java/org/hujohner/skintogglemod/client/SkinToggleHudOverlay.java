package org.hujohner.skintogglemod.client;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.hujohner.skintogglemod.SkinToggleMod;

/**
 * HUD for visual representation of enabled skin parts.
 */
public class SkinToggleHudOverlay implements HudElement {
    private final int SIZE_X = 16;
    private final int SIZE_Y = 31;

    /**
     * Draws the silhouette of the player in the selected corner.
     * Each part is coloured red or green based on whether the respective
     * skin part is enabled.
     * @param context the {@link GuiGraphics} instance
     * @param tickCounter Progress for linearly interpolating between the previous and current game state
     */
    @Override
    public void render(GuiGraphics context, DeltaTracker tickCounter) {
        if (SkinToggleMod.hud == SkinToggleMod.HudType.NONE) {
            return;
        }

        int x = 0;
        int y = 0;
        Minecraft client = Minecraft.getInstance();
        if (client != null) {
            int width = client.getWindow().getGuiScaledWidth();
            int height = client.getWindow().getGuiScaledHeight();

            switch (SkinToggleMod.hud) {
                case TOP_RIGHT -> x = width - SIZE_X;
                case BOTTOM_LEFT -> y = height - SIZE_Y;
                case BOTTOM_RIGHT -> {
                    x = width - SIZE_X;
                    y = height - SIZE_Y;
                }
            }

            int red = ChatFormatting.RED.getColor() | -16777216;
            int green = ChatFormatting.GREEN.getColor() | -16777216;

            // head
            context.fill(
                    x+4, y,
                    x+4 + 8, y + 8,
                    client.options.isModelPartEnabled(PlayerModelPart.HAT) ? green : red);

            // left arm
            context.fill(
                    x, y+8,
                    x + 4, y+8 + 12,
                    client.options.isModelPartEnabled(PlayerModelPart.LEFT_SLEEVE) ? green : red);
            // right arm
            context.fill(
                    x+12, y+8,
                    x+12 + 4, y+8 + 12,
                    client.options.isModelPartEnabled(PlayerModelPart.RIGHT_SLEEVE) ? green : red);

            // torso
            context.fill(
                    x+4, y+8,
                    x+4 + 8, y+8 + 12,
                    client.options.isModelPartEnabled(PlayerModelPart.JACKET) ? green : red);

            // left leg
            context.fill(
                    x+4, y+20,
                    x+4 + 4, y+20 + 11,
                    client.options.isModelPartEnabled(PlayerModelPart.LEFT_PANTS_LEG) ? green : red);
            // right leg
            context.fill(
                    x+8, y+20,
                    x+8 + 4, y+20 + 11,
                    client.options.isModelPartEnabled(PlayerModelPart.RIGHT_PANTS_LEG) ? green : red);
        }
    }
}
