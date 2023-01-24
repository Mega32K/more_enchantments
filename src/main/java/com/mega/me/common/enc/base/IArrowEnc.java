package com.mega.me.common.enc.base;

import net.minecraft.world.entity.projectile.AbstractArrow;

public interface IArrowEnc {
    void arrowTick(AbstractArrow abstractArrow, boolean pre);
}
