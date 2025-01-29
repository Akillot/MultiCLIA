package core.pages;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Random;

import static core.configs.AppearanceConfigs.*;
import static core.logic.CommandManager.exitPage;
import static core.configs.TextConfigs.*;
import static core.logic.CommandManager.mainMenuRerun;
import static core.ui.DisplayManager.*;
import static java.lang.System.out;

public class CryptographyPage {

    public static void displayEncryptionPage() {
        marginBorder(1, 2);
        message("Cryptography:", sysLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, searchingLineAlignment, false,
                    getColor(sysLayoutColor) + "> ", "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "encryption", "/en" -> encryptionMenu();
                case "decryption", "/de" -> decryptionMenu();
                case "rerun", "/rr" -> {
                    modifyMessage('n',1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list of commands", "/lc" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands() {
        modifyMessage('n',1);
        message("·  Encryption [" + getColor(sysMainColor)
                + "/en" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Decryption [" + getColor(sysMainColor)
                + "/de" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  List Of Commands [" + getColor(sysMainColor)
                + "/lc" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Exit [" + getColor(sysMainColor)
                + "/e" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::println);
    }

    private static void encryptionMenu(){
        modifyMessage('n', 1);
        out.print(alignment(58) + getColor(sysLayoutColor) + "Select encryption algorithm [" +
                getColor(sysMainColor) +"AES" + getColor(sysLayoutColor) + "|" + getColor(sysMainColor) + "1 " + getColor(sysLayoutColor) +
                getColor(218) + "RSA" + getColor(sysLayoutColor) + "|" + getColor(218) + "2 " + getColor(sysLayoutColor) +
                getColor(206) + "Chacha20" + getColor(sysLayoutColor) + "|" + getColor(206) + "3 " + getColor(sysLayoutColor) +
                getColor(204) + "Blowfish" + getColor(sysLayoutColor) + "|" + getColor(204) + "4" + getColor(sysLayoutColor) + "]: ");

        String algorithm = scanner.nextLine().toLowerCase();
        modifyMessage('n', 1);

        switch (algorithm) {
            case "aes", "1" -> encryptAES();
            case "rsa", "2" -> encryptRSA();
            case "chacha20", "3" -> encryptChaCha20();
            case "blowfish", "4" -> encryptBlowfish();
            default -> out.print("");
        }
    }

    private static void decryptionMenu() {
        modifyMessage('n', 1);
        out.print(alignment(58) + getColor(sysLayoutColor) + "Select decryption algorithm [" +
                getColor(sysMainColor) +"AES" + getColor(sysLayoutColor) + "|" + getColor(sysMainColor) + "1 " + getColor(sysLayoutColor) +
                getColor(218) + "RSA" + getColor(sysLayoutColor) + "|" + getColor(218) + "2 " + getColor(sysLayoutColor) +
                getColor(206) + "Chacha20" + getColor(sysLayoutColor) + "|" + getColor(206) + "3 " + getColor(sysLayoutColor) +
                getColor(204) + "Blowfish" + getColor(sysLayoutColor) + "|" + getColor(204) + "4" + getColor(sysLayoutColor) + "]: ");

        String algorithm = scanner.nextLine().toLowerCase();
        modifyMessage('n', 1);

        switch (algorithm) {
            case "aes", "1" -> decryptAES();
            case "rsa", "2" -> decryptRSA();
            case "chacha20", "3" -> decryptChaCha20();
            case "blowfish", "4" -> decryptBlowfish();
            default -> out.print("");
        }
    }

    //encrypt aes
    private static void encryptAES() {
        try {
            out.print(alignment(58) + getColor(sysLayoutColor) + "Enter plain text to encrypt: ");
            String plainText = scanner.nextLine();

            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

            String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            modifyMessage('n',1);
            message("Encrypted Text [ECB]: "
                    + getColor(sysMainColor) + encryptedText, sysLayoutColor, 58, 0, out::print);
            message("Key [Base64 encoded]: " + getColor(sysMainColor)
                    + base64Key, sysLayoutColor, 58, 0, out::println);

        } catch (Exception e) {
            message("Error encrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    //decrypt aes
    private static void decryptAES() {
        try {
            out.print(alignment(58) + getColor(sysLayoutColor) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(58) + getColor(sysLayoutColor) + "Key [Base64 encoded]: ");
            String base64Key = scanner.nextLine();

            byte[] decodedKey = Base64.getDecoder().decode(base64Key);
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            modifyMessage('n',1);
            message("Decrypted Text: " + getColor(sysMainColor) + decryptedText, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error decrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    // encrypt rsa
    private static void encryptRSA() {
        try {
            out.print(alignment(58) + getColor(sysLayoutColor) + "Enter plain text to encrypt: ");
            String plainText = scanner.nextLine();

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair keyPair = keyGen.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            String base64PrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            message("Encrypted Text: " + getColor(218) + encryptedText, sysLayoutColor, 58, 0, out::println);
            message("Private Key [Base64]: " + getColor(218) + base64PrivateKey, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error encrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    //decrypt rsa
    private static void decryptRSA() {
        try {
            out.print(alignment(58) + getColor(sysLayoutColor) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(58) + getColor(sysLayoutColor) + "Enter RSA private key [Base64]: ");
            String base64PrivateKey = scanner.nextLine();

            byte[] decodedKey = Base64.getDecoder().decode(base64PrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            modifyMessage('n',1);
            message("Decrypted Text: " + getColor(218) + decryptedText, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error decrypting RSA: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    // encrypt ChaCha20
    private static void encryptChaCha20() {
        try {
            out.print(alignment(58) + getColor(sysLayoutColor) + "Enter plain text to encrypt: ");
            String plainText = scanner.nextLine();
            modifyMessage('n',1);

            byte[] key = new byte[32];
            byte[] nonce = new byte[12];

            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(key);
            secureRandom.nextBytes(nonce);

            Cipher cipher = Cipher.getInstance("ChaCha20");
            SecretKeySpec secretKey = new SecretKeySpec(key, "ChaCha20");
            ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, 1);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            String base64Key = Base64.getEncoder().encodeToString(key);
            String base64Nonce = Base64.getEncoder().encodeToString(nonce);

            message("Encrypted Text [ChaCha20]: " + getColor(206) + encryptedText, sysLayoutColor, 58, 0, out::print);
            message("Key [Base64]: " + getColor(206) + base64Key, sysLayoutColor, 58, 0, out::print);
            message("Nonce [Base64]: " + getColor(206) + base64Nonce, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error encrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    // decrypt ChaCha20
    private static void decryptChaCha20() {
        try {
            out.print(alignment(58) + getColor(sysLayoutColor) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(58) + getColor(sysLayoutColor) + "Key [Base64] encoded: ");
            String base64Key = scanner.nextLine();

            out.print(alignment(58) + getColor(sysLayoutColor) + "Nonce [Base64] encoded: ");
            String base64Nonce = scanner.nextLine();

            byte[] key = Base64.getDecoder().decode(base64Key);
            byte[] nonce = Base64.getDecoder().decode(base64Nonce);

            Cipher cipher = Cipher.getInstance("ChaCha20");
            SecretKeySpec secretKey = new SecretKeySpec(key, "ChaCha20");
            ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, 1);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            modifyMessage('n',1);
            message("Decrypted Text: " + getColor(206) + decryptedText, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error decrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    // encrypt Blowfish
    private static void encryptBlowfish() {
        try {
            out.print(alignment(58) + getColor(sysLayoutColor) + "Enter plain text to encrypt: ");
            String plainText = scanner.nextLine();

            byte[] key = new byte[16];
            Random random = new Random();
            random.nextBytes(key);

            Cipher cipher = Cipher.getInstance("Blowfish");
            SecretKeySpec secretKey = new SecretKeySpec(key, "Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            String base64Key = Base64.getEncoder().encodeToString(key);

            modifyMessage('n',1);
            message("Encrypted Text [Blowfish]: "
                    + getColor(204) + encryptedText, sysLayoutColor, 58, 0, out::print);

            message("Key [Base64]: " + getColor(204) + base64Key, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error encrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    // decrypt Blowfish
    private static void decryptBlowfish() {
        try {
            out.print(alignment(58) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(58) + getColor(sysLayoutColor) + "Key [Base64] encoded: ");
            String base64Key = scanner.nextLine();

            byte[] key = Base64.getDecoder().decode(base64Key);

            Cipher cipher = Cipher.getInstance("Blowfish");
            SecretKeySpec secretKey = new SecretKeySpec(key, "Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            modifyMessage('n',1);
            message("Decrypted Text: " + getColor(204) + decryptedText, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error decrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }
}