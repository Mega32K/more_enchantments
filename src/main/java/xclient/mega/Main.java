package xclient.mega;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.lwjgl.glfw.GLFW;
import xclient.mega.event.RenderEvent;
import xclient.mega.mod.Module;
import xclient.mega.mod.ModuleManager;
import xclient.mega.utils.RainbowFont;
import xclient.mega.utils.Render2DUtil;
import xclient.mega.utils.RendererUtils;
import xclient.mega.utils.TimeHelper;

import java.awt.*;
import java.util.*;
import java.util.List;

@Mod("x_client")
public class Main {
    public static String version = "V1.1";

    public static Saver<Integer> GAMMA;
    public static TimeHelper base_timehelper;

    public static boolean enable_display_info = false;
    public static int _x_ = 3, _y_ = 70;

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
    public static boolean quickly_place;
    public static boolean key_display;

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
    public static Module<Boolean> QUICKLY_PLACE;
    public static Module<Boolean> KEY_DISPLAY;

    public static Module<Float> SUPER_KILL_AURA$RANGE;
    public static Module<Boolean> SUPER_KILL_AURA$ATTACK_PLAYER;

    public static List<Module<Component>> PLAYER_CAMERA;

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

    public static KeyMapping OPEN2 =  new KeyMapping("key.y",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            "key.category.x_client");


    public static void registerKey(KeyMapping... keyMappings) {
        for (KeyMapping key : keyMappings)
            ClientRegistry.registerKeyBinding(key);
    }

    public static Main instance = null;

