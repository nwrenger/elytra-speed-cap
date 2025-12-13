package io.github.nwrenger.elytraspeedcap.network;

import org.jspecify.annotations.NonNull;

import io.github.nwrenger.elytraspeedcap.ElytraSpeedCap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SyncMaxSpeedPayload(double maxSpeed) implements CustomPacketPayload {

    // Vanilla packet ID (example-mod:sync_max_speed)
    @NonNull
    public static final Identifier SYNC_MAX_SPEED_PAYLOAD_ID = Identifier
            .fromNamespaceAndPath(ElytraSpeedCap.MOD_ID, "sync_max_speed");

    // Fabric / MC payload type
    public static final CustomPacketPayload.@NonNull Type<@NonNull SyncMaxSpeedPayload> ID = new CustomPacketPayload.Type<>(
            SYNC_MAX_SPEED_PAYLOAD_ID);

    // How to (de)serialize this payload
    @NonNull
    public static final StreamCodec<RegistryFriendlyByteBuf, @NonNull SyncMaxSpeedPayload> CODEC = StreamCodec
            .composite(
                    ByteBufCodecs.DOUBLE, // write/read a double
                    SyncMaxSpeedPayload::maxSpeed,
                    (Double ms) -> new SyncMaxSpeedPayload(ms));

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
