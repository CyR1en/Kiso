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


import com.cyr1en.kiso.mc.configuration.annotation.Configuration;
import com.cyr1en.kiso.mc.configuration.annotation.Header;
import com.cyr1en.kiso.mc.configuration.base.ConfigManager;
import lombok.Getter;
import org.atteo.classindex.ClassIndex;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class PluginConfigManager {

    @Getter
    private final ConfigManager manager;
    @Getter
    private final HashMap<Class<? extends BaseConfig>, Object> configs;

    public PluginConfigManager(ConfigManager manager) {
        this.manager = manager;
        this.configs = new HashMap<>();
    }

    private <T> void put(Class<? extends BaseConfig> clazz, T config) {
        configs.put(clazz, config);
    }

    public <T extends BaseConfig> T getConfig(Class<T> clazz) {
        return clazz.cast(configs.get(clazz));
    }

    public void reloadConfigs() {
        configs.forEach((k, v) -> getConfig(k).getConfig().reloadConfig());
    }

    public final boolean setupConfigs() {
        // boolean isSafeToStart = true;
        Iterable<Class<?>> annotatedClasses = ClassIndex.getAnnotated(Configuration.class);
        annotatedClasses.forEach(i -> {
            String[] header = i.isAnnotationPresent(Header.class) ? i.getAnnotation(Header.class).header(): new String[]{};
            try {
                Constructor<?> constructor = i.getDeclaredConstructor(ConfigManager.class, String[].class);
                if(!constructor.isAccessible()) constructor.setAccessible(true);
                BaseConfig baseConfig = (BaseConfig) constructor.newInstance(manager, header);
                put((Class<? extends BaseConfig>) i, baseConfig);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return true;
    }

}
