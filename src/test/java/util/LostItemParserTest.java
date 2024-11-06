package util;

import org.example.lostandfoundapp.model.LostItem;
import org.example.lostandfoundapp.util.PdfFileParserUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LostItemParserTest {

    @Test
    void testParseLinesIntoLostItems() {
        String[] lines = {
                "ItemName: Wallet",
                "Quantity: 1",
                "Place: Park",
                "------------------------------------",
                "ItemName: Phone",
                "Quantity: 2",
                "Place: Office",
                "------------------------------------"
        };

        List<LostItem> lostItems = PdfFileParserUtil.parseLinesIntoLostItems(lines);

        assertEquals(2, lostItems.size());

        LostItem firstItem = lostItems.get(0);
        assertEquals("Wallet", firstItem.getItemName());
        assertEquals(1, firstItem.getQuantity());
        assertEquals("Park", firstItem.getPlace());

        LostItem secondItem = lostItems.get(1);
        assertEquals("Phone", secondItem.getItemName());
        assertEquals(2, secondItem.getQuantity());
        assertEquals("Office", secondItem.getPlace());
    }

    @Test
    void testParseLinesIntoLostItemsWithEmptyLines() {
        String[] lines = {
                "ItemName: Wallet",
                "Quantity: 1",
                "Place: Park",
                "",
                "ItemName: Phone",
                "Quantity: 2",
                "Place: Office",
                ""
        };

        List<LostItem> lostItems = PdfFileParserUtil.parseLinesIntoLostItems(lines);

        assertEquals(2, lostItems.size());

        LostItem firstItem = lostItems.get(0);
        assertEquals("Wallet", firstItem.getItemName());
        assertEquals(1, firstItem.getQuantity());
        assertEquals("Park", firstItem.getPlace());

        LostItem secondItem = lostItems.get(1);
        assertEquals("Phone", secondItem.getItemName());
        assertEquals(2, secondItem.getQuantity());
        assertEquals("Office", secondItem.getPlace());
    }

    @Test
    void testParseLinesIntoLostItemsWithEmptyInput() {
        String[] lines = {};

        List<LostItem> lostItems = PdfFileParserUtil.parseLinesIntoLostItems(lines);

        assertTrue(lostItems.isEmpty());
    }

    @Test
    void testParseLinesIntoLostItemsWithNoItems() {
        String[] lines = {
                "------------------------------------",
                "------------------------------------"
        };

        List<LostItem> lostItems = PdfFileParserUtil.parseLinesIntoLostItems(lines);

        assertTrue(lostItems.isEmpty());
    }

    @Test
    void testParseLinesIntoLostItemsWithIncompleteItem() {
        String[] lines = {
                "ItemName: Wallet",
                "Quantity: 1"
        };

        List<LostItem> lostItems = PdfFileParserUtil.parseLinesIntoLostItems(lines);

        assertEquals(1, lostItems.size());

        LostItem firstItem = lostItems.get(0);
        assertEquals("Wallet", firstItem.getItemName());
        assertEquals(1, firstItem.getQuantity());
        assertNull(firstItem.getPlace());
    }

    @Test
    void testParseLinesIntoLostItemsWithMultipleConsecutiveSeparators() {
        String[] lines = {
                "ItemName: Wallet",
                "Quantity: 1",
                "Place: Park",
                "------------------------------------",
                "------------------------------------",
                "ItemName: Phone",
                "Quantity: 2",
                "Place: Office",
                "------------------------------------"
        };

        List<LostItem> lostItems = PdfFileParserUtil.parseLinesIntoLostItems(lines);

        assertEquals(2, lostItems.size());

        LostItem firstItem = lostItems.get(0);
        assertEquals("Wallet", firstItem.getItemName());
        assertEquals(1, firstItem.getQuantity());
        assertEquals("Park", firstItem.getPlace());

        LostItem secondItem = lostItems.get(1);
        assertEquals("Phone", secondItem.getItemName());
        assertEquals(2, secondItem.getQuantity());
        assertEquals("Office", secondItem.getPlace());
    }
}