package io.github.cottonmc.assembly.container;

import io.github.cottonmc.assembly.block.entity.CrusherBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class CrusherContainer extends Container {

	BlockPos pos;
	CrusherBlockEntity be;

	public CrusherContainer(BlockPos pos, PlayerEntity player) {
		this.pos = pos;
		this.be = (CrusherBlockEntity)player.getEntityWorld().getBlockEntity(pos);
	}

	@Override
	public boolean canUse(PlayerEntity playerEntity) {
		return true;
	}
}
