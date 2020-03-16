package com.cyr1en.kiso.utils;

import java.lang.reflect.Array;
import java.util.Objects;

public class KisoArray<T> {

  private final T[] array;

  private KisoArray(Class<? extends Object[]> clazz, int capacity) {
    array = (T[]) Array.newInstance(clazz, capacity);
  }

  public boolean contains(T a) {
    for(T t: array) {
      if(Objects.equals(t, a))
        return true;
    }
    return false;
  }

  public T[] getAsArray() {
    return array;
  }

  @SafeVarargs
  public static <T> KisoArray<T> of(T ... t) {
    return new KisoArray<>(t.getClass(), t.length);
  }
}
