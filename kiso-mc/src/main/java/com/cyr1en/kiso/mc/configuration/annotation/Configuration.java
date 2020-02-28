package com.cyr1en.kiso.mc.configuration.annotation;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotating a class with this signifies that it's a config file
 */
@IndexAnnotated
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {

  /**
   * Custom name for the configuration file.
   *
   * @return Custom name for the configuration file
   */
  String name() default "";
}
