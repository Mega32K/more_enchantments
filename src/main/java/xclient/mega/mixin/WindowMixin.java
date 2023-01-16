package xclient.mega.mixin;

import com.mojang.blaze3d.platform.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xclient.mega.BmMain;
import xclient.mega.Main;

@Mixin(Window.class)
public class WindowMixin {
    @Shadow
    @Final
    private long window;

    @Inject(method = "setTitle", at = @At("HEAD"), cancellable = true)
    public void set(String p_85423_, CallbackInfo ci) {
        GLFW.glfwSetWindowTitle(this.window, "X-Client | " + Main.version + " Time:" + BmMain.date.getHours() + ":" + BmMain.date.getMinutes());
        ci.cancel();
    }
}
