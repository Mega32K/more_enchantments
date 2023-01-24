package com.mega.me.common.enc.base;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EncInfo {
    public Enchantment.Rarity rarity;
    public EnchantmentCategory category;
    public EquipmentSlot[] slots;
    public EncInfo(Enchantment.Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        this.rarity = rarity;
        this.category = category;
        this.slots = slots;
    }

    public static EncInfo create(Enchantment.Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        return new EncInfo(rarity, category, slots);
    }

    public enum EncGetterType {
        FIRST,
        MAX,
        MIN,
        RAND
    }
}
