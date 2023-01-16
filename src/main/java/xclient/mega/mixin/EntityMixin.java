package xclient.mega.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xclient.mega.MegaUtil;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow(remap = false)
    private boolean isAddedToWorld;

    @Shadow
    public abstract EntityType<?> getType();

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
        if (MegaUtil.getT(getType())) {
            isAddedToWorld = false;
            ci.cancel();
        }
    }

    @Inject(method = "baseTick", at = @At("HEAD"), cancellable = true)
    public void base(CallbackInfo ci) {
        if (MegaUtil.getT(getType()))
            ci.cancel();
    }

    @Inject(method = "isRemoved", at = @At("HEAD"), cancellable = true)
    public void isRemoved(CallbackInfoReturnable<Boolean> ci) {
        if (MegaUtil.getT(getType()))
            ci.setReturnValue(true);
    }
}
