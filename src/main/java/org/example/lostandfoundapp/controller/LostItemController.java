package org.example.lostandfoundapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.lostandfoundapp.dto.LostItemDTO;
import org.example.lostandfoundapp.model.Claim;
import org.example.lostandfoundapp.model.LostItem;
import org.example.lostandfoundapp.service.LostItemService;
import org.example.lostandfoundapp.util.PdfFileParserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/lost-items")
@Slf4j
public class LostItemController {
    private final LostItemService lostItemService;

    public LostItemController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        List<LostItem> items = readLostItemsFromPDF(file);
        items.forEach(lostItemService::saveLostItem);

        log.info("Lost items were successfully stored in the database.");

        return ResponseEntity.ok("File uploaded and items saved successfully.");
    }

    @PostMapping
    public LostItem createLostItem(@RequestBody LostItemDTO lostItemDTO) {
        LostItem lostItem = new LostItem();
        // Map lost item fields from DTO to entity
        lostItem.setItemName(lostItemDTO.getItemName());
        lostItem.setQuantity(lostItemDTO.getQuantity());
        lostItem.setPlace(lostItemDTO.getPlace());
        return lostItemService.saveLostItem(lostItem);
    }

    @GetMapping()
    public List<LostItem> getAllLostItems() {
        log.info("All lost items are being retrieved from the database.");
        return lostItemService.getAllLostItems();
    }

    @PostMapping("/claim")
    public ResponseEntity<Claim> claimItem(@RequestParam Long lostItemId, @RequestParam Long userId, @RequestParam int quantity) {
        Claim claim = lostItemService.claimItem(lostItemId, userId, quantity);
        return ResponseEntity.ok(claim);
    }

    @GetMapping("/claims")
    public ResponseEntity<List<Claim>> getAllClaims() {
        List<Claim> claims = lostItemService.getAllClaims();

        log.info("Claimed items are retrieved from the database.");

        return ResponseEntity.ok(claims);
    }

    private List<LostItem> readLostItemsFromPDF(MultipartFile file) {
        return PdfFileParserUtil.parseFile(file);
    }
}