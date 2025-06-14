package tools.cryptography;

import core.Page;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

import static core.CommandManager.*;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.*;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;

public class CryptographyPage extends Page {

    private static final int easyComplexityColor = 85;
    private static final int mediumComplexityColor = 214;
    private static final int strongComplexityColor = 177;
    private static final int extraComplexityColor = 201;

    private static final String CHAR_POOL_EASY = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_POOL_MEDIUM = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String CHAR_POOL_STRONG = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";
    private static final String CHAR_POOL_EXTRA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789<>.,/|\\?!+-*&^%$#@!~'}{)(";

    private static int passwordLength;


    private String[][] commands = {
            {"Encryption", "en"},
            {"Decryption", "de"},
            {"Hashing", "ha"},
            {"Generate password", "genpass"},
            {"Clear", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    private static final String[] CRYPTO_ASCII_LOGO = {
            "╔═══════════════════════════════════════════════════════╗",
            "║                                                       ║",
            "║   ██████╗██████╗ ██╗   ██╗██████╗ ████████╗ ██████╗   ║",
            "║  ██╔════╝██╔══██╗╚██╗ ██╔╝██╔══██╗╚══██╔══╝██╔═══██╗  ║",
            "║  ██║     ██████╔╝ ╚████╔╝ ██████╔╝   ██║   ██║   ██║  ║",
            "║  ██║     ██╔══██╗  ╚██╔╝  ██╔═══╝    ██║   ██║   ██║  ║",
            "║  ╚██████╗██║  ██║   ██║   ██║        ██║   ╚██████╔╝  ║",
            "║   ╚═════╝╚═╝  ╚═╝   ╚═╝   ╚═╝        ╚═╝    ╚═════╝   ║",
            "║                                                       ║",
            "╚═══════════════════════════════════════════════════════╝"
    };

    public void displayMenu() {
        marginBorder(1, 2);
        clearTerminal();
        displayLogo(getDefaultTextAlignment(),CRYPTO_ASCII_LOGO);
        insertControlChars('n',1);

        displayListOfCommands(commands);

        while (true) {
            slowMotionText(0,
                    getSearchingLineAlignment(),
                    false,
                    getColor(getLayoutColor()) + getSearchingArrow(),
                    "");

            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "encryption", "en" -> encryptionMenu();
                case "decryption", "de" -> decryptionMenu();
                case "hashing", "ha" -> hashSHA256();
                case "generate password", "genpass" -> generatePassword();
                case "clear", "cl" -> clearTerminal();
                case "help", "h" -> displayListOfCommands(commands);
                case "quit", "q", "exit", "e" -> {
                    exitPageFormatting();
                    clearAndRestartApp();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    @Override
    protected void displayListOfCommands(String[][] commands) {
        super.displayListOfCommands(commands);
    }

    private static void encryptionMenu() {
        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Select encryption algorithm [" +
                getColor(getMainColor()) + "AES" + getColor(getLayoutColor()) + "|" + getColor(getMainColor()) + "1 " + getColor(getLayoutColor()) +
                getColor(218) + "RSA" + getColor(getLayoutColor()) + "|" + getColor(218) + "2 " + getColor(getLayoutColor()) +
                getColor(206) + "Chacha20" + getColor(getLayoutColor()) + "|" + getColor(206) + "3 " + getColor(getLayoutColor()) +
                getColor(204) + "Blowfish" + getColor(getLayoutColor()) + "|" + getColor(204) + "4" + getColor(getLayoutColor()) + "]: ");

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
        out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Select decryption algorithm [" +
                getColor(getMainColor()) + "AES" + getColor(getLayoutColor()) + "|" + getColor(getMainColor()) + "1 " + getColor(getLayoutColor()) +
                getColor(218) + "RSA" + getColor(getLayoutColor()) + "|" + getColor(218) + "2 " + getColor(getLayoutColor()) +
                getColor(206) + "Chacha20" + getColor(getLayoutColor()) + "|" + getColor(206) + "3 " + getColor(getLayoutColor()) +
                getColor(204) + "Blowfish" + getColor(getLayoutColor()) + "|" + getColor(204) + "4" + getColor(getLayoutColor()) + "]: ");

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

    // Encrypt aes
    private static void encryptAES() {
        try {
            out.print(alignment(getDefaultTextAlignment())
                    + getColor(getLayoutColor()) + "Enter plain text to encrypt: ");
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
                    + getColor(getMainColor()) + encryptedText,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            message("Key [Base64 encoded]: " + getColor(getMainColor())
                    + base64Key,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error" + getColor(getLayoutColor()) +" encrypting text: " +  e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);
        }
    }

    // Decrypt aes
    private static void decryptAES() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Key [Base64 encoded]: ");
            String base64Key = scanner.nextLine();

            byte[] decodedKey = Base64.getDecoder().decode(base64Key);
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            insertControlChars('n', 1);
            message("Decrypted Text: " + getColor(getMainColor()) + decryptedText,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);

        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error" + getColor(getLayoutColor()) +" decrypting text: " +  e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);
        }
    }

    // Encrypt rsa
    private static void encryptRSA() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter plain text to encrypt: ");
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

            message("Encrypted Text: " + getColor(218) + encryptedText,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);

            message("Private Key [Base64]: " + getColor(218) + base64PrivateKey,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);

        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error" + getColor(getLayoutColor()) +" encrypting text: " +  e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);
        }
    }

    // Decrypt rsa
    private static void decryptRSA() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter RSA private key [Base64]: ");
            String base64PrivateKey = scanner.nextLine();

            byte[] decodedKey = Base64.getDecoder().decode(base64PrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            insertControlChars('n', 1);
            message("Decrypted Text: " + getColor(218) + decryptedText,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);

        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error" + getColor(getLayoutColor()) +" decrypting text: " +  e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);
        }
    }

    // Encrypt ChaCha20
    private static void encryptChaCha20() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter plain text to encrypt: ");
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

            message("Encrypted Text [ChaCha20]: " + getColor(206) + encryptedText,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::print);

            message("Key [Base64]: " + getColor(206) + base64Key,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::print);

            message("Nonce [Base64]: " + getColor(206) + base64Nonce,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);

        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error" + getColor(getLayoutColor()) +" encrypting text: " +  e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);
        }
    }

