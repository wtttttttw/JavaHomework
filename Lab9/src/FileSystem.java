import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileSystem {
    public static void main(String[] args) throws IOException {
        Path dir0 = Files.createDirectory(Paths.get("C:\\Users\\matte\\OneDrive\\Desktop\\chudova"));
        Path file0 = Files.createFile(Paths.get("C:\\Users\\matte\\OneDrive\\Desktop\\chudova\\anastasia.txt"));
        Path dirs = Files.createDirectories(Paths.get("C:\\Users\\matte\\OneDrive\\Desktop\\chudova\\dir1\\dir2\\dir3"));
        file0 = Files.copy(file0, Paths.get("C:\\Users\\matte\\OneDrive\\Desktop\\chudova\\dir1\\dir2\\dir3\\anastasia.txt"), REPLACE_EXISTING);
        Path file1 = Files.createFile(Paths.get("C:\\Users\\matte\\OneDrive\\Desktop\\chudova\\dir1\\file1.txt"));
        Path file2 = Files.createFile(Paths.get("C:\\Users\\matte\\OneDrive\\Desktop\\chudova\\dir1\\dir2\\file2.txt"));

        String startDirectory = "C:\\Users\\matte\\OneDrive\\Desktop\\chudova";
        try (Stream<Path> walkStream = Files.walk(Paths.get(startDirectory))) {
            walkStream.forEach(path -> {
                if (Files.isRegularFile(path)) {
                    System.out.println("F: " + path);
                } else if (Files.isDirectory(path)) {
                    System.out.println("D: " + path);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path dir1ToDelete = Paths.get("C:\\Users\\matte\\OneDrive\\Desktop\\chudova\\dir1");
        if (Files.exists(dir1ToDelete)) {
            try (Stream<Path> walk = Files.walk(dir1ToDelete)) {
                walk.sorted(Comparator.reverseOrder()) // удаляем сначала файлы, затем директории
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                            System.out.println("Deleted: " + path);
                        } catch (IOException e) {
                            System.err.println("Cannot delete: " + path + " - " + e.getMessage());
                        }
                    });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        

        }
    }
