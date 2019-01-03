package classes;

import classes.dbftools.MyDBFConverter;
import classes.dbftools.MyDBFWriter;
import classes.javadbf.DBFField;
import classes.security.EncryptionKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.ws.BindingProvider;
import serviciowebtb.ArrayOfString;
import serviciowebtb.ServicioWebTB;
import serviciowebtb.ServicioWebTBSoap;

/**
 *
 * @author FD
 */
public final class AppBrain {

    private boolean autoConvert;
    private AppConfig appConfig;

    public AppBrain(boolean autoConvert) {
        this.autoConvert = autoConvert;
        readAppConfig();
    }

    public boolean isAutoConvert() {
        return autoConvert;
    }

    public void setAutoConvert(boolean autoConvert) {
        this.autoConvert = autoConvert;
    }

    public void showError(String error) {
        if (autoConvert) {
            Static.DisplayOnConsole("Error", error);
        } else {
            Static.Error("Error", error);
        }
    }

    public void exit() {
        Static.CloseApplication();
    }

    public void readAppConfig() {
        appConfig = (AppConfig) FileManager.deserializeObject();
        if (appConfig != null) {
            appConfig.revealItems();
            String wsLocationRes = appConfig.updateWebServiceLocation();
            if (!wsLocationRes.equals("")) {
                showError(wsLocationRes);
                exit();
            }
        } else {
            try {
                appConfig = new AppConfig(true);
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
            showError("No ha sido posible cargar los datos iniciales.\nLa aplicación no puede continuar.");
            exit();
        }
    }

    public ArrayList<DBFField> readDBFColumns(String filePath) throws Exception {
        return MyDBFConverter.readDBFColumns(filePath);
    }

    public String getFieldTypeName(String typeName) {
        String result;
        switch (typeName) {
            case "NUMBER":
            case "78":
                result = "Número entero";
                break;
            case "FLOAT":
            case "70":
                result = "Número decimal";
                break;
            case "LOGICAL":
            case "76":
                result = "Valor lógico";
                break;
            case "DATE":
            case "68":
                result = "Fecha";
                break;
            case "CHARACTER":
            case "67":
            case "MEMO":
            case "77":
                result = "Texto";
                break;
            default:
                result = "¡NO SOPORTADO!";
                break;
        }
        return result;
    }

    public ArrayList<String> convertDBFFile(ArrayList<DBFField> columns, String fileName, String originFilePath, String destinationFilePath, String destinationDB, boolean dropTable, String logFileDir) {
        ArrayList<String> res = new ArrayList<>();
        try {
            ArrayList<String>
                    conversionResults = MyDBFConverter.convertDBFFile(columns, fileName, originFilePath, destinationFilePath, destinationDB, dropTable, logFileDir),
                    encryptionResults = appConfig.encryptConversionResult(conversionResults);
            List<String> encryptedSentences = encryptionResults.subList(2, encryptionResults.size());
            if (!destinationFilePath.equals("")) {
                Static.SaveFile(conversionResults, destinationFilePath);
            }
            ArrayOfString sqls = new ArrayOfString();
            for (String string : encryptedSentences) {
                sqls.getString().add(string);
            }
            String
                    signature = encryptionResults.get(0) + "." + encryptionResults.get(1),
                    jsonStringFromWS = invokeWebService(signature, sqls),
                    resultFromWS = MyDBFWriter.extractValueFromJSON(jsonStringFromWS, "result"),
                    newFilePath = Static.generateDestinationFilePath(originFilePath),
                    dbfGenerationResult = MyDBFConverter.generateDBF(newFilePath, columns, MyDBFWriter.extractRowsFromJSON(jsonStringFromWS, "data"));
            res.add((!resultFromWS.equals("")) ? resultFromWS : (!dbfGenerationResult.equals("")) ? dbfGenerationResult: "El proceso ha finalizado.");
        } catch (Exception exc) {
            res.add(exc.getMessage());
        } finally {
            return res;
        }
    }

    private String invokeWebService(String firma, ArrayOfString sql) {
        ServicioWebTB service = new serviciowebtb.ServicioWebTB();
        ServicioWebTBSoap port = service.getServicioWebTBSoap();
        BindingProvider bindingProvider = (BindingProvider) port;
        String wsLocation = appConfig.getWebServiceLocation().toString();
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsLocation);
        String response = port.cargarSQL(firma, sql);
        return response;
    }
    
}
