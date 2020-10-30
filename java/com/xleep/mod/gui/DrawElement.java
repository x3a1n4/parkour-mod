package com.xleep.mod.gui;

import com.xleep.mod.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;

import static com.xleep.mod.gui.MainGUI.hexToInt;

public class DrawElement {
    static Minecraft mc = Minecraft.getMinecraft();
    static int width;
    static int height;
    static FontRenderer renderer = mc.fontRendererObj;

    static int backgroundColor = hexToInt(Config.textBackground);
    static int keyTextColor = hexToInt(Config.keyTextColor);
    static int keyTextPressedColor = hexToInt(Config.keyPressedTextColor);
    static int keyBackgroundColor = hexToInt(Config.keyBackgroundColor);
    static int keyBackgroundPressedColor = hexToInt(Config.keyPressedBackgroundColor);

    //update variables
    //this runs every time there's a RenderGameOverlayEvent?
    public static void renderOverlay(RenderGameOverlayEvent event){
        ScaledResolution sr = new ScaledResolution(mc);
        width = sr.getScaledWidth();
        height = sr.getScaledHeight();
    }

    //update variables if config changes
    public static void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        backgroundColor = hexToInt(Config.textBackground);
        keyTextColor = hexToInt(Config.keyTextColor);
        keyTextPressedColor = hexToInt(Config.keyPressedTextColor);
        keyBackgroundColor = hexToInt(Config.keyBackgroundColor);
        keyBackgroundPressedColor = hexToInt(Config.keyPressedBackgroundColor);
    }

    public static void drawCoordElement(int x, int y, String text, int border) {
        drawCoordElement(x, y, text, border, backgroundColor, true);
    }
    public static void drawCoordElement(int x, int y, String text, int border, int backgroundColor, boolean dropShadow) {
        Gui.drawRect(x, y, x + renderer.getStringWidth(text) + 1 * border, y + 7 + 2 * border, backgroundColor);
        renderer.drawString(text, (x + border), (y + border), new Color(0, 0, 0, 40).getRGB(), dropShadow);
    }

    public static void drawTranslucentElement(int x, int y, String text){
        GlStateManager.enableBlend();

        renderer.drawString(text, x, y, new Color(127, 127, 127, 30).getRGB());
    }

    public static void drawKey(int x, int y, int keyX, int keyY, boolean pressed, String text) {
        if (pressed) {
            Gui.drawRect(width - x, height - y, width + keyX - x, height + keyY - y, keyBackgroundPressedColor);
            renderer.drawString(text, (width + (keyX - renderer.getStringWidth(text)) / 2 + 1 - x), (height + keyY / 2 - 3 - y), keyTextPressedColor, false);
        } else {
            Gui.drawRect(width - x, height - y, width + keyX - x, height + keyY - y, keyBackgroundColor);
            renderer.drawString(text, (width + (keyX - renderer.getStringWidth(text)) / 2 + 1 - x), (height + keyY / 2 - 3 - y), keyTextColor, false);
        }
    }
}
