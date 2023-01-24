package com.mega.me.common.enc.bow;

import com.mega.me.common.enc.base.EncBase;
import com.mega.me.common.enc.base.EncInfo;
import com.mega.me.common.event.ProjectileShootEvent;
import com.mega.me.common.registry.EncRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class EncLightingArrow extends EncBase {
    public static String ENTITY_ENC_NBT = "lighting_arrow";
    public static String ENTITY_ENC_LEVEL = "arrow_enchant_effect_lighting_arrow_level";
    public EncLightingArrow(EncInfo i) {
        super(i);
    }

    @Override
    public void initHitMap(Entity target, Map<String, Integer> map) {
        map.put(target.getPersistentData().getString(ENTITY_ENC_NBT), target.getPersistentData().getInt(ENTITY_ENC_LEVEL));
    }

    @SubscribeEvent
    public void onShoot(ProjectileShootEvent event) {
        if (event.getOwner() instanceof LivingEntity livingEntity) {
            event.getProjectile().getPersistentData().putString(ENTITY_ENC_NBT, getString());
            event.getProjectile().getPersistentData().putInt(ENTITY_ENC_LEVEL, getEnchantLevel(livingEntity, EncInfo.EncGetterType.MAX, EquipmentSlot.MAINHAND));
        }
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinCost(int p_44679_) {
        return super.getMinCost(p_44679_) + 4;
    }

    @Override
    public void arrowHitBlock(Level world, BlockPos pos, Map<Enchantment, Integer> element) {
        for (Enchantment e : element.keySet()) {
            System.out.println(e.getRegistryName());
            if (e.equals(this)) {
                int level = element.get(e);
                System.out.println("Lighting!!" + level);
                for (int i=0;i<level;i++) {
                    LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(world);
                    lightningBolt.setDamage(level * 2.5F);
                    lightningBolt.setPos(pos.getX(), pos.getY(), pos.getZ());
                    lightningBolt.onAddedToWorld();
                    world.addFreshEntity(lightningBolt);
                }
            }
        }
    }

    @Override
    public void arrowHitEntity(Level world, @Nullable Entity target, Map<Enchantment, Integer> element) {
        super.arrowHitEntity(world, target, element);
        for (Enchantment e : element.keySet()) {
            if (e.equals(this)) {
                int level = element.get(e);
                for (int i=0;i<level;i++) {
                    LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(world);
                    lightningBolt.setDamage(level * 2.5F);
                    lightningBolt.setPos(target.getX(), target.getY(), target.getZ());
                    lightningBolt.onAddedToWorld();
                    world.addFreshEntity(lightningBolt);
                }
                if (target instanceof LivingEntity living) {
                    living.hurt(DamageSource.LIGHTNING_BOLT, level * 2F);
                }
            }
        }
    }

    @Override
    protected boolean checkCompatibility(Enchantment p_44690_) {
        return super.checkCompatibility(p_44690_) && p_44690_ != EncRegistry.BOMB_ARROW.get();
    }
}
