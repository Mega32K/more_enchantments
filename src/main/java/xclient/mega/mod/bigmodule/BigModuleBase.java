package xclient.mega.mod.bigmodule;

import com.mojang.blaze3d.vertex.PoseStack;
import xclient.mega.utils.Vec2d;

public abstract class BigModuleBase {
    public boolean isPressing;
    public Vec2d pos = new Vec2d(0, 0);

    public void release(int mx, int my) {
        isPressing = false;
    }

    public void startPress(int mx, int my) {
        isPressing = true;
    }

    public boolean isInRange(int mx, int my, Vec2d start, Vec2d end) {
        int sx = (int) start.x;
        int sy = (int) start.y;
        int ex = (int) end.x;
        int ey = (int) end.y;
        return mx >= sx && my >= sy && mx < ex && my < ey;

    }

    public abstract void render(PoseStack stack, int x, int y);
}
