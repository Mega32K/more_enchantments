package com.mega.me.common.enc.weapon;

import com.mega.me.common.enc.base.EncBase;
import com.mega.me.common.enc.base.EncInfo;
import com.mega.me.common.enc.base.EncManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EncCursedJumping extends EncBase {
    public EncCursedJumping(EncInfo i) {
        super(i);
    }

    @Override
    public void doPostAttack(LivingEntity p_44686_, Entity p_44687_, int p_44688_) {
        super.doPostAttack(p_44686_, p_44687_, p_44688_);
        p_44687_.setDeltaMovement(p_44686_.getDeltaMovement().x, p_44686_.getDeltaMovement().y + getEnchantLevel(p_44686_, EncInfo.EncGetterType.MAX, EncManager.MAIN_HAND) * 0.5D, p_44686_.getDeltaMovement().z);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinCost(int p_44679_) {
        return super.getMinCost(p_44679_) + 5;
    }
}
