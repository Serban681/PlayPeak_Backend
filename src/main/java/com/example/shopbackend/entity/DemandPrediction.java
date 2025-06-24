package com.example.shopbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@ToString
@Table(name="demand_prediction")
public class DemandPrediction {
    @Id
    @Column(name="demand_prediction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    @Column(name="no_of_orders")
    private float noOfOrders;

    @Column(name="is_predicted")
    private boolean isPredicted;
}
