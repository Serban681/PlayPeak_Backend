package com.example.shopbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
@Table(name="product_variance_demand")
public class ProductVarianceDemand {
    @Id
    @Column(name="product_variance_demand_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "product_variance_id")
    private ProductVariance productVariance;

    @OneToMany
    @JoinColumn(name = "product_variance_demand_id")
    private List<DemandPrediction> demandPredictions;
}
