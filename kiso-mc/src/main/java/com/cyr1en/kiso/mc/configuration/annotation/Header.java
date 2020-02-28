package com.cyr1en.kiso.mc.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to provide the {@link String[]} that
 * the config-manager is going to use when generating the
 * header for the config file.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Header {

  /**
   * Header for the config file.
   *
   * @return Header for the config file.
   */
  String[] header() default "";
}
