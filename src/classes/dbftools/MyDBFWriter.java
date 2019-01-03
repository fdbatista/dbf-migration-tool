package classes.dbftools;

import classes.javadbf.*;
import java.io.*;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class MyDBFWriter {

    public static String generateDBF(String filePath, ArrayList<DBFField> columns, ArrayList<Object[]> rows) throws DBFException, IOException {

        DBFWriter writer = new DBFWriter();
        int fieldsCount = columns.size();
        DBFField[] dbfFields = new DBFField[fieldsCount + 1];
        for (int i = 0; i < fieldsCount; i++) {
            DBFField column = columns.get(i);
            if (column.getDataType() == DBFField.FIELD_TYPE_D) {
                column.setDataType(DBFField.FIELD_TYPE_C);
                column.setFieldLength(10);
            } else {
                column.setDataType(DBFField.FIELD_TYPE_C);
            }
            dbfFields[i] = column;
        }
        dbfFields[dbfFields.length - 1] = MyDBFWriter.generateDBField("OBSERVAC", DBFField.FIELD_TYPE_C, 250, 0);
        writer.setFields(dbfFields);
        for (Object[] row : rows) {
            Object[] newRow = new Object[row.length];
            for (int i = 0; i < row.length; i++) {
                newRow[i] = row[i].toString();
            }
            writer.addRecord(newRow);
        }
        FileOutputStream fos = new FileOutputStream(filePath);
        writer.write(fos);
        return "";
    }

    private static void test(String filePath) throws DBFException, IOException {
        DBFField[] fields = new DBFField[3];
        fields[0] = generateDBField("emp_code", DBFField.FIELD_TYPE_C, 10, 0);
        fields[1] = generateDBField("emp_name", DBFField.FIELD_TYPE_C, 20, 0);
        fields[2] = generateDBField("emp_salary", DBFField.FIELD_TYPE_N, 12, 2);
        DBFWriter writer = new DBFWriter();
        writer.setFields(fields);
        writer.addRecord(new Object[]{"1", "John Smith", 5000.75});
        writer.addRecord(new Object[]{"2", "Jane Doe", 7700.07});
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            writer.write(fos);
        }
    }

    public static DBFField generateDBField(String name, byte type, int length, int decCount) {
        DBFField res = new DBFField();
        res.setName(name);
        res.setDataType(type);
        res.setFieldLength(length);
        res.setDecimalCount(decCount);
        return res;
    }

    public static ArrayList<Object[]> extractRowsFromJSON(String jsonString, String index) {
        JSONObject obj = new JSONObject(jsonString);
        JSONArray array = obj.getJSONArray(index);
        int rowsCount = array.length();
        ArrayList<Object[]> rowsList = new ArrayList<>();
        for (int i = 0; i < rowsCount; i++) {
            JSONArray row = array.getJSONArray(i);
            Object[] objRow = row.toList().toArray();
            rowsList.add(objRow);
        }
        return rowsList;
    }
    
    public static String extractValueFromJSON(String jsonString, String index) {
        String res = new JSONObject(jsonString).getString(index);
        return res;
    }

}
