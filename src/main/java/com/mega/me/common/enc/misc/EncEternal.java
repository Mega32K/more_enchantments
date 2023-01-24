package com.mega.me.common.enc.misc;

import com.mega.me.common.enc.base.EncBase;
import com.mega.me.common.enc.base.EncInfo;
import com.mega.me.common.enc.base.IPlayerEnc;
import net.minecraft.world.entity.player.Player;

public class EncEternal extends EncBase implements IPlayerEnc {
    public EncEternal(EncInfo i) {
        super(i);
    }

    @Override
    public void playerTick(Player player, boolean pre) {
        getSlotItems(player).values().forEach(stack -> {
            if (stack.getTag() != null) {
                stack.getTag().putBoolean("Unbreakable", true);
            }
        });
    }

    @Override
    public int getMaxLevel() {
        return getMinLevel();
    }

    @Override
    public int getMinCost(int p_44679_) {
        return super.getMinCost(p_44679_) + p_44679_;
    }
}
