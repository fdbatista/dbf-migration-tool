package classes.dbftools;

import classes.javadbf.DBFException;
import classes.javadbf.DBFField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyDBFConverter {

    public static ArrayList<DBFField> readDBFColumns(String filePath) throws Exception {
        return MyDBFReader.readDBFColumns(filePath);
    }

    private static ArrayList<Object[]> readDBFRows(String filePath) {
        return MyDBFReader.readDBFRows(filePath);
    }

    public static String generateDBF(String filePath, ArrayList<DBFField> columns, ArrayList<Object[]> rows) throws DBFException, IOException {
        return (rows.size() > 0) ? MyDBFWriter.generateDBF(filePath, columns, rows) : "";
    }

    /*public static String convertDBFFile(ArrayList<DBFField> columns, String fileName, String originFilePath, String destinationFilePath, String destinationDB, boolean dropTable, String logFileDir) {
        String resultScript = "", tableQualifier = destinationDB.equals("mysql") ? "`" : "", tableName = tableQualifier + fileName + tableQualifier, columnNames = "";
        try {
            ArrayList<Object[]> rows = readDBFRows(originFilePath);
            int rowsCount = rows.size(), colsCount = columns.size();

            //Generar sentencia DDL para crear la tabla
            String sqlDataType, dbfDataType;
            switch (destinationDB) {

                case "oracle": {
                    if (dropTable) {
                        resultScript = "begin\n\n  begin\n    execute immediate 'drop table " + tableName + " purge';\n  exception\n    when others then null;\n  end;\n\n";
                    }
                    resultScript += "  execute immediate 'create table " + tableName + "\n  (\n    ID_PAGO integer primary key not null,\n";
                    
                    for (int i = 0; i < colsCount; i++) {
                        DBFField column = columns.get(i);
                        dbfDataType = String.valueOf(column.getDataType());

                        switch (dbfDataType) {
                            case "MEMO":
                            case "77":
                                sqlDataType = "clob";
                                break;
                            case "NUMBER":
                            case "78":
                                sqlDataType = "number(" + column.getFieldLength() + ")";
                                break;
                            case "FLOAT":
                            case "70":
                                sqlDataType = "number(" + column.getFieldLength() + ", " + column.getDecimalCount() + ")";
                                break;
                            case "LOGICAL":
                            case "76":
                                sqlDataType = "number(1)";
                                break;
                            case "DATE":
                            case "68":
                                sqlDataType = "date";
                                break;
                            default:
                                sqlDataType = "varchar2(" + column.getFieldLength() + ")";
                                break;
                        }
                        String columnName = tableQualifier + column.getName() + tableQualifier;
                        resultScript += "    " + columnName + " " + sqlDataType + ((i < colsCount - 1) ? "," : "") + "\n";
                        columnNames += columnName + ((i < colsCount - 1) ? "," : "");
                    }
                }
                break;

                case "mysql": {
                    if (dropTable) {
                        resultScript = "drop table if exists " + tableName + ";\n\n";
                    }
                    resultScript += "create table " + tableName + "\n(\n";

                    for (int i = 0; i < colsCount; i++) {
                        DBFField column = columns.get(i);
                        dbfDataType = String.valueOf(column.getDataType());

                        switch (dbfDataType) {
                            case "MEMO":
                            case "77":
                                sqlDataType = "longtext(4294967295)";
                                break;
                            case "NUMBER":
                            case "67":
                                sqlDataType = "bigint(" + column.getFieldLength() + ")";
                                break;
                            case "FLOAT":
                            case "70":
                                sqlDataType = "double(" + column.getFieldLength() + ", " + column.getDecimalCount() + ")";
                                break;
                            case "LOGICAL":
                            case "76":
                                sqlDataType = "boolean";
                                break;
                            case "DATE":
                            case "68":
                                sqlDataType = "date";
                                break;
                            default:
                                sqlDataType = "varchar(" + column.getFieldLength() + ")";
                                break;
                        }
                        String columnName = tableQualifier + column.getName() + tableQualifier;
                        resultScript += "  " + columnName + " " + sqlDataType + ((i < colsCount - 1) ? "," : "") + "\n";
                        columnNames += columnName + ((i < colsCount - 1) ? "," : "");
                    }
                }
                break;

                case "postgresql": {
                    if (dropTable) {
                        resultScript = "drop table if exists " + tableName + ";\n\n";
                    }
                    resultScript += "create table " + tableName + "\n(\n";

                    for (int i = 0; i < colsCount; i++) {
                        DBFField column = columns.get(i);
                        dbfDataType = String.valueOf(column.getDataType());

                        switch (dbfDataType) {
                            case "MEMO":
                            case "77":
                                sqlDataType = "text";
                                break;
                            case "NUMBER":
                            case "78":
                                sqlDataType = "numeric(" + column.getFieldLength() + ")";
                                break;
                            case "FLOAT":
                            case "70":
                                sqlDataType = "numeric(" + column.getFieldLength() + ", " + column.getDecimalCount() + ")";
                                break;
                            case "LOGICAL":
                            case "76":
                                sqlDataType = "boolean";
                                break;
                            case "DATE":
                            case "68":
                                sqlDataType = "date";
                                break;
                            default:
                                sqlDataType = "character varying(" + column.getFieldLength() + ")";
                                break;
                        }
                        String columnName = tableQualifier + column.getName() + tableQualifier;
                        resultScript += "  " + columnName + " " + sqlDataType + ((i < colsCount - 1) ? "," : "") + "\n";
                        columnNames += columnName + ((i < colsCount - 1) ? "," : "");
                    }
                }
                break;
                default:
                    break;
            }
            resultScript += destinationDB.equals("oracle") ? "  )';\n\n" : ");\n\n";

            //Generar sentencias DML tipo INSERT para cada fila
            for (int i = 0; i < rowsCount; i++) {
                Object[] record = rows.get(i);
                String values = "";

                for (int j = 0; j < colsCount; j++) {
                    String sqlValue = "";
                    try {
                        DBFField column = columns.get(j);
                        dbfDataType = String.valueOf(column.getDataType());
                        //Object dbfValue = record. getTypedValue(field.getName());
                        Object dbfValue = record[j];

                        switch (dbfDataType) {
                            case "LOGICAL":
                            case "76":
                                sqlValue = destinationDB.equals("oracle") ? dbfValue.toString().toLowerCase().equals("true") ? "1" : "0" : dbfValue.toString().toLowerCase();
                                break;
                            case "DATE":
                            case "68":
                                Calendar calendario = Calendar.getInstance();
                                calendario.setTime((Date) dbfValue);
                                sqlValue = "'" + calendario.get(Calendar.YEAR) + "/" + calendario.get(Calendar.MONTH) + "/" + calendario.get(Calendar.DAY_OF_MONTH) + "'";
                                sqlValue = destinationDB.equals("oracle") ? "TO_DATE(" + sqlValue + ", 'YYYY/MM/DD')" : sqlValue;
                                break;
                            default:
                                sqlValue = "'" + dbfValue.toString().trim().replaceAll("'", "").replaceAll("&", "y") + "'";
                                if (sqlValue.equals("''")) {
                                    sqlValue = "NULL";
                                }
                                break;
                        }
                    } catch (Exception e) {
                        sqlValue = "NULL";
                    } finally {
                        values += sqlValue + ((j < colsCount - 1) ? ", " : "");
                    }
                }
                if (destinationDB.equals("oracle")) {
                    resultScript += "  execute immediate 'insert into " + tableName + " (ID_PAGO," + columnNames + ") values (SEQ_ID_PAGO.NEXTVAL, " + values.replace("'", "''").replaceAll("(\\r|\\n|\\r\\n)+", "") + ")';\n";
                } else {
                    resultScript += "insert into " + tableName + " (" + columnNames + ") values (" + values + ");\n";
                }
            }
            resultScript += destinationDB.equals("oracle") ? "\n  execute immediate 'alter package telebanca compile';\n  execute immediate 'commit';\n\nend;\n" : "";
        } catch (Exception e) {
            resultScript = "Ocurrió un error al realizar la operación: " + e.getMessage();
        } finally {
            return resultScript;
        }
    }*/
    public static ArrayList<String> convertDBFFile(ArrayList<DBFField> columns, String fileName, String originFilePath, String destinationFilePath, String destinationDB, boolean dropTable, String logFileDir) {
        ArrayList<String> res = new ArrayList<>();
        String resultScript = "", tableQualifier = destinationDB.equals("mysql") ? "`" : "", tableName = tableQualifier + fileName + tableQualifier, columnNames = "";
        try {
            ArrayList<Object[]> rows = readDBFRows(originFilePath);
            int rowsCount = rows.size(), colsCount = columns.size();
            //Generar sentencia DDL para crear la tabla
            String sqlDataType, dbfDataType;
            switch (destinationDB) {

                case "oracle": {
                    if (dropTable) {
                        res.add("begin execute immediate 'drop table " + tableName + " purge'; exception when others then null; end;");
                    }
                    resultScript = "begin execute immediate 'create table " + tableName + " (ID_PAGO integer primary key not null,";
                    for (int i = 0; i < colsCount; i++) {
                        DBFField column = columns.get(i);
                        dbfDataType = String.valueOf(column.getDataType());
                        switch (dbfDataType) {
                            case "MEMO":
                            case "77":
                                sqlDataType = "clob";
                                break;
                            case "NUMBER":
                            case "78":
                                sqlDataType = "number(" + column.getFieldLength() + ")";
                                break;
                            case "FLOAT":
                            case "70":
                                sqlDataType = "number(" + column.getFieldLength() + ", " + column.getDecimalCount() + ")";
                                break;
                            case "LOGICAL":
                            case "76":
                                sqlDataType = "number(1)";
                                break;
                            case "DATE":
                            case "68":
                                sqlDataType = "date";
                                break;
                            default:
                                sqlDataType = "varchar2(" + column.getFieldLength() + ")";
                                break;
                        }
                        String columnName = tableQualifier + column.getName() + tableQualifier;
                        resultScript += columnName + " " + sqlDataType + ((i < colsCount - 1) ? "," : "");
                        columnNames += columnName + ((i < colsCount - 1) ? "," : "");
                    }
                }
                break;

                case "mysql": {
                    if (dropTable) {
                        res.add("drop table if exists " + tableName + ";");
                    }
                    resultScript += "create table " + tableName + "(";
                    for (int i = 0; i < colsCount; i++) {
                        DBFField column = columns.get(i);
                        dbfDataType = String.valueOf(column.getDataType());
                        switch (dbfDataType) {
                            case "MEMO":
                            case "77":
                                sqlDataType = "longtext(4294967295)";
                                break;
                            case "NUMBER":
                            case "67":
                                sqlDataType = "bigint(" + column.getFieldLength() + ")";
                                break;
                            case "FLOAT":
                            case "70":
                                sqlDataType = "double(" + column.getFieldLength() + ", " + column.getDecimalCount() + ")";
                                break;
                            case "LOGICAL":
                            case "76":
                                sqlDataType = "boolean";
                                break;
                            case "DATE":
                            case "68":
                                sqlDataType = "date";
                                break;
                            default:
                                sqlDataType = "varchar(" + column.getFieldLength() + ")";
                                break;
                        }
                        String columnName = tableQualifier + column.getName() + tableQualifier;
                        resultScript += columnName + " " + sqlDataType + ((i < colsCount - 1) ? "," : "");
                        columnNames += columnName + ((i < colsCount - 1) ? "," : "");
                    }
                }
                break;

                case "postgresql": {
                    if (dropTable) {
                        res.add("drop table if exists " + tableName + ";");
                    }
                    resultScript += "create table " + tableName + "(";

                    for (int i = 0; i < colsCount; i++) {
                        DBFField column = columns.get(i);
                        dbfDataType = String.valueOf(column.getDataType());

                        switch (dbfDataType) {
                            case "MEMO":
                            case "77":
                                sqlDataType = "text";
                                break;
                            case "NUMBER":
                            case "78":
                                sqlDataType = "numeric(" + column.getFieldLength() + ")";
                                break;
                            case "FLOAT":
                            case "70":
                                sqlDataType = "numeric(" + column.getFieldLength() + ", " + column.getDecimalCount() + ")";
                                break;
                            case "LOGICAL":
                            case "76":
                                sqlDataType = "boolean";
                                break;
                            case "DATE":
                            case "68":
                                sqlDataType = "date";
                                break;
                            default:
                                sqlDataType = "character varying(" + column.getFieldLength() + ")";
                                break;
                        }
                        String columnName = tableQualifier + column.getName() + tableQualifier;
                        resultScript += columnName + " " + sqlDataType + ((i < colsCount - 1) ? "," : "");
                        columnNames += columnName + ((i < colsCount - 1) ? "," : "");
                    }
                }
                break;
                default:
                    break;
            }
            resultScript += destinationDB.equals("oracle") ? ")'; end;" : ");";
            res.add(resultScript);

            //Generar sentencias DML tipo INSERT para cada fila
            for (int i = 0; i < rowsCount; i++) {
                Object[] record = rows.get(i);
                String values = "";

                for (int j = 0; j < colsCount; j++) {
                    String sqlValue = "";
                    try {
                        DBFField column = columns.get(j);
                        dbfDataType = String.valueOf(column.getDataType());
                        //Object dbfValue = record. getTypedValue(field.getName());
                        Object dbfValue = record[j];

                        switch (dbfDataType) {
                            case "LOGICAL":
                            case "76":
                                sqlValue = destinationDB.equals("oracle") ? dbfValue.toString().toLowerCase().equals("true") ? "1" : "0" : dbfValue.toString().toLowerCase();
                                break;
                            case "DATE":
                            case "68":
                                Calendar calendario = Calendar.getInstance();
                                calendario.setTime((Date) dbfValue);
                                sqlValue = "'" + calendario.get(Calendar.YEAR) + "/" + calendario.get(Calendar.MONTH) + "/" + calendario.get(Calendar.DAY_OF_MONTH) + "'";
                                sqlValue = destinationDB.equals("oracle") ? "TO_DATE(" + sqlValue + ", 'YYYY/MM/DD')" : sqlValue;
                                break;
                            default:
                                sqlValue = "'" + dbfValue.toString().trim().replaceAll("'", "").replaceAll("&", "y") + "'";
                                if (sqlValue.equals("''")) {
                                    sqlValue = "NULL";
                                }
                                break;
                        }
                    } catch (Exception e) {
                        sqlValue = "NULL";
                    } finally {
                        values += sqlValue + ((j < colsCount - 1) ? ", " : "");
                    }
                }
                if (destinationDB.equals("oracle")) {
                    resultScript = "begin execute immediate 'insert into " + tableName + " (ID_PAGO," + columnNames + ") values (SEQ_ID_PAGO.NEXTVAL, " + values.replace("'", "''").replaceAll("(\\r|\\n|\\r\\n)+", "") + ")'; end;";
                } else {
                    resultScript = "insert into " + tableName + " (" + columnNames + ") values (" + values + ");";
                }
                res.add(resultScript);
            }
            resultScript = destinationDB.equals("oracle") ? "begin execute immediate 'alter package telebanca compile'; execute immediate 'commit'; end;" : "";
            res.add(resultScript);
        } catch (Exception e) {
            res.add("Ocurrió un error al realizar la operación: " + e.getMessage());
        } finally {
            return res;
        }
    }

}
