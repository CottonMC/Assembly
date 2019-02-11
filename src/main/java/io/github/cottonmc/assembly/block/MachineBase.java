package io.github.cottonmc.assembly.block;

import io.github.cottonmc.assembly.block.entity.CrusherBlockEntity;
import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import io.github.prospector.silk.block.SilkBlockWithEntity;
import net.minecraft.world.IWorld;

public class MachineBase extends SilkBlockWithEntity implements NamedBlock, InventoryProvider {
	public String name;

	public static final Settings DEFAULT_SETTINGS = FabricBlockSettings.create(Material.PART).setStrength(0.5f, 8f).build();

	protected MachineBase(String name, Settings settings) {
		super(settings);
		this.name = name;
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Block getBlock() {
		return this;
	}

	@Override
	public SidedInventory getInventory(BlockState state, IWorld world, BlockPos pos) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be!=null && be instanceof CrusherBlockEntity) {
			return ((CrusherBlockEntity)be).getInventory();
		}
	}
}
