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

import com.cyr1en.kiso.mc.I18N;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandMessenger {

  private String prefix;

  private String playerOnlyMessage;
  private String commandInvalidMessage;
  private String noPermMessage;

  public CommandMessenger(String prefix) {
    this.prefix = prefix;
    playerOnlyMessage = "This command is only for players.";
    commandInvalidMessage = "The command the you entered is invalid";
    noPermMessage = "You don't have permission to execute this command.";
  }

  public void sendMessage(CommandSender sender, String message) {
    sendMessage(sender, message, true);
  }

  public void sendMessage(CommandSender sender, String message, boolean configuredPrefix) {
    String prefix = configuredPrefix ? this.prefix : "&6[&aKiso-Command&6] ";
    String formattedMsg = ChatColor.translateAlternateColorCodes('&', prefix + message);
    sender.sendMessage(formattedMsg);
  }

  public void sendErrMessage(CommandSender p, String message) {
    sendMessage(p, "&c" + message);
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getPlayerOnlyMessage() {
    return playerOnlyMessage;
  }

  public void setPlayerOnlyMessage(String playerOnlyMessage) {
    this.playerOnlyMessage = playerOnlyMessage;
  }

  public String getCommandInvalidMessage() {
    return commandInvalidMessage;
  }

  public void setCommandInvalidMessage(String commandInvalidMessage) {
    this.commandInvalidMessage = commandInvalidMessage;
  }

  public String getNoPermMessage() {
    return noPermMessage;
  }

  public void setNoPermMessage(String noPermMessage) {
    this.noPermMessage = noPermMessage;
  }
}
