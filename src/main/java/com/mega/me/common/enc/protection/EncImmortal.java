package com.mega.me.common.enc.protection;

import com.mega.me.common.enc.base.EncBase;
import com.mega.me.common.enc.base.EncInfo;
import com.mega.me.common.enc.base.EncManager;
import com.mega.me.util.PU;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Random;

public class EncImmortal extends EncBase {
    public EncImmortal(EncInfo i) {
        super(i);
    }

    @SubscribeEvent
    public void livingPlayerDeath(LivingDeathEvent event) {
        List<ItemStack> armor = getArmor(event.getEntityLiving());
        if (event.getEntityLiving() instanceof Player player) {
            int maxLevel = getEnchantLevel(player, EncInfo.EncGetterType.MAX, EncManager.ES_ARMOR);
            if (maxLevel > 0) {
                Random random = new Random();
                if (random.nextDouble() <= maxLevel * 0.2 - 0.05) {
                    player.setHealth(Math.max(1, player.getHealth()));
                    player.heal(5 + maxLevel * 3);
                    player.level.playSound(null, player, SoundEvents.TOTEM_USE, SoundSource.PLAYERS,  1, 1);
                    PU pu = new PU(player.level, ParticleTypes.DRAGON_BREATH, player.getX(), player.getY() + 0.5, player.getZ());
                    pu.spawnHorizontalCircle(0.2, 1);
                    event.setCanceled(true);
                }
            }
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
