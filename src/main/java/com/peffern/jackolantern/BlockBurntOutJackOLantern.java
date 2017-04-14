package com.peffern.jackolantern;

import java.lang.reflect.Field;

import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Blocks.Vanilla.BlockCustomPumpkin;
import com.bioxx.tfc.api.TFCBlocks;

import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBurntOutJackOLantern extends BlockCustomPumpkin
{	
	public BlockBurntOutJackOLantern()
	{
		super(false);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		super.registerBlockIcons(iconRegister);
		IIcon faceOff = iconRegister.registerIcon(this.getTextureName() + "_face_off");
		//use the super one but override the face icon with the off texture using reflection
		try
		{
			Field faceIcon = BlockCustomPumpkin.class.getDeclaredField("faceIcon");
			faceIcon.setAccessible(true);
			faceIcon.set(this, faceOff);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	//relight if rightclicked with a torch
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float sideX, float sideY, float sideZ)
	{
		ItemStack item = player.getCurrentEquippedItem();
		
		if(item != null && item.getItem() == Item.getItemFromBlock(TFCBlocks.torch))
		{
			int dir = world.getBlockMetadata(x,y,z);
			world.setBlock(x, y, z, TFCBlocks.litPumpkin, dir, 3);
		}
		return true;
	}
}
