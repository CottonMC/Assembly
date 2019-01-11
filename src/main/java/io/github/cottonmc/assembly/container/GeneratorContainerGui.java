package io.github.cottonmc.assembly.container;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.cottonmc.assembly.block.entity.GeneratorBlockEntity;
import io.github.cottonmc.assembly.container.widget.GeneratorButtonWidget;
import io.github.cottonmc.assembly.util.AssemblyNetworking;
import io.github.cottonmc.energy.CottonEnergy;
import io.github.cottonmc.energy.api.DefaultEnergyTypes;
import net.minecraft.client.gui.ContainerGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.TextFormat;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class GeneratorContainerGui extends ContainerGui {

	private static final Identifier TEXTURE = new Identifier("assembly:textures/gui/gui_generator.png");

	BlockPos pos;
	GeneratorBlockEntity be;

	public GeneratorContainerGui(BlockPos pos, PlayerEntity player) {
		super(new GeneratorContainer(pos, player));
		this.pos = pos;
		this.be = (GeneratorBlockEntity)player.getEntityWorld().getBlockEntity(pos);
		be.fromTag(player.getEntityWorld().getBlockEntity(pos).toTag(new CompoundTag()));
		this.containerWidth = 138;
		this.containerHeight = 74;
	}

	@Override
	protected void onInitialized() {
		super.onInitialized();
		int topPadded = ((this.height - this.containerHeight) / 2 + 5);
		int leftPadded = ((this.width - this.containerWidth) / 2) + 5;
		this.addButton(new GeneratorButtonWidget(4, leftPadded+8, topPadded+36, "low_voltage") {
			@Override
			public void onPressed(double x, double y) {
				AssemblyNetworking.changeGenerator(be, DefaultEnergyTypes.LOW_VOLTAGE);
			}
		});
		this.addButton(new GeneratorButtonWidget(2, leftPadded+40, topPadded+36, "medium_voltage") {
			@Override
			public void onPressed(double x, double y) {
				AssemblyNetworking.changeGenerator(be, DefaultEnergyTypes.MEDIUM_VOLTAGE);
			}
		});
		this.addButton(new GeneratorButtonWidget(1, leftPadded+72, topPadded+36, "high_voltage") {
			@Override
			public void onPressed(double x, double y) {
				AssemblyNetworking.changeGenerator(be, DefaultEnergyTypes.HIGH_VOLTAGE);
			}
		});
		this.addButton(new GeneratorButtonWidget(3, leftPadded+104, topPadded+36, "ultra_high_voltage") {
			@Override
			public void onPressed(double x, double y) {
				AssemblyNetworking.changeGenerator(be, DefaultEnergyTypes.ULTRA_HIGH_VOLTAGE);
			}
		});
	}

	@Override
	protected void drawBackground(float v, int i, int i1) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.client.getTextureManager().bindTexture(TEXTURE);
		int guiX = (this.width - this.containerWidth) / 2;
		int guiY = (this.height - this.containerHeight) / 2;
		this.drawTexturedRect(guiX, guiY, 0, 0, this.containerWidth, this.containerHeight);
		Identifier energyId = CottonEnergy.ENERGY_REGISTRY.getId(be.getPrimaryEnergyType());
		String text = ""+TextFormat.DARK_GRAY+new TranslatableTextComponent("msg.assembly.outputting").getText();
		String text2 = ""+TextFormat.DARK_GRAY+new TranslatableTextComponent("energy."+energyId.getNamespace()+"."+energyId.getPath()).getText();
		fontRenderer.draw(text, (width / 2f) - (fontRenderer.getStringWidth(text) / 2f), (height / 2f) - (fontRenderer.fontHeight * 3f), 1);
		fontRenderer.draw(text2, (width / 2f) - (fontRenderer.getStringWidth(text2) / 2f), (height / 2f) - (fontRenderer.fontHeight * 1.5f), 1);
	}

}
