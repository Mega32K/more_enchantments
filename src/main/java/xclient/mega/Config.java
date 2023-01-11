package xclient.mega;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.ConfigValue<List<String>> ENTITY_BANNED_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> EFFECT_BANNED_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> STRUCTURE_BANNED_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> TEXTURE_BANNED_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> TEXTURE_NAME_BANNED_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> PARTICLE_BANNED_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> ITEM_BANNED_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> ITEM_ENCHANTMENT_BANNED_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> ITEM_CLASS_BANNED_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> EXPLOSION_BANNED_LIST;
    public static ForgeConfigSpec.BooleanValue ITEM_BANNED_EMPTY;
    public static ForgeConfigSpec.ConfigValue<List<String>> DIMENSION_BANNED_LIST;
    public static ForgeConfigSpec.ConfigValue<List<String>> SPECIAL_EVENTS_BANNED_LIST;

    public static ForgeConfigSpec.ConfigValue<Float> killaura_range;
    public static ForgeConfigSpec.ConfigValue<Boolean> killaura_attackPlayer;
    public static ForgeConfigSpec.ConfigValue<Boolean> auto_attack;
    public static ForgeConfigSpec.ConfigValue<Boolean> enableHurtEffect;
    public static ForgeConfigSpec.ConfigValue<Boolean> superKillAura;
    public static ForgeConfigSpec.ConfigValue<Float> reach_distance;
    public static ForgeConfigSpec.ConfigValue<Boolean> no_fall;
    public static ForgeConfigSpec.ConfigValue<Boolean> respawn;
    public static ForgeConfigSpec.ConfigValue<Boolean> fly;
    public static ForgeConfigSpec.ConfigValue<Boolean> sprint;
    public static ForgeConfigSpec.ConfigValue<Boolean> renderPlayerOutline;
    public static ForgeConfigSpec.ConfigValue<Boolean> full_bright;
    public static ForgeConfigSpec.ConfigValue<Boolean> dner;
    public static ForgeConfigSpec.ConfigValue<Boolean> quickly_place;
    public static ForgeConfigSpec.ConfigValue<Boolean> key_display;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("Banned Settings").push("banedList");
        ENTITY_BANNED_LIST = COMMON_BUILDER.define("entityTypes", new ArrayList<>());
        ITEM_BANNED_LIST = COMMON_BUILDER.define("items", new ArrayList<>());
        ITEM_CLASS_BANNED_LIST = COMMON_BUILDER.define("itemClasses", new ArrayList<>());
        PARTICLE_BANNED_LIST = COMMON_BUILDER.define("particleTypes", new ArrayList<>());
        ITEM_ENCHANTMENT_BANNED_LIST = COMMON_BUILDER.define("enchantment", new ArrayList<>());
        EFFECT_BANNED_LIST = COMMON_BUILDER.define("effects", new ArrayList<>());
        STRUCTURE_BANNED_LIST = COMMON_BUILDER.define("structures", new ArrayList<>());
        TEXTURE_BANNED_LIST = COMMON_BUILDER.define("textures", new ArrayList<>());
        TEXTURE_NAME_BANNED_LIST = COMMON_BUILDER.define("texture_names", new ArrayList<>());
        EXPLOSION_BANNED_LIST = COMMON_BUILDER.define("explosionTypes", new ArrayList<>());
        DIMENSION_BANNED_LIST = COMMON_BUILDER.define("dimensions", new ArrayList<>());
        SPECIAL_EVENTS_BANNED_LIST = COMMON_BUILDER.define("special_events", new ArrayList<>());
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("Banned Settings").push("ban logic");
        ITEM_BANNED_EMPTY = COMMON_BUILDER.define("isEmpty", false);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("X-Client").push("modules");
        killaura_range = COMMON_BUILDER.define("killaura_range", 3.8F);
        killaura_attackPlayer = COMMON_BUILDER.define("killaura_attackPlayer", false);
        auto_attack = COMMON_BUILDER.define("auto_attack", false);
        enableHurtEffect = COMMON_BUILDER.define("enableHurtEffect", false);
        superKillAura = COMMON_BUILDER.define("superKillAura", false);
        no_fall = COMMON_BUILDER.define("no_fall", false);
        respawn = COMMON_BUILDER.define("respawn", false);
        reach_distance = COMMON_BUILDER.define("reach_distance", 0F);
        fly = COMMON_BUILDER.define("fly", false);
        sprint = COMMON_BUILDER.define("sprint", false);
        renderPlayerOutline = COMMON_BUILDER.define("renderPlayerOutline", false);
        full_bright = COMMON_BUILDER.define("full_bright", false);
        dner = COMMON_BUILDER.define("dner", false);
        key_display = COMMON_BUILDER.define("key_display", false);
        quickly_place = COMMON_BUILDER.define("quickly_place", false);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
