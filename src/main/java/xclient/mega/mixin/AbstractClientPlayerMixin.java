package xclient.mega.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import xclient.mega.Main;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {

    public AbstractClientPlayerMixin(Level p_36114_, BlockPos p_36115_, float p_36116_, GameProfile p_36117_) {
        super(p_36114_, p_36115_, p_36116_, p_36117_);
    }

    @Override
    public void tick() {
        super.tick();
        if (Main.renderPlayerOutline) {
            MobEffectInstance instance = new MobEffectInstance(MobEffects.GLOWING, 3, 1);
            addEffect(instance);
        }
    }
}
