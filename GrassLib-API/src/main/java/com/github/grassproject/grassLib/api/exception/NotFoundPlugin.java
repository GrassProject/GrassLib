package com.github.grassproject.grassLib.api.exception;

public class NotFoundPlugin extends ClassNotFoundException {
    public NotFoundPlugin(String plugin) {
        super("Can't found plugin!: " + plugin);
    }
}
