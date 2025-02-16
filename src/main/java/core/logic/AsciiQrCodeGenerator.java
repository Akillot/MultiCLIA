package core.logic;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.nio.file.Paths;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.insertControlChars;
import static core.configs.TextConfigs.message;
import static java.lang.System.out;

public class AsciiQrCodeGenerator {

    private static void generateQR(String data, String filePath) throws Exception {
        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
        MatrixToImageWriter.writeToPath(matrix, "PNG", Paths.get(filePath));
        insertControlChars('n',1);
        message("QR code saved in: " + filePath, sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }
}