import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Main {

    public static void main(String[] args) {

        GameProgress gameProgress1 = new GameProgress(90, 5, 2, 5.5);
        GameProgress gameProgress2 = new GameProgress(72, 8, 3, 7.3);
        GameProgress gameProgress3 = new GameProgress(38, 12, 5, 17.2);

        saveGame("D://Games/savegames/save1.dat", gameProgress1);
        saveGame("D://Games/savegames/save2.dat", gameProgress2);
        saveGame("D://Games/savegames/save3.dat", gameProgress3);

        List<String> list = new ArrayList<>();
        list.add("D://Games/savegames/save1.dat");
        list.add("D://Games/savegames/save2.dat");
        list.add("D://Games/savegames/save3.dat");
        zipFiles("D://Games/savegames/zip_save.zip", list);
    }

    static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void zipFiles(String pathZip, List<String> listFiles) {
        try (
                ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZip));
        ) {
            for (String file : listFiles) {
                File FileToZip = new File(file);
                FileInputStream fis = new FileInputStream(file);
                ZipEntry entry = new ZipEntry(FileToZip.getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
                Files.delete(FileToZip.toPath());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
