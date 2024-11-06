package org.example.lostandfoundapp.service;

import org.example.lostandfoundapp.model.Claim;
import org.example.lostandfoundapp.model.LostItem;
import org.example.lostandfoundapp.repository.ClaimRepository;
import org.example.lostandfoundapp.repository.LostItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class LostItemServiceTest {

    @Mock
    private LostItemRepository lostItemRepository;

    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private MockUserService mockUserService;

    @InjectMocks
    private LostItemService lostItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAValidLostItem() {
        LostItem lostItem = new LostItem();
        lostItem.setItemName("Laptop");
        lostItem.setQuantity(1);
        lostItem.setPlace("Train Station");

        when(lostItemRepository.save(any(LostItem.class))).thenReturn(lostItem);

        LostItem savedItem = lostItemService.saveLostItem(lostItem);

        assertNotNull(savedItem);
        assertEquals("Laptop", savedItem.getItemName());
    }

    @Test
    void testSaveAnInvalidLostItem() {
        LostItem lostItem = new LostItem();
        lostItem.setQuantity(-1);

        assertThrows(RuntimeException.class, () -> lostItemService.saveLostItem(lostItem));
    }

    @Test
    void testGetAllLostItemsFromAnEmptyDatabase() {
        when(lostItemRepository.findAll()).thenReturn(Collections.emptyList());

        List<LostItem> lostItems = lostItemService.getAllLostItems();

        assertTrue(lostItems.isEmpty());
    }

    @Test
    void testClaimAValidItem() {
        LostItem lostItem = new LostItem();
        lostItem.setItemName("Laptop");
        lostItem.setQuantity(2);

        when(lostItemRepository.findById(anyLong())).thenReturn(Optional.of(lostItem));
        when(mockUserService.getUserName(anyLong())).thenReturn("Alice Johnson");
        when(claimRepository.save(any(Claim.class))).thenReturn(new Claim());

        Claim claim = lostItemService.claimItem(1L, 1001L, 1);

        assertNotNull(claim);
    }

    @Test
    void testGetAllClaimsFromAnEmptyDatabase() {
        when(claimRepository.findAll()).thenReturn(Collections.emptyList());

        List<Claim> claims = lostItemService.getAllClaims();

        assertTrue(claims.isEmpty());
    }

    @Test
    void testClaimItemWithInvalidLostItemId() {
        when(lostItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lostItemService.claimItem(1L, 1001L, 1));
    }

    @Test
    void testClaimItemWithInvalidUserId() {
        LostItem lostItem = new LostItem();
        lostItem.setItemName("Laptop");
        lostItem.setQuantity(2);

        when(lostItemRepository.findById(anyLong())).thenReturn(Optional.of(lostItem));
        when(mockUserService.getUserName(anyLong())).thenReturn("Unknown User");

        assertThrows(RuntimeException.class, () -> lostItemService.claimItem(1L, 9999L, 1));
    }

    @Test
    void testClaimItemWithQuantityExceedsAvailable() {
        LostItem lostItem = new LostItem();
        lostItem.setItemName("Laptop");
        lostItem.setQuantity(1);

        when(lostItemRepository.findById(anyLong())).thenReturn(Optional.of(lostItem));

        assertThrows(RuntimeException.class, () -> lostItemService.claimItem(1L, 1001L, 2));
    }
}
