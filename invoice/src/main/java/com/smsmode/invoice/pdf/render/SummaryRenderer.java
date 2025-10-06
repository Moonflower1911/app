package com.smsmode.invoice.pdf.render;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static com.smsmode.invoice.pdf.style.PdfStyles.*;
import static com.smsmode.invoice.pdf.util.PdfText.*;

public final class SummaryRenderer {
    private SummaryRenderer() {}

    public static float drawNotesBox(PDPageContentStream cs, float x, float topY,
                                     float width, String notes) throws IOException {
        float headerH = 20f;
        float gap = 8f;

        // header bar
        float headerBottomY = topY - headerH;
        cs.setNonStrokingColor(TEAL);
        cs.addRect(x, headerBottomY, width, headerH);
        cs.fill();

        // header label
        float tx = x + (width - stringWidth(FONT_BOLD, FONT_SIZE_NOTES_HEADER, "Notes")) / 2f;
        float ty = headerBottomY + (headerH - capHeight(FONT_BOLD, FONT_SIZE_NOTES_HEADER)) / 2f;
        cs.setNonStrokingColor(Color.WHITE);
        cs.beginText();
        cs.setFont(FONT_BOLD, FONT_SIZE_NOTES_HEADER);
        cs.newLineAtOffset(tx, ty);
        cs.showText("Notes");
        cs.endText();

        // body text
        float bodyTop = headerBottomY - gap;
        float y = bodyTop - capHeight(FONT_BOLD, FONT_SIZE_NOTES_BODY);
        cs.setNonStrokingColor(Color.BLACK);
        for (String line : wrap(FONT_BOLD, FONT_SIZE_NOTES_BODY, width - 14f, notes == null ? "" : notes)) {
            cs.beginText();
            cs.setFont(FONT_BOLD, FONT_SIZE_NOTES_BODY);
            cs.newLineAtOffset(x + 14f, y);
            cs.showText(line);
            cs.endText();
            y -= 16f;
        }
        return y;
    }

    public static float drawTotalsBox(PDPageContentStream cs, float x, float topY,
                                      float width, List<String[]> lines) throws IOException {
        float rowH = 22f;
        float padX = 10f;

        float yTop = topY;
        for (int i = 0; i < lines.size(); i++) {
            String label = lines.get(i)[0];
            String value = lines.get(i)[1];

            // background shading
            if (i < lines.size() - 1) {
                cs.setNonStrokingColor(new Color(245, 245, 245));
            } else {
                cs.setNonStrokingColor(new Color(238, 238, 238));
            }
            cs.addRect(x, yTop - rowH, width, rowH);
            cs.fill();

            // separator line
            cs.setStrokingColor(new Color(210, 214, 220));
            cs.setLineWidth(i == lines.size() - 1 ? 1.2f : 0.5f);
            cs.moveTo(x, yTop - rowH);
            cs.lineTo(x + width, yTop - rowH);
            cs.stroke();

            // text
            boolean bold = (i == lines.size() - 1);
            var font = bold ? FONT_BOLD : FONT_REG;
            float size = bold ? FONT_SIZE_TOTALS_BOLD : FONT_SIZE_TOTALS;
            float baseY = (yTop - rowH) + (rowH - capHeight(font, size)) / 2f;

            // label
            cs.setNonStrokingColor(Color.DARK_GRAY);
            cs.beginText();
            cs.setFont(font, size);
            cs.newLineAtOffset(x + padX, baseY);
            cs.showText(label);
            cs.endText();

            // value
            cs.setNonStrokingColor(Color.BLACK);
            float vW = stringWidth(font, size, value);
            cs.beginText();
            cs.setFont(font, size);
            cs.newLineAtOffset(x + width - padX - vW, baseY);
            cs.showText(value);
            cs.endText();

            yTop -= rowH;
        }

        cs.setStrokingColor(new Color(210, 214, 220));
        cs.setLineWidth(0.5f);
        cs.addRect(x, topY - lines.size() * rowH, width, lines.size() * rowH);
        cs.stroke();

        return topY - lines.size() * rowH;
    }
}
