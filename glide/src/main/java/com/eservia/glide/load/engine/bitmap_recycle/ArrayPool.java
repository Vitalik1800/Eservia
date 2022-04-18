package com.eservia.glide.load.engine.bitmap_recycle;

public interface ArrayPool {

  int STANDARD_BUFFER_SIZE_BYTES = 64 * 1024;


  @Deprecated
  <T> void put(T array, Class<T> arrayClass);


  <T> void put(T array);

  <T> T get(int size, Class<T> arrayClass);

  <T> T getExact(int size, Class<T> arrayClass);

  void clearMemory();


  void trimMemory(int level);
}
