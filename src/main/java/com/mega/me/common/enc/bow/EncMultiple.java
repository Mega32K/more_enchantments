package com.mega.me.common.enc.bow;

import com.mega.me.common.enc.base.EncBase;
import com.mega.me.common.enc.base.EncInfo;
import com.mega.me.common.enc.base.EncManager;
import com.mega.me.common.event.ProjectileShootEvent;
import com.mega.me.mixin.EntityAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;
import java.util.UUID;

public class EncMultiple extends EncBase {
    public EncMultiple(EncInfo i) {
        super(i);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int p_44679_) {
        return super.getMinCost(p_44679_) + p_44679_;
    }

    public static double getRandomY(double y, double d, Random r) {
        return y + (d * r.nextDouble() - d/2);
    }

    @SubscribeEvent
    public void onShoot(ProjectileShootEvent event) {
        Random r = new Random();
        if (event.getOwner() instanceof LivingEntity livingEntity) {
            int level = getEnchantLevel(livingEntity, EncInfo.EncGetterType.MAX, EncManager.ES_COMBAT);
            for (int i=0;i<level;i++) {
                Projectile abstractArrow = EntityType.ARROW.create(event.getProjectile().level);
                if (abstractArrow != null) {
                    ((EntityAccessor) abstractArrow).set(event.getProjectile().getPersistentData());
                    abstractArrow.setUUID(new UUID(r.nextInt(114), r.nextInt(514)));
                    abstractArrow.setPos(abstractArrow.getX() + livingEntity.getLookAngle().x * 3, getRandomY(abstractArrow.getY(), 1, r), abstractArrow.getZ() +  + livingEntity.getLookAngle().z * 3);
                    abstractArrow.setOwner(event.getOwner());
                    abstractArrow.shootFromRotation(event.getOwner(), event.getXrot(), event.getYrot(), event.getA(), event.getSpeedScale(), event.getSpeedLevel());
                }
            }
        }
    }
}
