package io.github.cottonmc.assembly.item;

import io.github.cottonmc.assembly.Assembly;
import io.github.cottonmc.cotton.Cotton;
import io.github.cottonmc.resources.CottonResources;
import net.minecraft.item.Item;

public class ItemBase extends Item {

	protected String name;

	public static Settings DEFAULT_SETTINGS = new Item.Settings().itemGroup(Assembly.asmGroup);
	public static Settings COTTON_SETTINGS = new Item.Settings().itemGroup(Cotton.commonGroup);

	public ItemBase(String name, Settings settings) {
		super(settings);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
