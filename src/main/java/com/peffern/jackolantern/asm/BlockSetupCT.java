package com.peffern.jackolantern.asm;

import java.util.ListIterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class BlockSetupCT implements IClassTransformer
{

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) 
	{
		if(name.equals("com.bioxx.tfc.BlockSetup"))
		{
			return asmify(basicClass);
		}
		else
			return basicClass;
	}
	
	//find the place where its making the pumpkin and use the custom one
	
	private byte[] asmify(byte[] bytes)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		
		for(MethodNode m : classNode.methods)
		{
			if(m.name.equals("loadBlocks") && m.desc.equals("()V"))
			{
				ListIterator<AbstractInsnNode> it = m.instructions.iterator();
				while(it.hasNext())
				{
					AbstractInsnNode i = it.next();
					if(i instanceof TypeInsnNode)
					{
						TypeInsnNode tinsn = (TypeInsnNode)i;
						
						if(tinsn.desc.equals("com/bioxx/tfc/Blocks/Vanilla/BlockCustomPumpkin"))
						{
							TypeInsnNode newTinsn = new TypeInsnNode(tinsn.getOpcode(), "com/peffern/jackolantern/BlockCustomCustomPumpkin");
							m.instructions.insert(tinsn, newTinsn);
							m.instructions.remove(tinsn);
						}
					}
					else if(i instanceof MethodInsnNode)
					{
						MethodInsnNode minsn = (MethodInsnNode)i;
						
						if(minsn.owner.equals("com/bioxx/tfc/Blocks/Vanilla/BlockCustomPumpkin"))
						{
							MethodInsnNode newMinsn = new MethodInsnNode(minsn.getOpcode(), "com/peffern/jackolantern/BlockCustomCustomPumpkin", minsn.name, minsn.desc, minsn.itf);
							m.instructions.insert(minsn,newMinsn);
							m.instructions.remove(minsn);
						}
					}
				}
				
			}
		}
		
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
	}

}
