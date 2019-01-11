package io.github.cottonmc.assembly;

import io.github.cottonmc.assembly.block.ModBlocks;
import io.github.cottonmc.assembly.container.GeneratorContainer;
import io.github.cottonmc.assembly.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class Assembly implements ModInitializer {

	public static final ItemGroup asmGroup = FabricItemGroupBuilder.build(new Identifier("assembly:assembly_tab"), () -> new ItemStack(ModBlocks.GENERATOR));

	 public static final Identifier GENERATOR_CONTAINER = new Identifier("assembly:generator_container");

	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModItems.init();

		ContainerProviderRegistry.INSTANCE.registerFactory(GENERATOR_CONTAINER, (identifier, player, buf) -> {
			BlockPos pos = buf.readBlockPos();
			return new GeneratorContainer(pos, player);
		});
	}
}
