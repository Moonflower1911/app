package com.smsmode.invoice.pdf.render;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;

import java.awt.Color;
import java.io.IOException;

public final class WatermarkRenderer {
    private WatermarkRenderer() {}

        public static void drawProforma(PDPageContentStream cs, float pageW, float pageH) throws IOException {
        var font = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
        float size = 96f;
        String text = "PROFORMA";

        var gs = new PDExtendedGraphicsState();
        gs.setNonStrokingAlphaConstant(0.08f);
        gs.setStrokingAlphaConstant(0.08f);

        cs.saveGraphicsState();
        cs.setGraphicsStateParameters(gs);
        cs.setNonStrokingColor(new Color(40, 40, 40));

        // translate to center, then rotate
        Matrix m = new Matrix();
        m.translate(pageW / 2f, pageH / 2f);
        m.rotate((float) Math.toRadians(35));
        cs.transform(m);

        float textW = font.getStringWidth(text) / 1000f * size;

        cs.beginText();
        cs.setFont(font, size);
        cs.newLineAtOffset(-textW / 2f, 0f);
        cs.showText(text);
        cs.endText();

        cs.restoreGraphicsState();
    }
}
