package com.yungnickyoung.minecraft.yungslaw.proxy;

public interface IProxy {
    default void preInit() {}
    default void init() {}
    default void postInit() {}
}
