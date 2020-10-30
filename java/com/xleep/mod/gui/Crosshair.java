package com.xleep.mod.gui;

import com.xleep.mod.config.Config;
import com.xleep.mod.gui.coords.Facing;
import com.xleep.mod.gui.coords.HHTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.xleep.mod.gui.DrawElement.drawCoordElement;
import static com.xleep.mod.gui.DrawElement.drawTranslucentElement;

import java.text.DecimalFormat;

public class Crosshair {
    //draw hh timer and facing over crosshair, but decently translucent
    Minecraft mc = Minecraft.getMinecraft();

    FontRenderer renderer = mc.fontRendererObj;

    //draw loop
    public void renderOverlay(RenderGameOverlayEvent event) {
        ScaledResolution sr = new ScaledResolution(mc);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();

        if (Config.enabledCrosshairText){
            String hhText = HHTimer.getMs() + " (" + HHTimer.getTicks() + ")";
            //hh timer
            //draw above crosshair
            //this is drawn too high
            drawTranslucentElement(
                    width/2 - renderer.getStringWidth(hhText)/2,
                    //make 20 customizable in config
                    height/2 - Config.crosshairTextSpacing - 8, //8 is height of text
                    hhText);

            String facingText = Facing.crosshairFacing + " (" + Facing.direction + ")";
            //facing
            //draw below crosshair
            drawTranslucentElement(
                    width/2  - renderer.getStringWidth(facingText)/2,
                    //make 20 customizable in config
                    height/2 + Config.crosshairTextSpacing,
                    facingText);

        }


    }
    
}
