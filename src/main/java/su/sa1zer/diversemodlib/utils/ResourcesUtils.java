package su.sa1zer.diversemodlib.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class ResourcesUtils {

    public static File getFileFromResources(InputStream stream) {
        try (InputStream inputStream = stream) {
            File file = File.createTempFile("tempFile", ".tmp");

            OutputStream out = Files.newOutputStream(file.toPath());
            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.close();
            file.deleteOnExit();

            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
