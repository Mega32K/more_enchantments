package com.mega.me.common.enc.bow;

import com.mega.me.common.enc.base.EncBase;
import com.mega.me.common.enc.base.EncInfo;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EncPowerful extends EncBase {
    public EncPowerful(EncInfo i) {
        super(i);
    }

    @SubscribeEvent
    public void livingDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity e) {
            event.setAmount(event.getAmount() + getEnchantLevel(e, EncInfo.EncGetterType.MAX, EquipmentSlot.MAINHAND)*2);
        }
    }

    @Override
    public int getMinCost(int p_44679_) {
        return super.getMinCost(p_44679_) + 5;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
