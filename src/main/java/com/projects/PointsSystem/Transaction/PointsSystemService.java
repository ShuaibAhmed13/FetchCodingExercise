package com.projects.PointsSystem.Transaction;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Points System class (Service Layer)
 * This class has all of the logic that is done 'behind the scenes' for the API
 * The class uses a list for storing transactions rather than connecting to a database
 *      (via a repository class) as specified in the Coding Exercise pdf
 */
@Service
public class PointsSystemService {

    //Transactions stored within a list
    private List<Transaction> transactionList = new ArrayList<>();

    //constructor preloaded with dummy data (from coding exercise pdf)
    public PointsSystemService() throws Exception {
        addTransaction(new Transaction( "DANNON", 300, ZonedDateTime.parse("2020-10-31T10:00:00Z")));
        addTransaction(new Transaction( "MILLER COORS", 10000, ZonedDateTime.parse("2020-11-01T14:00:00Z")));
        addTransaction(new Transaction( "DANNON", -200, ZonedDateTime.parse("2020-10-31T15:00:00Z")));
        addTransaction(new Transaction("UNILEVER", 200,  ZonedDateTime.parse("2020-10-31T11:00:00Z")));
        addTransaction(new Transaction("DANNON", 1000,  ZonedDateTime.parse("2020-11-02T14:00:00Z")));
    }

    //Returns the transaction list
    public List<Transaction> getAllTransactions() {
        return transactionList;
    }

    /**
     * Spend points following the 2 defined rules
     * Ensures points are spent only if the user has a sufficient amount
     * @param pointsSpent
     * @return
     * @throws Exception
     */
    public HashMap<String, Integer> spendPoints(Integer pointsSpent) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();
        if(pointsSpent > getTotalRemainingPoints()) throw new Exception("Insufficient points");
        for(Transaction transaction: transactionList) {
            if(pointsSpent > transaction.getRemainingPoints() && transaction.getRemainingPoints() >= 0) {
                map.put(transaction.getPayer(), map.getOrDefault(transaction.getPayer(), 0) -
                        transaction.getRemainingPoints());
                pointsSpent -= transaction.getRemainingPoints();
                transaction.setRemainingPoints(0);
            } else {
                map.put(transaction.getPayer(), map.getOrDefault(transaction.getPayer(), 0) - pointsSpent);
                transaction.setRemainingPoints(transaction.getRemainingPoints() - pointsSpent);
                pointsSpent = 0;
            }
        }
        return map;
    }


    /**
     * Creates and returns a hashmap of every entry in the transactions list
     * @return
     */
    public HashMap<String, Integer> getBalance() {
        HashMap<String, Integer> map = new HashMap<>();
        for(Transaction transaction: transactionList) {
            map.put(transaction.getPayer(), map.getOrDefault(transaction.getPayer(), 0) + transaction.getRemainingPoints());
        }
        return map;
    }

    /**
     * Add a transaction to the transaction list as long as all values are valid
     * If added transaction is of negative points, it is only added if the total payer amount for that user is not negative
     * @param transaction
     * @return
     * @throws Exception
     */
    public String addTransaction(Transaction transaction) throws Exception {
        if(transaction.getPayer() == null) throw new Exception("Payer cannot be null");
        else if(transaction.getPoints() == null) throw new Exception("Points cannot be null");
        else if(transaction.getTimestamp() == null) throw new Exception("Timestamp cannot be null");
        else {
            if(transaction.getRemainingPoints() < 0) {
                try {
                    return addNegativePoints(transaction);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            } else {
                this.transactionList.add(transaction);
                Collections.sort(transactionList);
                return "Transaction completed";
            }
        }
    }

    /**
     * A helper method used by the spendPoints method
     * This method calculates the total points by all payers for the user
     * @return
     */
    private Integer getTotalRemainingPoints() {
        int remaining = 0;
        for(Transaction transaction: transactionList) remaining += transaction.getRemainingPoints();
        return remaining;
    }

    /**
     * A helper method used by the addTransaction method
     * This method takes in a transaction with negative points and adds up previous positive amounts to ensure that the
     *      remainingPoints value is no longer negative
     * If the remainingPoints are negative and the payer did not provide enough positive points previously, an error is thrown
     * @param transaction
     * @return
     * @throws Exception
     */
    private String addNegativePoints(Transaction transaction) throws Exception {
        int points = transaction.getPoints();
        String payer = transaction.getPayer();
        List<Integer> indices = new ArrayList<>();
        for(int i = transactionList.size()-1; i >= 0; i--) {
            if(transactionList.get(i).getPayer().equals(payer)) {
                if(transactionList.get(i).getRemainingPoints() <= 0) throw new Exception("Error: Payer points go negative");
                points += transactionList.get(i).getRemainingPoints();
                indices.add(i);
                if(points >= 0) break;
            }
        }
        if(points >= 0) {
            transaction.setRemainingPoints(points);
            for (int index: indices) transactionList.get(index).setRemainingPoints(0);
            Collections.sort(transactionList);
            this.transactionList.add(transaction);
            return "Transaction completed";
        }
        throw new Exception("Error: Payer points go negative");
    }
}
