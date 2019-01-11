package io.github.cottonmc.assembly.item;

import io.github.cottonmc.assembly.Assembly;
import net.minecraft.item.Item;

public class ItemBase extends Item {

	protected String name;

	public static Settings DEFAULT_SETTINGS = new Item.Settings().itemGroup(Assembly.asmGroup);

	public ItemBase(String name, Settings settings) {
		super(settings);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
