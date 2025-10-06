package com.smsmode.invoice.pdf.render;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.time.LocalDate;

import static com.smsmode.invoice.pdf.style.PdfStyles.*;
import static com.smsmode.invoice.pdf.util.PdfImages.*;
import static com.smsmode.invoice.pdf.util.PdfText.*;

public final class HeaderRenderer {
    private HeaderRenderer() {}

    public static void render(PDDocument doc, PDPageContentStream cs,
                              float pageW, float headerY, LocalDate invoiceDate) throws IOException {
        // Logo (auto-fit)
        PDImageXObject logo = logoFromClasspath(doc, "nozoul-newest.png");
        float[] size = fit(logo.getWidth(), logo.getHeight(), 230f, 230f);
        float logoX = MARGIN, logoY = headerY + (HEADER_H - size[1]) / 2f;
        cs.drawImage(logo, logoX, logoY, size[0], size[1]);

        // date (right-bottom)
        String dateText = "Date: " + invoiceDate;
        float textW = stringWidth(FONT_BOLD, FONT_SIZE_HEADER_DATE, dateText);
        float x = pageW - MARGIN - textW;
        float baseY = headerY;
        cs.beginText();
        cs.setFont(FONT_BOLD, FONT_SIZE_HEADER_DATE);
        cs.newLineAtOffset(x, baseY);
        cs.showText(dateText);
        cs.endText();
    }
}
