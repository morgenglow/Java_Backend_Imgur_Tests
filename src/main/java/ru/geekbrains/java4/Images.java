package ru.geekbrains.java4;

public enum Images {
    POSITIVE("src/test/resources/avatar.jpg"),
    TOO_BIG(""),
    ZERO_SIZE(""),
    SMALL("");

    public final String path;

    Images(String path) {
        this.path = path;
    }
}
