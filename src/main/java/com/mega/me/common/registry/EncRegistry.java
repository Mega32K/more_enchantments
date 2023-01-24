package com.mega.me.common.registry;

import com.mega.me.MoreEnchantments;
import com.mega.me.common.enc.bow.*;
import com.mega.me.common.enc.protection.EncImmortal;
import com.mega.me.common.enc.protection.EncMagicProtector;
import com.mega.me.common.enc.protection.EncProtector;
import com.mega.me.common.enc.base.EncManager;
import com.mega.me.common.enc.base.EncInfo;
import com.mega.me.common.enc.weapon.EncCursedJumping;
import com.mega.me.common.enc.weapon.EncLifesteal;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EncRegistry {
    public static DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MoreEnchantments.MOD_ID);

    public static RegistryObject<Enchantment> IMMORTAL = register("immortal", new EncImmortal(EncInfo.create(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR, EncManager.ES_ARMOR)));

    public static RegistryObject<Enchantment> PROTECTOR = register("protector", new EncProtector(EncInfo.create(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR, EncManager.ES_ARMOR)));

    public static RegistryObject<Enchantment> MAGIC_PROTECTOR = register("magic_protector", new EncMagicProtector(EncInfo.create(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR, EncManager.ES_ARMOR)));

    public static RegistryObject<Enchantment> LIFE_STEAL = register("lifesteal", new EncLifesteal(EncInfo.create(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EncManager.ES_COMBAT)));

    public static RegistryObject<Enchantment> BOMB_ARROW = register("bomb_arrow", new EncBombArrow(EncInfo.create(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.BOW, EncManager.ES_COMBAT)));

    public static RegistryObject<Enchantment> BULLSEYE = register("bullseye", new EncBullseye(EncInfo.create(Enchantment.Rarity.RARE, EnchantmentCategory.BOW, EncManager.ES_COMBAT)));

    public static RegistryObject<Enchantment> LIGHTING_ARROW = register("lighting_arrow", new EncLightingArrow(EncInfo.create(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.BOW, EncManager.ES_COMBAT)));

    public static RegistryObject<Enchantment> MULTIPLE = register("multiple", new EncMultiple(EncInfo.create(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.BOW, EncManager.ES_COMBAT)));

    public static RegistryObject<Enchantment> POWERFUL = register("powerful", new EncPowerful(EncInfo.create(Enchantment.Rarity.COMMON, EnchantmentCategory.BOW, EncManager.ES_COMBAT)));

    public static RegistryObject<Enchantment> CURSED_JUMPING = register("cursed_jumping", new EncCursedJumping(EncInfo.create(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, EncManager.ES_COMBAT)));

    public static Enchantment get(RegistryObject<Enchantment> ro) {
        return ro.get();
    }

    public static RegistryObject<Enchantment> register(String name, Enchantment o) {
        return ENCHANTMENTS.register(name, () ->o);
    }

    public static void registry() {
        ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
