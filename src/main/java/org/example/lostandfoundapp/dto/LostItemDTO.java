package org.example.lostandfoundapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LostItemDTO {
    private String itemName;
    private int quantity;
    private String place;
}