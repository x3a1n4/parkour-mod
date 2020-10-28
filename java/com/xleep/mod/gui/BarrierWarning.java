package com.xleep.mod.gui;

import com.xleep.mod.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;

import java.math.RoundingMode;
import java.text.DecimalFormat;

//this class is the barrier warning class
//it displays a small "!" in the top right corner if the player is facing a barrier
public class BarrierWarning {
    Minecraft mc = Minecraft.getMinecraft();

    FontRenderer renderer = this.mc.fontRendererObj;

    int width;

    boolean lookingAtBarrier;

    public void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        //see if the player is looking at barrier
        //the "this.mc.theWorld.getBlockState(this.mc.objectMouseOver.getBlockPos()).getBlock().getRegistryName().equals("minecraft:barrier")" crashes the game
        this.lookingAtBarrier = false;
        //do all this to avoid crashes
        if (mc.objectMouseOver != null &&
                mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                mc.objectMouseOver.getBlockPos() != null &&
                mc.theWorld != null){
            if(mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock().getRegistryName().equals("minecraft:barrier")){
                lookingAtBarrier = true;
            }
        }
    }

    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            if (!mc.gameSettings.showDebugInfo) {
                //if player is looking at barrier and barrier warning is enabled
                if (lookingAtBarrier && Config.enabledBarrierWarning) {
                    //this draws an exclamation point in the top right of the screen
                    //this scales it so that the exclamation point is bigger
                    GlStateManager.scale(2.0F, 2.0F, 2.0F);

                    renderer.drawString("\247c!", this.width / 2 - 3, 2, 1);
                    //scale it back down
                    GlStateManager.scale(0.5D, 0.5D, 0.5D);
                }
            }
        }
    }

}
