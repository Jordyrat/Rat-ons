package com.examplemod.mixins.transformers.skyhanni;

import at.hannibal2.skyhanni.api.event.SkyHanniEvents;
import com.examplemod.ExampleMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;

@Mixin(SkyHanniEvents.class)
public abstract class MixinSkyHanniEvents {

    @Final
    @Shadow
    protected abstract void registerMethod(Method method, Object instance);

    @Inject(method = "init*", at = @At(value = "HEAD"), remap = false)
    private void init(List<?> instances, CallbackInfo ci) {
        for (Object module : ExampleMod.modules) {
            for (Method method : module.getClass().getDeclaredMethods()) {
                registerMethod(method, module);
            }
        }
    }
}
