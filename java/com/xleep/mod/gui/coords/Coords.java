package com.xleep.mod.gui.coords;

import com.xleep.mod.config.Config;
import com.xleep.mod.gui.DrawElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Coords {
    Minecraft mc = Minecraft.getMinecraft();

    HHTimer hhTimer = new HHTimer();

    DrawElement drawElement = new DrawElement();

    int width;

    int height;

    String direction;

    EnumFacing enumfacing;

    String textTextColor = "\247" + Config.textTextColor;

    String textNumberColor = "\247" + Config.textNumberColor;

    DecimalFormat df = new DecimalFormat("0." + (new String(new char[Config.coordPrecision])).replace("\000", "0"));

    //int backgroundColor = MainGUI.hexToInt(Config.textBackground);

    String posX;

    String posY;

    String posZ;

    double v_xz;

    String facing;

    long hhms;
    long hhTicks;

    public void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        EntityPlayerSP player = mc.thePlayer;
        if (player == null)
            return;

        //run hhTimer
        hhms = hhTimer.getMs();
        hhTicks = hhTimer.getTicks();
        hhTimer.onTick();

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
            hhTimer.onFrame();
            EntityPlayerSP player = mc.thePlayer;
            if (player == null)
                return;
            ScaledResolution sr = new ScaledResolution(mc);
            width = sr.getScaledWidth();
            height = sr.getScaledHeight();
            facing = df.format((((player.rotationYaw + 180.0F) % 360.0F + 360.0F) % 360.0F - 180.0F));
            enumfacing = player.getHorizontalFacing();
            switch (enumfacing) {
                case NORTH:
                    direction = "-Z";
                    break;
                case SOUTH:
                    direction = "+Z";
                    break;
                case WEST:
                    direction = "-X";
                    break;
                case EAST:
                    direction = "+X";
                    break;
            }
            if (!mc.gameSettings.showDebugInfo) {
                //hh timer
                drawElement.drawCoordElement(Config.locationHHTextX, Config.locationHHTextY, textTextColor + Config.hhFormat.replace("{hhms}", textNumberColor + hhms + textTextColor).replace("{hhTicks}", textNumberColor + hhTicks + textTextColor), 2);

                //facing
                drawElement.drawCoordElement(Config.locationFacingTextX, Config.locationFacingTextY, textTextColor + "Facing: " + textNumberColor + facing + textTextColor + " (" + textNumberColor + direction + textTextColor + ")", 2);

                //velocity
                drawElement.drawCoordElement(Config.locationVelocityTextX, Config.locationVelocityTextY, textTextColor + "Velocity: " + textNumberColor + (Config.enabledBlocksPerSecond ? (df.format(v_xz * 20.0D) + textTextColor + " b/s") : (df.format(v_xz) + textTextColor + " b/t")), 2);

                //coords
                drawElement.drawCoordElement(Config.locationXTextX, Config.locationXTextY, textTextColor + "X: " + textNumberColor + posX, 2);
                drawElement.drawCoordElement(Config.locationYTextX, Config.locationYTextY, textTextColor + "Y: " + textNumberColor + posY, 2);
                drawElement.drawCoordElement(Config.locationZTextX, Config.locationZTextY, textTextColor + "Z: " + textNumberColor + posZ, 2);
            }
        }
    }

    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        textTextColor = "\247" + Config.textTextColor;
        textNumberColor = "\247" + Config.textNumberColor;
        df = new DecimalFormat("0." + (new String(new char[Config.coordPrecision])).replace("\000", "0"));
    }
}
