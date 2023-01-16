package xclient.mega;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xclient.mega.event.GameUpdateEvent;
import xclient.mega.mod.bigmodule.ActionBmC;
import xclient.mega.mod.bigmodule.BigModuleBase;
import xclient.mega.mod.bigmodule.type.CombatBm;
import xclient.mega.mod.bigmodule.type.PlayerBm;
import xclient.mega.mod.bigmodule.type.RenderBm;
import xclient.mega.utils.TimeHelper;
import xclient.mega.utils.Vec2d;

import java.awt.*;
import java.util.Date;

@Mod.EventBusSubscriber
public class BmMain {
    public static Date date = new Date();
    public static BigModuleBase PLAYER;
    public static BigModuleBase COMBAT;
    public static BigModuleBase RENDER;
    public static TimeHelper timeHelper = new TimeHelper(20, 145);

    @SubscribeEvent
    public static void update(GameUpdateEvent event) {
        date = new Date();
    }

    public static BigModuleBase recreate(BigModuleBase base, BigModuleBase newBm) {
        if (base == null)
            return newBm;
        Saver<Vec2d> saver = new Saver<>(base.getPos());
        if (base instanceof ActionBmC b1 && newBm instanceof ActionBmC b2) {
            Saver<Boolean> saver2 = new Saver<>(b1.pushed);
            b2.pushed = saver2.getV();
        }
        newBm.pos = saver.getV();
        return newBm;
    }

    public static void setBms() {
        BigModuleBase.clear();
        COMBAT = recreate(COMBAT, new CombatBm().setName("Combat").setColor(new Color(timeHelper.integer_time / 2 + 30, 70, 88, timeHelper.integer_time)));
        PLAYER = recreate(PLAYER, new PlayerBm().setName("Player").setColor(new Color(timeHelper.integer_time, 40, 110, timeHelper.integer_time)));
        RENDER = recreate(RENDER, new RenderBm().setName("Render").setColor(new Color(timeHelper.integer_time, 80, 190, timeHelper.integer_time)));
    }
}
