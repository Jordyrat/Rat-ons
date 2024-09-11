package com.ratons.mixins.transformers.modidhider;

import com.ratons.Ratons;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "gg.skytils.skytilsmod.features.impl.misc.Funny", remap = false)
public class MixinSkytilsFunnny {
    @Inject(method = "joinedSkyblock*", at = @At("HEAD"), expect = 0, cancellable = true, remap = false)
    public void onJoinedSkyblockHead(CallbackInfo ci) {
        if (!Ratons.HIDE_MOD_ID) return;
        ci.cancel();
    }
}
