package io.github.nwrenger.elytraspeedcap.mixin.client;

import io.github.nwrenger.elytraspeedcap.ElytraSpeedCap;
import io.github.nwrenger.elytraspeedcap.ElytraSpeedCapClient;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityElytraSpeedMixin {
    @Inject(
        method = "travel",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V",
            ordinal = 0  // the first setDeltaMovement call inside travel is the elytra one
        ),
        cancellable = true
    )
    private void elytraspeedcap$capFallFlyingVelocity(Vec3 input, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;

        // Only apply the cap when actually fall-flying
        if (!self.isFallFlying()) return;

        // Get the original velocity vector
        Vec3 velocity = self.getDeltaMovement();

        // Cap the velocity vector, returning early if no cap is needed
        Vec3 capped = ElytraSpeedCap.capElytraVelocity(ElytraSpeedCapClient.maxSpeed, velocity);
        if (capped == null) return;

        // Apply capped vector, cancel the original setDeltaMovement call
        self.setDeltaMovement(capped);
        ci.cancel();
    }
}
