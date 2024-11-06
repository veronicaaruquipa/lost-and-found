package org.example.lostandfoundapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClaimTest {

    private Claim claim;
    private LostItem lostItem;
    private Validator validator;

    @BeforeEach
    void setUp() {
        claim = new Claim();
        lostItem = new LostItem();
        lostItem.setId(1L);
        lostItem.setItemName("Wallet");
        lostItem.setQuantity(1);
        lostItem.setPlace("Park");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidClaim() {
        claim.setUserId(100L);
        claim.setQuantity(1);
        claim.setLostItem(lostItem);

        Set<ConstraintViolation<Claim>> violations = validator.validate(claim);
        assertTrue(violations.isEmpty(), "There should be no constraint violations for a valid Claim");
    }

    @Test
    void testNegativeQuantity() {
        claim.setQuantity(-1);

        Set<ConstraintViolation<Claim>> violations = validator.validate(claim);
        assertFalse(violations.isEmpty(), "There should be constraint violations for negative quantity.");
    }

    @Test
    void testNullLostItem() {
        claim.setLostItem(null);
        Set<ConstraintViolation<Claim>> violations = validator.validate(claim);
        assertFalse(violations.isEmpty(), "There should be constraint violations for null lostItem.");
    }

    @Test
    void testNullUserId() {
        claim.setUserId(null);
        Set<ConstraintViolation<Claim>> violations = validator.validate(claim);
        assertFalse(violations.isEmpty(), "There should be constraint violations for null userId.");
    }
}