package xclient.mega;

import xclient.mega.mod.Module;

public interface IScreenClick {
    default void click(double x, double y, int code) {
        for (Module<?> module : Module.every) {
            if (XScreen.isInRange(module, (int) x, (int) y)) {
                if (code == 0) {
                    module.left();
                }
                if (code == 1) {
                    module.right();
                }
            }
        }
        Main.setModules();
    }
}
