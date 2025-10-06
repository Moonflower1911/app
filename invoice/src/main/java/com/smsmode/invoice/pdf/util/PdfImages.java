package com.smsmode.invoice.pdf.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public final class PdfImages {
    private PdfImages() {}
    public static PDImageXObject logoFromClasspath(PDDocument doc, String path) throws IOException {
        BufferedImage bimg = ImageIO.read(Objects.requireNonNull(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                "Resource not found: " + path));
        return LosslessFactory.createFromImage(doc, bimg);
    }
    public static float[] fit(float srcW, float srcH, float maxW, float maxH) {
        float scale = Math.min(maxW/srcW, maxH/srcH);
        return new float[]{srcW*scale, srcH*scale};
    }
}
