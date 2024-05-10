import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
/**
 * Example program to demonstrate how to use zlib compression with
 * Java.
 * Inspired by http://stackoverflow.com/q/6173920/600500.
 */
public class ZlibUtil {
    /**
     * Compresses a file with zlib compression.
     */
    public static void compressFile(File raw, File compressed)
            throws IOException {
        try (InputStream in = new FileInputStream(raw);
             OutputStream out =
                     new DeflaterOutputStream(new FileOutputStream(compressed))) {
            shovelInToOut(in, out);
        }
    }

    /**
     * Decompresses a zlib compressed file.
     */
    public static void decompressFile(File compressed, File raw)
            throws IOException {
        try (InputStream in =
                     new InflaterInputStream(new FileInputStream(compressed));
             OutputStream out = new FileOutputStream(raw)) {
            shovelInToOut(in, out);
        }
    }

    /**
     * Shovels all data from an input stream to an output stream.
     */
    private static void shovelInToOut(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1000];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }

}
