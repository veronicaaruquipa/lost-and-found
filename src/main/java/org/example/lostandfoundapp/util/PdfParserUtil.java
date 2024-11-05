package org.example.lostandfoundapp.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.lostandfoundapp.model.LostItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PdfParserUtil {
    private PdfParserUtil() {
    }

    public static List<LostItem> parseFile(MultipartFile file) {
        List<LostItem> lostItems = new ArrayList<>();

        try {
            String text = extractTextFromPDF(file);
            String[] lines = splitTextIntoLines(text);
            lostItems = parseLinesIntoLostItems(lines);
        } catch (IOException e) {
            log.error("Error reading PDF file: %s" + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: " + e.getMessage());
        }
        return lostItems;
    }

    private static String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    private static String[] splitTextIntoLines(String text) {
        return text.split("\n");
    }

    private static List<LostItem> parseLinesIntoLostItems(String[] lines) {
        List<LostItem> lostItems = new ArrayList<>();
        LostItem currentItem = null;

        for (String line : lines) {
            if (line.startsWith("ItemName:")) {
                currentItem = createNewItem(lostItems, currentItem, line.substring(9).trim());
            } else if (line.startsWith("Quantity:")) {
                setItemQuantity(currentItem, line.substring(9).trim());
            } else if (line.startsWith("Place:")) {
                setItemPlace(currentItem, line.substring(6).trim());
            } else if (line.startsWith("------------------------------------") || line.isBlank()) {
                addCurrentItemToList(lostItems, currentItem);
                currentItem = null;
            }
        }
        addCurrentItemToList(lostItems, currentItem);
        return lostItems;
    }

    private static void addCurrentItemToList(List<LostItem> lostItems, LostItem currentItem) {
        if (currentItem != null) {
            lostItems.add(currentItem);
        }
    }

    private static LostItem createNewItem(List<LostItem> lostItems, LostItem currentItem, String itemName) {
        addCurrentItemToList(lostItems, currentItem);
        currentItem = new LostItem();
        currentItem.setItemName(itemName);
        return currentItem;
    }

    private static void setItemQuantity(LostItem currentItem, String quantityStr) {
        if (currentItem != null) {
            try {
                currentItem.setQuantity(Integer.parseInt(quantityStr));
            } catch (NumberFormatException e) {
                log.error("Invalid quantity format: " + quantityStr);
                currentItem.setQuantity(0); // Default value or handle as needed
            }
        }
    }

    private static void setItemPlace(LostItem currentItem, String place) {
        if (currentItem != null) {
            currentItem.setPlace(place);
        }
    }
}
