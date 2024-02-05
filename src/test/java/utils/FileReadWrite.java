package utils;

import java.io.*;
import java.util.*;

public class FileReadWrite {
    public HashMap<String, Object> readCSVFileWithHeader(String fileName, String id) throws IOException {
        try {
            FileInputStream fileInputStream = null;
            ClassLoader classLoader = this.getClass().getClassLoader();
            File dataFile = new File(classLoader.getResource(fileName).getFile());
            fileInputStream = new FileInputStream(dataFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String[] lineItem = null;
            String line;
            HashMap mapRead = new LinkedHashMap<>();

            //Fetch headers in array
            String header[] = reader.readLine().split(";");

            // Read the file
            while ((line = reader.readLine()) != null) {
                lineItem = line.split(";");
                if (lineItem[0].equals(id)) {
                    for (int i = 0; i < lineItem.length; i++) {
                        mapRead.put(header[i], lineItem[i]);
                    }
                    return mapRead;
                }
            }
            return mapRead;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
