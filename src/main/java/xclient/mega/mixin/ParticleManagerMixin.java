package xclient.mega.mixin;

import xclient.mega.MegaUtil;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.particles.ParticleOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Queue;

@Mixin(ParticleEngine.class)
public class ParticleManagerMixin {
    @Shadow
    @Final
    private Map<ParticleRenderType, Queue<Particle>> particles;

    @Inject(method = "makeParticle", at = @At("HEAD"), cancellable = true)
    public <T extends ParticleOptions> void addP(T p_107396_, double p_107397_, double p_107398_, double p_107399_, double p_107400_, double p_107401_, double p_107402_, CallbackInfoReturnable<Particle> cir) {
        if (MegaUtil.get(p_107396_.getType()))
            cir.setReturnValue(null);
    }

}
