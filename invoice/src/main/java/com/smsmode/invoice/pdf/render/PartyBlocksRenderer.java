package com.smsmode.invoice.pdf.render;

import com.smsmode.invoice.resource.common.PartyInfo;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.awt.*;
import java.io.IOException;

import static com.smsmode.invoice.pdf.style.PdfStyles.*;
import static com.smsmode.invoice.pdf.util.PdfText.*;

public final class PartyBlocksRenderer {
    private PartyBlocksRenderer() {}

    public static void drawLabel(PDPageContentStream cs, float x, float y, float w, String label) throws IOException {
        cs.setNonStrokingColor(TEAL);
        cs.addRect(x, y, w, LABEL_H);
        cs.fill();

        float textW = stringWidth(FONT_BOLD, FONT_SIZE_PARTY_LABEL, label);
        float tx = x + (w - textW) / 2f;
        float ty = y + (LABEL_H - capHeight(FONT_BOLD, FONT_SIZE_PARTY_LABEL)) / 2f;

        cs.setNonStrokingColor(Color.WHITE);
        cs.beginText();
        cs.setFont(FONT_BOLD, FONT_SIZE_PARTY_LABEL);
        cs.newLineAtOffset(tx, ty);
        cs.showText(label);
        cs.endText();
        cs.setNonStrokingColor(Color.BLACK);
    }

    public static float drawDetails(PDPageContentStream cs, PartyInfo p,
                                    float x, float yStart, float maxW) throws IOException {
        float y = yStart;

        for (String line : wrap(FONT_BOLD, FONT_SIZE_PARTY_NAME, maxW, ns(p.getName()))) {
            cs.beginText(); cs.setFont(FONT_BOLD, FONT_SIZE_PARTY_NAME); cs.newLineAtOffset(x, y); cs.showText(line); cs.endText();
            y -= LINE_GAP;
        }
        for (String line : wrap(FONT_REG, FONT_SIZE_PARTY_DETAILS, maxW, ns(p.getAddress()))) {
            cs.beginText(); cs.setFont(FONT_REG, FONT_SIZE_PARTY_DETAILS); cs.newLineAtOffset(x, y); cs.showText(line); cs.endText();
            y -= LINE_GAP;
        }
        for (String line : wrap(FONT_REG, FONT_SIZE_PARTY_DETAILS, maxW, ns(p.getEmail()))) {
            cs.beginText(); cs.setFont(FONT_REG, FONT_SIZE_PARTY_DETAILS); cs.newLineAtOffset(x, y); cs.showText(line); cs.endText();
            y -= LINE_GAP;
        }
        for (String line : wrap(FONT_REG, FONT_SIZE_PARTY_DETAILS, maxW, ns(p.getPhone()))) {
            cs.beginText(); cs.setFont(FONT_REG, FONT_SIZE_PARTY_DETAILS); cs.newLineAtOffset(x, y); cs.showText(line); cs.endText();
            y -= LINE_GAP;
        }
        return y;
    }
}
