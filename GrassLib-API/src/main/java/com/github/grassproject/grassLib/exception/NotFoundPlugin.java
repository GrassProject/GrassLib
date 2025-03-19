package com.github.grassproject.grassLib.exception;

public class NotFoundPlugin extends ClassNotFoundException {
    public NotFoundPlugin(String plugin) {
        super("Can't found plugin!: "+plugin);
    }
}
