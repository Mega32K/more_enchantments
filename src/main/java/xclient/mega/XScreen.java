package xclient.mega;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xclient.mega.mod.Module;
import xclient.mega.mod.ModuleManager;
import xclient.mega.utils.Vec2d;

@Mod.EventBusSubscriber
public class XScreen extends Screen implements IScreenClick{

    protected XScreen() {
        super(new TextComponent(""));
    }

    public static int mouseX;
    public static int mouseY;

    
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public static boolean isInRange(Module<?> module, int x, int y) {
        return x >= module.x && y >= module.y && x < module.x + module.width && y < module.y + module.height;
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_94697_) {
        click(x, y, p_94697_);
        MegaUtil.writeXCLIENT();
        return super.mouseClicked(x, y, p_94697_);
    }

    @Override
    public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
        Main.KEY_DISPLAY_BM.release((int) p_94722_, (int) p_94723_);
        return super.mouseReleased(p_94722_, p_94723_, p_94724_);
    }

    @Override
    public boolean mouseDragged(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_) {
        if (Main.KEY_DISPLAY_BM.isInRange((int) p_94699_, (int) p_94700_, new Vec2d(Main._x_, Main._y_), new Vec2d(Main._x_ + 63, Main._y_ + 61))) {
            Main.KEY_DISPLAY_BM.startPress((int) p_94699_, (int) p_94700_);
        }
        return super.mouseDragged(p_94699_, p_94700_, p_94701_, p_94702_, p_94703_);
    }

    @Override
    public void render(PoseStack stack, int mx, int my, float pt) {
        mouseX = mx;
        mouseY = my;
        int x=0,y=0;
        for (Module<?> module : ModuleManager.modules) {
            if (module.getFather() == null)
                module.render(stack, x, y, isInRange(module, mx, my));
            y += 11;
            if (y >= 110) {
                x += 130;
                y = 0;
            }
        }
        y+=15;
        if (Main.killaura_displayInfo) {
            for (Module<?> module : ModuleManager.modules) {
                if (module.getFather() == Main.SUPER_KILL_AURA) {
                    module.render(stack, x, y, isInRange(module, mx, my));
                    y += 11;
                }
            }
        }
        super.render(stack, mx, my, pt);
    }

    @Override
    public boolean mouseScrolled(double p_94686_, double p_94687_, double p_94688_) {
        Main.key_scale += p_94688_ / 20F;
        MegaUtil.writeXCLIENT();
        return super.mouseScrolled(p_94686_, p_94687_, p_94688_);
    }

    @Override
    public Component getTitle() {
        return new TextComponent("The X Screen").withStyle(ChatFormatting.BLUE);
    }

    @Override
    protected void init() {
        minecraft = Minecraft.getInstance();
        Main.setModules();
        super.init();
    }

    @SubscribeEvent
    public static void loggedOut(ClientPlayerNetworkEvent.LoggedOutEvent event) {
        MegaUtil.writeXCLIENT();
    }
}
