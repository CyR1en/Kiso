/*
 * MIT License
 *
 * Copyright (c) 2020 Ethan Bacurio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.cyr1en.kiso.mc.command;

import com.cyr1en.kiso.utils.FastStrings;
import com.cyr1en.kiso.utils.FileUtil;
import com.cyr1en.kiso.utils.KisoArray;
import com.google.common.collect.Lists;
import org.bukkit.command.*;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class CommandManager implements CommandExecutor {

  private static final PluginDescriptionFile PLUGIN_DESCRIPTION_FILE;
  private static final String DEFAULT_PREFIX;

  static {
    PLUGIN_DESCRIPTION_FILE = resolveDescriptionFile();
    DEFAULT_PREFIX = "&6[&aKiso-Command&6] ";
  }

  private JavaPlugin plugin;
  private CommandMessenger messenger;

  private List<AbstractCommand> commands;

  private Function<CommandManager, Boolean> fallBack;

  private CommandManager(JavaPlugin plugin, Function<CommandManager, Boolean> fallBack, String prefix, String playerOnlyMessage, String commandInvalidMessage) {
    this.plugin = plugin;
    commands = Lists.newArrayList();
    this.fallBack = fallBack;

    prefix = FastStrings.isBlank(prefix) ?
            (Objects.nonNull(PLUGIN_DESCRIPTION_FILE) ? makePrefix() : DEFAULT_PREFIX) : prefix ;

    messenger = new CommandMessenger(prefix);
    messenger.setPlayerOnlyMessage(playerOnlyMessage);
    messenger.setCommandInvalidMessage(commandInvalidMessage);
  }

  private String makePrefix() {
    String pluginName = PLUGIN_DESCRIPTION_FILE.getName();
    return "&6[&a" + pluginName + "&6] ";
  }

  public void registerCommand(Class<? extends AbstractCommand> cmd) {
    try {
      Constructor c = cmd.getConstructor(JavaPlugin.class, CommandMessenger.class);
      AbstractCommand command = (AbstractCommand) c.newInstance(plugin, messenger);
      commands.add(command);
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  public void registerTabCompleter(PluginCommand pluginCommand) {
    CommandTabCompleter commandTabCompleter = new CommandTabCompleter(this);
    Objects.requireNonNull(pluginCommand).setTabCompleter(commandTabCompleter);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length == 0)
      return fallBack.apply(this);

    for (AbstractCommand cmd : commands)
      if (args[0].equalsIgnoreCase(cmd.getName()) || KisoArray.of(cmd.getAlias()).contains(args[0])) {
        if (sender instanceof ConsoleCommandSender && cmd.isPlayerOnly()) {
          messenger.sendMessage(sender, messenger.getPlayerOnlyMessage());
          return false;
        }
        return cmd.onCommand(sender, Arrays.copyOfRange(args, 1, args.length));
      } else
        messenger.sendMessage(sender, messenger.getCommandInvalidMessage());
    return false;
  }

  public List<AbstractCommand> getCommands() {
    return commands;
  }

  private static PluginDescriptionFile resolveDescriptionFile() {
    InputStream is = FileUtil.getResourceAsStream("plugin.yml");
    PluginDescriptionFile pdl = null;
    try {
      pdl = new PluginDescriptionFile(is);
    } catch (InvalidDescriptionException e) {
      e.printStackTrace();
    }
    return pdl;
  }
}
