package com.mega.me.mixin;

import com.mega.me.common.enc.base.EncBase;
import com.mega.me.common.enc.base.IPlayerEnc;
import com.mega.me.common.event.PlayerUpdateEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick_head(CallbackInfo ci) {
        EncBase.OBJs.forEach(encBase -> {
            if (encBase instanceof IPlayerEnc iPlayerEnc)
                iPlayerEnc.playerTick((Player) (Object)this, false);
        });;
        if (MinecraftForge.EVENT_BUS.post(new PlayerUpdateEvent.Pre((Player) (Object)this)))
            ci.cancel();
    }

    @Inject(method = "tick", at = @At("RETURN"), cancellable = true)
    public void tick_return(CallbackInfo ci) {
        EncBase.OBJs.forEach(encBase -> {
            if (encBase instanceof IPlayerEnc iPlayerEnc)
                iPlayerEnc.playerTick((Player) (Object)this, false);
        });
        if (MinecraftForge.EVENT_BUS.post(new PlayerUpdateEvent.Post((Player) (Object)this)))
            ci.cancel();
    }
}
