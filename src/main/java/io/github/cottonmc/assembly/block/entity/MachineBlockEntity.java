package io.github.cottonmc.assembly.block.entity;

import net.fabricmc.fabric.block.entity.ClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;

public abstract class MachineBlockEntity extends BlockEntity implements ClientSerializable {
	public MachineBlockEntity(BlockEntityType<?> blockEntityType) {
		super(blockEntityType);
	}

	@Override
	public void fromClientTag(CompoundTag compoundTag) {
		fromTag(compoundTag);
	}

	@Override
	public CompoundTag toClientTag(CompoundTag compoundTag) {
		return toTag(compoundTag);
	}
}
