package xclient.mega.mixin;

import com.mojang.blaze3d.platform.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xclient.mega.Main;

import java.io.InputStream;
import java.util.Date;

@Mixin(Window.class)
public class WindowMixin {
    @Shadow
    @Final
    private long window;

    @Inject(method = "setTitle", at = @At("HEAD"), cancellable = true)
    public void set(String p_85423_, CallbackInfo ci) {
        GLFW.glfwSetWindowTitle(this.window, "X-Client | " + Main.version + " Time:" + new Date().getHours() + ":" + new Date().getMinutes());
        ci.cancel();
    }

    @Inject(method = "setIcon", at = @At("HEAD"))
    private void setI(InputStream p_85396_, InputStream p_85397_, CallbackInfo ci) {

    }
}
