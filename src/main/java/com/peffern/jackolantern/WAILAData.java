package com.peffern.jackolantern;

import java.util.List;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Util.Helper;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WAILAData implements IWailaDataProvider
{

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		// TODO Auto-generated method stub
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
Block block = accessor.getBlock();
		
		if(block == TFCBlocks.litPumpkin)
		{
			currenttip = jackOBody(itemStack, currenttip, accessor, config);
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return currenttip;
	}
	
	public List<String> jackOBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config)
	{
		if (JackOFix.jackOLanternBurnTime != 0)
		{
			NBTTagCompound tag = accessor.getNBTData();
			long hours = JackOFix.jackOLanternBurnTime - (TFC_Time.getTotalHours() - tag.getInteger("hourPlaced"));

			if (hours > 0)
				currenttip.add(hours + " " + TFC_Core.translate("gui.hoursRemaining") + " (" + Helper.roundNumber((100f * hours) / JackOFix.jackOLanternBurnTime, 10) + "%)");
		}
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
			int y, int z) {
		if (te != null)
			te.writeToNBT(tag);
		return tag;
	}
	
	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.registerBodyProvider(new WAILAData(), BlockCustomCustomPumpkin.class);
		reg.registerNBTProvider(new WAILAData(), BlockCustomCustomPumpkin.class);
	}

}
