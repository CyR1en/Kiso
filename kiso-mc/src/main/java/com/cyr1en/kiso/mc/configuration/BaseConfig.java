/*
 * MIT License
 *
 * Copyright (c) 2019 Ethan Bacurio
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

package com.cyr1en.kiso.mc.configuration;

import com.cyr1en.kiso.mc.configuration.base.Config;
import com.cyr1en.kiso.mc.configuration.base.ConfigManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseConfig implements IConfig {

  private final String[] header;
  private ConfigManager configManager;
  private Node[] nodes;
  private Config config;

  protected BaseConfig(ConfigManager configManager, String[] header, Node[] nodes) {
    this.configManager = configManager;
    this.header = header;
    this.nodes = nodes;
  }

  boolean init() {
    File f = configManager.getConfigFile(this.getClass().getSimpleName() + ".yml");
    if (!f.exists()) {
      initializeNodes();
      return false;
    }
    initializeNodes();
    return true;
  }

  private void initializeNodes() {
    config = configManager.getNewConfig(this.getClass().getSimpleName() + ".yml", header);
    for (Node node : nodes) {
      String[] comment = node.getComment();
      if (config.get(node.key()) == null) {
        if (comment.length == 0)
          config.set(node.key(), node.getDefaultValue());
        else
          config.set(node.key(), node.getDefaultValue(), comment);
        config.saveConfig();
      }
    }
  }

  @Override
  public String getString(Node node) {
    return config.getString(node.key());
  }

  @Override
  public boolean getBoolean(Node node) {
    return config.getBoolean(node.key());
  }

  @Override
  public int getInt(Node node) {
    return config.getInt(node.key());
  }

  @Override
  public List<String> getList(Node node) {
    List<String> casted = new ArrayList<>();
    for (Object o : config.getList(node.key()))
      casted.add(String.valueOf(o));
    return casted;
  }

  public Config getConfig() {
    return config;
  }
}