    public Main() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        MinecraftForge.EVENT_BUS.register(this);
        Networking.registerMessage();
        registerKey(DISPLAY_INFO, OPEN, OPEN2);
        setModules();
        if (base_timehelper == null)
            base_timehelper = new TimeHelper(20, 160);
        if (timeHelper == null)
            timeHelper = new TimeHelper(10, 170);
        Main.instance = this;
        MegaUtil.read();
    }

    public static TimeHelper timeHelper;

    public static void setPlayerCamera(Level level) {
        setModules();
        PLAYER_CAMERA = new ArrayList<>();
        for (Player player : level.players()) {
            Module<Component> player_camera = new Module<>("", player.getDisplayName(), false, Minecraft.getInstance().font).setLeft(b -> Minecraft.getInstance().cameraEntity = player).setColor(new Color(timeHelper.integer_time / 2 ,130, timeHelper.integer_time, timeHelper.integer_time));
            PLAYER_CAMERA.add(player_camera);
        }
    }

    public static void setModules() {
        ModuleManager.modules.clear();
        ModuleManager.configuration_father_modules.clear();
        Module.every.clear();
        CLIENT = new Module<>("[Forge]X-Client:1.0").setFont(RainbowFont.INS);
        AUTO_ATTACK = new Module<>("Auto Attack", auto_attack, false, RainbowFont.NORMAL).setLeft((d -> auto_attack = !auto_attack));
        ENABLE_HURT_EFFECT = new Module<>("Hurt Effect", enableHurtEffect, false, RainbowFont.NORMAL).setLeft((d -> enableHurtEffect = !enableHurtEffect));

        SUPER_KILL_AURA = new Module<>("Super KillAura", superKillAura, false, RainbowFont.NORMAL).setLeft((d -> superKillAura = !superKillAura)).setRight(d -> killaura_displayInfo = !killaura_displayInfo);
        SUPER_KILL_AURA$RANGE = new Module<>("KillAura Range", killaura_range, false, RainbowFont.NORMAL).setLeft((d -> killaura_range += 0.5F)).setRight(d -> {
            if (killaura_range > 0)
                killaura_range -= 0.1F;
        }).setFather(SUPER_KILL_AURA);
        SUPER_KILL_AURA$ATTACK_PLAYER = new Module<>("KillAura AttackPlayer", killaura_attackPlayer, false, RainbowFont.NORMAL).setLeft((d -> killaura_attackPlayer = !killaura_attackPlayer))
                .setFather(SUPER_KILL_AURA);

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
        QUICKLY_PLACE = new Module<>("Quickly Place", quickly_place, false, RainbowFont.NORMAL).setLeft(d -> quickly_place = !quickly_place);
        KEY_DISPLAY = new Module<>("Key Display", key_display, false, RainbowFont.NORMAL).setLeft(d -> key_display = !key_display);

        YScreen.OPEN_INVENTORY = new Module<>("Open Inv").setLeft(b -> {
            Entity entity = Minecraft.instance.cameraEntity;
            if (YScreen.display_players) {
                if (entity instanceof Player player) {
                    Minecraft.getInstance().tutorial.onOpenInventory();
                    Minecraft.getInstance().setScreen(new InventoryScreen(player));
                }
            }
        }).unaddToList();
        YScreen.RETURN_LOCAL = new Module<>("Return Local").setLeft(b -> Minecraft.getInstance().cameraEntity = Minecraft.getInstance().player).unaddToList();
    }


    @Mod.EventBusSubscriber(value = Dist.CLIENT)
    public static class TickKeys {
        @SubscribeEvent
        public static void onLoggedOut(ClientPlayerNetworkEvent.LoggedOutEvent event) {
            MegaUtil.writeXCLIENT();
        }

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
            if (event.side.isClient() && event.player instanceof LocalPlayer) {
                if (sprint) {
                    event.player.setSprinting(true);
                }
            }
        }

        @SubscribeEvent
        public static void ld(LivingDeathEvent event) {
            if (respawn) {
                if (event.getEntityLiving() instanceof LocalPlayer)
                    if (Minecraft.getInstance().player != null)
                        Minecraft.getInstance().player.connection.send(new ServerboundChatPacket("/back"));
            }
        }

        @SubscribeEvent
        public static void renderUpdate(TickEvent.RenderTickEvent event){
            if (quickly_place)
                Minecraft.getInstance().rightClickDelay = 0;
            Minecraft.getInstance().getWindow().setTitle("X-Client | " + Main.version);
        }

        @SubscribeEvent
        public static void onKey(InputEvent.KeyInputEvent event) {
            Minecraft mc = Minecraft.getInstance();
            if (OPEN.consumeClick())
                Minecraft.getInstance().setScreen(new XScreen());
            if (OPEN2.consumeClick())
                Minecraft.getInstance().setScreen(new YScreen());
            if (mc.player != null) {
                Abilities abilities = new Abilities();
                abilities.mayfly = true;
                mc.player.connection.send(new ServerboundPlayerAbilitiesPacket(abilities));
            }
        }

        public static int client_ticks = 0;

        @SubscribeEvent
        public static void clientTick(TickEvent.ClientTickEvent event) {
            client_ticks++ ;
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
                    if (no_fall && player.fallDistance <= .5F && player.fallDistance > .2F)
                        player.connection.send(new ServerboundMovePlayerPacket.StatusOnly(true));
                }
                if (superKillAura && client_ticks % 20 == 0) {
                    for (Entity entity : xclient.mega.utils.MegaUtil.getEntitiesToWatch(512, player)) {
                        if (entity instanceof LivingEntity livingEntity && point != player && !livingEntity.isDeadOrDying() && !livingEntity.isInvisible() && player.distanceTo(entity) <= killaura_range && livingEntity.deathTime <= 0) {
                            if (mc.gameMode != null ) {
                                //noinspection ConstantConditions
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
        public static void renderTick(RenderEvent event) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            //Entity point = mc.crosshairPickEntity;
            Entity toWatch = xclient.mega.utils.MegaUtil.getEntityToWatch(20, player);
            Font font = mc.font;
            PoseStack stack = new PoseStack();
                if (toWatch != null) {
                    if (toWatch instanceof LivingEntity living_point)
                        InventoryScreen.renderEntityInInventory(105, 100, 30, 45, 45, living_point);
                    else font.drawShadow(stack, "No model", 105, 100, 0xFFFFFFFF);
                    if (toWatch instanceof LivingEntity livingEntity) {
                        font.drawShadow(stack, "Health:" + String.format("%.2f", livingEntity.getHealth()) + "/" + String.format("%.2f", livingEntity.getMaxHealth()), 105, (int) toWatch.getEyeHeight() * 5 + 118, RendererUtils.WHITE);
                        font.drawShadow(stack, "MainHandItem:" + livingEntity.getMainHandItem().getDisplayName().getString(), 105, (int) toWatch.getEyeHeight() * 5 + 126, RendererUtils.WHITE);
                    }
                }
            int x = 0; int y = 0;
            if (enable_display_info && !(mc.screen instanceof XScreen) && !YScreen.display_players) {
                for (Module<?> module :ModuleManager.modules) {
                    module.render(stack, x, y, false);
                    y+=11;
                }
            }
            if (key_display) {
                int background = new Color(255, 255, 255, 50).getRGB();
                int color = new Color(0, 0, 0, base_timehelper.integer_time).getRGB();
                Options options = mc.options;
                Render2DUtil.drawRect(stack, _x_ + 1 + 20, _y_, 20, 20, options.keyUp.isDown() ? color : background);
                Render2DUtil.drawRect(stack, _x_ + 20 + 1, _y_ + 20 + 1, 20, 20, options.keyDown.isDown() ? color : background);
                Render2DUtil.drawRect(stack, _x_, _y_ + 20 + 1, 20, 20, options.keyLeft.isDown() ? color : background);
                Render2DUtil.drawRect(stack, _x_ + 40 + 2, _y_ + 20 + 1, 20, 20, options.keyRight.isDown() ? color : background);
                Render2DUtil.drawRect(stack, _x_ + 1, _y_ + 40 + 2 + 2, 61, 15, options.keyJump.isDown() ? color : background);
            }
        }
    }
}
