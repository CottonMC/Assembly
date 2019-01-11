package io.github.cottonmc.assembly;

import io.github.cottonmc.assembly.container.GeneratorContainerGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.gui.GuiProviderRegistry;
import net.minecraft.util.math.BlockPos;

public class AssemblyClient implements ClientModInitializer {
	// private SpriteProvider provider = new SpriteProvider();

	@Override
	public void onInitializeClient() {
		// SpriteEvent.PROVIDE.register(provider);
		// BlockEntityRendererRegistry.INSTANCE.register(AndGateBlockEntity.class, new AndGateRenderer());

		GuiProviderRegistry.INSTANCE.registerFactory(Assembly.GENERATOR_CONTAINER, (identifier, player, buf) -> {
			BlockPos pos = buf.readBlockPos();
			return new GeneratorContainerGui(pos, player);
		});
	}

}
