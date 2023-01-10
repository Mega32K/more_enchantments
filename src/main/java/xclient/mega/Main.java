package xclient.mega;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.lwjgl.glfw.GLFW;
import xclient.mega.mod.Module;
import xclient.mega.mod.ModuleManager;
import xclient.mega.utils.RainbowFont;
import xclient.mega.utils.RendererUtils;

import java.util.HashSet;
import java.util.Set;

@Mod("x_client")
public class Main {
    public static String version = "V1.1";

    public static Saver<Integer> GAMMA;

    public static boolean enable_display_info = false;

    public static boolean killaura_displayInfo = false;
    public static float killaura_range = 3.8F;
    public static boolean killaura_attackPlayer = true;

    public static Set<Player> renderPlayers = new HashSet<>();
    public static boolean auto_attack = false;
    public static boolean enableHurtEffect = true;
    public static boolean superKillAura = false;
    public static float reach_distance = 0;
    public static boolean no_fall;
    public static boolean respawn;
    public static boolean fly;
    public static boolean sprint;
    public static boolean renderPlayerOutline;
    public static boolean full_bright;
    public static boolean dner;

    public static Module<?> CLIENT;
    public static Module<Boolean> AUTO_ATTACK;
    public static Module<Boolean> ENABLE_HURT_EFFECT;
    public static Module<Boolean> SUPER_KILL_AURA;
    public static Module<Float> REACH;
    public static Module<Boolean> FLY;
    public static Module<Boolean> NO_FALL;
    public static Module<Boolean> RESPAWN;
    public static Module<Boolean> SPRINT;
    public static Module<Boolean> RENDER_OUTLINE;
    public static Module<Boolean> FULL_BRIGHT;
    public static Module<Boolean> DISABLE_NEGATIVE_EFFECT_RENDERER;

    public static Module<Float> SUPER_KILL_AURA$RANGE;
    public static Module<Boolean> SUPER_KILL_AURA$ATTACK_PLAYER;

    public static KeyMapping DISPLAY_INFO =  new KeyMapping("key.info",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_F9,
            "key.category.x_client");

    public static KeyMapping OPEN =  new KeyMapping("key.message",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_J,
            "key.category.x_client");


    public static void registerKey(KeyMapping... keyMappings) {
        for (KeyMapping key : keyMappings)
            ClientRegistry.registerKeyBinding(key);
    }

