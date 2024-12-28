package com.ratons.mixins.transformers;

import com.ratons.utils.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(World.class)
public class MixinWorld {

    @Inject(method = "unloadEntities", at = @At("HEAD"))
    private void unloadEntities(Collection<Entity> entityCollection, CallbackInfo ci) {
        for (Entity entity : entityCollection) EntityUtils.postDespawn(entity);
    }

    @Inject(method = "onEntityRemoved", at = @At("HEAD"))
    private void onEntityRemoved(Entity entityIn, CallbackInfo ci) {
        EntityUtils.postDespawn(entityIn);
    }
}
