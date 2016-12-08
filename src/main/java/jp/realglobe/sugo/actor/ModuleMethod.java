package jp.realglobe.sugo.actor;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * モジュールの関数であることを示す
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
@Documented
public @interface ModuleMethod {}
