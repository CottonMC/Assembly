package io.github.cottonmc.assembly.block;

import io.github.cottonmc.assembly.Assembly;
import io.github.cottonmc.assembly.block.entity.*;
import io.github.cottonmc.assembly.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;



public class ModBlocks {
	public static final Block GENERATOR = register(new GeneratorBlock(), Assembly.asmGroup);

	public static final BlockEntityType<GeneratorBlockEntity> GENERATOR_BE = register("generator", GeneratorBlockEntity::new);

	public static void init() {

	}

	public static Block register(NamedBlock block, ItemGroup tab) {
		Registry.register(Registry.BLOCK, "assembly:" + block.getName(), block.getBlock());
		BlockItem item = new BlockItem(block.getBlock(), new Item.Settings().itemGroup(tab));
		ModItems.register(block.getName(), item);
		return block.getBlock();
	}

	public static BlockEntityType register(String name, Supplier<BlockEntity> be) {
		return Registry.register(Registry.BLOCK_ENTITY, "assembly:" + name, BlockEntityType.Builder.create(be).method_11034(null));
	}

}
