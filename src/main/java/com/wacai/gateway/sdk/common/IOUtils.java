package com.wacai.gateway.sdk.common;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * IO Utils
 *
 * @author bairen
 *
 */
public class IOUtils {

    public static final int READ_BUFFER_SIZE = 4096;

    private static final int EOF = -1;

    /**
     * Read fully from the InputStream
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] readFully(InputStream in) throws IOException {
        try {
            byte[] buf = new byte[READ_BUFFER_SIZE];
            int read = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            while ((read = in.read(buf)) != EOF) {
                os.write(buf, 0, read);
            }
            os.close();
            return os.toByteArray();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public static File createFile(File parent, String child) {
        File file = new File(parent, child);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file.exists()) {
            throw new RuntimeException("create file failed , file path is " + file.getAbsolutePath());
        }
        return file;
    }
}