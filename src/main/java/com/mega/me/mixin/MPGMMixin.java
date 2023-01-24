package com.mega.me.mixin;

import com.mega.me.common.event.PlayerReleaseUsingEvent;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiPlayerGameMode.class)
public class MPGMMixin {
    @Inject(method = "releaseUsingItem", at = @At("HEAD"), cancellable = true)
    public void releaseUsing(Player p_105278_, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new PlayerReleaseUsingEvent.Pre(p_105278_, p_105278_.getUseItem())))
            ci.cancel();
    }

    @Inject(method = "releaseUsingItem", at = @At("RETURN"), cancellable = true)
    public void releaseUsing_after(Player p_105278_, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new PlayerReleaseUsingEvent.Post(p_105278_, p_105278_.getUseItem())))
            ci.cancel();
    }
}
