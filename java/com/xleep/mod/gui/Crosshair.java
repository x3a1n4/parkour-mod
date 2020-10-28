package com.xleep.mod.gui;

import com.xleep.mod.config.Config;
import com.xleep.mod.gui.coords.HHTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.DecimalFormat;

public class Crosshair {
    //draw hh timer and facing over crosshair, but decently translucent
    Minecraft mc = Minecraft.getMinecraft();
    HHTimer hhTimer = new HHTimer();

    //Copied from CoordsGUI for the time being
    DecimalFormat df = new DecimalFormat("0." + (new String(new char[Config.crosshairCoordPrecision])).replace("\000", "0"));
    EnumFacing enumfacing;
    String facing;
    String direction;


    //draw loop
    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event) {
        //Copied from CoordsGUI for the time being
        //Should make into its own class
        EntityPlayerSP player = this.mc.thePlayer;
        this.facing = this.df.format((((player.rotationYaw + 180.0F) % 360.0F + 360.0F) % 360.0F - 180.0F));
        this.enumfacing = player.getHorizontalFacing();
        switch (this.enumfacing) {
            case NORTH:
                this.direction = "-Z";
                break;
            case SOUTH:
                this.direction = "+Z";
                break;
            case WEST:
                this.direction = "-X";
                break;
            case EAST:
                this.direction = "+X";
                break;
        }
        /*
        DrawElement.drawCoordElement(
                Config.locationHHTextX,
                Config.locationHHTextY,
                this.textTextColor + Config.hhFormat.replace("{hhms}", this.textNumberColor + this.hhms + this.textTextColor).replace("{hhTicks}", this.textNumberColor + this.hhTicks + this.textTextColor),
                2,
                );

        DrawElement.drawCoordElement(
                Config.locationFacingTextX,
                Config.locationFacingTextY,
                this.textTextColor + "Facing: " + this.textNumberColor + this.facing + this.textTextColor + " (" + this.textNumberColor + this.direction + this.textTextColor + ")",
                2);
        */

    }
    
}
