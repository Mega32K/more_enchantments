package com.mega.me;

import com.mega.me.common.registry.EncRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MoreEnchantments.MOD_ID)
public class MoreEnchantments {
    public static final String MOD_ID = "more_enchantments_m";
    public static final Logger LOGGER = LogManager.getLogger("MoreEnchantments");
    public MoreEnchantments() {
        MinecraftForge.EVENT_BUS.register(this);
        EncRegistry.registry();
        LOGGER.info("Mod Load Successfully!");
    }
}
