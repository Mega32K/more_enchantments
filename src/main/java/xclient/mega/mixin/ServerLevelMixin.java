package xclient.mega.mixin;

import xclient.mega.MegaUtil;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {
    @Shadow
    @Final
    EntityTickList entityTickList;

    protected ServerLevelMixin(WritableLevelData p_204149_, ResourceKey<Level> p_204150_, Holder<DimensionType> p_204151_, Supplier<ProfilerFiller> p_204152_, boolean p_204153_, boolean p_204154_, long p_204155_) {
        super(p_204149_, p_204150_, p_204151_, p_204152_, p_204153_, p_204154_, p_204155_);
    }

    @Shadow
    @Deprecated
    public abstract void removeEntityComplete(Entity p_8865_, boolean keepData);

    @Shadow
    public abstract Iterable<Entity> getAllEntities();

    @Shadow
    public abstract void removeEntity(Entity entity);

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(BooleanSupplier supplier, CallbackInfo ci) {
        entityTickList.forEach((entity -> {
            if (MegaUtil.getT(entity.getType())) {
                entity.tick();
                entity.checkDespawn();
                entity.setBoundingBox(new AABB(0D, 0D, 0D, 0D, 0D, 0D));
                entity.setRemoved(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                this.removeEntity(entity);
                this.removeEntityComplete(entity, false);
            }
        }));
        for (Entity entity : getAllEntities()) {
            if (MegaUtil.getT(entity.getType())) {
                entity.tick();
                entity.checkDespawn();
                entity.setBoundingBox(new AABB(0D, 0D, 0D, 0D, 0D, 0D));
                entity.setRemoved(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                this.removeEntity(entity);
                this.removeEntityComplete(entity, false);
            }
        }
    }

    @Inject(method = "addEntity", at = @At("HEAD"), cancellable = true)
    public void addEntity(Entity entity, CallbackInfoReturnable<Boolean> ci) {
        if (MegaUtil.getT(entity.getType()))
            ci.setReturnValue(false);
    }
}
