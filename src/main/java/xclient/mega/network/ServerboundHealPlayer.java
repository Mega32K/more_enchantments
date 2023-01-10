package xclient.mega.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ServerboundHealPlayer {
    public float value;

    public ServerboundHealPlayer(FriendlyByteBuf buffer) {
        value = buffer.readFloat();
    }

    public ServerboundHealPlayer(float v) {
        this.value = v;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(this.value);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getSender() != null) {
                System.out.println(ctx.get().getSender());
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public static void run(Player player, float value) {
        player.heal(value);
        System.out.println(player.getDisplayName());
    }

    public static void run2(MinecraftServer server) {
        System.out.println(server);
    }
}

