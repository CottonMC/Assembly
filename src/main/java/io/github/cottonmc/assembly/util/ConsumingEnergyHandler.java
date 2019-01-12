package io.github.cottonmc.assembly.util;

import io.github.cottonmc.energy.impl.EnergyHandler;

public class ConsumingEnergyHandler extends EnergyHandler {

	public ConsumingEnergyHandler(int maxEnergy) {
		super(maxEnergy);
	}

	@Override
	public boolean canExtractEnergy() {
		return false;
	}
}
