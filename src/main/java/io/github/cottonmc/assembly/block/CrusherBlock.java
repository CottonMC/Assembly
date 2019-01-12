package io.github.cottonmc.assembly.block;

import io.github.cottonmc.assembly.block.entity.CrusherBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CrusherBlock extends MachineBase {

	public static final EnumProperty<Direction> FACING = Properties.FACING_HORIZONTAL;

	protected CrusherBlock() {
		super("grinder", DEFAULT_SETTINGS);
		this.setDefaultState(this.getStateFactory().getDefaultState()
				.with(FACING, Direction.NORTH));
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return new CrusherBlockEntity();
	}

	@Override
	public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
		BlockEntity be = world.getBlockEntity(pos);
		if (!world.isClient && !player.isSneaking() && be instanceof CrusherBlockEntity) {
//			ContainerProviderRegistry.INSTANCE.openContainer(Assembly.CRUSHER_CONTAINER, player, buf -> buf.writeBlockPos(pos));
		}
		return true;
	}

	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> builder) {
		builder.with(FACING);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerHorizontalFacing());
	}

}
