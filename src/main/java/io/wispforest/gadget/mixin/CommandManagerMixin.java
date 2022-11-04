package io.wispforest.gadget.mixin;

import io.wispforest.gadget.network.AnnounceS2CPacket;
import io.wispforest.gadget.network.GadgetNetworking;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManagerMixin {
    @Inject(method = "sendCommandTree(Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At("HEAD"))
    private void onReloadPermissions(ServerPlayerEntity player, CallbackInfo ci) {
        boolean canReplaceStacks = Permissions.check(player, "gadget.replaceStack", 4);

        GadgetNetworking.CHANNEL.serverHandle(player).send(new AnnounceS2CPacket(canReplaceStacks));
    }
}
