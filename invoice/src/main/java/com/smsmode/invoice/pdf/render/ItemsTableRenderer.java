package com.smsmode.invoice.pdf.render;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

import static com.smsmode.invoice.pdf.style.PdfStyles.*;
import static com.smsmode.invoice.pdf.util.PdfText.*;

public final class ItemsTableRenderer {
    private ItemsTableRenderer() {}

    private static final float HEADER_H  = 24f;
    private static final float ROW_MIN_H = 20f; // baseline if short text
    private static final float PAD_X     = 8f;
    private static final float PAD_Y     = 5f;  // vertical padding inside a row

    /** Let paginator know the header height. */
    public static float headerHeight() { return HEADER_H; }

    /** Measure how tall one row must be given the Description column width. */
    public static float measureRowHeight(String[] row, float descColWidth) throws IOException {
        String desc = row[0] == null ? "" : row[0];
        String[] rawLines = desc.split("\\n");
        float usable = descColWidth - 2 * PAD_X;

        int totalLines = 0;
        for (String ln : rawLines) {
            // Remove marker if present
            if (ln.startsWith("§BLUE§")) {
                ln = ln.substring("§BLUE§".length());
            }
            var wrapped = wrap(FONT_REG, FONT_SIZE_BODY, usable, ln);
            totalLines += wrapped.isEmpty() ? 1 : wrapped.size();
        }

        float cap = capHeight(FONT_REG, FONT_SIZE_BODY);
        float textBlock = totalLines == 0 ? cap : (totalLines * FONT_LINE_HEIGHT);
        return Math.max(ROW_MIN_H, PAD_Y + textBlock + PAD_Y);
    }

    /** Draw header + the given rows with variable heights. Returns table bottom Y. */
    public static void drawTableHeader(PDPageContentStream cs, float x, float headerTopY,
                                       float[] colW, String[] headers) throws IOException {
        float tableW = 0f;
        for (float w : colW) tableW += w;

        // header background
        cs.setNonStrokingColor(TEAL);
        cs.addRect(x, headerTopY - HEADER_H, tableW, HEADER_H);
        cs.fill();

        // header labels
        cs.setNonStrokingColor(Color.WHITE);
        float textBaseY = headerTopY - HEADER_H + (HEADER_H - capHeight(FONT_BOLD, FONT_SIZE_HEADER)) / 2f;
        float colX = x;
        for (int i = 0; i < headers.length; i++) {
            cs.beginText();
            cs.setFont(FONT_BOLD, FONT_SIZE_HEADER);
            cs.newLineAtOffset(colX + PAD_X, textBaseY);
            cs.showText(headers[i]);
            cs.endText();
            colX += colW[i];
        }

        // header border lines (top, bottom, left, right)
        cs.setStrokingColor(GRID);
        cs.setLineWidth(0.5f);
        // top border
        cs.moveTo(x, headerTopY);
        cs.lineTo(x + tableW, headerTopY);
        // bottom border
        cs.moveTo(x, headerTopY - HEADER_H);
        cs.lineTo(x + tableW, headerTopY - HEADER_H);
        // left border
        cs.moveTo(x, headerTopY);
        cs.lineTo(x, headerTopY - HEADER_H);
        // right border
        cs.moveTo(x + tableW, headerTopY);
        cs.lineTo(x + tableW, headerTopY - HEADER_H);
        cs.stroke();

        colX = x;
        for (int i = 0; i < colW.length - 1; i++) {
            colX += colW[i];
            cs.moveTo(colX, headerTopY);
            cs.lineTo(colX, headerTopY - HEADER_H);
        }
        cs.stroke();
    }

    public static void drawRow(PDPageContentStream cs, float x, float rowTop,
                               float[] colW, String[] row) throws IOException {
        float rowH = measureRowHeight(row, colW[0]);
        float rowBottom = rowTop - rowH;

        // table total width
        float tableW = 0f;
        for (float w : colW) tableW += w;

        cs.setStrokingColor(GRID);
        cs.setLineWidth(0.5f);

        // draw vertical borders (including left and right edges)
        cs.moveTo(x, rowTop);
        cs.lineTo(x, rowBottom);
        float colX = x;
        for (int i = 0; i < colW.length - 1; i++) {
            colX += colW[i];
            cs.moveTo(colX, rowTop);
            cs.lineTo(colX, rowBottom);
        }
        cs.moveTo(x + tableW, rowTop);
        cs.lineTo(x + tableW, rowBottom);

        // draw horizontal borders (top & bottom of row)
        cs.moveTo(x, rowTop);
        cs.lineTo(x + tableW, rowTop);
        cs.moveTo(x, rowBottom);
        cs.lineTo(x + tableW, rowBottom);

        // commit all borders
        cs.stroke();

        // Description cell text
        String desc = row[0] == null ? "" : row[0];
        String[] rawLines = desc.split("\\n");
        float lineBaseY = rowTop - PAD_Y - capHeight(FONT_REG, FONT_SIZE_BODY);
        float usable = colW[0] - 2 * PAD_X;
        for (int li = 0; li < rawLines.length; li++) {
            String ln = rawLines[li];
            if (li == 0 && ln.startsWith("§BLUE§")) {
                ln = ln.substring("§BLUE§".length());
                cs.setNonStrokingColor(new Color(0, 102, 204));
                cs.setFont(FONT_BOLD, FONT_SIZE_BODY_BOLD);
            } else {
                cs.setNonStrokingColor(new Color(64, 64, 64));
                cs.setFont(FONT_REG, FONT_SIZE_BODY);
            }
            var wrapped = wrap(FONT_REG, FONT_SIZE_BODY, usable, ln);
            if (wrapped.isEmpty()) wrapped = List.of("");
            for (String wl : wrapped) {
                cs.beginText();
                cs.setFont(FONT_REG, FONT_SIZE_BODY);
                cs.newLineAtOffset(x + PAD_X, lineBaseY);
                cs.showText(wl);
                cs.endText();
                lineBaseY -= FONT_LINE_HEIGHT;
            }
        }

        // Numeric columns
        float tx = x + colW[0];
        for (int c = 1; c < colW.length; c++) {
            String text = row[c] == null ? "" : row[c];
            float tw = stringWidth(FONT_REG, FONT_SIZE_BODY, text);
            float rx = tx + colW[c] - PAD_X - tw;
            float by = rowBottom + (rowH - capHeight(FONT_REG, FONT_SIZE_BODY)) / 2f;
            cs.beginText();
            cs.setFont(FONT_REG, FONT_SIZE_BODY);
            cs.newLineAtOffset(rx, by);
            cs.showText(text);
            cs.endText();
            tx += colW[c];
        }
    }

}
