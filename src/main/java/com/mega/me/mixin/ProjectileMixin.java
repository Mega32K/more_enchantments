package com.mega.me.mixin;

import com.mega.me.common.event.ProjectileShootEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public class ProjectileMixin {
    @Inject(method = "shootFromRotation", at = @At("HEAD"), cancellable = true)
    public void shoot(Entity p_37252_, float p_37253_, float p_37254_, float p_37255_, float p_37256_, float p_37257_, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new ProjectileShootEvent((Projectile) (Object) this, p_37252_, p_37253_, p_37254_, p_37255_, p_37256_, p_37257_)))
            ci.cancel();
    }
}
