package com.xleep.mod.gui.coords;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.Date;

public class HHTimer {
    Minecraft mc = Minecraft.getMinecraft();

    public boolean state_keyForeward;

    public boolean lastStateForeward;

    public boolean lastStateJump;

    public boolean isLocked;

    public boolean state_keyForeward_ticks;

    public boolean lastStateForeward_ticks;

    public boolean lastStateJump_ticks;

    public boolean isLocked_ticks;

    private long ms;

    private long ticks;

    public long finalTicks;

    public long finalms;

    public Date date = new Date();

    public boolean isKey(int id) {
        if (id < 0)
            return Mouse.isButtonDown(id + 100);
        return Keyboard.isKeyDown(id);
    }

    //every frame
    public void onFrame() {
        if (Keyboard.isCreated()) {
            //if the forward key is down, uses the keyboard and not the game system
            if (isKey(this.mc.gameSettings.keyBindForward.getKeyCode())) {
                this.state_keyForeward = true;
                //if it wasn't pressed last time
                if (!this.lastStateForeward) {
                    //get the time
                    this.date = new Date();
                    this.isLocked = false;
                }
                this.lastStateForeward = true;
            } else {
                //TODO: find out what this does
                this.lastStateForeward = false;
            }

            //if the jump key is pressed
            if (isKey(this.mc.gameSettings.keyBindJump.getKeyCode())) {
                //if the key is forward
                if (this.state_keyForeward && !this.lastStateJump) {
                    if (!this.isLocked) {
                        long currentMillis = System.currentTimeMillis();
                        this.ms = currentMillis - this.date.getTime();
                        this.isLocked = true;
                    }
                    this.state_keyForeward = false;
                    this.lastStateJump = true;
                }
                return;
            }
            this.lastStateJump = false;
        }
    }

    //called every game tick
    public void onTick() {
        if (this.mc.gameSettings.keyBindForward.isKeyDown()) {
            this.state_keyForeward_ticks = true;

            //if forward wasn't pressed last tick, then set ticks to 0
            if (!this.lastStateForeward_ticks) {
                this.ticks = 0L;
                this.isLocked_ticks = false;
            }
            this.lastStateForeward_ticks = true;
        } else {
            this.lastStateForeward_ticks = false;
        }
        if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
            if (this.state_keyForeward_ticks &&
                    !this.lastStateJump_ticks) {
                if (!this.isLocked_ticks) {
                    this.finalTicks = this.ticks;
                    this.finalms = this.ms;
                    this.isLocked_ticks = true;
                }
                this.state_keyForeward_ticks = false;
                this.lastStateJump_ticks = true;
            }
        } else {
            this.lastStateJump_ticks = false;
        }
        this.ticks++;
    }

    public long getMs() {
        return this.finalms;
    }

    public long getTicks() {
        return this.finalTicks;
    }


}
