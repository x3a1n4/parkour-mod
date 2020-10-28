package com.xleep.mod.gui;

import com.xleep.mod.config.Config;
import com.xleep.mod.gui.coords.HHTimer;
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


import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CoordsGUI {
    Minecraft mc = Minecraft.getMinecraft();

    FontRenderer renderer = this.mc.fontRendererObj;

    HHTimer hhTimer = new HHTimer();

    DrawElement drawElement = new DrawElement();

    ScaledResolution sr;

    int width;

    int height;

    int rawFacing;

    String direction;

    EnumFacing enumfacing;

    boolean lookingAtBarrier;

    String textTextColor = "\247" + Config.textTextColor;

    String textNumberColor = "\247" + Config.textNumberColor;

    DecimalFormat df = new DecimalFormat("0." + (new String(new char[Config.coordPrecision])).replace("\000", "0"));

    int backgroundColor = hexToInt(Config.textBackground);

    int keyTextColor = hexToInt(Config.keyTextColor);

    int keyTextPressedColor = hexToInt(Config.keyPressedTextColor);

    int keyBackgroundColor = hexToInt(Config.keyBackgroundColor);

    int keyBackgroundPressedColor = hexToInt(Config.keyPressedBackgroundColor);

    String posX;

    String posY;

    String posZ;

    double v_xz;

    String facing;

    boolean forwardPressed;

    boolean leftPressed;

    boolean backPressed;

    boolean rightPressed;

    boolean sprintPressed;

    boolean sneakPressed;

    boolean jumpPressed;

    long hhms;

    long hhTicks;

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;
        this.hhms = this.hhTimer.getMs();
        this.hhTicks = this.hhTimer.getTicks();
        this.hhTimer.onTick();
        EntityPlayerSP player = this.mc.thePlayer;
        if (player == null)
            return;

        //see if the player is looking at barrier
        this.lookingAtBarrier = false;
        if (this.mc.objectMouseOver != null &&
                this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                this.mc.objectMouseOver.getBlockPos() != null &&
                this.mc.theWorld.getBlockState(this.mc.objectMouseOver.getBlockPos()).getBlock().getRegistryName().equals("minecraft:barrier"))
            this.lookingAtBarrier = true;
        this.df.setRoundingMode(RoundingMode.HALF_UP);
        this.posX = this.df.format(player.posX);
        this.posY = this.df.format(player.posY);
        this.posZ = this.df.format(player.posZ);

        //find the distance between the last xz pos and the current xz pos
        this.v_xz = Math.sqrt(
                Math.pow(player.posX - player.lastTickPosX, 2.0D) +
                        Math.pow(player.posZ - player.lastTickPosZ, 2.0D)
        );
        this.forwardPressed = this.mc.gameSettings.keyBindForward.isKeyDown();
        this.leftPressed = this.mc.gameSettings.keyBindLeft.isKeyDown();
        this.backPressed = this.mc.gameSettings.keyBindBack.isKeyDown();
        this.rightPressed = this.mc.gameSettings.keyBindRight.isKeyDown();
        this.sprintPressed = this.mc.gameSettings.keyBindSprint.isKeyDown();
        this.sneakPressed = this.mc.gameSettings.keyBindSneak.isKeyDown();
        this.jumpPressed = this.mc.gameSettings.keyBindJump.isKeyDown();
    }

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            this.hhTimer.onFrame();
            EntityPlayerSP player = this.mc.thePlayer;
            if (player == null)
                return;
            ScaledResolution sr = new ScaledResolution(this.mc);
            this.width = sr.getScaledWidth();
            this.height = sr.getScaledHeight();
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
            if (!this.mc.gameSettings.showDebugInfo) {
                drawElement.drawCoordElement(Config.locationHHTextX, Config.locationHHTextY, this.textTextColor + Config.hhFormat.replace("{hhms}", this.textNumberColor + this.hhms + this.textTextColor).replace("{hhTicks}", this.textNumberColor + this.hhTicks + this.textTextColor), 2);
                drawElement.drawCoordElement(Config.locationFacingTextX, Config.locationFacingTextY, this.textTextColor + "Facing: " + this.textNumberColor + this.facing + this.textTextColor + " (" + this.textNumberColor + this.direction + this.textTextColor + ")", 2);
                drawElement.drawCoordElement(Config.locationVelocityTextX, Config.locationVelocityTextY, this.textTextColor + "Velocity: " + this.textNumberColor + (Config.enabledBlocksPerSecond ? (this.df.format(this.v_xz * 20.0D) + this.textTextColor + " b/s") : (this.df.format(this.v_xz) + this.textTextColor + " b/t")), 2);
                drawElement.drawCoordElement(Config.locationXTextX, Config.locationXTextY, this.textTextColor + "X: " + this.textNumberColor + this.posX, 2);
                drawElement.drawCoordElement(Config.locationYTextX, Config.locationYTextY, this.textTextColor + "Y: " + this.textNumberColor + this.posY, 2);
                drawElement.drawCoordElement(Config.locationZTextX, Config.locationZTextY, this.textTextColor + "Z: " + this.textNumberColor + this.posZ, 2);
                //System.out.println(this.lookingAtBarrier);

                //if player is looking at barrier and barrier warning is enabled
                if (this.lookingAtBarrier && Config.enabledBarrierWarning) {
                    //this draws an exclamation point in the top right of the screen
                    //this scales it so that the exclamation point is bigger
                    GlStateManager.scale(2.0F, 2.0F, 2.0F);

                    this.renderer.drawString("\247c!", this.width / 2 - 3, 2, 1);
                    //scale it back down
                    GlStateManager.scale(0.5D, 0.5D, 0.5D);
                }
            }
            if (Config.enabledKeystrokes) {
                drawElement.drawKey(Config.locationForwardX, Config.locationForwardY, Config.sizeForwardX, Config.sizeForwardY, this.forwardPressed, "W");
                drawElement.drawKey(Config.locationLeftX, Config.locationLeftY, Config.sizeLeftX, Config.sizeLeftY, this.leftPressed, "A");
                drawElement.drawKey(Config.locationBackX, Config.locationBackY, Config.sizeBackX, Config.sizeBackY, this.backPressed, "S");
                drawElement.drawKey(Config.locationRightX, Config.locationRightY, Config.sizeRightX, Config.sizeRightY, this.rightPressed, "D");
                drawElement.drawKey(Config.locationSprintX, Config.locationSprintY, Config.sizeSprintX, Config.sizeSprintY, this.sprintPressed, "Sprint");
                drawElement.drawKey(Config.locationSneakX, Config.locationSneakY, Config.sizeSneakX, Config.sizeSneakY, this.sneakPressed, "Sneak");
                drawElement.drawKey(Config.locationJumpX, Config.locationJumpY, Config.sizeJumpX, Config.sizeJumpY, this.jumpPressed, "");
            }
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase("parkourmod")) {
            this.textTextColor = "\247" + Config.textTextColor;
            this.textNumberColor = "\247" + Config.textNumberColor;
            this.df = new DecimalFormat("0." + (new String(new char[Config.coordPrecision])).replace("\000", "0"));
        }
    }

    public static int hexToInt(String hex) {
        int r = Integer.parseInt(hex.substring(1, 3), 16);
        int g = Integer.parseInt(hex.substring(3, 5), 16);
        int b = Integer.parseInt(hex.substring(5, 7), 16);
        int a = Integer.parseInt(hex.substring(7, 9), 16);
        return (new Color(r, g, b, a)).getRGB();
    }
}
