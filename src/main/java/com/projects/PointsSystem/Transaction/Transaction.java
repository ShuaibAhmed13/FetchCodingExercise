package com.projects.PointsSystem.Transaction;

import lombok.Getter;
import lombok.Setter;

/**
 * Transaction Class
 */
@Getter
@Setter
public class Transaction {

    private static Integer id = 0;
    private String payer;
    private Integer points;
    private String timestamp;
    private Integer remainingPoints;

    public Transaction(String payer, Integer points, String timestamp) {
        this.id++;
        this.payer = payer;
        this.points = points;
        this.timestamp = timestamp;
        this.remainingPoints = points;
    }
}
