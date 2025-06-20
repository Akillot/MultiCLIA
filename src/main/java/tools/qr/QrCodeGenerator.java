package tools.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.nio.file.Paths;

import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.TextConfigs.insertControlChars;
import static core.ui.configs.TextConfigs.message;
import static java.lang.System.out;

public class QrCodeGenerator {

    public static void generateQR(String data, String filePath, int width, int height) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
            MatrixToImageWriter.writeToPath(matrix, "PNG", Paths.get(filePath));

            insertControlChars('n', 1);
            message("QR code saved in: " + filePath,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

        } catch (WriterException | IOException e) {
            message("Error " + getColor(getLayoutColor()) + "generating QR code: " + e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);
        }
    }

    public static void generateAsciiQr(String data, int size) throws Exception {
        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, size, size);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) out.print(matrix.get(x, y) ? getColor(getLayoutColor()) + "██" : "  ");
            insertControlChars('n', 1);
        }
    }
}
