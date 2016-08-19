package com.mystery0.isafe.PublicMethod;

import java.io.File;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class DeleteFile
{
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if(file.isDirectory()){
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (File childFile : childFiles)
            {
                delete(childFile);
            }
            file.delete();
        }
    }
}
