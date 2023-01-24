package com.mega.me.common.enc.base;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public interface EnchantDo {
    void run(Enchantment enc, ItemStack stack);
}
