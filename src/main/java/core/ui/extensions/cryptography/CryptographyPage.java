package core.ui.extensions.cryptography;

import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Random;

import static core.logic.CommandManager.exitPage;
import static core.logic.CommandManager.mainMenuRerun;
import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.ui.essential.configs.DisplayManager.scanner;
import static core.ui.essential.configs.TextConfigs.*;
import static java.lang.System.out;

public class CryptographyPage {

    public static void displayCryptographyPage() {
        marginBorder(1, 2);
        message("Cryptography:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, getSearchingLineAlignment(), false,
                    getColor(layoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "encryption", "/en" -> encryptionMenu();
                case "decryption", "/de" -> decryptionMenu();
                case "hashing", "/ha" -> hashSHA256();
                case "restart", "/rs" -> {
                    insertControlChars('n', 1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list", "/ls" -> displayListOfCommands();
                case "quit", "/q" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands() {
        insertControlChars('n', 1);
        message("·  Encryption [" + getColor(mainColor)
                + "/en" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Decryption [" + getColor(mainColor)
                + "/de" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Hashing [" + getColor(mainColor)
                + "/ha" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Restart [" + getColor(mainColor)
                + "/rs" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Clear terminal [" + getColor(mainColor)
                + "/cl" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  List [" + getColor(mainColor)
                + "/ls" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Quit [" + getColor(mainColor)
                + "/q" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static void encryptionMenu() {
        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Select encryption algorithm [" +
                getColor(mainColor) + "AES" + getColor(layoutColor) + "|" + getColor(mainColor) + "1 " + getColor(layoutColor) +
                getColor(218) + "RSA" + getColor(layoutColor) + "|" + getColor(218) + "2 " + getColor(layoutColor) +
                getColor(206) + "Chacha20" + getColor(layoutColor) + "|" + getColor(206) + "3 " + getColor(layoutColor) +
                getColor(204) + "Blowfish" + getColor(layoutColor) + "|" + getColor(204) + "4" + getColor(layoutColor) + "]: ");

        String algorithm = scanner.nextLine().toLowerCase();
        insertControlChars('n', 1);

        switch (algorithm) {
            case "aes", "1" -> encryptAES();
            case "rsa", "2" -> encryptRSA();
            case "chacha20", "3" -> encryptChaCha20();
            case "blowfish", "4" -> encryptBlowfish();
            default -> out.print("");
        }
    }

    private static void decryptionMenu() {
        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Select decryption algorithm [" +
                getColor(mainColor) + "AES" + getColor(layoutColor) + "|" + getColor(mainColor) + "1 " + getColor(layoutColor) +
                getColor(218) + "RSA" + getColor(layoutColor) + "|" + getColor(218) + "2 " + getColor(layoutColor) +
                getColor(206) + "Chacha20" + getColor(layoutColor) + "|" + getColor(206) + "3 " + getColor(layoutColor) +
                getColor(204) + "Blowfish" + getColor(layoutColor) + "|" + getColor(204) + "4" + getColor(layoutColor) + "]: ");

        String algorithm = scanner.nextLine().toLowerCase();
        insertControlChars('n', 1);

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
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter plain text to encrypt: ");
            String plainText = scanner.nextLine();

            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

            String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            insertControlChars('n', 1);
            message("Encrypted Text [ECB]: "
                    + getColor(mainColor) + encryptedText, layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            message("Key [Base64 encoded]: " + getColor(mainColor)
                    + base64Key, layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

        } catch (Exception e) {
            message("Error encrypting text: " + e.getMessage(), layoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::println);
        }
    }

    //decrypt aes
    private static void decryptAES() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Key [Base64 encoded]: ");
            String base64Key = scanner.nextLine();

            byte[] decodedKey = Base64.getDecoder().decode(base64Key);
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            insertControlChars('n', 1);
            message("Decrypted Text: " + getColor(mainColor) + decryptedText, layoutColor, getDefaultTextAlignment(), 0, out::println);
        } catch (Exception e) {
            message("Error decrypting text: " + e.getMessage(), layoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }

    // encrypt rsa
    private static void encryptRSA() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter plain text to encrypt: ");
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

            message("Encrypted Text: " + getColor(218) + encryptedText, layoutColor, getDefaultTextAlignment(), 0, out::println);
            message("Private Key [Base64]: " + getColor(218) + base64PrivateKey, layoutColor, getDefaultTextAlignment(), 0, out::println);
        } catch (Exception e) {
            message("Error encrypting text: " + e.getMessage(), layoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }

    //decrypt rsa
    private static void decryptRSA() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter RSA private key [Base64]: ");
            String base64PrivateKey = scanner.nextLine();

            byte[] decodedKey = Base64.getDecoder().decode(base64PrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            insertControlChars('n', 1);
            message("Decrypted Text: " + getColor(218) + decryptedText, layoutColor, getDefaultTextAlignment(), 0, out::println);
        } catch (Exception e) {
            message("Error decrypting RSA: " + e.getMessage(), layoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }

    // encrypt ChaCha20
    private static void encryptChaCha20() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter plain text to encrypt: ");
            String plainText = scanner.nextLine();
            insertControlChars('n', 1);

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

            message("Encrypted Text [ChaCha20]: " + getColor(206) + encryptedText, layoutColor, getDefaultTextAlignment(), 0, out::print);
            message("Key [Base64]: " + getColor(206) + base64Key, layoutColor, getDefaultTextAlignment(), 0, out::print);
            message("Nonce [Base64]: " + getColor(206) + base64Nonce, layoutColor, getDefaultTextAlignment(), 0, out::println);
        } catch (Exception e) {
            message("Error encrypting text: " + e.getMessage(), layoutColor, getDefaultTextAlignment(), 0, out::println);
        }
    }

    // decrypt ChaCha20
    private static void decryptChaCha20() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Key [Base64] encoded: ");
            String base64Key = scanner.nextLine();

            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Nonce [Base64] encoded: ");
            String base64Nonce = scanner.nextLine();

            byte[] key = Base64.getDecoder().decode(base64Key);
            byte[] nonce = Base64.getDecoder().decode(base64Nonce);

            Cipher cipher = Cipher.getInstance("ChaCha20");
            SecretKeySpec secretKey = new SecretKeySpec(key, "ChaCha20");
            ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, 1);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            insertControlChars('n', 1);
            message("Decrypted Text: " + getColor(206) + decryptedText, layoutColor,
                    getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (Exception e) {
            message("Error decrypting text: " + e.getMessage(), layoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::println);
        }
    }

    // encrypt Blowfish
    private static void encryptBlowfish() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter plain text to encrypt: ");
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

            insertControlChars('n', 1);
            message("Encrypted Text [Blowfish]: "
                    + getColor(204) + encryptedText, layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

            message("Key [Base64]: " + getColor(204) + base64Key, layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (Exception e) {
            message("Error encrypting text: " + e.getMessage(), layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }

    // decrypt Blowfish
    private static void decryptBlowfish() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Key [Base64] encoded: ");
            String base64Key = scanner.nextLine();

            byte[] key = Base64.getDecoder().decode(base64Key);

            Cipher cipher = Cipher.getInstance("Blowfish");
            SecretKeySpec secretKey = new SecretKeySpec(key, "Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            insertControlChars('n', 1);
            message("Decrypted Text: " + getColor(204) + decryptedText, layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (Exception e) {
            message("Error decrypting text: " + e.getMessage(), layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }

    private static void hashSHA256() {
        try {
            insertControlChars('n', 1);
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter value to hash: ");
            String inputText = scanner.nextLine();

            if(inputText.isEmpty()) {
                message("The value should not to be empty.", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                return;
            }

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(inputText.getBytes(StandardCharsets.UTF_8));

            message("Hash [" + getColor(mainColor) + "SHA256" + getColor(layoutColor) + "] output: "
                    + getColor(mainColor) + bytesToHex(hash), layoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::println);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing input.", e);
        }
    }

    private static @NotNull String bytesToHex(byte @NotNull [] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}