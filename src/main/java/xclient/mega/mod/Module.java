package xclient.mega.mod;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import xclient.mega.Main;
import xclient.mega.MegaUtil;
import xclient.mega.utils.*;

import java.awt.*;

public class Module<T> {
    private String name;
    public Minecraft mc;
    public T value;
    public Font font;
    public Vec2d color = new Vec2d(0 ,0);
    public ModuleTodo left;
    public ModuleTodo right;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean enableColorPutter;

    public Module(String name, T value, boolean enableColorPutter, Font font) {
        this.font = font;
        this.enableColorPutter = enableColorPutter;
        this.name = name;
        this.value = value;
        this.mc = Minecraft.getInstance();
        ModuleManager.addModule(this);
    }

    static void saveInfo() {
        MegaUtil.writeXCLIENT();
    }

    public Module(String name, T value, boolean enableColorPutter) {
        this(name, value, enableColorPutter, Minecraft.getInstance().font);
    }

    public Module(String name, T value) {
        this(name, value, false);
    }

    public Module<T> setLeft(ModuleTodo left) {
        this.left = left;
        return this;
    }

    public Module<T> unaddToList() {
        ModuleManager.modules.remove(this);
        ModuleManager.configuration_father_modules.add(this);
        return this;
    }

    public Module<T> setRight(ModuleTodo right) {
        this.right = right;
        return this;
    }

    public void left() {
        if (left != null)
            left.run(this);
        else System.out.println(getName() + " left module is NULL!");
        saveInfo();
    }

    public void right() {
        if (right != null)
            right.run(this);
        else System.out.println(getName() + " right module is NULL!");
        saveInfo();
    }

    public String getName() {
        return enableColorPutter ? ColorPutter.rainbow(name) : name;
    }

    public String getPos() {
        return "x:" + x + " y:" + y + " width:" + width + " height" + height;
    }

    public String getInfo() {
        return getName() + (value != null ? ":"+value : "");
    }

    public Module<T> setName(String name) {
        this.name = name;
        return this;
    }

    public Module<T> setFont(Font font) {
        this.font = font;
        return this;
    }

    public Module<T> setColor(Vec2d color) {
        this.color = color;
        return this;
    }

    public Module<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public Module(String name) {
        this(name, null);
    }

    public void render(PoseStack stack, int x, int y) {
        if (value instanceof Float f)
            value = (T) Float.valueOf(String.format("%.2f", f));
        int color = new Color(55, 71, 90, 150).getRGB();
        this.x = x;
        this.y = y;
        Render2DUtil.drawRect(stack, x, y, (int) (width*1.5F), height+1, color);
        this.font.drawShadow(stack, getInfo(), x, y, RendererUtils.WHITE);
        if (width == 0)
            width = font.width(getInfo());
        if (height == 0)
            height = font.lineHeight;
    }
}
