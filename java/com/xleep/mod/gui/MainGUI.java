package com.xleep.mod.gui;

import com.sun.jna.platform.win32.Guid;
import com.xleep.mod.gui.coords.Coords;
import com.xleep.mod.gui.coords.Facing;
import com.xleep.mod.gui.coords.HHTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vector3d;
import net.minecraft.world.gen.structure.StructureMineshaftPieces;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;
import scala.Array;

import java.awt.*;
import java.util.Date;

//this is the main GUI class
//this class runs events
public class MainGUI {
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

        DrawElement.renderOverlay(event);

        keyboard.renderOverlay(event);
        coords.renderOverlay(event);
        barrierWarning.renderOverlay(event);
        crosshair.renderOverlay(event);

        /*
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        int color = new Color(127, 127, 127, 30).getRGB();
        //int alpha = 50;
        Minecraft.getMinecraft().fontRendererObj.drawString("Hello, world!", 100, 100, color);
        GL11.glEnable(GL11.GL_BLEND);
        */

        //Minecraft.getMinecraft().fontRendererObj.drawString("Hello, world!", 50, 50, color | (alpha << 24));

    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        //remove the rising tick events
        if (event.phase == TickEvent.Phase.START)
            return;
        //do this first, since it's referenced by later classes
        HHTimer.onTick();
        //System.out.println(new Date().getTime());

        keyboard.clientTick(event);
        coords.clientTick(event);
        barrierWarning.clientTick(event);
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
        if (event.modID.equalsIgnoreCase("parkourmod")) {
            DrawElement.onConfigurationChangedEvent(event);
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

    public static Array<Vector3d> blockToScreen(BlockPos blockPos){
        //https://stackoverflow.com/questions/48133920/how-to-convert-new-canvas-ui-image-position-to-linerenderer-position

        //the new bits:
        /*
        float dist = (Camera.main.transform.position - newListOfPoints[0]).magnitude;
        Rect r = RectTransformToScreenSpace((RectTransform)image.transform);
        Vector3 v3 = new Vector3(r.xMin, r.yMin, dist);
        //more or less original code:
        Vector3 val = Camera.main.ScreenToWorldPoint(v3);
        for(int i = 0; i < newListOfPoints.Count; i++) {
            line.SetPosition(i, new Vector3(newListOfPoints[i].x, val.y, newListOfPoints[i].z));
        }

        //helper function:
        public static Rect RectTransformToScreenSpace(RectTransform transform) {
            Vector2 size = Vector2.Scale(transform.rect.size, transform.lossyScale);
            Rect rect = new Rect(transform.position.x, transform.position.y, size.x, size.y);
            rect.x -= (transform.pivot.x * size.x);
            rect.y -= ((1.0f - transform.pivot.y) * size.y);
            return rect;
        }
        
         */
    }
}
