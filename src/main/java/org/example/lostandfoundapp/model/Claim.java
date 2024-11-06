package org.example.lostandfoundapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Getter
@Setter
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID is mandatory")
    private Long userId;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Positive(message = "Quantity must be positive")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "lost_item_id", nullable = false)
    @JsonBackReference
    private LostItem lostItem;
}