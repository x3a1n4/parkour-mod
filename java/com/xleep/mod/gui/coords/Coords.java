package com.xleep.mod.gui.coords;

import com.xleep.mod.config.Config;
import com.xleep.mod.gui.DrawElement;
import com.xleep.mod.gui.MainGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.xleep.mod.gui.DrawElement.drawCoordElement;

public class Coords {
    Minecraft mc = Minecraft.getMinecraft();

    int width;

    int height;

    String textTextColor = "\247" + Config.textTextColor;

    String textNumberColor = "\247" + Config.textNumberColor;

    String crosshairTextTextColor = "\247" + Config.crosshairTextTextColor;

    String crosshairTextNumberColor = "\247" + Config.crosshairTextNumberColor;

    DecimalFormat df = new DecimalFormat("0." + (new String(new char[Config.coordPrecision])).replace("\000", "0"));

    String posX;

    String posY;

    String posZ;

    double v_xz;

    public void clientTick(TickEvent.ClientTickEvent event) {
        EntityPlayerSP player = mc.thePlayer;
        if (player == null)
            return;

        //round coordinate values
        df.setRoundingMode(RoundingMode.HALF_UP);
        posX = df.format(player.posX);
        posY = df.format(player.posY);
        posZ = df.format(player.posZ);

        //find the distance between the last xz pos and the current xz pos
        v_xz = Math.sqrt(
                Math.pow(player.posX - player.lastTickPosX, 2.0D) +
                        Math.pow(player.posZ - player.lastTickPosZ, 2.0D)
        );
    }

    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {

            EntityPlayerSP player = mc.thePlayer;
            if (player == null)
                return;
            ScaledResolution sr = new ScaledResolution(mc);
            width = sr.getScaledWidth();
            height = sr.getScaledHeight();

            if (!mc.gameSettings.showDebugInfo) {
                //hh timer
                drawCoordElement(Config.locationHHTextX, Config.locationHHTextY, textTextColor + Config.hhFormat.replace("{hhms}", textNumberColor + HHTimer.getMs() + textTextColor).replace("{hhTicks}", textNumberColor + HHTimer.getTicks() + textTextColor), 2);

                //facing
                drawCoordElement(Config.locationFacingTextX, Config.locationFacingTextY, textTextColor + "Facing: " + textNumberColor + Facing.facing + textTextColor + " (" + textNumberColor + Facing.direction + textTextColor + ")", 2);

                //velocity
                drawCoordElement(Config.locationVelocityTextX, Config.locationVelocityTextY, textTextColor + "Velocity: " + textNumberColor + (Config.enabledBlocksPerSecond ? (df.format(v_xz * 20.0D) + textTextColor + " b/s") : (df.format(v_xz) + textTextColor + " b/t")), 2);

                //coords
                drawCoordElement(Config.locationXTextX, Config.locationXTextY, textTextColor + "X: " + textNumberColor + posX, 2);
                drawCoordElement(Config.locationYTextX, Config.locationYTextY, textTextColor + "Y: " + textNumberColor + posY, 2);
                drawCoordElement(Config.locationZTextX, Config.locationZTextY, textTextColor + "Z: " + textNumberColor + posZ, 2);
            }


        }
    }

    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        textTextColor = "\247" + Config.textTextColor;
        textNumberColor = "\247" + Config.textNumberColor;
        crosshairTextTextColor = "\247" + Config.crosshairTextTextColor;
        crosshairTextNumberColor = "\247" + Config.crosshairTextNumberColor;
        df = new DecimalFormat("0." + (new String(new char[Config.coordPrecision])).replace("\000", "0"));
    }
}
