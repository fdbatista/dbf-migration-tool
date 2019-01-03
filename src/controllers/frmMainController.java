package controllers;

import classes.AppBrain;
import classes.Static;
import classes.javadbf.DBFField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author FD
 */
public class frmMainController extends GenericController {

    String operationResult = "", dbfFilePath = "", scriptFilePath = "", fileName = "", destinationDB = "oracle", logFileDir = "", dbTableName = "telebanca_db";
    Boolean cleanInstallation = false, autoConvert = false, saveConversionToFile = false;
    //List<Field> columns;
    ArrayList<DBFField> columns;
    Set<Map.Entry<String, String>> appArguments;

    File selectedFile, savedFile;
    Service<Void> backgroundService;

    @FXML
    ImageView btnCloseApp;

    @FXML
    ImageView btnMinimApp;

    @FXML
    Button btnAccept;
    
    @FXML
    Button btnOpenFile;

    @FXML
    Label lblFileName;

    @FXML
    TextArea txtTableDescription;

    @FXML
    ComboBox cmbDestinationDB;

    @FXML
    CheckBox chkDropTable;

    @FXML
    private void convertDBF() throws IOException {
        if (cmbDestinationDB.getValue() != null) {
            if (saveConversionToFile) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Archivos SQL (*.sql)", "*.sql"),
                        new FileChooser.ExtensionFilter("Todos los archivos (*.*)", "*.*")
                );
                fileChooser.setTitle("Generar código SQL");
                destinationDB = cmbDestinationDB.getValue().toString().toLowerCase();
                fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setInitialFileName(fileName.substring(0, fileName.lastIndexOf(".")) + "_" + destinationDB + ".sql");
                savedFile = fileChooser.showSaveDialog(null);
                if (savedFile != null) {
                    try {
                        scriptFilePath = savedFile.getAbsolutePath();
                    } catch (Exception e) {
                        showMessage(e.getMessage(), "#f8f6d5", "#d3dd45");
                    }
                }
            }
            startManualConversionInBackground();
        } else {
            showMessage("Debe seleccionar un gestor de base de datos.", "#f8f6d5", "#d3dd45");
            cmbDestinationDB.requestFocus();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
        java -jar DBFMigrationTool.jar --dbf=E:\Desarrollo\Java\NetBeans\JavaDBF\Dev\App\DBFMigrationTool\src\db.dbf --clean=yes --convert=oracle --output=C:\Users\felix.batista\Desktop\db.sql
         */
        appArguments = Static.getAppArguments();
        autoConvert = (appArguments.size() > 0);
        appBrain = new AppBrain(autoConvert);

        if (autoConvert) {
            appBrain.showError("\nParametros:");
            for (Map.Entry<String, String> entry : appArguments) {
                String paramName = entry.getKey().trim().toLowerCase(), paramValue = entry.getValue().trim();
                appBrain.showError(paramName + ": " + paramValue);
                switch (paramName) {
                    case "dbf": {
                        dbfFilePath = paramValue;
                        autoConvert = true;
                    }
                    break;
                    case "clean": {
                        cleanInstallation = paramValue.toLowerCase().equals("yes");
                    }
                    break;
                    case "convert": {
                        destinationDB = paramValue.toLowerCase();
                    }
                    break;
                    case "output": {
                        scriptFilePath = paramValue;
                    }
                    break;
                    case "log": {
                        logFileDir = paramValue;
                    }
                    break;
                    default:
                        break;
                }
            }

            chkDropTable.setSelected(cleanInstallation);

            switch (destinationDB) {
                case "oracle": {
                    cmbDestinationDB.setValue("Oracle");
                }
                break;
                case "mysql": {
                    cmbDestinationDB.setValue("MySQL");
                }
                break;
                case "postgresql": {
                    cmbDestinationDB.setValue("PostgreSQL");
                }
                break;
                default:
                    break;
            }
            cmbDestinationDB.setDisable(true);
            chkDropTable.setDisable(true);
            startAutomaticConversionInBackground();
        } else {
            autoConvert = false;
            dbfFilePath = "";
            btnAccept.setDisable(true);
            Tooltip.install(btnCloseApp, new Tooltip("Cerrar"));
            Tooltip.install(btnMinimApp, new Tooltip("Minimizar"));
            ObservableList<String> dbManagers = FXCollections.observableArrayList("Oracle", "MySQL", "PostgreSQL");
            cmbDestinationDB.setItems(dbManagers);
            cmbDestinationDB.setValue("Oracle");
            cleanInstallation = true;
            chkDropTable.setSelected(cleanInstallation);
        }
    }

    @FXML
    public void close() {
        appBrain.exit();
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage) btnMinimApp.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void btnCloseMouseEntered() {
        btnCloseApp.setImage(new Image(getClass().getResource("/img/close_hover.png").toExternalForm()));
    }

    @FXML
    public void btnCloseMouseExited() {
        btnCloseApp.setImage(new Image(getClass().getResource("/img/close.png").toExternalForm()));
    }

    @FXML
    public void btnMinimMouseEntered() {
        btnMinimApp.setImage(new Image(getClass().getResource("/img/minimize_hover.png").toExternalForm()));
    }

    @FXML
    public void btnMinimMouseExited() {
        btnMinimApp.setImage(new Image(getClass().getResource("/img/minimize.png").toExternalForm()));
    }
    
    private void startManualConversionInBackground() {
        btnAccept.setDisable(true);
        btnOpenFile.setDisable(true);
        showMessage("Conversión en proceso...", "#f8f6d5", "#d3dd45");
        destinationDB = cmbDestinationDB.getValue().toString().toLowerCase();
        cleanInstallation = chkDropTable.isSelected();

        backgroundService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        ArrayList<String> conversionResult = appBrain.convertDBFFile(columns, dbTableName, dbfFilePath, scriptFilePath, destinationDB, cleanInstallation, logFileDir);
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    btnOpenFile.setDisable(false);
                                    showMessage(conversionResult.get(0), "#effff3", "#00e00e");
                                    txtTableDescription.setText(conversionResult.get(0));
                                } finally {
                                    latch.countDown();
                                }
                            }
                        });
                        latch.await();
                        //Keep with the background work
                        return null;
                    }
                };
            }
        };
        backgroundService.start();
    }

    private void startAutomaticConversionInBackground() {
        int startIndex = dbfFilePath.lastIndexOf("/");
        startIndex = startIndex == -1 ? dbfFilePath.lastIndexOf("\\") : startIndex;
        fileName = dbfFilePath.substring(startIndex == -1 ? 0 : startIndex + 1);
        /*lblFileName.setText(fileName);
        lblFileName.setTooltip(new Tooltip(dbfFilePath));
        btnAccept.setDisable(true);*/
        showMessage("Conversión en proceso...", "#f8f6d5", "#d3dd45");
        destinationDB = cmbDestinationDB.getValue().toString().toLowerCase();
        cleanInstallation = chkDropTable.isSelected();

        backgroundService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        columns = appBrain.readDBFColumns(dbfFilePath);
                        ArrayList<String> conversionResult = appBrain.convertDBFFile(columns, dbTableName, dbfFilePath, scriptFilePath, destinationDB, cleanInstallation, logFileDir);
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    showMessage(conversionResult.get(3), "", "");
                                    appBrain.exit();
                                } finally {
                                    latch.countDown();
                                }
                            }
                        });
                        latch.await();
                        //Keep with the background work
                        return null;
                    }
                };
            }
        };
        backgroundService.start();
    }

    private void readColumnsInBackground() {
        int startIndex = dbfFilePath.lastIndexOf("/");
        startIndex = startIndex == -1 ? dbfFilePath.lastIndexOf("\\") : startIndex;
        fileName = dbfFilePath.substring(startIndex == -1 ? 0 : startIndex + 1);
        lblFileName.setText(fileName);
        lblFileName.setTooltip(new Tooltip(dbfFilePath));

        backgroundService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        columns = appBrain.readDBFColumns(dbfFilePath);
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    btnAccept.setDisable(false);
                                    int cantCols = columns.size();
                                    String tableDesc = "Cantidad de columnas: " + String.valueOf(cantCols) + "\n";
                                    for (int i = 0; i < cantCols; i++) {
                                        DBFField column = columns.get(i);
                                        String dataType = appBrain.getFieldTypeName(String.valueOf(column.getDataType()));
                                        tableDesc += "\n" + String.valueOf(i + 1) + ". " + column.getName() + ": " + dataType;
                                    }
                                    txtTableDescription.setText(tableDesc);
                                    showMessage("Fichero cargado.", "#effff3", "#00e00e");
                                    btnAccept.setDisable(false);
                                    
                                } finally {
                                    latch.countDown();
                                }
                            }
                        });
                        latch.await();
                        //Keep with the background work
                        return null;
                    }
                };
            }
        };
        backgroundService.start();
    }

    public void selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione un fichero");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos DBF (*.dbf)", "*.dbf"),
                new FileChooser.ExtensionFilter("Todos los archivos (*.*)", "*.*")
        );
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            dbfFilePath = selectedFile.getAbsolutePath();
            showMessage("Leyendo estructura de la base de datos...", "#f8f6d5", "#d3dd45");
            readColumnsInBackground();
        }
    }

}
