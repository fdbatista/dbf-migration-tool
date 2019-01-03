package classes.security;

import java.io.Serializable;
import java.util.ArrayList;

public class EncryptionKey implements Serializable {
    
    private String id, value;
    private ArrayList<String> signatures;

    public EncryptionKey(String arrIndex, String arrValue) {
        this.id = arrIndex;
        this.value = arrValue;
        this.signatures = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<String> getSignatures() {
        return signatures;
    }

    public void setSignatures(ArrayList<String> signatures) {
        this.signatures = signatures;
    }
    
}
