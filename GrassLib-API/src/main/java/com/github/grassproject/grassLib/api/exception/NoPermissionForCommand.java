package com.github.grassproject.grassLib.api.exception;

public class NoPermissionForCommand extends Exception {
    public NoPermissionForCommand(String per) {
        super("명령어를 실행할 권한이 없습니다. "+per);
    }
}
