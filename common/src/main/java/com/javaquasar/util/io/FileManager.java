package com.javaquasar.util.io;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Properties;

/**
 * Created by Java Quasar on 04.08.17.
 */
public class FileManager {

    private static final int BUFFER = 8 * 1024;

    public static void main(String[] args) throws IOException {
        createFile("./test/test.txt");
    }

    public static List<String> readListStringFromFile(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            return Files.readAllLines(Paths.get(path));
        }
        return null;
    }

    public static String readFile(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            StringBuilder sb = new StringBuilder();
            String currentLine;
            try (FileReader fr = new FileReader(path);
                 BufferedReader br = new BufferedReader(fr)) {
                while ((currentLine = br.readLine()) != null) {
                    sb.append(currentLine);
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
        return null;
    }

    public static void writeFile(String path, String text) throws IOException {
        File file = createFile(path);
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
            writer.println(text);
        }
    }

    public static void writeFile(String path, List<String> list) throws IOException {
        File file = createFile(path);
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
            for(String s : list) {
                writer.println(s);
            }
        }
    }

    public static void writeFile(String path, InputStream is) throws IOException {
        writeFile(path, is, BUFFER);
    }

    @Deprecated
    public static void writeFileAllBites(String path, InputStream is) throws IOException {
        try (OutputStream outStream = new FileOutputStream(createFile(path))) {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            outStream.write(buffer);
        }
    }

    public static void writeFile(String path, InputStream is, int bufferSize) throws IOException {
        try (OutputStream outStream = new FileOutputStream(createFile(path))) {
            byte[] buffer = new byte[bufferSize];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }
    }

    public static File createFile(String pathToFile) throws IOException {
        File file = new File(pathToFile);
        if (!file.exists()) {
            createFolder(file.getParent());
            file.createNewFile();
        }
        return file;
    }

    public static File createFolder(String pathToFolder) throws IOException {
        File folder = new File(pathToFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    public static void deleteFile(String pathToFile) {
        File file = new File(pathToFile);
        if (file.exists()) {
            file.delete();
        }
    }

    public static Properties getProperties(String path) {
        Properties prop = new Properties();
        try(InputStream input = new FileInputStream(path)) {
            prop.load(input);
        } catch (FileNotFoundException ex) {
            // TODO
        } catch (IOException ex) {
            // TODO
        }
        return prop;
    }

    private static void copyFileUsingFileStreams(File source, File dest) throws IOException {
        try (InputStream input = new FileInputStream(source);
             OutputStream output = new FileOutputStream(dest) ) {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        }
    }

    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        try (FileChannel inputChannel = new FileInputStream(source).getChannel();
             FileChannel outputChannel = new FileOutputStream(dest).getChannel()) {
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        }
    }

    private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    public static void copyFile(File source, File dest) throws IOException {
        copyFileUsingJava7Files(source, dest);
    }

    public static void copyFile(String source, String dest) throws IOException {
        copyFileUsingJava7Files(new File(source), new File(dest));
    }

    public static void purgeDirectory(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            deleteFile(new File(path));
        }
    }

    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteFile(f);
            }
        } else {
            file.delete();
        }
    }

    public static String getNameFileWithoutFolder(String pathToFile) {
        File file = new File(pathToFile);
        System.out.println("file.getName( = " + file.getName());
        return file.getName();
    }
}

