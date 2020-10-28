package com.xleep.mod.gui;

import com.xleep.mod.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.xleep.mod.gui.DrawElement.drawKey;
import java.math.RoundingMode;
import java.text.DecimalFormat;

//this displays the keyboard inputs in the bottom right corner
public class Keyboard {
    Minecraft mc = Minecraft.getMinecraft();

    //DrawElement drawElement = new DrawElement();

    int keyTextColor = MainGUI.hexToInt(Config.keyTextColor);

    int keyTextPressedColor = MainGUI.hexToInt(Config.keyPressedTextColor);

    int keyBackgroundColor = MainGUI.hexToInt(Config.keyBackgroundColor);

    int keyBackgroundPressedColor = MainGUI.hexToInt(Config.keyPressedBackgroundColor);

    boolean forwardPressed;

    boolean leftPressed;

    boolean backPressed;

    boolean rightPressed;

    boolean sprintPressed;

    boolean sneakPressed;

    boolean jumpPressed;

    public void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        forwardPressed = mc.gameSettings.keyBindForward.isKeyDown();
        leftPressed = mc.gameSettings.keyBindLeft.isKeyDown();
        backPressed = mc.gameSettings.keyBindBack.isKeyDown();
        rightPressed = mc.gameSettings.keyBindRight.isKeyDown();
        sprintPressed = mc.gameSettings.keyBindSprint.isKeyDown();
        sneakPressed = mc.gameSettings.keyBindSneak.isKeyDown();
        jumpPressed = mc.gameSettings.keyBindJump.isKeyDown();
    }

    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            if (Config.enabledKeystrokes) {
                drawKey(Config.locationForwardX, Config.locationForwardY, Config.sizeForwardX, Config.sizeForwardY, forwardPressed, "W");
                drawKey(Config.locationLeftX, Config.locationLeftY, Config.sizeLeftX, Config.sizeLeftY, leftPressed, "A");
                drawKey(Config.locationBackX, Config.locationBackY, Config.sizeBackX, Config.sizeBackY, backPressed, "S");
                drawKey(Config.locationRightX, Config.locationRightY, Config.sizeRightX, Config.sizeRightY, rightPressed, "D");
                drawKey(Config.locationSprintX, Config.locationSprintY, Config.sizeSprintX, Config.sizeSprintY, sprintPressed, "Sprint");
                drawKey(Config.locationSneakX, Config.locationSneakY, Config.sizeSneakX, Config.sizeSneakY, sneakPressed, "Sneak");
                drawKey(Config.locationJumpX, Config.locationJumpY, Config.sizeJumpX, Config.sizeJumpY, jumpPressed, "");
            }
        }
    }

}
