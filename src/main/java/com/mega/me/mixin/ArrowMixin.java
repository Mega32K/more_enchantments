package com.mega.me.mixin;

import com.mega.me.common.enc.base.EncBase;
import com.mega.me.common.enc.base.EncManager;
import com.mega.me.common.enc.base.IArrowEnc;
import com.mega.me.common.enc.bow.EncBombArrow;
import com.mega.me.common.registry.EncRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.registries.RegistryObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(AbstractArrow.class)
public abstract class ArrowMixin extends Projectile {
    protected ArrowMixin(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        EncBase.OBJs.forEach(encBase -> {
            if (encBase instanceof IArrowEnc iArrowEnc) {
                iArrowEnc.arrowTick((AbstractArrow) (Object) this, true);
            }
        });
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick_before(CallbackInfo ci) {
        EncBase.OBJs.forEach(encBase -> {
            if (encBase instanceof IArrowEnc iArrowEnc)
                iArrowEnc.arrowTick((AbstractArrow) (Object)this, false);
        });
    }

    @Inject(method = "onHitBlock", at = @At("RETURN"))
    public void hitBlock(BlockHitResult p_36755_, CallbackInfo ci) {
        Map<String, Integer> map = new HashMap<>();
        EncManager.initEnchantMap(this, map);
        for (EncBase encBase : EncBase.OBJs) {
            Map<Enchantment, Integer> map2 = new HashMap<>();
            for (String key : map.keySet()) {
                for (RegistryObject<Enchantment> obj : EncRegistry.ENCHANTMENTS.getEntries()) {
                    if (EncBase.getString(obj.get().getRegistryName()).equals(key)) {
                        map2.put(obj.get(), map.get(key));
                    }
                }
            }
            encBase.arrowHitBlock(level, p_36755_.getBlockPos(), map2);
        }
    }

    @Inject(method = "onHitEntity", at = @At("RETURN"))
    public void onHitEntity(EntityHitResult p_36755_, CallbackInfo ci) {
        Map<String, Integer> map = new HashMap<>();
        EncManager.initEnchantMap(this, map);
        for (EncBase encBase : EncBase.OBJs) {
            Map<Enchantment, Integer> map2 = new HashMap<>();
            for (String key : map.keySet()) {
                for (RegistryObject<Enchantment> obj : EncRegistry.ENCHANTMENTS.getEntries()) {
                    if (EncBase.getString(obj.get().getRegistryName()).equals(key)) {
                        map2.put(obj.get(), map.get(key));
                    }
                }
            }
            encBase.arrowHitEntity(level, p_36755_.getEntity(), map2);
        }
    }

}
