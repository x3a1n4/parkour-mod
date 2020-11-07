package com.xleep.mod.gui.proximityScatter;

import com.xleep.mod.Keybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.nio.charset.MalformedInputException;
import java.util.Date;
import java.util.List;

//this class draws a dot for every attempt on a jump
public class ProximityScatter {
    private Minecraft mc = Minecraft.getMinecraft();
    private List<Point> points;

    //on frame
    public void renderOverlay(RenderGameOverlayEvent event){
        if(Keybinds.selectBlock.isKeyDown()){
            //toggle into block selection mode
            //this should highlight blocks, and then the player can right click to choose a block
            //this will then show some sort of comfirmation message

            //copied from barrierWarning
            if (mc.objectMouseOver != null &&
                    mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                    mc.objectMouseOver.getBlockPos() != null){

                BlockPos blockPos = mc.objectMouseOver.getBlockPos();


            }
        }
    }

    //on tick
    public void clientTick(TickEvent.ClientTickEvent event){

    }
}
