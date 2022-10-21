package com.projects.PointsSystem.Transaction;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * Transaction Class
 */
@Getter
@Setter
public class Transaction implements Comparable<Transaction>{

    private static Integer id = 0;
    private String payer;
    private Integer points;
    private ZonedDateTime timestamp;
    private Integer remainingPoints;

    public Transaction(String payer, Integer points, ZonedDateTime timestamp) {
        this.id++;
        this.payer = payer;
        this.points = points;
        this.timestamp = timestamp;
        this.remainingPoints = points;
    }

    @Override
    public int compareTo(Transaction t) {
        return getTimestamp().compareTo(t.getTimestamp());
    }

    @Override
    public String toString() {
        return this.payer + " " + this.remainingPoints + " " + this.timestamp;
    }
}
