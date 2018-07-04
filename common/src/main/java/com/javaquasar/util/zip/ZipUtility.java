package com.javaquasar.util.zip;

import com.javaquasar.util.io.FileManager;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Java Quasar on 04.08.17.
 */
public class ZipUtility {

    // This Zip file contains 11 PNG images
    private static final String FILE_NAME = "C:\\temp\\pics.zip";
    private static final String OUTPUT_DIR = "C:\\temp\\Images\\";
    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) throws IOException {
        File file = new File("./excel/build.gradle");
        System.out.println(file.getName());
        System.out.println(File.separator);
        String pathToZip = "." + File.separator + "test_zip.zip";
        String pathToFolder = "." + File.separator + "test_zip" + File.separator;
        //extractAll(pathToZip, pathToFolder);
        extractByName(pathToZip, pathToFolder, "test_folder");
    }

    public static void extractAll(String fromZip, String pathToDirectory) throws IOException {
        File zip = new File(fromZip);
        ZipFile zipFile = new ZipFile(zip);
        //ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zip));
        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            FileManager.createFolder(pathToDirectory);
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                System.out.println(entry.getName());
                if (entry.isDirectory()) {
                    FileManager.createFolder(pathToDirectory + entry.getName());
                } else {
                    try (InputStream is = zipFile.getInputStream(entry)) {
                        FileManager.writeFile(pathToDirectory + entry.getName(), is);
                    }
                }


            }
        } finally {
            zipFile.close();
        }
    }

    public static void extractByName(String fromZip, String pathToDirectory, String regExp) throws IOException {
        File zip = new File(fromZip);
        ZipFile zipFile = new ZipFile(zip);
        Pattern r = Pattern.compile(regExp);
        //ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zip));
        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                System.out.println(entry.getName());
                String nameEntry = FileManager.getNameFileWithoutFolder(entry.getName()).toLowerCase();
                Matcher m = r.matcher(nameEntry);
                if (m.find()) {
                    FileManager.createFolder(pathToDirectory);
                    if (entry.isDirectory()) {
                        FileManager.createFolder(pathToDirectory + entry.getName());
                        //extractByName(fromZip, pathToDirectory, entry.getName() + "*");
                    } else {
                        try (InputStream is = zipFile.getInputStream(entry)) {
                            FileManager.writeFile(pathToDirectory + entry.getName(), is);
                        }
                    }
                }
            }
        } finally {
            zipFile.close();
        }
    }

    public static void extractAll8(URI fromZip, final Path toDirectory) throws IOException {
//        FileSystems.newFileSystem(fromZip, Collections.emptyMap())
//                .getRootDirectories()
//                .forEach(root -> {
//                    // in a full implementation, you'd have to
//                    // handle directories
//                    Files.walk(root).forEach(path -> {
//                        try {
//                            Files.copy(path, toDirectory);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    });
//                });
    }

    private void extractAll(URI fromZip, Path toDirectory) throws IOException {
        FileSystem fileSystem = FileSystems.newFileSystem(fromZip, Collections.emptyMap());
        for (Path root : fileSystem.getRootDirectories()) {
            Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    // You can do anything you want with the path here
                    Files.copy(file, toDirectory);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    // In a full implementation, you'd need to create each
                    // sub-directory of the destination directory before
                    // copying files into it
                    return super.preVisitDirectory(dir, attrs);
                }
            });
        }
    }

    public static void zip(String zipName, List<String> listfiles) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(zipName);
            ZipOutputStream zos = new ZipOutputStream(fos)) {
            for(String fileName : listfiles) {
                addToZipFile(fileName, zos);
            }
        }
    }

    public static void addToZipFile(String fileName, ZipOutputStream zos) throws IOException {
        addToZipFile(fileName, zos, BUFFER_SIZE);
    }

    public static void addToZipFile(String fileName, ZipOutputStream zos, int bufferSize) throws IOException {
        File file = new File(fileName);
        try(FileInputStream fis = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[bufferSize];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            zos.closeEntry();
        }
    }

}
