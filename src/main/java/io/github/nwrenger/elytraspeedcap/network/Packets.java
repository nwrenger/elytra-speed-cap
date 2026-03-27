package io.github.nwrenger.elytraspeedcap.network;

import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public final class Packets {

    private Packets() {
    }

    public static void register() {
        // Must be called on BOTH physical sides during common init
        PayloadTypeRegistry.clientboundPlay().register(
                SyncMaxSpeedPayload.ID,
                SyncMaxSpeedPayload.CODEC);
    }

    public static void syncMaxSpeed(@NonNull ServerPlayer player, double maxSpeed) {
        ServerPlayNetworking.send(player, new SyncMaxSpeedPayload(maxSpeed));
    }
}
