package org.example.lostandfoundapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

class LostItemTest {
    private LostItem lostItem;
    private Validator validator;

    @BeforeEach
    void setUp() {
        lostItem = new LostItem();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testBlankItemName() {
        lostItem.setItemName("");
        lostItem.setQuantity(1);
        lostItem.setPlace("Park");

        Set<ConstraintViolation<LostItem>> violations = validator.validate(lostItem);
        assertFalse(violations.isEmpty(), "There should be constraint violations for blank item name.");
    }

    @Test
    void testNegativeQuantity() {
        lostItem.setItemName("Wallet");
        lostItem.setQuantity(-1);
        lostItem.setPlace("Park");

        Set<ConstraintViolation<LostItem>> violations = validator.validate(lostItem);
        assertFalse(violations.isEmpty(), "There should be constraint violations for negative quantity.");
    }

    @Test
    void testBlankPlace() {
        lostItem.setItemName("Wallet");
        lostItem.setQuantity(1);
        lostItem.setPlace("");

        Set<ConstraintViolation<LostItem>> violations = validator.validate(lostItem);
        assertFalse(violations.isEmpty(), "There should be constraint violations for blank place.");
    }
}
