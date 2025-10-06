package com.smsmode.invoice.pdf;

import com.smsmode.invoice.enumeration.BookingTypeEnum;
import com.smsmode.invoice.pdf.render.ItemsTableRenderer;
import com.smsmode.invoice.pdf.render.SummaryRenderer;
import com.smsmode.invoice.pdf.render.WatermarkRenderer;
import com.smsmode.invoice.resource.booking.common.StayResource;
import com.smsmode.invoice.resource.booking.get.FeeGetResource;
import com.smsmode.invoice.resource.common.PartyInfo;
import com.smsmode.invoice.resource.invoice.post.InvoicePdfPostResource;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static com.smsmode.invoice.pdf.render.HeaderRenderer.render;
import static com.smsmode.invoice.pdf.render.PartyBlocksRenderer.drawDetails;
import static com.smsmode.invoice.pdf.render.PartyBlocksRenderer.drawLabel;
import static com.smsmode.invoice.pdf.style.PdfStyles.*;
import static com.smsmode.invoice.pdf.util.PdfText.ns;

@Component
public class InvoicePdfGenerator {

    public byte[] generate(InvoicePdfPostResource payload) {
        boolean proforma = true;
        String currency = "MAD";

        PartyInfo billedFrom = new PartyInfo();
        billedFrom.setName("Nozoul");
        billedFrom.setAddress("123 Rue Mohammed V, Casablanca, Maroc");
        billedFrom.setEmail("contact@nozoul.ma");
        billedFrom.setPhone("+212 522 123 456");

        PartyInfo billedTo = new PartyInfo();
        billedTo.setName("Hamza Habchi");
        billedTo.setAddress("Casablanca, Maroc");
        billedTo.setEmail("hamzahabchi.perso@gmail.com");
        billedTo.setPhone("+212 603 615 503");

        LocalDate invoiceDate = LocalDate.now();

        String staticNote = "Thank you for your business and trust.\n" +
                            "We look forward to working with you again.";

        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            float pageW = PDRectangle.A4.getWidth();
            float pageH = PDRectangle.A4.getHeight();
            float contentW = pageW - 2 * MARGIN;
            float headerY = pageH - MARGIN - HEADER_H;

            // Column widths
            float unitPriceW = 70f, qtyW = 58f, taxPctW = 50f, taxAmtW = 86f, totalW = 86f;
            float fixed = unitPriceW + qtyW + taxPctW + taxAmtW + totalW;
            float descW = contentW - fixed;
            float[] colWidths = {descW, unitPriceW, qtyW, taxPctW, taxAmtW, totalW};
            String[] headers = {"Description", "Unit Price", "Quantity", "Tax %", "Tax Amount", "Total Amount"};

            // Build rows
            List<String[]> rows = new ArrayList<>();
            if (payload.getType() == BookingTypeEnum.GROUP) {
                if (payload.getItems() != null) {
                    payload.getItems().forEach(item -> {
                        addStayRow(rows, item.getStay());
                        addFeesRows(rows, item.getFees());
                    });
                }
            } else if (payload.getType() == BookingTypeEnum.SINGLE) {
                addStayRow(rows, payload.getStay());
                addFeesRows(rows, payload.getFees());
            }

            // Totals calculation
            NumberFormat currencyFmt = makeCurrencyFormat(currency);
            BigDecimal subtotal = rows.stream()
                    .map(r -> parseMoney(r[1]).multiply(parseMoney(r[2])))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal discountPct = BigDecimal.ZERO;
            BigDecimal discount = subtotal.multiply(discountPct)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            BigDecimal subAfterDiscount = subtotal.subtract(discount);
            BigDecimal taxTotal = rows.stream()
                    .map(r -> parseMoney(r[4]).multiply(parseMoney(r[2])))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal grandTotal = subAfterDiscount.add(taxTotal).setScale(2, RoundingMode.HALF_UP);

            List<String[]> totalsRows = List.of(
                    new String[]{"Subtotal (excl. tax)", money(subtotal, currencyFmt)},
                    new String[]{"Discount (" + discountPct.toPlainString() + "%)", money(discount, currencyFmt)},
                    new String[]{"After discount (excl. tax)", money(subAfterDiscount, currencyFmt)},
                    new String[]{"Tax", money(taxTotal, currencyFmt)},
                    new String[]{"Total (incl. tax)", money(grandTotal, currencyFmt)}
            );

            // Pagination
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);
            PDPageContentStream cs = new PDPageContentStream(doc, page);
            float currentY = drawPageHeader(cs, doc, pageW, pageH, headerY, proforma, invoiceDate, billedFrom, billedTo, true);

