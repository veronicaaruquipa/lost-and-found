package org.example.lostandfoundapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class LostItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Item name is mandatory")
    private String itemName;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Positive(message = "Quantity must be positive")
    private int quantity;

    @NotBlank(message = "Place is mandatory")
    private String place;

    @OneToMany(mappedBy = "lostItem", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Claim> claims = new ArrayList<>();
}