package com.xleep.mod.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui extends GuiConfig {
    public ConfigGui(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(parentScreen), "parkourmod", false, false, "Change stuff here.");
    }

    private static List<IConfigElement> getConfigElements(GuiScreen parent) {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.add(new ConfigElement(Config.config.getCategory("General".toLowerCase())));
        list.add(new ConfigElement(Config.config.getCategory("Text Color".toLowerCase())));
        list.add(new ConfigElement(Config.config.getCategory("Text Location".toLowerCase())));
        list.add(new ConfigElement(Config.config.getCategory("Keystrokes Color".toLowerCase())));
        list.add(new ConfigElement(Config.config.getCategory("Keystrokes Location".toLowerCase())));
        list.add(new ConfigElement(Config.config.getCategory("Keystrokes Size".toLowerCase())));
        return list;
    }
}
