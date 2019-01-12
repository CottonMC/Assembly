package io.github.cottonmc.assembly.container;

import io.github.cottonmc.assembly.block.entity.GeneratorBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.awt.geom.GeneralPath;

public class GeneratorContainer extends Container {

	BlockPos pos;
	GeneratorBlockEntity be;

	public GeneratorContainer(BlockPos pos, PlayerEntity player) {
		this.pos = pos;
		this.be = (GeneratorBlockEntity) player.getEntityWorld().getBlockEntity(pos);
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}

}
