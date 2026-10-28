package de.alphaconqueror.examplemod.mixins;

import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NetHandlerPlayServer.class)
public class MixinNetHandlerPlayServer {

    /**
     * No-op leave message. We handle it ourselves.
     */
    @Redirect(method = "onDisconnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/server"
            + "/management/ServerConfigurationManager;sendChatMsg"
            + "(Lnet/minecraft/util/IChatComponent;)V"))
    public void sendChatMsgInjected(final ServerConfigurationManager instance,
            final IChatComponent component) {
        // add a custom leave message here or block it completely
        System.out.println("Custom leave message.");
    }
}
