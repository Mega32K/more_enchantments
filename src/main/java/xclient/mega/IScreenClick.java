package xclient.mega;

import xclient.mega.mod.Module;
import xclient.mega.mod.bigmodule.ActionBmC;
import xclient.mega.mod.bigmodule.BigModuleBase;

public interface IScreenClick {
    default void click(double x, double y, int code) {
        for (BigModuleBase bm : BigModuleBase.every) {
            if (bm.isInRange_asModule())
                bm.click(code);
            for (Module<?> module : Module.every) {
                if (module.getFather_Bm() instanceof ActionBmC actionBmC && actionBmC.pushed)
                    if (XScreen.isInRange(module, (int) x, (int) y)) {
                        if (code == 0) {
                            module.left();
                        }
                        if (code == 1) {
                            module.right();
                        }
                    }
            }
        }
        Main.setModules();
    }
}
