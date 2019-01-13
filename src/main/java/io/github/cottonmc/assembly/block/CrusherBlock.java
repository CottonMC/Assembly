package io.github.cottonmc.assembly.block;

import io.github.cottonmc.assembly.block.entity.CrusherBlockEntity;
import io.github.cottonmc.assembly.item.ModItems;
import io.github.cottonmc.energy.api.DefaultEnergyTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CrusherBlock extends MachineBase {

	public static final EnumProperty<Direction> FACING = Properties.FACING_HORIZONTAL;
	public static final BooleanProperty ACTIVE = Properties.LIT;

	protected CrusherBlock() {
		super("crusher", DEFAULT_SETTINGS);
		this.setDefaultState(this.getStateFactory().getDefaultState()
				.with(FACING, Direction.NORTH)
				.with(ACTIVE, false));
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return new CrusherBlockEntity();
	}

	@Override
	public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
		BlockEntity be = world.getBlockEntity(pos);
		if (!world.isClient && be instanceof CrusherBlockEntity) {
			CrusherBlockEntity crusher = (CrusherBlockEntity) be;
			if (!player.isSneaking()) {
//			ContainerProviderRegistry.INSTANCE.openContainer(Assembly.CRUSHER_CONTAINER, player, buf -> buf.writeBlockPos(pos));
				if (player.getStackInHand(hand).getItem() == ModItems.COPPER_INGOT) {
					crusher.setInvStack(0, player.getStackInHand(hand));
					player.setStackInHand(hand, ItemStack.EMPTY);
				} else if (player.getStackInHand(hand).equals(ItemStack.EMPTY)) {
					player.setStackInHand(hand, crusher.getInvStack(1));
					crusher.setInvStack(1, ItemStack.EMPTY);
				}
			} else {
				player.addChatMessage(new StringTextComponent(""+crusher.getEnergyComponent(DefaultEnergyTypes.LOW_VOLTAGE).getCurrentEnergy()), true);
			}
		}
		return true;
	}

	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> builder) {
		builder.with(FACING, ACTIVE);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerHorizontalFacing().getOpposite());
	}

}
