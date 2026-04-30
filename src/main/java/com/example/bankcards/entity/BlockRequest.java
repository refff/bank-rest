package com.example.bankcards.entity;

import com.example.bankcards.entity.enums.BlockRequestStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "card_id", nullable = false, unique = true)
    private Card card;

    private BlockRequestStatus status;
}

