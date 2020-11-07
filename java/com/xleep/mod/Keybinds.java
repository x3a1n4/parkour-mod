package com.xleep.mod;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keybinds {
    public static KeyBinding selectBlock;

    public static void register()
    {
        selectBlock = new KeyBinding("Select Landing Block", Keyboard.KEY_R, "key.categories.gameplay");

        ClientRegistry.registerKeyBinding(selectBlock);
    }
}
