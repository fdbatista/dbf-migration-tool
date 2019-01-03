package classes.dbftools;

import classes.javadbf.DBFField;
import classes.javadbf.DBFReader;
import java.io.*;
import java.util.ArrayList;

public class MyDBFReader {

    public static ArrayList<DBFField> readDBFColumns(String filePath) throws Exception {
        ArrayList<DBFField> res = new ArrayList<>();
        InputStream inputStream = new FileInputStream(filePath);
        DBFReader reader = new DBFReader(inputStream);
        int numberOfFields = reader.getFieldCount();
        for (int i = 0; i < numberOfFields; i++) {
            DBFField field = reader.getField(i);
            res.add(field);
        }
        inputStream.close();
        return res;
    }
    
    public static ArrayList<Object[]> readDBFRows(String filePath) {
        ArrayList<Object[]> res = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(filePath);
            DBFReader reader = new DBFReader(inputStream);
            Object[] rowObjects;
            while ((rowObjects = reader.nextRecord()) != null) {
                res.add(rowObjects);
            }
            inputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return res;
        }
    }
    
}
