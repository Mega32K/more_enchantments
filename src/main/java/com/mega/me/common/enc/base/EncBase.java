package com.mega.me.common.enc.base;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EncBase extends Enchantment {
    public static List<EncBase> OBJs = new ArrayList<>();

    public EncBase(EncInfo i) {
        super(i.rarity, i.category, i.slots);
        MinecraftForge.EVENT_BUS.register(this);
        OBJs.add(this);
    }

    public String getString() {
        return getRegistryName() != null ? getRegistryName().getPath() : "";
    }

    public static String getString(ResourceLocation resourceLocation) {
        if (resourceLocation == null)
            return "";
        return resourceLocation.getPath();
    }

    public void initHitMap(Entity target, Map<String, Integer> map) {

    }

    public void arrowHitBlock(Level world, BlockPos pos, Map<Enchantment, Integer> element) {
    }

    public void arrowHitEntity(Level world, @Nullable Entity target, Map<Enchantment, Integer> element) {
    }

    public static List<ItemStack> getArmor(LivingEntity l) {
        List<ItemStack> stacks = new ArrayList<>();
        l.getArmorSlots().forEach(stacks::add);
        return stacks;
    }

    public boolean livingArmorEnchantment(LivingEntity livingEntity) {
        return livingHasEnchantment(livingEntity, EncManager.ES_ARMOR);
    }

    public boolean livingHasEnchantment(LivingEntity livingEntity, EquipmentSlot... slots) {
        List<ItemStack> stacks = new ArrayList<>();
        for (EquipmentSlot slot : slots) {
            stacks.add(livingEntity.getItemBySlot(slot));
        }
        for (ItemStack s : stacks)
            if (s.isEnchanted()) {
                Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(s);
                for (Enchantment enchantment : map.keySet()) {
                    if (enchantment.equals(this))
                        return true;
                }
            }
        return false;
    }

    public int getEnchantLevel(LivingEntity livingEntity, EncInfo.EncGetterType type, EquipmentSlot... slots) {
        List<ItemStack> stacks = new ArrayList<>();
        for (EquipmentSlot slot : slots) {
            stacks.add(livingEntity.getItemBySlot(slot));
        }
        if (type == EncInfo.EncGetterType.FIRST)
            for (ItemStack s : stacks)
                if (s.isEnchanted()) {
                    Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(s);
                    for (Enchantment enchantment : map.keySet()) {
                        if (enchantment.equals(this))
                            return EnchantmentHelper.getItemEnchantmentLevel(enchantment, s);
                    }
                }
        if (type == EncInfo.EncGetterType.MAX) {
            int max = 0;
            for (ItemStack s : stacks)
                if (s.isEnchanted()) {
                    Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(s);
                    for (Enchantment enchantment : map.keySet()) {
                        if (enchantment.equals(this)) {
                            int integer = EnchantmentHelper.getItemEnchantmentLevel(enchantment, s);
                            if (max < integer)
                                max = integer;
                        }
                    }
                }
            return max;
        }
        if (type == EncInfo.EncGetterType.MIN) {
            int min = 0;
            for (ItemStack s : stacks)
                if (s.isEnchanted()) {
                    Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(s);
                    for (Enchantment enchantment : map.keySet()) {
                        if (enchantment.equals(this)) {
                            int integer = EnchantmentHelper.getItemEnchantmentLevel(enchantment, s);
                            if (min > integer)
                                min = integer;
                        }
                    }
                }
            return min;
        }
        return 0;
    }

    public Map<ItemStack, Integer> getEnchantLevel(LivingEntity livingEntity, EquipmentSlot... slots) {
        List<ItemStack> stacks = new ArrayList<>();
        Map<ItemStack, Integer> levels = new HashMap<>();
        for (EquipmentSlot slot : slots) {
            stacks.add(livingEntity.getItemBySlot(slot));
        }
        for (ItemStack s : stacks)
            if (s.isEnchanted()) {
                Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(s);
                for (Enchantment enchantment : map.keySet()) {
                    if (enchantment.equals(this))
                        levels.put(s, EnchantmentHelper.getItemEnchantmentLevel(enchantment, s));
                }
            }
        return levels;
    }
}
