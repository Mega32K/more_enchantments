package xclient.mega;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.client.gui.ScrollPanel;
import xclient.mega.mod.Module;
import xclient.mega.mod.ModuleManager;

import java.util.List;

public class XScreen extends Screen {
    protected XScreen() {
        super(new TextComponent(""));
    }
    public EditBox editBox1;
    public EditBox editBox2;
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public static boolean isInRange(Module<?> module, int x, int y) {
        return x >= module.x && y >= module.y && x < module.x + module.width && y < module.y + module.height;
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_94697_) {
        for (List<Module<?>> list : ModuleManager.all)
            for (Module<?> module : list) {
                if (isInRange(module, (int) x, (int) y)) {
                    if (p_94697_ == 0) {
                        module.left();
                    }
                    if (p_94697_ == 1) {
                        module.right();
                    }
                }
            }
        Main.setModules();
        return super.mouseClicked(x, y, p_94697_);
    }

    @Override
    public void render(PoseStack stack, int mx, int my, float pt) {
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
    public Component getTitle() {
        return new TextComponent("The X Screen").withStyle(ChatFormatting.BLUE);
    }

    @Override
    protected void init() {
        minecraft = Minecraft.getInstance();
        Main.setModules(); 
        editBox1 = new EditBox(minecraft.font, 10, 140, 80, 20, new TextComponent(String.valueOf(Main._x_)));
        editBox2 = new EditBox(minecraft.font, 10, 165, 80, 20, new TextComponent(String.valueOf(Main._y_)));
        addRenderableWidget(editBox1);
        addRenderableWidget(editBox2);
        addRenderableWidget(new Button(10, 190, 80, 20, new TextComponent("Set pos"), (b) -> {
            String x_s = editBox1.getValue();
            String y_s = editBox2.getValue();
            if (!x_s.contains(".") && !x_s.contains(",") && !x_s.contains("，") && !x_s.contains(" ") && !x_s.isEmpty())
                Main._x_ = Integer.valueOf(x_s);
            if (!y_s.contains(".") && !y_s.contains(",") && !y_s.contains("，") && !y_s.contains(" ") && !y_s.isEmpty())
                Main._y_ = Integer.valueOf(y_s);
        }));
        super.init();
    }
}
