package xclient.mega.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xclient.mega.Main;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    @Shadow
    @Final
    public ClientPacketListener connection;

    public LocalPlayerMixin(ClientLevel p_108548_, GameProfile p_108549_) {
        super(p_108548_, p_108549_);
    }

    @Inject(method = "tickDeath", at = @At("HEAD"), cancellable = true)
    public void tickDeath(CallbackInfo ci) {
        if (Main.respawn) {
            connection.send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
            ci.cancel();
        }
    }
}
