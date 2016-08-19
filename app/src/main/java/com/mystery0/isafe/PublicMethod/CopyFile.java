package com.mystery0.isafe.PublicMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile
{
    public static boolean fileCopy(String oldFilePath, String newFilePath) throws IOException
    {
        if (!fileExists(oldFilePath))
        {
            return false;
        }
        FileInputStream inputStream = new FileInputStream(new File(oldFilePath));
        byte[] data = new byte[1024];
        FileOutputStream outputStream = new FileOutputStream(new File(newFilePath));
        while (inputStream.read(data) != -1)
        {
            outputStream.write(data);
        }
        inputStream.close();
        outputStream.close();
        return true;
    }

    private static boolean fileExists(String filePath)
    {
        File file = new File(filePath);
        return file.exists();
    }
}