            // Draw table header
            ItemsTableRenderer.drawTableHeader(cs, MARGIN, currentY, colWidths, headers);
            currentY -= ItemsTableRenderer.headerHeight();

            for (int r = 0; r < rows.size(); r++) {
                float rowH = ItemsTableRenderer.measureRowHeight(rows.get(r), colWidths[0]);
                if (currentY - rowH < MARGIN + 100f) {
                    cs.close();
                    page = new PDPage(PDRectangle.A4);
                    doc.addPage(page);
                    cs = new PDPageContentStream(doc, page);
                    currentY = drawPageHeader(cs, doc, pageW, pageH, headerY, proforma, invoiceDate, billedFrom, billedTo, false);
                    ItemsTableRenderer.drawTableHeader(cs, MARGIN, currentY, colWidths, headers);
                    currentY -= ItemsTableRenderer.headerHeight();
                }
                ItemsTableRenderer.drawRow(cs, MARGIN, currentY, colWidths, rows.get(r));
                currentY -= rowH;
            }

            // Draw summary on last page
            float notesW = 220f;
            float totalsW = 230f;
            float spacingAfterTable = 20f;
            float notesTopY = currentY - spacingAfterTable;
            float totalsTopY = notesTopY;
            String finalNotes = (payload.getNotes() != null && !payload.getNotes().isBlank())
                    ? payload.getNotes() + "\n\n" + staticNote
                    : staticNote;

            SummaryRenderer.drawNotesBox(cs, MARGIN, notesTopY, notesW, finalNotes);
            SummaryRenderer.drawTotalsBox(cs, pageW - MARGIN - totalsW, totalsTopY, totalsW, totalsRows);

