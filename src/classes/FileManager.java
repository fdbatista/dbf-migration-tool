package classes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    private static final String configFilePath = System.getProperty("user.dir") + "\\data\\config.fdb",
            wsFilePath = System.getProperty("user.dir") + "\\data\\servicio.txt";

    public static void serializeObject(Object obj) {
        try {
            File file = new File(configFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(configFilePath);
            ObjectOutputStream out = new ObjectOutputStream(stream);
            out.writeObject(obj);
            out.close();
            stream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Object deserializeObject() {
        Object obj = null;
        try {
            FileInputStream stream = new FileInputStream(configFilePath);
            ObjectInputStream in = new ObjectInputStream(stream);
            obj = in.readObject();
            in.close();
            stream.close();
        } catch (Exception ex) {
            
        }
        return obj;
    }

    public static String getFileContent(String path, Charset encoding) throws Exception {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static String getFileContent(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static String getWebServiceLocation() throws IOException {
        return getFileContent(wsFilePath).replaceAll("(\\r|\\n|\\r\\n)+", "");
    }

    public static void createFile(String content, String filePath) {
        byte data[] = content.getBytes();
        Path p = Paths.get(filePath);
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p))) {
            out.write(data, 0, data.length);
        } catch (IOException x) {
            System.err.println(x);
        }
    }
    
    public static void createWebServiceFile(String content, String filePath) {
        createFile(content, wsFilePath);
    }
}
