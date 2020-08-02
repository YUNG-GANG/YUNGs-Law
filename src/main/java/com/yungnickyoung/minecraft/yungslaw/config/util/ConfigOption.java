package com.yungnickyoung.minecraft.yungslaw.config.util;

import java.util.Map;

public class ConfigOption<T> {
    public String name;
    public String fullName;
    public Class<?> type;
    private T value;
    private boolean hidden = false;
    private String category = "";

    public ConfigOption(String name, T value) {
        this.name = name.toLowerCase();
        this.value = value;
        this.type = value.getClass();
    }

    public T get() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public void set(Object value) {
        this.value = (T)value;
    }

    public ConfigOption<T> hidden() {
        this.hidden = true;
        return this;
    }

    public ConfigOption<T> setCategory(String category) {
        category = category.toLowerCase();
        this.category = category;
        this.fullName = category + "." + name;
        return this;
    }

    public ConfigOption<T> addToMap(Map<String, ConfigOption<?>> map) {
        map.put(fullName, this);
        return this;
    }
}