    // Decrypt ChaCha20
    private static void decryptChaCha20() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Key [Base64] encoded: ");
            String base64Key = scanner.nextLine();

            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Nonce [Base64] encoded: ");
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
            message("Decrypted Text: " + getColor(206) + decryptedText,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error" + getColor(getLayoutColor()) +" decrypting text: " +  e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);
        }
    }

    // Encrypt Blowfish
    private static void encryptBlowfish() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter plain text to encrypt: ");
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
                    + getColor(204) + encryptedText, getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            message("Key [Base64]: " + getColor(204) + base64Key,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error" + getColor(getLayoutColor()) +" encrypting text: " +  e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);
        }
    }

    // Decrypt Blowfish
    private static void decryptBlowfish() {
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter encrypted text to decrypt: ");
            String encryptedText = scanner.nextLine();

            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Key [Base64] encoded: ");
            String base64Key = scanner.nextLine();

            byte[] key = Base64.getDecoder().decode(base64Key);

            Cipher cipher = Cipher.getInstance("Blowfish");
            SecretKeySpec secretKey = new SecretKeySpec(key, "Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);

            insertControlChars('n', 1);
            message("Decrypted Text: " + getColor(204) + decryptedText,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error" + getColor(getLayoutColor()) +" decrypting text: " +  e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    0,
                    out::println);
        }
    }

    // Hashing
    private static void hashSHA256() {
        try {
            insertControlChars('n', 1);
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter value to hash: ");
            String inputText = scanner.nextLine();

            if(inputText.isEmpty()) {
                message("The value should not to be empty.",
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::println);
                return;
            }

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(inputText.getBytes(StandardCharsets.UTF_8));

            message("Hash [" + getColor(getMainColor())
                            + "SHA256" + getColor(getLayoutColor()) + "] output: "
                            + getColor(getMainColor()) + bytesToHex(hash),
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);
        } catch (NoSuchAlgorithmException e) {
            insertControlChars('n', 1);
            throw new RuntimeException(getColor(getRejectionColor()) + "Error" +
                    getColor(getLayoutColor()) + " hashing input.", e);
        }
    }

    private static @NotNull String bytesToHex(byte @NotNull [] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    private static void generatePassword(){
        insertControlChars('n', 1);
        message("Password generator:",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);
        try {
            out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter length of password [1-80]: ");
            passwordLength = scanner.nextInt();
            if (passwordLength <= 0 || passwordLength > 80) {
                message("Invalid password length. Please enter a number between 1 and 80.",
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::println);
                return;
            }
        } catch (Exception e) {
            message("Invalid input" + getColor(getLayoutColor()) + ". Please enter a number.",
                   getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);
            scanner.nextLine();
        }

        scanner.nextLine();

        insertControlChars('n', 1);
        out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Password complexity ["
                + getColor(easyComplexityColor) + "light" + getColor(getLayoutColor()) + "|"
                + getColor(easyComplexityColor) + "1" + getColor(getLayoutColor()) + ", "
                + getColor(mediumComplexityColor) + "medium" + getColor(getLayoutColor()) + "|"
                + getColor(mediumComplexityColor) + "2" + getColor(getLayoutColor()) + ", "
                + getColor(strongComplexityColor) + "strong" + getColor(getLayoutColor()) + "|"
                + getColor(strongComplexityColor) + "3" + getColor(getLayoutColor()) + ", "
                + getColor(extraComplexityColor) + "extra" + getColor(getLayoutColor()) + "|"
                + getColor(extraComplexityColor) + "4" + getColor(getLayoutColor()) + "]: ");

        String passwordComplexity = scanner.nextLine().toLowerCase();
        String generatedPassword = createPassword(passwordComplexity);

        if (generatedPassword != null) {
            insertControlChars('n', 1);
            message("Generated Password: " + generatedPassword,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

            displayConfirmation("Enter", "y", "+",
                    "to open and", "n", "-", "to skip",
                    getAcceptanceColor(),
                    getRejectionColor(),
                    getLayoutColor(),
                    getDefaultTextAlignment());

            choice("Copy to clipboard",
                    copyToClipboard(generatedPassword),
                    getLayoutColor(),
                    getLayoutColor(),
                    getRejectionColor());

            marginBorder(2, 1);
        } else {
            insertControlChars('n', 1);
            message("Invalid complexity option" + getColor(getLayoutColor()) + ". Please try again.",
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);
        }
    }

    // Managing password difficulty
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

    // Generating password from the exact char pool
    private static @NotNull String generatePasswordFromPool(@NotNull String charPool) {
        StringBuilder passwordBuilder = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(charPool.length());
            passwordBuilder.append(charPool.charAt(index));
        }

        return passwordBuilder.toString();
    }
}