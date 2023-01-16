package xclient.mega.mixin;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xclient.mega.Main;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    public <T extends Entity> void sr(T p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_, CallbackInfoReturnable<Boolean> cir) {
        if (!p_114491_.shouldRender(p_114493_, p_114494_, p_114495_)) {
            cir.setReturnValue(false);
        } else if (Main.renderPlayerOutline && p_114491_ instanceof Player)
            cir.setReturnValue(true);
    }
}
