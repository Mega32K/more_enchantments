package xclient.mega.mod.bigmodule.type;

import net.minecraft.world.entity.player.Player;
import xclient.mega.mod.bigmodule.ActionBm;
import xclient.mega.mod.bigmodule.ActionBmC;

import java.util.HashSet;
import java.util.Set;

public class RenderBm extends ActionBmC implements ActionBm, xclient.mega.mod.bigmodule.RenderBm {
    public static Set<Player> players = new HashSet<>();
}
