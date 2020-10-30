package com.xleep.mod.gui;

import com.xleep.mod.gui.coords.Coords;
import com.xleep.mod.gui.coords.Facing;
import com.xleep.mod.gui.coords.HHTimer;
import net.minecraft.world.gen.structure.StructureMineshaftPieces;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.Date;

//this is the main GUI class
//this class runs events
public class MainGUI {
    DrawElement drawElement = new DrawElement();

    Keyboard keyboard = new Keyboard();
    Coords coords = new Coords();
    BarrierWarning barrierWarning = new BarrierWarning();
    Crosshair crosshair = new Crosshair();

    Date date = new Date();
    //maybe a better way to do these
    //maybe have classes extend something
    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event) {
        //do these first, since it's referenced by later classes
        HHTimer.onFrame();
        Facing.renderOverlay(event);

        drawElement.renderOverlay(event);

        keyboard.renderOverlay(event);
        coords.renderOverlay(event);
        barrierWarning.renderOverlay(event);
        crosshair.renderOverlay(event);

    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        //remove the rising tick events
        if (event.phase == TickEvent.Phase.START)
            return;
        //do this first, since it's referenced by later classes
        HHTimer.onTick();
        System.out.println(new Date().getTime());

        keyboard.clientTick(event);
        coords.clientTick(event);
        barrierWarning.clientTick(event);
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
        if (event.modID.equalsIgnoreCase("parkourmod")) {
            drawElement.onConfigurationChangedEvent(event);
            coords.onConfigurationChangedEvent(event);
        }
    }

    public static int hexToInt(String hex) {
        try{
            int r = Integer.parseInt(hex.substring(1, 3), 16);
            int g = Integer.parseInt(hex.substring(3, 5), 16);
            int b = Integer.parseInt(hex.substring(5, 7), 16);
            int a = Integer.parseInt(hex.substring(7, 9), 16);
            return (new Color(r, g, b, a)).getRGB();
        }catch (StringIndexOutOfBoundsException e ){
            return (Color.black.getRGB());
        }

    }
}
