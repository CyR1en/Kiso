package com.cyr1en.kiso.mc.command;

import lombok.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Data
public class CommandContext {
  private final CommandManager manager;
  private final CommandSender sender;
  private final Command command;
  private final String[] args;
}
