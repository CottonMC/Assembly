package io.github.cottonmc.assembly.block.entity;

import io.github.cottonmc.assembly.block.ModBlocks;
import io.github.cottonmc.energy.CottonEnergy;
import io.github.cottonmc.energy.api.*;
import io.github.cottonmc.energy.event.PowerGenEvent;
import net.fabricmc.fabric.util.HandlerArray;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.function.Consumer;

public class GeneratorBlockEntity extends MachineBlockEntity implements Tickable, EnergyComponentHolder {

	private EnergyType exportType = DefaultEnergyTypes.LOW_VOLTAGE;

	//Transient data to throttle sync down here (if needed)

	public GeneratorBlockEntity() {
		super(ModBlocks.GENERATOR_BE);
	}

	@Override
	public void tick() {
		// Infinite energy source for testing purposes before UniMan gets made. DO NOT EVER MAKE A GENERATOR LIKE THIS.
		for (Direction dir : Direction.values()) {
			BlockPos pos = this.getPos().offset(dir);
			BlockEntity be = world.getBlockEntity(pos);
			if (be instanceof EnergyComponentHolder) {
				EnergyComponentHolder comp = (EnergyComponentHolder) be;
				if (comp.canConnectTo(exportType) && comp.getEnergyComponent(exportType).canInsertEnergy()) {
					EnergyComponent energy = comp.getEnergyComponent(exportType);
					energy.insertEnergy(exportType.getMaximumTransferSize(), ActionType.SIMULATE);
					runEvent();
					be.markDirty();
				}
			} else if (be instanceof SidedEnergyComponentHolder) {
				SidedEnergyComponentHolder comp = (SidedEnergyComponentHolder) be;
				if (comp.canConnectTo(dir.getOpposite(), exportType) && comp.getEnergyComponent(dir.getOpposite(), exportType).canInsertEnergy()) {
					EnergyComponent energy = comp.getEnergyComponent(dir.getOpposite(), exportType);
					energy.insertEnergy(exportType.getMaximumTransferSize(), ActionType.PERFORM);
					runEvent();
					be.markDirty();
				}
			}
		}
	}

	public void runEvent() {
		for (Consumer<BlockEntity> handler : ((HandlerArray<Consumer<BlockEntity>>) PowerGenEvent.GENERATE_POWER).getBackingArray()) {
			handler.accept((BlockEntity) this);
		}
	}

	@Override
	public CompoundTag toTag(CompoundTag compound) {
		CompoundTag tag = super.toTag(compound);
		tag.putString("EnergyType", CottonEnergy.ENERGY_REGISTRY.getId(exportType).toString());
		return tag;
	}

	@Override
	public void fromTag(CompoundTag compound) {
		super.fromTag(compound);
		setExportType(CottonEnergy.ENERGY_REGISTRY.get(new Identifier(compound.getString("EnergyType"))));
	}

	@Override
	public EnergyType getPrimaryEnergyType() {
		return exportType;
	}

	@Override
	public boolean canConnectTo(EnergyType... energyTypes) {
		for (EnergyType type : energyTypes) {
			if (type instanceof ElectricalEnergyType) return true;
		}
		return false;
	}

	@Override
	public EnergyComponent getEnergyComponent(EnergyType energyType) {
		return new EnergyComponent() {
			@Override
			public int getMaxEnergy() {
				return 256;
			}

			@Override
			public int getCurrentEnergy() {
				return 256;
			}

			@Override
			public boolean canInsertEnergy() {
				return false;
			}

			@Override
			public int insertEnergy(int i, ActionType actionType) {
				return i;
			}

			@Override
			public boolean canExtractEnergy() {
				return true;
			}

			@Override
			public int extractEnergy(int i, ActionType actionType) {
				return 0;
			}
		};
	}

	public void setExportType(EnergyType exportType) {
		this.exportType = exportType;
	}
}
