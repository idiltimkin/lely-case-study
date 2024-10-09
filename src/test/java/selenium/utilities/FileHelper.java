package selenium.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {
    public static String DOWNLOAD_FOLDER;

    static {
        try {
            DOWNLOAD_FOLDER = Files.createTempDirectory("downloads").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean isFileDownloaded(String fileName) {
        var file = new File(Paths.get(FileHelper.DOWNLOAD_FOLDER, fileName).toString());
        return file.exists();
    }
}
