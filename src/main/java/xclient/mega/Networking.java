package xclient.mega;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import xclient.mega.network.ServerboundHealPlayer;

public class Networking {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("x_client", "fnetworking"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION));
        INSTANCE.registerMessage(
                nextID(),
                ServerboundHealPlayer.class,
                ServerboundHealPlayer::toBytes,
                ServerboundHealPlayer::new,
                ServerboundHealPlayer::handler
        );
    }
}
