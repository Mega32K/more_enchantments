package com.mega.me.common.enc.protection;

import com.mega.me.common.enc.base.EncBase;
import com.mega.me.common.enc.base.EncInfo;
import com.mega.me.common.enc.base.EncManager;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

public class EncMagicProtector extends EncBase {
    public EncMagicProtector(EncInfo i) {
        super(i);
    }

    @Override
    public int getMinCost(int p_44679_) {
        return super.getMinCost(p_44679_) + p_44679_;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @SubscribeEvent
    public void livingDamage(LivingDamageEvent event) {
        if (livingArmorEnchantment(event.getEntityLiving()) && reducible(event.getSource())) {
            float damage = event.getAmount()/4F;
            float allDamage = 0F;
            Map<ItemStack, Integer> map = getEnchantLevel(event.getEntityLiving(), EncManager.ES_ARMOR);
            for (Integer i : map.values()) {
                damage *= (1 - i * 0.2F);
                allDamage+=damage;
                damage = event.getAmount() / 4F;
            }
            event.setAmount(allDamage);
        }
    }

    public static boolean reducible(DamageSource source) {
        if (source.isBypassInvul())
            return false;
        if (source.isMagic())
            return true;
        if (source.isBypassMagic())
            return true;
        return source.isFire();
    }
}
