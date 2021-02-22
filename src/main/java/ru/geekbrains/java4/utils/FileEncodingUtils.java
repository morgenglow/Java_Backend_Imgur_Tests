package ru.geekbrains.java4.utils;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class FileEncodingUtils {
    public byte[] getFileContent(String inputImageFilePath) {
        File inputFile = new File(inputImageFilePath);
        byte[] fileContent = new byte[0];
        try {
            fileContent = org.apache.commons.io.FileUtils.readFileToByteArray(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
