/*
 *  Copyright (C) 2016 Peffern

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.peffern.jackolantern;


import javax.swing.JOptionPane;

import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;


@Mod(modid = JackOFix.MODID, name = JackOFix.MODNAME, version = JackOFix.VERSION, dependencies = "required-after:" + "terrafirmacraft" + ";")
public class JackOFix
{	
	
	static Block jackOLanternOff;
	
    public static final String MODID = "JackOFix";
    public static final String MODNAME = "JackOFix";
    public static final String VERSION = "1.2";
    
    static int jackOLanternBurnTime = 144;
    
    static int jackOLanternLightLevel = 16;
    
    static boolean enablePickup = true;
    
    static Configuration config;
    
    //do config
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	jackOLanternBurnTime = config.get(Configuration.CATEGORY_GENERAL, "jackOLanternBurnTime", 144, "This is how long the jack o'Lantern burns for. Set to 0 for them to last forever.").getInt();
    	jackOLanternLightLevel = config.get(Configuration.CATEGORY_GENERAL, "jackOLanternLightLevel",16,"This is the brightness of the jack o'Lantern (0-16)").getInt();
    	enablePickup = !config.get(Configuration.CATEGORY_GENERAL, "enableJackOLanternsRot", false, "If this is set to true, once jack o'Lanterns are placed they cannot be picked up").getBoolean();
    	if(config.hasChanged())
    		config.save();
    }
    
    //main setup
    @EventHandler
    public void init(FMLInitializationEvent event)
    {    	
    	jackOLanternOff = new BlockBurntOutJackOLantern().setHardness(1.0F).setStepSound(Block.soundTypeWood).setBlockName("UnlitPumpkin").setBlockTextureName("pumpkin");
    	GameRegistry.registerBlock(jackOLanternOff, ItemTerraBlock.class, "UnlitPumpkin");
    	
    	FMLInterModComms.sendMessage("Waila", "register", "com.peffern.jackolantern.WAILAData.callbackRegister");
    }
}
