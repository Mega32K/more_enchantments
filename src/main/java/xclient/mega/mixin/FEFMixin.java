package xclient.mega.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import xclient.mega.Main;

@Mixin(value = ForgeEventFactory.class, remap = false)
public class FEFMixin {
    /**
     * @author mega
     * @reason w
     */
    @Overwrite
    public static int onArrowLoose(ItemStack stack, Level level, Player player, int charge, boolean hasAmmo)
    {
        if (Main.quickly_bow)
            return 200;
        ArrowLooseEvent event = new ArrowLooseEvent(player, stack, level, charge, hasAmmo);
        if (MinecraftForge.EVENT_BUS.post(event))
            return -1;
        return event.getCharge();
    }
}
