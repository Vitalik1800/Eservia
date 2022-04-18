package com.eservia.glide.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
// Needs to be parsed from class files in JAR.
@Retention(RetentionPolicy.CLASS)
public @interface Index {
  String[] modules() default {};

  String[] extensions() default {};
}
