package de.alphaconqueror.examplemod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class ExampleCommand extends CommandBase {

    public static final ExampleCommand INSTANCE = new ExampleCommand();

    private ExampleCommand() {}

    @Override
    public String getCommandName() {
        return "example";
    }

    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return '/' + this.getCommandName();
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args) {
        sender.addChatMessage(new ChatComponentText("Hello world!"));
    }
}
