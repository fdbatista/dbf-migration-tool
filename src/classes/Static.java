/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;

/**
 *
 * @author FD
 */
public class Static {
    
    private static String appTitle = "DBFMigrationTool";
    private static Set<Map.Entry<String, String>> appArguments;
    
    public static Set<Map.Entry<String, String>> getAppArguments() {
        return appArguments;
    }

    public static void setAppArguments(Set<Map.Entry<String, String>> pAppArguments) {
        appArguments = pAppArguments;
    }
    
    public static ButtonType Confirmation(String encabezado, String contenido) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(appTitle);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setResizable(false);
        return alert.showAndWait().get();
    }
    
    public static void Information(String encabezado, String contenido) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(appTitle);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setResizable(false);
        alert.showAndWait();
    }
    
    public static void Warning(String encabezado, String contenido) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(appTitle);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setResizable(false);
        alert.showAndWait();
    }
    
    public static void Error(String encabezado, String contenido) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(appTitle);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setResizable(false);
        alert.showAndWait();
    }
    
    public static void DisplayOnConsole(String encabezado, String contenido) {
        System.out.println((!encabezado.equals("") ? encabezado + ": " : "") + contenido);
    }
    
    public static String InputTextBox(String texto, String encabezado, String contenido) {
        TextInputDialog dialog = new TextInputDialog(texto);
        dialog.setTitle(appTitle);
        dialog.setHeaderText(encabezado);
        dialog.setContentText(contenido);
        dialog.initStyle(StageStyle.TRANSPARENT);
        return dialog.showAndWait().get();
    }
    
    public static String SelectList(List<String> elems, String elemPredeterm, String encabezado, String contenido) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(elemPredeterm, elems);
        dialog.setTitle(appTitle);
        dialog.setHeaderText(encabezado);
        dialog.setContentText(contenido);
        dialog.initStyle(StageStyle.TRANSPARENT);
        return dialog.showAndWait().get();
    }
    
    public static String SaveFile(ArrayList<String> contents, String path){
        try {
            File file = new File(path);
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);
            for (String content : contents) {
                fileWriter.write(content);
            }
            fileWriter.close();
            return "El proceso ha finalizado.";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    
    public static void CloseApplication() {
        Platform.exit();
    }
    
    private static String lpadValue(String value) {
        return ((value.length() == 1) ? "0" + value : value);
    }
    
    public static String generateDestinationFilePath(String originFilePath) {
        Calendar calendar = Calendar.getInstance();
        String
                dateSeparator = "",
                year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2) + dateSeparator,
                month = lpadValue(String.valueOf(calendar.get(Calendar.MONTH) + 1)) + dateSeparator,
                day = lpadValue(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))),
                newName = "E04" + year + month + day;
        int startIndex = originFilePath.lastIndexOf("/");
        startIndex = startIndex == -1 ? originFilePath.lastIndexOf("\\") : startIndex;
        startIndex = startIndex == -1 ? 0 : startIndex + 1;
        int endingIndex = originFilePath.lastIndexOf(".");
        String[] subStrings = new String[] { originFilePath.substring(0, startIndex), originFilePath.substring(endingIndex) };
        int i = 1;
        String newFileName = subStrings[0] + newName + subStrings[1];
        File file = new File(newFileName);
        while (file.exists()) {
            newFileName = subStrings[0] + newName + "_" + (i++) + subStrings[1];
            file = new File(newFileName);
        }
        return newFileName;
    }
    
}
