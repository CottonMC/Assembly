package io.github.cottonmc.assembly.item;

import io.github.cottonmc.assembly.Assembly;
import io.github.cottonmc.cotton.registry.CommonItems;
import io.github.cottonmc.resources.CottonResources;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ModItems {

	public static final Item COPPER_INGOT = CommonItems.register("copper_ingot", new ItemBase("copper_ingot", ItemBase.COTTON_SETTINGS));
	public static final Item COPPER_DUST = CommonItems.register("copper_dust", new ItemBase("copper_dust", ItemBase.COTTON_SETTINGS));
	public static final Item IRON_DUST = CommonItems.register("iron_dust", new ItemBase("iron_dust", ItemBase.COTTON_SETTINGS));

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
