package core.pages;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Random;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;
import static core.logic.CommandManager.*;
import static core.ui.DisplayManager.*;
import static java.lang.System.out;

public class SecurityPage {

    private static final int easyComplexityColor = 85;

    private static final int mediumComplexityColor = 214;

    private static final int strongComplexityColor = 177;

    private static final int extraComplexityColor = 201;

    private static final String CHAR_POOL_EASY = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_POOL_MEDIUM = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String CHAR_POOL_STRONG = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";
    private static final String CHAR_POOL_EXTRA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789<>.,/|\\?!+-*&^%$#@!~'}{)(";

    private static int passwordLength;

    public static void displaySecurityPage() {
        Security.addProvider(new BouncyCastleProvider());
        marginBorder(1, 2);
        message("Security:", sysLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, searchingLineAlignment, false, getColor(sysLayoutColor) + "> ", "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "generate password", "/gp" -> passwordCreatorMenu();
                case "encryption", "/en" -> encryptionMenu();
                case "decryption", "/de" -> decryptionMenu();
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
        modifyMessage('n', 1);
        message("·  Generate password [" + getColor(sysMainColor) + "/gp"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Encryption [" + getColor(sysMainColor) + "/en"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Decryption [" + getColor(sysMainColor) + "/de"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  List Of Commands [" + getColor(sysMainColor) + "/lc"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Exit [" + getColor(sysMainColor) + "/e"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::println);
    }

    private static void passwordCreatorMenu() {
        modifyMessage('n', 1);
        out.print(alignment(58) + getColor(sysLayoutColor) + "Enter length of password [1-80]: ");
        try {
            passwordLength = scanner.nextInt();
            if (passwordLength <= 0 || passwordLength > 80) {
                message("Invalid password length. Please enter a number between 1 and 80.",
                        sysLayoutColor, 58, 0, out::println);
                return;
            }
        } catch (Exception e) {
            message("Invalid input. Please enter a number.", sysLayoutColor, 58, 0, out::print);
            scanner.nextLine();
            return;
        }

        scanner.nextLine();

        modifyMessage('n', 1);
        out.print(alignment(58) + getColor(sysLayoutColor) + "Password complexity ["
                + getColor(easyComplexityColor) + "light" + getColor(sysLayoutColor) + "|"
                + getColor(easyComplexityColor) + "1" + getColor(sysLayoutColor) + ", "
                + getColor(mediumComplexityColor) + "medium" + getColor(sysLayoutColor) + "|"
                + getColor(mediumComplexityColor) + "2" + getColor(sysLayoutColor) + ", "
                + getColor(strongComplexityColor) + "strong" + getColor(sysLayoutColor) + "|"
                + getColor(strongComplexityColor) + "3" + getColor(sysLayoutColor) + ", "
                + getColor(extraComplexityColor) + "extra" + getColor(sysLayoutColor) + "|"
                + getColor(extraComplexityColor) + "4" + getColor(sysLayoutColor) + "]: ");

        String passwordComplexity = scanner.nextLine().toLowerCase();
        String generatedPassword = createPassword(passwordComplexity);
        if (generatedPassword != null) {
            modifyMessage('n', 1);
            message("Generated Password: " + generatedPassword,
                    sysLayoutColor, 58, 0, out::println);
        } else {
            message("Invalid complexity option. Please try again.", sysLayoutColor, 58, 0, out::println);
        }
    }

    private static @Nullable String createPassword(@NotNull String passwordComplexity) {
        String charPool;
        int color;

        switch (passwordComplexity) {
            case "light", "1" -> {
                charPool = CHAR_POOL_EASY;
                color = easyComplexityColor;
            }
            case "medium", "2" -> {
                charPool = CHAR_POOL_MEDIUM;
                color = mediumComplexityColor;
            }
            case "strong", "3" -> {
                charPool = CHAR_POOL_STRONG;
                color = strongComplexityColor;
            }
            case "extra", "4" -> {
                charPool = CHAR_POOL_EXTRA;
                color = extraComplexityColor;
            }
            default -> {
                return null;
            }
        }

        return getColor(color) + generatePasswordFromPool(charPool);
    }

    private static @NotNull String generatePasswordFromPool(@NotNull String charPool) {
        StringBuilder passwordBuilder = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(charPool.length());
            passwordBuilder.append(charPool.charAt(index));
        }

        return passwordBuilder.toString();
    }

    private static void encryptionMenu(){
        modifyMessage('n', 1);

        out.print(alignment(58) + getColor(sysLayoutColor) + "Select encryption algorithm [" +
                getColor(sysMainColor) +"AES" + getColor(sysLayoutColor) + "/" +
                getColor(sysMainColor) + "RSA" + getColor(sysLayoutColor) + "/" +
                getColor(sysMainColor) + "Chacha20" + getColor(sysLayoutColor) + "/" +
                getColor(sysMainColor) + "Blowfish" + getColor(sysLayoutColor) + "]: ");
        String algorithm = scanner.nextLine().toLowerCase();

        switch (algorithm) {
            case "aes" -> encryptAES();
            case "rsa" -> encryptRSA();
            case "chacha20" -> encryptChaCha20();
            case "blowfish" -> encryptBlowfish();
            default -> out.print("");
        }
    }

    private static void decryptionMenu() {
        modifyMessage('n', 1);

        out.print(alignment(58) + getColor(sysLayoutColor) + "Select decryption algorithm [" +
                getColor(sysMainColor) +"AES" + getColor(sysLayoutColor) + "/" +
                getColor(sysMainColor) + "RSA" + getColor(sysLayoutColor) + "/" +
                getColor(sysMainColor) + "Chacha20" + getColor(sysLayoutColor) + "/" +
                getColor(sysMainColor) + "Blowfish" + getColor(sysLayoutColor) + "]: ");
        String algorithm = scanner.nextLine().toLowerCase();

        switch (algorithm) {
            case "aes" -> decryptAES();
            case "rsa" -> decryptRSA();
            case "chacha20" -> decryptChaCha20();
            case "blowfish" -> decryptBlowfish();
            default -> out.print("");
        }
    }

    //encrypt aes
    private static void encryptAES() {
        try {
            modifyMessage('n', 1);
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
            message("Encrypted Text [ECB]: " + getColor(sysMainColor) + encryptedText, sysLayoutColor, 58, 0, out::println);
            message("Key [" + getColor(sysMainColor) + "Base64" + getColor(sysLayoutColor)
                    + " encoded]: " + getColor(sysMainColor) + base64Key, sysLayoutColor, 58, 0, out::println);

        } catch (Exception e) {
            message("Error encrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    //decrypt aes
    private static void decryptAES() {
        try {
            modifyMessage('n', 1);
            out.print(alignment(58) + getColor(sysLayoutColor) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(58) + getColor(sysLayoutColor) + "Key [" + getColor(sysMainColor) + "Base64" + getColor(sysLayoutColor)
                    + " encoded]: ");
            String base64Key = scanner.nextLine();

            byte[] decodedKey = Base64.getDecoder().decode(base64Key);
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            message("Decrypted Text: " + getColor(sysMainColor) + decryptedText, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error decrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    // encrypt rsa
    private static void encryptRSA() {
        try {
            modifyMessage('n', 1);
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

            message("Encrypted Text: " + getColor(sysMainColor) + encryptedText, sysLayoutColor, 58, 0, out::println);
            message("Private Key [Base64]: " + getColor(sysMainColor) + base64PrivateKey, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error encrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    //decrypt rsa
    private static void decryptRSA() {
        try {
            modifyMessage('n', 1);
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

            message("Decrypted Text: " + getColor(sysMainColor) + decryptedText, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error decrypting RSA: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    // encrypt ChaCha20
    private static void encryptChaCha20() {
        try {
            modifyMessage('n', 1);
            out.print(alignment(58) + getColor(sysLayoutColor) + "Enter plain text to encrypt: ");
            String plainText = scanner.nextLine();

            byte[] key = new byte[32];
            Random random = new Random();
            random.nextBytes(key);
            byte[] nonce = new byte[12];
            random.nextBytes(nonce);

            Cipher cipher = Cipher.getInstance("ChaCha20");
            SecretKeySpec secretKey = new SecretKeySpec(key, "ChaCha20");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(nonce));

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            String base64Key = Base64.getEncoder().encodeToString(key);
            String base64Nonce = Base64.getEncoder().encodeToString(nonce);

            message("Encrypted Text [ChaCha20]: " + getColor(sysMainColor) + encryptedText, sysLayoutColor, 58, 0, out::println);
            message("Key [Base64]: " + getColor(sysMainColor) + base64Key, sysLayoutColor, 58, 0, out::println);
            message("Nonce [Base64]: " + getColor(sysMainColor) + base64Nonce, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error encrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    // decrypt ChaCha20
    private static void decryptChaCha20() {
        try {
            modifyMessage('n', 1);
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
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(nonce));

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            message("Decrypted Text: " + getColor(sysMainColor) + decryptedText, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error decrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    // encrypt Blowfish
    private static void encryptBlowfish() {
        try {
            modifyMessage('n', 1);
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

            message("Encrypted Text [Blowfish]: " + getColor(sysMainColor) + encryptedText, sysLayoutColor, 58, 0, out::println);
            message("Key [Base64]: " + getColor(sysMainColor) + base64Key, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error encrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    // decrypt Blowfish
    private static void decryptBlowfish() {
        try {
            modifyMessage('n', 1);
            out.print(alignment(58) + getColor(sysLayoutColor) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(58) + getColor(sysLayoutColor) + "Key [Base64] encoded: ");
            String base64Key = scanner.nextLine();

            byte[] key = Base64.getDecoder().decode(base64Key);

            Cipher cipher = Cipher.getInstance("Blowfish");
            SecretKeySpec secretKey = new SecretKeySpec(key, "Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            message("Decrypted Text: " + getColor(sysMainColor) + decryptedText, sysLayoutColor, 58, 0, out::println);
        } catch (Exception e) {
            message("Error decrypting text: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }
}