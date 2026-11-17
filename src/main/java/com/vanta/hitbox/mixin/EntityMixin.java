package com.vanta.hitbox.mixin;

import com.vanta.hitbox.HitboxConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "getBoundingBox", at = @At("RETURN"), cancellable = true)
    private void expandHitbox(CallbackInfoReturnable<Box> cir) {
        if (!HitboxConfig.enabled) return;
        if (!((Object) this instanceof LivingEntity)) return;
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if ((Object) this == client.player) return;

        float m = HitboxConfig.multiplier;
        if (m <= 1.0f) return;

        Box o = cir.getReturnValue();
        double cx = (o.minX + o.maxX) * 0.5;
        double cy = (o.minY + o.maxY) * 0.5;
        double cz = (o.minZ + o.maxZ) * 0.5;
        double hx = (o.maxX - o.minX) * 0.5 * m;
        double hy = (o.maxY - o.minY) * 0.5 * m;
        double hz = (o.maxZ - o.minZ) * 0.5 * m;

        cir.setReturnValue(new Box(
            cx - hx, cy - hy, cz - hz,
            cx + hx, cy + hy, cz + hz
        ));
    }
}
