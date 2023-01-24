package com.mega.me.common.enc.base;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.Map;

public class EncManager {
    public static EquipmentSlot[] ES_COMBAT = {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};

    public static EquipmentSlot MAIN_HAND = EquipmentSlot.MAINHAND;

    public static EquipmentSlot OFF_HAND = EquipmentSlot.OFFHAND;

    public static EquipmentSlot[] ES_ARMOR = {EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.LEGS};

    public static Map<String, Integer> initEnchantMap(Entity entity, Map<String, Integer> map) {
        EncBase.OBJs.forEach(encBase -> encBase.initHitMap(entity, map));
        return map;
    }
}
