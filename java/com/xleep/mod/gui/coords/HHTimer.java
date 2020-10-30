package com.xleep.mod.gui.coords;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.Date;

public class HHTimer {
    static Minecraft mc = Minecraft.getMinecraft();

    public static boolean state_keyForeward;

    public static boolean lastStateForeward;

    public static boolean lastStateJump;

    public static boolean isLocked;

    public static boolean state_keyForeward_ticks;

    public static boolean lastStateForeward_ticks;

    public static boolean lastStateJump_ticks;

    public static boolean isLocked_ticks;

    private static long ms;

    private static long ticks;

    public static long finalTicks;

    public static long finalms;

    public static Date date = new Date();

    public static boolean isKey(int id) {
        if (id < 0)
            return Mouse.isButtonDown(id + 100);
        return Keyboard.isKeyDown(id);
    }

    //every frame
    public static void onFrame() {
        if (Keyboard.isCreated()) {
            //if the forward key is down, uses the keyboard and not the game system
            if (isKey(mc.gameSettings.keyBindForward.getKeyCode())) {
                state_keyForeward = true;
                //if it wasn't pressed last time
                if (!lastStateForeward) {
                    //get the time
                    date = new Date();
                    isLocked = false;
                }
                lastStateForeward = true;
            } else {
                lastStateForeward = false;
            }

            //if the jump key is pressed
            if (isKey(mc.gameSettings.keyBindJump.getKeyCode())) {
                //if the key is forward
                if (state_keyForeward && !lastStateJump) {
                    if (!isLocked) {
                        long currentMillis = System.currentTimeMillis();
                        ms = currentMillis - date.getTime();
                        isLocked = true;
                    }
                    state_keyForeward = false;
                    lastStateJump = true;
                }
                return;
            }
            lastStateJump = false;
        }
    }

    //called every game tick
    public static void onTick() {
        //if forward key is down
        if (mc.gameSettings.keyBindForward.isKeyDown()) {
            //set forward ticks to true
            state_keyForeward_ticks = true;

            //if forward wasn't pressed last tick, then set ticks to 0
            if (!lastStateForeward_ticks) {
                ticks = 0L;
                //this makes it count ticks
                isLocked_ticks = false;
            }

            //set the last state forward to true
            lastStateForeward_ticks = true;
        } else {
            //set the last state forward to false
            lastStateForeward_ticks = false;
        }
        //if jump is pressed
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            //if ???
            if (state_keyForeward_ticks && !lastStateJump_ticks) {
                if (!isLocked_ticks) {
                    finalTicks = ticks;
                    finalms = ms;
                    isLocked_ticks = true;
                    //System.out.println(finalTicks);
                    //System.out.println(finalms);
                }
                state_keyForeward_ticks = false;
                lastStateJump_ticks = true;
            }
        } else {
            lastStateJump_ticks = false;
        }
        ticks++;
    }

    //often wrong
    public static long getMs() {
        return finalms;
    }

    //doubled for some reason
    public static long getTicks() {
        return finalTicks;
    }


}
