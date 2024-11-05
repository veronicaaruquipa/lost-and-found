package org.example.lostandfoundapp.service;

import lombok.extern.slf4j.Slf4j;
import org.example.lostandfoundapp.model.Claim;
import org.example.lostandfoundapp.model.LostItem;
import org.example.lostandfoundapp.repository.ClaimRepository;
import org.example.lostandfoundapp.repository.LostItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class LostItemService {
    private final LostItemRepository lostItemRepository;
    private final ClaimRepository claimRepository;
    private final MockUserService mockUserService;

    public LostItemService(LostItemRepository lostItemRepository, ClaimRepository claimRepository, MockUserService mockUserService) {
        this.lostItemRepository = lostItemRepository;
        this.claimRepository = claimRepository;
        this.mockUserService = mockUserService;
    }

    public LostItem saveLostItem(LostItem lostItem) {
        return lostItemRepository.save(lostItem);
    }

    public List<LostItem> getAllLostItems() {
        return lostItemRepository.findAll();
    }

    public Claim claimItem(Long lostItemId, Long userId, int quantity) {
        LostItem lostItem = lostItemRepository.findById(lostItemId).orElseThrow(() -> new RuntimeException("Item not found"));

        String userName = mockUserService.getUserName(userId);
        Claim claim = new Claim();

        claim.setUserId(userId);
        claim.setQuantity(quantity);
        claim.setLostItem(lostItem);

        claim = claimRepository.save(claim);

        log.info(String.format("User %s (ID: %d) claimed %d %s(s).", userName, userId, quantity, lostItem.getItemName()));

        return claim;
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }
}