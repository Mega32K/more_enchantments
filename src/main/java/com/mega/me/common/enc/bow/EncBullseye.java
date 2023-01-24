package com.mega.me.common.enc.bow;

import com.mega.me.common.enc.base.EncBase;
import com.mega.me.common.enc.base.EncInfo;
import com.mega.me.common.enc.base.IArrowEnc;
import com.mega.me.common.event.ProjectileShootEvent;
import com.mega.me.util.MegaUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

public class EncBullseye extends EncBase implements IArrowEnc {
    public static String ENTITY_ENC_NBT = "bullseye";
    public static String ENTITY_ENC_LEVEL = "arrow_enchant_effect_bullseye_level";
    public EncBullseye(EncInfo i) {
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
    public void arrowTick(AbstractArrow abstractArrow, boolean pre) {
        Entity owner = abstractArrow.getOwner();
        if (pre &&  !abstractArrow.getPersistentData().getBoolean("inGround") && owner instanceof Player player && abstractArrow.getPersistentData().getInt(ENTITY_ENC_LEVEL) > 0) {
            int level = abstractArrow.getPersistentData().getInt(ENTITY_ENC_LEVEL);
            System.out.println(level);
            Entity target = MegaUtil.getEntityToWatch(level, player);
            if (target != null && abstractArrow.distanceTo(target ) < 7 * level)
                MegaUtil.motionEtoAnotherE(abstractArrow, target);
        }
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int p_44679_) {
        return super.getMinCost(p_44679_) + 7;
    }
}
