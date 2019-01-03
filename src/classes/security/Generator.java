package classes.security;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class Generator implements Serializable {

    private final String baseCharacters;

    private final SecureRandom randomGenerator;

    public Generator() {
        baseCharacters = "y|pM$2m7;EeWA3NkGZH`Tgi^h}X:Uf.lL8otBRQJzKa!w#rdO(<6>DIS{0xu~+[]b,-Y?*59q=c_nvP1j%V@sCF)4";
        //baseCharacters = "ypM2m7EeWA3NkGZHTgihXUflL8otBRQJzKawrdO6DIS0xubY59qcnvP1jVsCF4";
        randomGenerator = new SecureRandom();
    }

    private ArrayList<Character> stringToCharacterList(String string, boolean shuffle) {
        ArrayList<Character> res = new ArrayList<>();
        int strLength = string.length();
        for (int i = 0; i < strLength; i++) {
            res.add(string.charAt(i));
        }
        if (shuffle) {
            Collections.shuffle(res);
        }
        return res;
    }

    public String generateRandomSimpleString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(baseCharacters.charAt(randomGenerator.nextInt(baseCharacters.length())));
        }
        return sb.toString();
    }

    public String generateRandomSecureString(int length) {
        StringBuilder sb = new StringBuilder(length);
        ArrayList<Character> charsList = stringToCharacterList(baseCharacters, true);
        int randomIndex, charsListSize = charsList.size();
        for (int i = 0; i < length; i++) {
            randomIndex = randomGenerator.nextInt(charsListSize);
            sb.append(charsList.get(randomIndex));
            charsList.remove(randomIndex);
            if (charsListSize - 1 == 1) {
                String pp = "2";
            }
            if (--charsListSize == 0) {
                charsList = stringToCharacterList(baseCharacters, true);
                charsListSize = charsList.size();
            }
        }
        return sb.toString();
    }

    public ArrayList<EncryptionKey> generateEncryptionKeys(int keysCount, String key) {
        ArrayList<EncryptionKey> encryptionKeys = new ArrayList<>();
        try {
            Security.init();
            for (int k = 0; k < 100; k++) {
                EncryptionKey newEncKey = new EncryptionKey(Security.encrypt(generateRandomSecureString(16), key), Security.encrypt(generateRandomSecureString(16), key));
                newEncKey.setSignatures(generateSignatureRules(key, 25, 15));
                encryptionKeys.add(newEncKey);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return encryptionKeys;
    }

    public ArrayList<String> generateSignatureRules(String encryptionKey, int keyLength, int ruleLength) throws Exception {
        char[] chars = baseCharacters.toCharArray();
        int charsCount = chars.length, newIndex = randomGenerator.nextInt(charsCount);

        ArrayList<String> keyRules = new ArrayList<>();
        for (int j = 0; j < keyLength; j++) {
            ArrayList<Integer> indexesUsed = new ArrayList<>();
            String possibleValues = "";
            for (int i = 0; i < ruleLength; i++) {
                while (indexesUsed.contains(newIndex)) {
                    newIndex = randomGenerator.nextInt(charsCount);
                }
                indexesUsed.add(newIndex);
                possibleValues += String.valueOf(newIndex) + ((i == ruleLength - 1) ? "" : ",");
            }
            keyRules.add(Security.encrypt(possibleValues, encryptionKey));
        }
        return keyRules;
    }

    public String randomizeString(String string) {
        ArrayList<Character> chars = new ArrayList<>();
        int stringLength = string.length();
        for (int i = 0; i < stringLength; i++) {
            chars.add(string.charAt(i));
        }
        Collections.shuffle(chars);
        StringBuilder sb = new StringBuilder();
        chars.forEach((aChar) -> {
            sb.append(aChar);
        });
        return sb.toString();
    }

    public String charListToString(ArrayList<Character> charsList) {
        int size = charsList.size();
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append(charsList.get(i));
        }
        return sb.toString();
    }

    public char getCharAtIndex(int index) {
        return baseCharacters.charAt(index);
    }

}