    public Main() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        MinecraftForge.EVENT_BUS.register(this);
        Networking.registerMessage();
        registerKey(DISPLAY_INFO, OPEN);
        setModules();
    }

    public static void setModules() {
        ModuleManager.modules.clear();
        ModuleManager.configuration_father_modules.clear();
        CLIENT = new Module<>("[Forge]X-Client:1.0").setFont(RainbowFont.INS);
        AUTO_ATTACK = new Module<>("Auto Attack", auto_attack, false, RainbowFont.NORMAL).setLeft((d -> {auto_attack = !auto_attack;System.out.println(auto_attack);}));
        ENABLE_HURT_EFFECT = new Module<>("Hurt Effect", enableHurtEffect, false, RainbowFont.NORMAL).setLeft((d -> enableHurtEffect = !enableHurtEffect));

        SUPER_KILL_AURA = new Module<>("Super KillAura", superKillAura, false, RainbowFont.NORMAL).setLeft((d -> superKillAura = !superKillAura)).setRight(d -> killaura_displayInfo = !killaura_displayInfo);
        SUPER_KILL_AURA$RANGE = new Module<>("KillAura Range", killaura_range, false, RainbowFont.NORMAL).setLeft((d -> killaura_range += 0.5F)).setRight(d -> {
            if (killaura_range > 0)
                killaura_range -= 0.1F;
        }).unaddToList();
        SUPER_KILL_AURA$ATTACK_PLAYER = new Module<>("KillAura AttackPlayer", killaura_attackPlayer, false, RainbowFont.NORMAL).setLeft((d -> killaura_attackPlayer = !killaura_attackPlayer)).unaddToList();

        REACH = new Module<>("Reach", reach_distance, false, RainbowFont.NORMAL).setLeft((d -> reach_distance += 0.1F)).setRight((d -> {
            if (reach_distance > 0F)
                reach_distance -= 0.1F;
        }));
        FLY = new Module<>("Fly", fly, false, RainbowFont.NORMAL).setLeft((d -> {
            fly = !fly;
            Minecraft mc = Minecraft.getInstance();
            Abilities abilities = new Abilities();
            abilities.mayfly = fly;
            if (mc.player != null) {
                mc.player.getAbilities().mayfly = fly;
                mc.player.connection.send(new ServerboundPlayerAbilitiesPacket(abilities));
            }
        }));
        NO_FALL = new Module<>("No Fall", no_fall, false, RainbowFont.NORMAL).setLeft((d -> no_fall = !no_fall));
        RESPAWN = new Module<>("Respawn", respawn, false, RainbowFont.NORMAL).setLeft((d -> respawn = !respawn));
        SPRINT = new Module<>("Sprint", sprint, false, RainbowFont.NORMAL).setLeft((d -> sprint = !sprint));
        RENDER_OUTLINE = new Module<>("Render Players Outline", renderPlayerOutline, false, RainbowFont.NORMAL).setLeft((d -> renderPlayerOutline = !renderPlayerOutline));
        FULL_BRIGHT = new Module<>("Full Bright", full_bright, false, RainbowFont.NORMAL).setLeft((d -> {
            full_bright = !full_bright;
            GAMMA = new Saver<>((int)Minecraft.getInstance().options.gamma);
            if (full_bright) {
                Minecraft.getInstance().options.gamma = 100D;
            } else Minecraft.getInstance().options.gamma = GAMMA.getV();
        }));
        DISABLE_NEGATIVE_EFFECT_RENDERER = new Module<>("Disable NegativeEffect Rendering", dner, false, RainbowFont.NORMAL).setLeft((d -> dner = !dner));

    }


    @Mod.EventBusSubscriber(value = Dist.CLIENT)
    public static class TickKeys {

        @SubscribeEvent
        public static void input(InputEvent.KeyInputEvent event) {
            if (DISPLAY_INFO.consumeClick())
                enable_display_info = !enable_display_info;
        }
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT)
    public static class KeyEvents {
        @SubscribeEvent
        public static void playerUpdate(TickEvent.PlayerTickEvent event) {
            if (event.side.isClient()) {
                if (sprint)
                    event.player.setSprinting(true);
            }
        }

        @SubscribeEvent
        public static void onKey(InputEvent.KeyInputEvent event) {
            Minecraft mc = Minecraft.getInstance();
            if (OPEN.consumeClick()) {
                Minecraft.getInstance().setScreen(new XScreen());
            }
            if (mc.player != null) {
                Abilities abilities = new Abilities();
                abilities.mayfly = true;
                mc.player.connection.send(new ServerboundPlayerAbilitiesPacket(abilities));
            }
        }

        public static int client_ticks = 0;

        @SubscribeEvent
        public static void clientTick(TickEvent.ClientTickEvent event) {
            client_ticks++;
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            Entity point = mc.crosshairPickEntity;
            if (player != null) {
                if (fly) {
                    Abilities abilities = new Abilities();
                    abilities.mayfly = true;
                    player.getAbilities().mayfly = true;
                    player.connection.send(new ServerboundPlayerAbilitiesPacket(abilities));
                }
                if (client_ticks % 20 == 0) {
                    if (no_fall && player.fallDistance > 0.5F)
                        player.connection.send(new ServerboundMovePlayerPacket.StatusOnly(true));
                }
                if (superKillAura && client_ticks % 20 == 0) {
                    for (Entity entity : xclient.mega.utils.MegaUtil.getEntitiesToWatch(150, player)) {
                        if (entity instanceof LivingEntity livingEntity && point != player && !livingEntity.isDeadOrDying() && !livingEntity.isInvisible() && player.distanceTo(entity) <= killaura_range && livingEntity.deathTime <= 0) {
                            if (mc.gameMode != null ) {
                                if (!killaura_attackPlayer || (killaura_attackPlayer && !(livingEntity instanceof Player)))
                                    mc.gameMode.attack(player, entity);
                            }
                            player.swing(InteractionHand.MAIN_HAND);
                        }
                    }
                }
            }
            if (point != null) {
                if (player != null)
                    if (auto_attack && client_ticks % 7 == 0) {
                        if (point instanceof LivingEntity livingEntity && point != player && !livingEntity.isDeadOrDying() && !livingEntity.isInvisible() && player.distanceTo(point) <= 3.8F && livingEntity.deathTime <= 0) {
                            if (mc.gameMode != null) {
                                mc.gameMode.attack(player, point);
                                player.swing(InteractionHand.MAIN_HAND);
                            }
                        }
                    }
            }
        }

        @SubscribeEvent
        public static void renderTick(RenderGameOverlayEvent event) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            int rainbow_colour = (int) ((int) Util.getMillis() / 1000F % 1);
            //Entity point = mc.crosshairPickEntity;
            Entity toWatch = xclient.mega.utils.MegaUtil.getEntityToWatch(20, player);
            Font font = mc.font;
            PoseStack stack = new PoseStack();
                if (toWatch != null) {
                    if (toWatch instanceof LivingEntity living_point)
                        InventoryScreen.renderEntityInInventory(105, 100, 30, 45, 45, living_point);
                    else font.drawShadow(stack, "No model", 105, 100, rainbow_colour);
                    if (toWatch instanceof LivingEntity livingEntity) {
                        font.drawShadow(stack, "Health:" + String.format("%.2f", livingEntity.getHealth()) + "/" + String.format("%.2f", livingEntity.getMaxHealth()), 105, (int) toWatch.getEyeHeight() * 5 + 118, RendererUtils.WHITE);
                        font.drawShadow(stack, "MainHandItem:" + livingEntity.getMainHandItem().getDisplayName().getString(), 105, (int) toWatch.getEyeHeight() * 5 + 126, RendererUtils.WHITE);
                    }
                }
            int x = 0; int y = 0;
            if (enable_display_info && !(mc.screen instanceof XScreen)) {
                for (Module<?> module :ModuleManager.modules) {
                    module.render(stack, x, y);
                    y+=11;
                }
            }
        }
    }

    @SubscribeEvent
    public void writeList(ClientPlayerNetworkEvent.LoggedInEvent event) {
        MegaUtil.read();
    }
}
