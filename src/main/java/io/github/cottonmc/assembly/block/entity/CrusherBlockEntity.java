package io.github.cottonmc.assembly.block.entity;

import io.github.cottonmc.assembly.block.ModBlocks;
import io.github.cottonmc.assembly.item.ModItems;
import io.github.cottonmc.assembly.util.ConsumingEnergyHandler;
import io.github.cottonmc.assembly.util.component.SimpleItemComponent;
import io.github.cottonmc.energy.api.*;
import io.github.cottonmc.energy.impl.EnergySerializer;
import net.minecraft.container.Container;
import net.minecraft.container.LockContainer;
import net.minecraft.container.LockableContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.TextComponent;
import net.minecraft.util.Tickable;

public class CrusherBlockEntity extends MachineBlockEntity implements Tickable, EnergyComponentHolder, Inventory, LockableContainer {

	private EnergyType exportType = DefaultEnergyTypes.LOW_VOLTAGE;
	private ConsumingEnergyHandler energy = new ConsumingEnergyHandler(32);
	private SimpleItemComponent inv = new SimpleItemComponent(2);
	private LockContainer lock;

	private int processTicks;
	private int maxProcessTicks = 200;
	private int consumptionTicks; // not gonna use till UM gets its numbers stabilized
	private int maxConsumptionTicks = 5; // not gonna use till UM gets its numbers stabilized

	//Transient data to throttle sync down here (if needed)

	public CrusherBlockEntity() {
		super(ModBlocks.CRUSHER_BE);
		energy.listen(this::markDirty);
		inv.listen(this::markDirty);
	}

	@Override
	public void tick() {
		// gonna make into a Recipe later, wanna get a proof of concept out now
		if (inv.get(0).getItem() == ModItems.COPPER_INGOT) {
			if (processTicks < maxProcessTicks) {
				int extr = energy.extractEnergy(1, ActionType.SIMULATE);
				if (extr == 0) {
					energy.extractEnergy(0, ActionType.PERFORM);
					processTicks++;
				}
			} else {
				inv.extract(0, 1);
				inv.insert(1, new ItemStack(ModItems.COPPER_DUST, 1));
				processTicks = 0;
			}
		}
	}

	@Override
	public CompoundTag toTag(CompoundTag compound) {
		CompoundTag tag = super.toTag(compound);
		tag.put("Energy", EnergySerializer.serialize(energy));
		tag.put("Inv", inv.toTag());
		tag.putInt("CurrentProcessTicks", processTicks);
		return tag;
	}

	@Override
	public void fromTag(CompoundTag tag) {
		super.fromTag(tag);
		EnergySerializer.deserialize(energy, tag.getCompound("Energy"));
		inv.fromTag(tag.getCompound("Inv"));
		processTicks = tag.getInt("CurrentProcessTicks");
	}

	@Override
	public EnergyType getPrimaryEnergyType() {
		return DefaultEnergyTypes.LOW_VOLTAGE;
	}

	@Override
	public boolean canConnectTo(EnergyType... energyTypes) {
		for (EnergyType type : energyTypes) {
			if (type == DefaultEnergyTypes.LOW_VOLTAGE) return true;
		}
		return false;
	}

	@Override
	public EnergyComponent getEnergyComponent(EnergyType energyType) {
		return energy;
	}
	
	// ---

	//delegates Inventory to inv {
	@Override
	public void clearInv() {
		inv.clear();
	}

	@Override
	public TextComponent getName() {
		return inv.getName();
	}

	@Override
	public int getInvSize() {
		return inv.size();
	}

	@Override
	public boolean isInvEmpty() {
		return inv.isEmpty();
	}

	@Override
	public ItemStack getInvStack(int slotIndex) {
		return inv.get(slotIndex);
	}

	@Override
	public ItemStack takeInvStack(int slotIndex, int amount) {
		return inv.extract(slotIndex, amount);
	}

	@Override
	public ItemStack removeInvStack(int slotIndex) {
		return inv.extract(slotIndex);
	}

	@Override
	public void setInvStack(int slotIndex, ItemStack itemStack) {
		inv.put(slotIndex, itemStack);
	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity player) {
		//This one we always have to implement ourselves
		return player.squaredDistanceTo((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	//}

	//implements ContainerProvider (implied by LockableContainer) {

	@Override
	public Container createContainer(PlayerInventory var1, PlayerEntity var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContainerId() {
		// TODO Auto-generated method stub
		return null;
	}
	//}

	//implements LockableContainer {
	@Override
	public boolean hasContainerLock() {
		return lock!=null && !lock.isEmpty();
	}

	@Override
	public void setContainerLock(LockContainer lock) {
		this.lock = lock;
	}

	@Override
	public LockContainer getContainerLock() {
		return lock;
	}
	//}

}