            cs.close();
            doc.save(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate invoice PDF", e);
        }
    }

    private float drawPageHeader(
            PDPageContentStream cs, PDDocument doc, float pageW, float pageH,
            float headerY, boolean proforma, LocalDate invoiceDate,
            PartyInfo billedFrom, PartyInfo billedTo,
            boolean isFirstPage
    ) throws IOException {
        if (proforma) {
            WatermarkRenderer.drawProforma(cs, pageW, pageH);
        }
        render(doc, cs, pageW, headerY, invoiceDate);

        if (isFirstPage) {
            final float COL_W = 190f;
            float fromX = MARGIN;
            float toX = pageW - MARGIN - COL_W;
            float labelY = headerY - 50f;
            float detailsTopY = labelY - 3f;
            float firstBaseline = detailsTopY - LINE_GAP;
            drawLabel(cs, fromX, labelY, COL_W, "From");
            drawLabel(cs, toX, labelY, COL_W, "To");
            drawDetails(cs, billedFrom, fromX, firstBaseline, COL_W);
            drawDetails(cs, billedTo, toX, firstBaseline, COL_W);
            return Math.min(firstBaseline - 90f, pageH - 250f);
        } else {
            // Just return Y position without drawing billing
            return headerY - 40f; // enough space under logo/title
        }
    }

    private void addStayRow(List<String[]> rows, StayResource stay) {
        if (stay != null && stay.getUnitRef() != null && stay.getTotalPrice() != null) {
            String nameLine = "§BLUE§" + ns(stay.getUnitRef().getName());
            StringBuilder desc = new StringBuilder(nameLine)
                    .append("\nFrom ").append(stay.getCheckinDate())
                    .append(" to ").append(stay.getCheckoutDate());

            if (stay.getOccupancy() != null) {
                desc.append("\nAdults: ").append(stay.getOccupancy().getAdults() != null
                        ? stay.getOccupancy().getAdults() : 0);
                if (stay.getOccupancy().getChildren() != null && !stay.getOccupancy().getChildren().isEmpty()) {
                    int totalChildren = stay.getOccupancy().getChildren().stream()
                            .mapToInt(c -> c.getQuantity() != null ? c.getQuantity() : 0)
                            .sum();
                    desc.append("\nChildren: ").append(totalChildren);
                    List<String> ageDetails = new ArrayList<>();
                    stay.getOccupancy().getChildren().forEach(c -> {
                        if (c.getAge() != null) ageDetails.add("age " + c.getAge());
                    });
                    if (!ageDetails.isEmpty()) {
                        desc.append(" (").append(String.join(", ", ageDetails)).append(")");
                    }
                }
            }

            rows.add(new String[]{
                    desc.toString(),
                    fmtMoney(stay.getTotalPrice().getUnitPriceExclTax()),
                    safeToString(stay.getTotalPrice().getQuantity()),
                    fmtPct(stay.getTotalPrice().getVatPercentage()),
                    fmtMoney(stay.getTotalPrice().getVatAmount()),
                    fmtMoney(stay.getTotalPrice().getAmountInclTax())
            });
        }
    }

    private void addFeesRows(List<String[]> rows, List<FeeGetResource> fees) {
        if (fees != null && !fees.isEmpty()) {
            fees.forEach(fee -> {
                if (fee.getFeeRef() != null && fee.getRate() != null) {
                    String feeNameLine = "§BLUE§" + ns(fee.getFeeRef().getName());
                    StringBuilder feeDesc = new StringBuilder(feeNameLine);
                    if (fee.getModality() != null) {
                        feeDesc.append("\n").append(fee.getModality().name().replace("_", " ").toLowerCase());
                    }
                    if (fee.getOccupancy() != null) {
                        feeDesc.append("\nAdults: ").append(fee.getOccupancy().getAdults() != null
                                ? fee.getOccupancy().getAdults() : 0);
                        if (fee.getOccupancy().getChildren() != null && fee.getOccupancy().getChildren().getQuantity() != null) {
                            feeDesc.append("\nChildren: ").append(fee.getOccupancy().getChildren().getQuantity());
                            if (fee.getOccupancy().getChildren().getAgeBucket() != null) {
                                Integer from = fee.getOccupancy().getChildren().getAgeBucket().getFromAge();
                                Integer to = fee.getOccupancy().getChildren().getAgeBucket().getToAge();
                                feeDesc.append(" (age ")
                                        .append(from != null ? from : "?")
                                        .append(" to ")
                                        .append(to != null ? to : "?")
                                        .append(")");
                            }
                        }
                    }
                    rows.add(new String[]{
                            feeDesc.toString(),
                            fmtMoney(fee.getRate().getUnitPriceExclTax()),
                            safeToString(fee.getRate().getQuantity()),
                            fmtPct(fee.getRate().getVatPercentage()),
                            fmtMoney(fee.getRate().getVatAmount()),
                            fmtMoney(fee.getRate().getAmountInclTax())
                    });
                }
            });
        }
    }

    private static String fmtMoney(BigDecimal v) {
        return v == null ? "" : v.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    private static String fmtPct(BigDecimal v) {
        return v == null ? "" : v.stripTrailingZeros().toPlainString();
    }

    private static NumberFormat makeCurrencyFormat(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        DecimalFormat df = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        df.setCurrency(currency);
        DecimalFormatSymbols sym = df.getDecimalFormatSymbols();
        sym.setCurrency(currency);
        sym.setCurrencySymbol(currency.getSymbol(Locale.US));
        df.setDecimalFormatSymbols(sym);
        String symbol = sym.getCurrencySymbol();
        if (symbol.length() > 1) {
            df.applyPattern("¤ #,##0.00;¤ -#,##0.00");
        } else {
            df.applyPattern("¤#,##0.00;¤-#,##0.00");
        }
        return df;
    }

    private static String money(BigDecimal v, NumberFormat fmt) {
        BigDecimal val = (v == null) ? BigDecimal.ZERO : v.setScale(2, RoundingMode.HALF_UP);
        return fmt.format(val);
    }

    private static BigDecimal parseMoney(String str) {
        try {
            return new BigDecimal(str);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private static String safeToString(BigDecimal v) {
        return v != null ? v.stripTrailingZeros().toPlainString() : "";
    }
}
