package com.mega.me.common.enc.weapon;

import com.mega.me.common.enc.base.EncBase;
import com.mega.me.common.enc.base.EncInfo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EncLifesteal extends EncBase {
    public EncLifesteal(EncInfo i) {
        super(i);
    }
    @Override
    public void doPostAttack(LivingEntity p_44686_, Entity p_44687_, int p_44688_) {
        p_44686_.heal(p_44688_ * .015F * p_44686_.getMaxHealth());
        super.doPostAttack(p_44686_, p_44687_, p_44688_);
    }

    @Override
    public int getMaxLevel() {
        return 7;
    }

    @Override
    public int getMinCost(int p_44679_) {
        return 6 + p_44679_;
    }

}
