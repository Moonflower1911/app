package com.smsmode.invoice.pdf.style;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.Color;

public final class PdfStyles {
    private PdfStyles() {}
    public static final float MARGIN = 40f;
    public static final float HEADER_H = 90f;
    public static final float LABEL_H = 20f;
    public static final float LINE_GAP = 16f;
    public static final Color TEAL = new Color(0,128,160);
    public static final Color GRID = new Color(210,214,220);
    public static final PDType1Font FONT_BOLD = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    public static final PDType1Font FONT_REG  = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

    public static final float FONT_SIZE_HEADER_DATE = 9f; // date text in header

    public static final float FONT_SIZE_HEADER = 9f;   // table header text
    public static final float FONT_SIZE_BODY = 8f;     // regular body text
    public static final float FONT_SIZE_BODY_BOLD = 9f; // bold items like blue title
    public static final float FONT_LINE_HEIGHT = 14f;   // line spacing inside cells

    public static final float FONT_SIZE_PARTY_LABEL = 9f;   // "From" / "To" label
    public static final float FONT_SIZE_PARTY_NAME = 9f;    // party name
    public static final float FONT_SIZE_PARTY_DETAILS = 9f; // address, email, phone

    public static final float FONT_SIZE_NOTES_HEADER = 9f;   // "Notes" label
    public static final float FONT_SIZE_NOTES_BODY = 9f;     // text in notes box
    public static final float FONT_SIZE_TOTALS = 8f;         // totals rows
    public static final float FONT_SIZE_TOTALS_BOLD = 8.5f;  // grand total row


}
