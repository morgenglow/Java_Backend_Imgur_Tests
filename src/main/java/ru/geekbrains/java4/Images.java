package ru.geekbrains.java4;

public enum Images {
    POSITIVE("src/test/resources/avatar.jpg"),
    TOO_BIG("src/test/resources/pnghuge.png"),
    ZERO_SIZE("src/test/resources/empty.jpg"),
    SMALL("src/test/resources/small.jpg"),
    SONG ("src/test/resources/songmp3.mp3"),
    TXT("src/test/resources/txt.txt"),
    PNG("src/test/resources/png.png");

    public final String path;

    Images(String path) {
        this.path = path;
    }
}
