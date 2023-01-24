package com.mega.me.common.enc.bow;

import com.mega.me.common.enc.base.EncBase;
import com.mega.me.common.enc.base.EncInfo;
import com.mega.me.common.event.ProjectileShootEvent;
import com.mega.me.common.registry.EncRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

public class EncBombArrow extends EncBase {
    public static String ENTITY_ENC_NBT = "bomb_arrow";
    public static String ENTITY_ENC_LEVEL = "arrow_enchant_effect_bombarrow_level";

    public EncBombArrow(EncInfo i) {
        super(i);
    }

    @Override
    public void initHitMap(Entity target, Map<String, Integer> map) {
        map.put(target.getPersistentData().getString(EncBombArrow.ENTITY_ENC_NBT), target.getPersistentData().getInt(EncBombArrow.ENTITY_ENC_LEVEL));
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
        return 3;
    }

    @Override
    public int getMinCost(int p_44679_) {
        return super.getMinCost(p_44679_) + p_44679_;
    }

    @Override
    public void arrowHitBlock(Level world, BlockPos pos, Map<Enchantment, Integer> element) {
        for (Enchantment e : element.keySet()) {
            if (!e.equals(this))
                return;
            int level = element.get(e);
            world.explode(null, pos.getX(), pos.getY(), pos.getZ(), level *3, Explosion.BlockInteraction.NONE);
        }
    }


    @Override
    protected boolean checkCompatibility(Enchantment p_44690_) {
        return super.checkCompatibility(p_44690_);
    }
}
