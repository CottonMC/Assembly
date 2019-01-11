package io.github.cottonmc.assembly.block;

import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.BlockRenderLayer;
import net.minecraft.entity.VerticalEntityPosition;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import io.github.prospector.silk.block.SilkBlockWithEntity;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

public class MachineBase extends SilkBlockWithEntity implements NamedBlock {
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
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.SOLID;
	}
}
