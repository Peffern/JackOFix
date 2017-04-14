package com.peffern.jackolantern;

import java.lang.reflect.Field;
import java.util.Random;

import javax.swing.JOptionPane;

import com.bioxx.tfc.Blocks.Vanilla.BlockCustomPumpkin;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TELightEmitter;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Custom version of tfc pumpkin block that lights with a TE and burns out after time
 * @author peffern
 *
 */
public class BlockCustomCustomPumpkin extends BlockCustomPumpkin implements ITileEntityProvider
{
	//track isLit since the parent vbl is private
	private boolean isLit = false;
	
	public BlockCustomCustomPumpkin(boolean lit) 
	{
		super(lit);
		//only tick if lit
		this.setTickRandomly(lit);
		this.isLit = lit;
	}
	
	//on rightclick, if torch, relight the jackolantern
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float sideX, float sideY, float sideZ)
	{
		if(!world.isRemote)
		{
			ItemStack item = player.getCurrentEquippedItem();
			if(this.isLit && item != null && item.getItem() == Item.getItemFromBlock(TFCBlocks.torch))
			{
				TileEntity te = world.getTileEntity(x, y, z);
				if(te != null)
				{
					TELightEmitter tele = (TELightEmitter)te;
					tele.hourPlaced = (int)TFC_Time.getTotalHours();
				}
			}
		}
		return true;
	}
	
	//only called if is a lit pumpkin
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		super.updateTick(world, x, y, z, rand);
		int meta = world.getBlockMetadata(x, y, z);

		if (!world.isRemote)
		{
			//if its light and we have burnout enabled
			if (JackOFix.jackOLanternBurnTime != 0 && world.getTileEntity(x, y, z) instanceof TELightEmitter)
			{
				//then check for burnout
				TELightEmitter te = (TELightEmitter) world.getTileEntity(x, y, z);
				if (TFC_Time.getTotalHours() > te.hourPlaced + JackOFix.jackOLanternBurnTime)
				{
					//burnout
					world.setBlock(x, y, z, JackOFix.jackOLanternOff, meta, 3);
				}
			}
		}
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		if(this.isLit)
		{
			return new TELightEmitter();
		}
		else
			return null;
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x,y,z);
		if(te != null)
		{
			((TELightEmitter) te).create();
		}
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		if(!isLit)
			return 0;
		return getLightValue();
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		if(this.isLit)
		{
			return new TELightEmitter();
		}
		else
			return null;
	}
	
	
	
	

}
