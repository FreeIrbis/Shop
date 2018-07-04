package com.javaquasar.util.io;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Java Quasar
 */
public class Sorter {
    public static boolean sortAndDeleteDuplicateLine(String pathToFile, String pathToDestinationFile) throws IOException {
        List<String> list = FileManager.readListStringFromFile(pathToFile);
        if(list != null || list.isEmpty()) {
            Set<String> treeSet = new TreeSet<>();
            for(String s : list) {
                treeSet.add(s);
            }
            list.clear();
            list.addAll(treeSet);
            FileManager.writeFile(pathToDestinationFile, list);
            return true;
        }
        return false;
    }

}
