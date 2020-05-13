package com.cyr1en.kiso.mc.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandContext {
  private final CommandManager manager;
  private final CommandSender sender;
  private final Command command;
  private final String[] args;

  public CommandContext(CommandManager manager, CommandSender sender, Command command, String[] args) {
    this.manager = manager;
    this.sender = sender;
    this.command = command;
    this.args = args;
  }

  public CommandManager getManager() {
    return manager;
  }

  public CommandSender getSender() {
    return sender;
  }

  public Command getCommand() {
    return command;
  }

  public String[] getArgs() {
    return args;
  }


}
