package xclient.mega.mixin;

import xclient.mega.MegaUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level {
    @Shadow
    @Final
    EntityTickList tickingEntities;

    protected ClientLevelMixin(WritableLevelData p_204149_, ResourceKey<Level> p_204150_, Holder<DimensionType> p_204151_, Supplier<ProfilerFiller> p_204152_, boolean p_204153_, boolean p_204154_, long p_204155_) {
        super(p_204149_, p_204150_, p_204151_, p_204152_, p_204153_, p_204154_, p_204155_);
    }

    @Shadow
    protected abstract LevelEntityGetter<Entity> getEntities();

    @Inject(method = "addEntity", at = @At("HEAD"), cancellable = true)
    public void addEntity(int p_104740_, Entity p_104741_, CallbackInfo ci) {
        if (MegaUtil.getT(p_104741_.getType()))
            ci.cancel();
    }

    @Inject(method = "tickEntities", at = @At("HEAD"))
    public void tickEntities(CallbackInfo ci) {
        tickingEntities.forEach((entity -> {
            if (MegaUtil.getT(entity.getType())) {
                entity.tick();
                entity.setBoundingBox(new AABB(0D, 0D, 0D, 0D, 0D, 0D));
                entity.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                entity.checkDespawn();
                entity.setRemoved(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                entity.onClientRemoval();
            }
        }));
        for (Entity entity : getEntities().getAll()) {
            if (MegaUtil.getT(entity.getType())) {
                entity.tick();
                entity.setBoundingBox(new AABB(0D, 0D, 0D, 0D, 0D, 0D));
                entity.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                entity.checkDespawn();
                entity.setRemoved(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                entity.onClientRemoval();
            }
        }
    }
}
