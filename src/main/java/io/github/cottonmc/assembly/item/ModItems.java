package io.github.cottonmc.assembly.item;

import io.github.cottonmc.assembly.Assembly;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ModItems {

	// public static final Item PCB = register(new ItemBase("pcb", ItemBase.DEFAULT_SETTINGS));

	public static void init() {

	}

	public static Item register(String name, Item item) {
		Registry.register(Registry.ITEM, "assembly:" + name, item);
		return item;
	}

	public static Item register(ItemBase item) {
		Registry.register(Registry.ITEM, "assembly:" + item.getName(), item);
		return item;
	}
}
