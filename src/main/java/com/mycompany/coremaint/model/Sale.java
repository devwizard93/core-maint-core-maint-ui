package com.mycompany.coremaint.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

@Entity
@Data
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private LocalDateTime startDate;
    @ElementCollection
    private List<String> productList;
    private Double price;

    @PrePersist
    public void prePersist() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Santiago"));
    }


}
