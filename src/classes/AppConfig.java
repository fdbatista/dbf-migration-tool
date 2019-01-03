package classes;

import classes.security.EncryptionKey;
import classes.security.Generator;
import classes.security.Security;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppConfig implements Serializable {

    private final String appEncKey = ":K?-Vq#laEx4wL%9";
    private ArrayList<EncryptionKey> encryptionKeys;
    private final ArrayList<String> keyRules;
    private URL webServiceLocation;
    private final Generator generator;

    public AppConfig(boolean populateItems) throws Exception {
        encryptionKeys = new ArrayList<>();
        generator = new Generator();
        keyRules = generator.generateSignatureRules(appEncKey, 25, 15);
        if (populateItems) {
            encryptionKeys = generator.generateEncryptionKeys(100, appEncKey);
            FileManager.serializeObject(this);
        }
    }

    public void setWebServiceLocation(String webServiceURL) throws MalformedURLException {
        this.webServiceLocation = new URL(webServiceURL);
    }

    public URL getWebServiceLocation() {
        return webServiceLocation;
    }
    
    public String updateWebServiceLocation() {
        String res;
        try {
            setWebServiceLocation(FileManager.getWebServiceLocation());
            res = "";
        } catch (MalformedURLException ex) {
            res = "La dirección del servicio no tiene el formato correcto.\nLa aplicación no puede continuar.";
        } catch (IOException ex) {
            res = ex.getClass().toString() + ": El fichero de especificación del servicio no se encuentra o está dañado.\nLa aplicación no puede continuar.";
        }
        return res;
    }

    public ArrayList<EncryptionKey> getEncryptionKeys() {
        return encryptionKeys;
    }

    public void setEncryptionKeys(ArrayList<EncryptionKey> items) {
        this.encryptionKeys = items;
    }

    public void addItem(EncryptionKey item) {
        encryptionKeys.add(item);
    }

    public EncryptionKey getEncryptionKey(int index) {
        return encryptionKeys.get(index);
    }

    public EncryptionKey getRandomEncryptionKey(int index) {
        return getEncryptionKey(index);
    }

    public void revealItems() {
        try {
            Security.init();
            for (EncryptionKey item : encryptionKeys) {
                item.setId(Security.decrypt(item.getId(), appEncKey));
                item.setValue(Security.decrypt(item.getValue(), appEncKey));
                //System.out.println("\"" + item.getValue() + "\",");
            }
            int keyRulesSize = keyRules.size();
            //System.out.println("-------------------------------------");
            for (int i = 0; i < keyRulesSize; i++) {
                keyRules.set(i, Security.decrypt(keyRules.get(i), appEncKey));
                //System.out.println("[" + keyRules.get(i) + "],");
            }
        } catch (Exception ex) {
            Logger.getLogger(AppConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> encryptConversionResult(ArrayList<String> conversionResults) throws Exception {
        ArrayList<String> res = new ArrayList<>();
        int index = new SecureRandom().nextInt(encryptionKeys.size());
        EncryptionKey encKey = getRandomEncryptionKey(index);
        String keySignature = generateSignature(), keyString = encKey.getValue();
        res.add(keySignature);
        res.add(Security.encryptToHex(String.valueOf(index + 1), appEncKey));
        for (String conversionResult : conversionResults) {
            res.add(Security.encryptToHex(conversionResult, keyString));
        }
        return res;
    }

    public String generateSignature() throws Exception {
        /*
        String res = "";
        SecureRandom random = new SecureRandom();
        for (String keyRule : keyRules) {
            String[] arrRule = keyRule.split(",");
            char randomChar = generator.getCharAtIndex(new Integer(arrRule[random.nextInt(arrRule.length)]));
            res += String.valueOf(randomChar);
        }
        return Security.encryptToHex(res, appEncKey);
        */
        String res = "";
        SecureRandom random = new SecureRandom();
        res = keyRules.stream().map((keyRule) -> keyRule.split(",")).map((arrRule) -> generator.getCharAtIndex(new Integer(arrRule[random.nextInt(arrRule.length)]))).map((randomChar) -> String.valueOf(randomChar)).reduce(res, String::concat);
        return Security.encryptToHex(res, appEncKey);
    }
}
