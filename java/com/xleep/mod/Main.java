package com.xleep.mod;

import com.xleep.mod.config.Config;
import com.xleep.mod.gui.MainGUI;
import com.xleep.mod.proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

//@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION, guiFactory = Main.GUIFACTORY, acceptedMinecraftVersions = Main.MINECRAFTVERSIONS)
@Mod(modid = "parkourmod", name = "Parkour Mod", version = "0.0.1", guiFactory = "com.xleep.mod.config.GuiFactory", acceptedMinecraftVersions = "[1.8.9]")
public class Main
{
    public static final String MODID = "parkourmod";
    public static final String NAME = "Parkour Mod";
    public static final String VERSION = "0.0.1";
    public static final String GUIFACTORY = "com.xleep.mod.config.GuiFactory";
    public static final String MINECRAFTVERSIONS = "[1.8.9]";

    @Mod.Instance
    public static Main instance;

    @SidedProxy(clientSide = "com.xleep.mod.proxy.ClientProxy", serverSide = "com.xleep.mod.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static Configuration config;

    @EventHandler
    public void preInit(FMLPreInitializationEvent preEvent) {
        Config.init(preEvent.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new Config());
        Keybinds.register();
        //this;
        proxy.preInit(preEvent);
    }

    @EventHandler
    public void init(FMLInitializationEvent Event) {
        //this;
        proxy.init(Event);

        // some example code
        //System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent postEvent) {
        //this makes things run event commands
        //ideally I would only have one of these, that updates everything
        //I think the ideal is to have one class that collects all the gui elements, as well as changes all the gui classes
        //and register that class here
        MinecraftForge.EVENT_BUS.register(new MainGUI());
        //this;
        proxy.postInit(postEvent);
    }

}
