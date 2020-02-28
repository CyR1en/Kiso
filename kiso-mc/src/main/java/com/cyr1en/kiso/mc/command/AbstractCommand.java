package com.cyr1en.kiso.mc.command;

import com.cyr1en.kiso.utils.FastStrings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public abstract class AbstractCommand implements ArgumentCounter {

  private JavaPlugin plugin;
  protected String commandName;
  protected String[] alias;
  protected String argument;
  protected String permission;
  protected String usage;
  protected List<AbstractCommand> children;
  protected boolean isPlayerOnly;
  protected CommandMessenger messenger;

  public AbstractCommand(JavaPlugin plugin, CommandMessenger messenger) {
    this.plugin = plugin;
    this.commandName = "";
    this.alias = new String[]{};
    this.argument = "";
    this.permission = "";
    this.usage = "";
    this.children = Lists.newArrayList();
    this.isPlayerOnly = false;
    this.messenger = messenger;
  }

  boolean onCommand(CommandSender sender, String[] args) {
    if (!sender.hasPermission(permission)) {
      String message = messenger.getNoPermMessage();
      messenger.sendErrMessage(sender, message);
      return false;
    }
    if (countRequiredArgs() != args.length) {
      String message = messenger.getCommandInvalidMessage();
      String usageMsg = FastStrings.isBlank(usage) ? commandName + " " + usage: usage;
      messenger.sendErrMessage(sender, message);
      messenger.sendErrMessage(sender, usageMsg);
      return false;
    }
    doCommand(sender, args);
    return true;
  }

  public JavaPlugin getPlugin() {
    return plugin;
  }

  public String getName() {
    return commandName;
  }

  public String[] getAlias() {
    return alias;
  }

  public String getArgument() {
    return argument;
  }

  public String getPermission() {
    return permission;
  }

  public List<AbstractCommand> getChildren() {
    return children;
  }

  public boolean isPlayerOnly() {
    return isPlayerOnly;
  }

  @Override
  public String getArguments() {
    return argument;
  }

  public abstract void doCommand(CommandSender sender, String[] args);

  /**
   * Tab completion for this command.
   *
   * <p>By default, every command will return an empty list for completion.</p>
   *
   * @param args arguments passed down from onCommand.
   * @return return command completion.
   */
  public List<String> onTabComplete(String[] args) {
    return ImmutableList.of();
  }
}
