package su.sa1zer.diversemodlib.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOUtils {

    public static void writeToFile(Path file, String data) {
        try {
            createFileWithParents(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(BufferedWriter bw = Files.newBufferedWriter(file)) {
            bw.write(data);

            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isDir(Path path) {
        return Files.isDirectory(path);
    }

    public static void createParentDirs(Path path) throws IOException {
        Path parent = path.getParent();
        if (parent != null && !isDir(parent)) {
            Files.createDirectories(parent);
        }
    }

    public static void createFileWithParents(Path path) throws IOException {
        if(!isDir(path)) {
            createParentDirs(path);
            if(!Files.exists(path))
                Files.createFile(path);
        }
    }

    public static void createDirWithParents(Path path) throws IOException {
        if(!isDir(path)) {
            createParentDirs(path);
            if(!isDir(path))
                Files.createDirectory(path);
        }
    }
}
