package com.smsmode.invoice.pdf.util;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class PdfText {
    private PdfText() {}

    public static float stringWidth(PDType1Font font, float size, String text) throws IOException {
        return font.getStringWidth(text) / 1000f * size;
    }

    public static float capHeight(PDType1Font font, float size) throws IOException {
        var desc = font.getFontDescriptor();
        float cap = (desc != null && desc.getCapHeight() > 0) ? desc.getCapHeight() : 700f;
        return cap / 1000f * size;
    }

    public static List<String> wrap(PDType1Font font, float size, float maxW, String text) throws IOException {
        List<String> out = new ArrayList<>();
        if (text == null || text.isBlank()) return out;
        String[] words = text.trim().split("\\s+");
        StringBuilder line = new StringBuilder();
        for (String w : words) {
            if (line.length() == 0) {
                if (stringWidth(font, size, w) <= maxW) line.append(w);
                else out.addAll(hardWrap(font, size, maxW, w));
            } else {
                String candidate = line + " " + w;
                if (stringWidth(font, size, candidate) <= maxW) line.append(" ").append(w);
                else {
                    out.add(line.toString());
                    if (stringWidth(font, size, w) <= maxW) line = new StringBuilder(w);
                    else { out.addAll(hardWrap(font, size, maxW, w)); line = new StringBuilder(); }
                }
            }
        }
        if (line.length() > 0) out.add(line.toString());
        return out;
    }

    private static List<String> hardWrap(PDType1Font font, float size, float maxW, String word) throws IOException {
        List<String> parts = new ArrayList<>();
        StringBuilder chunk = new StringBuilder();
        for (int i=0;i<word.length();i++) {
            char c = word.charAt(i);
            String cand = chunk.toString() + c;
            if (stringWidth(font, size, cand) <= maxW) chunk.append(c);
            else { if (chunk.length()>0) { parts.add(chunk.toString()); chunk.setLength(0); i--; } else parts.add(String.valueOf(c)); }
        }
        if (chunk.length()>0) parts.add(chunk.toString());
        return parts;
    }

    public static String ns(String s) { return s == null ? "" : s; }
}
