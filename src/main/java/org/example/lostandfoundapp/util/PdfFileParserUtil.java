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
public class PdfFileParserUtil {
    private PdfFileParserUtil() {
    }

    /**
     * Parses a PDF file and extracts lost items.
     *
     * @param file the PDF file to parse
     * @return a list of lost items extracted from the PDF file
     */
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

    /**
     * Extracts text from a PDF file.
     *
     * @param file the PDF file to extract text from
     * @return the extracted text
     * @throws IOException if an error occurs while reading the PDF file
     */
    private static String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    /**
     * Splits the extracted text into lines.
     *
     * @param text the extracted text
     * @return an array of lines
     */
    private static String[] splitTextIntoLines(String text) {
        return text.split("\n");
    }

    /**
     * Parses lines of text into lost items.
     *
     * @param lines the lines of text to parse
     * @return a list of lost items
     */
    public static List<LostItem> parseLinesIntoLostItems(String[] lines) {
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

    /**
     * Adds the current item to the list of lost items.
     *
     * @param lostItems   the list of lost items
     * @param currentItem the current item to add
     */
    private static void addCurrentItemToList(List<LostItem> lostItems, LostItem currentItem) {
        if (currentItem != null) {
            lostItems.add(currentItem);
        }
    }

    /**
     * Creates a new lost item and adds the current item to the list.
     *
     * @param lostItems   the list of lost items
     * @param currentItem the current item to add
     * @param itemName    the name of the new item
     * @return the new lost item
     */
    private static LostItem createNewItem(List<LostItem> lostItems, LostItem currentItem, String itemName) {
        addCurrentItemToList(lostItems, currentItem);
        currentItem = new LostItem();
        currentItem.setItemName(itemName);
        return currentItem;
    }

    /**
     * Sets the quantity of the current item.
     *
     * @param currentItem the current item
     * @param quantity    the quantity string to set
     */
    private static void setItemQuantity(LostItem currentItem, String quantity) {
        if (currentItem != null) {
            try {
                currentItem.setQuantity(Integer.parseInt(quantity));
            } catch (NumberFormatException e) {
                log.error("Invalid quantity format: " + quantity);
                currentItem.setQuantity(1);
            }
        }
    }

    /**
     * Sets the place of the current item.
     *
     * @param currentItem the current item
     * @param place       the place to set
     */
    private static void setItemPlace(LostItem currentItem, String place) {
        if (currentItem != null) {
            currentItem.setPlace(place);
        }
    }
}
