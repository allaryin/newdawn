/*
 * Copyright (c) by Stefan Feldbinder aka Two
 */
package two.newdawn;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import two.newdawn.API.NewDawnRegistry;
import two.newdawn.worldgen.biomes.VanillaBiomeProvider;
import two.newdawn.worldgen.biomes.modsupport.thaumcraft.ThaumcraftBiomeProvider;

/**
 *
 * @author Two
 */
@Mod(modid = "newdawn", name = "New Dawn", version = "140108")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class NewDawn {

  @Mod.Instance("NewDawn")
  public static NewDawn instance;
  public static final Config config = new Config();
  protected boolean useInternalTCSupport = true;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    config.initialize(event.getSuggestedConfigurationFile());
  }

  @Mod.EventHandler
  public void load(FMLInitializationEvent event) {
    config.load();
    this.useInternalTCSupport = config.getMiscBoolean("Use internal Thaumcraft support", true);
    NewDawnWorldType.register();
    config.save();
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    NewDawnRegistry.registerProvider(new VanillaBiomeProvider());
    if (useInternalTCSupport && Loader.isModLoaded("Thaumcraft")) {
      NewDawnRegistry.registerProvider(new ThaumcraftBiomeProvider());
    }
  }
}
