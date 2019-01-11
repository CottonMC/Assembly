package io.github.cottonmc.assembly.util;

import io.github.cottonmc.assembly.block.entity.GeneratorBlockEntity;
import io.github.cottonmc.assembly.block.entity.MachineBlockEntity;
import io.github.cottonmc.energy.CottonEnergy;
import io.github.cottonmc.energy.api.EnergyType;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.networking.CustomPayloadPacketRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.packet.CustomPayloadClientPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.CustomPayloadServerPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.ThreadTaskQueue;
import net.minecraft.util.math.BlockPos;

public class AssemblyNetworking implements ModInitializer {

	public static final Identifier MACHINE_SYNC = new Identifier("assembly:machine_sync");
	public static final Identifier MACHINE_REQUEST = new Identifier("assembly:machine_request");
	 public static final Identifier GENERATOR_SYNC = new Identifier("assembly:generator_sync");
	 public static final Identifier GENERATOR_CHANGE = new Identifier("assembly:generator_change");

	@Override
	public void onInitialize() {
		CustomPayloadPacketRegistry.CLIENT.register(MACHINE_SYNC, ((packetContext, packetByteBuf) -> {
			BlockPos pos = packetByteBuf.readBlockPos();
			CompoundTag tag = packetByteBuf.readCompoundTag();
			if (packetContext.getPlayer() != null && packetContext.getPlayer().getEntityWorld() != null) {
				BlockEntity be = packetContext.getPlayer().getEntityWorld().getBlockEntity(pos);
				if (be instanceof MachineBlockEntity && tag != null) {
					be.fromTag(tag);
				}
			}
		}));
		CustomPayloadPacketRegistry.SERVER.register(MACHINE_REQUEST, (packetContext, packetByteBuf) -> {
			BlockPos pos = packetByteBuf.readBlockPos();
			BlockEntity be = packetContext.getPlayer().getEntityWorld().getBlockEntity(pos);
			if (be instanceof MachineBlockEntity) {
				syncMachine((MachineBlockEntity) be, (ServerPlayerEntity) packetContext.getPlayer());
			}
		});
		 CustomPayloadPacketRegistry.CLIENT.register(GENERATOR_SYNC, ((packetContext, packetByteBuf) -> {
		 	BlockPos pos = packetByteBuf.readBlockPos();
		 	BlockEntity be = packetContext.getPlayer().getEntityWorld().getBlockEntity(pos);
		 	if (be instanceof GeneratorBlockEntity) {
				((GeneratorBlockEntity)be).setExportType(CottonEnergy.ENERGY_REGISTRY.get(packetByteBuf.readIdentifier()));
		 	}
		 }));
		 CustomPayloadPacketRegistry.SERVER.register(GENERATOR_CHANGE, ((packetContext, packetByteBuf) -> {
		 	ThreadTaskQueue queue = packetContext.getTaskQueue();
		 	BlockPos pos = packetByteBuf.readBlockPos();
		 	Identifier type = packetByteBuf.readIdentifier();
		 	queue.execute(() -> {
		 		BlockEntity be = packetContext.getPlayer().getEntityWorld().getBlockEntity(pos);
		 		if (be instanceof GeneratorBlockEntity) {
		 			((GeneratorBlockEntity)be).setExportType(CottonEnergy.ENERGY_REGISTRY.get(type));
		 			be.markDirty();
		 			AssemblyNetworking.syncGenerator((GeneratorBlockEntity)be, (ServerPlayerEntity)packetContext.getPlayer());
		 		}
		 	});
		 }));
	}

	@Environment(EnvType.CLIENT)
	public static void syncMachine(MachineBlockEntity module, ServerPlayerEntity player) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeBlockPos(module.getPos());
		buf.writeCompoundTag(module.toTag(new CompoundTag()));
		player.networkHandler.sendPacket(new CustomPayloadClientPacket(MACHINE_SYNC, buf));
	}
	@Environment(EnvType.CLIENT)
	public static void requestMachine(MachineBlockEntity module) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeBlockPos(module.getPos());
		MinecraftClient.getInstance().getNetworkHandler().getClientConnection().sendPacket(new CustomPayloadServerPacket(MACHINE_REQUEST, buf));
	}
	 @Environment(EnvType.CLIENT)
	 public static void syncGenerator(GeneratorBlockEntity generator, ServerPlayerEntity player) {
	 	PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
	 	buf.writeBlockPos(generator.getPos());
	 	buf.writeIdentifier(CottonEnergy.ENERGY_REGISTRY.getId(generator.getPrimaryEnergyType()));
	 	player.networkHandler.sendPacket(new CustomPayloadClientPacket(GENERATOR_SYNC, buf));
	 }
	 @Environment(EnvType.CLIENT)
	 public static void changeGenerator(GeneratorBlockEntity generator, EnergyType newType) {
	 	PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
	 	buf.writeBlockPos(generator.getPos());
	 	buf.writeIdentifier(CottonEnergy.ENERGY_REGISTRY.getId(newType));
	 	MinecraftClient.getInstance().getNetworkHandler().getClientConnection().sendPacket(new CustomPayloadServerPacket(GENERATOR_CHANGE, buf));
	 }

}
