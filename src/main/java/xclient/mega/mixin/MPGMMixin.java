package xclient.mega.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xclient.mega.Main;

@Mixin(MultiPlayerGameMode.class)
public class MPGMMixin {
    @Inject(method = "getPickRange", at = @At("RETURN"), cancellable = true)
    private void getPickRange(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValueF() + Main.reach_distance);
    }
}
