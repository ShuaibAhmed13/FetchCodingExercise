package com.projects.PointsSystem.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Points System class
 * Controller layer that connects the service layer to the API endpoints
 */
@RestController
@RequestMapping
public class PointsSystemController {

    @Autowired
    PointsSystemService pointsSystemService;

    @GetMapping("api/v1/all_transactions")
    public List<Transaction> getTransactionList() {
        return pointsSystemService.getAllTransactions();
    }

    @PostMapping("api/v1/add_transaction")
    public ResponseEntity addTransaction(@RequestBody Transaction transaction) {
        try {
            return new ResponseEntity<String>(pointsSystemService.addTransaction(transaction), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("api/v1/balance")
    public HashMap<String, Integer> getBalance() {
        return pointsSystemService.getBalance();
    }

    @GetMapping("api/v1/spend_points")
    public ResponseEntity spendPoints(@RequestParam Integer points) {
        try {
            return new ResponseEntity<HashMap<String, Integer>>(pointsSystemService.spendPoints(points), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
