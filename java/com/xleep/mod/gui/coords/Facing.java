package com.xleep.mod.gui.coords;

import com.xleep.mod.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.text.DecimalFormat;

public class Facing {
    private static Minecraft mc = Minecraft.getMinecraft();
    private static EnumFacing enumfacing;
    private static DecimalFormat df = new DecimalFormat("0." + (new String(new char[Config.coordPrecision])).replace("\000", "0"));
    private static DecimalFormat crosshairDf = new DecimalFormat("0." + (new String(new char[Config.crosshairFacingPrecision])).replace("\000", "0"));

    public static String facing;
    public static String crosshairFacing;
    public static String direction;

    public static void renderOverlay(RenderGameOverlayEvent event) {
        //get player
        EntityPlayerSP player = mc.thePlayer;
        //format facing
        float unformattedFacing = (((player.rotationYaw + 180.0F) % 360.0F + 360.0F) % 360.0F - 180.0F);
        facing = df.format(unformattedFacing);
        crosshairFacing = crosshairDf.format(unformattedFacing);

        //get direction
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
    }


}
