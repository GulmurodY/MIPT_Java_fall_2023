package com.myproject.application;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplicationTests {

    @Test
    void testFilterByAmount() {
        Application.TransactionService transactionService = new Application.TransactionService();
        InputStream inputStream = new ByteArrayInputStream(
                ("""
                        1,2023-12-01,150.00,Payment for groceries,201665
                        2,2023-12-02,55.80,Restaurant bill,438972
                        3,2023-12-03,300.00,Monthly rent,438972
                        4,2023-12-04,45.00,Transportation fee,581324
                        5,2023-12-05,500.00,Salary deposit,970661
                        """).getBytes()
        );

        try (Stream<Application.Transaction> transactions = new Application().readCSV(inputStream)) {
            Stream<Application.Transaction> filteredTransactions = transactionService.filterByAmount(transactions, 200.0);
            List<Application.Transaction> filteredList = filteredTransactions.toList();

            assertEquals(2, filteredList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGroupByType() {
        Application.TransactionService transactionService = new Application.TransactionService();
        InputStream inputStream = new ByteArrayInputStream(
                ("""
                        1,2023-12-01,150.00,Payment for groceries,201665
                        2,2023-12-02,55.80,Restaurant bill,438972
                        3,2023-12-03,300.00,Monthly rent,438972
                        4,2023-12-04,45.00,Transportation fee,581324
                        5,2023-12-05,500.00,Salary deposit,970661
                        """).getBytes()
        );

        try (Stream<Application.Transaction> transactions = new Application().readCSV(inputStream)) {
            Map<String, List<Application.Transaction>> groupedTransactions = transactionService.groupByType(transactions);

            assertEquals(5, groupedTransactions.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAggregateAmountByType() {
        Application.TransactionService transactionService = new Application.TransactionService();
        InputStream inputStream = new ByteArrayInputStream(
                ("""
                        1,2023-12-01,150.00,Payment for groceries,201665
                        2,2023-12-02,55.80,Restaurant bill,438972
                        3,2023-12-03,300.00,Monthly rent,438972
                        4,2023-12-04,45.00,Transportation fee,581324
                        5,2023-12-05,500.00,Salary deposit,970661
                        """).getBytes()
        );

        try (Stream<Application.Transaction> transactions = new Application().readCSV(inputStream)) {
            Map<String, Double> aggregatedAmounts = transactionService.aggregateAmountByType(transactions);

            assertEquals(5, aggregatedAmounts.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCalculateAverageAmountByType() {
        Application.TransactionService transactionService = new Application.TransactionService();

        InputStream inputStream = new ByteArrayInputStream(
                ("""
                        1,2023-12-01,150.00,Payment for groceries,201665
                        2,2023-12-02,55.80,Restaurant bill,438972
                        3,2023-12-03,300.00,Monthly rent,438972
                        4,2023-12-04,45.00,Transportation fee,581324
                        5,2023-12-05,500.00,Salary deposit,970661
                        6,2023-12-05,500.00,Restaurant bill,970661
                        """).getBytes()
        );

        try (Stream<Application.Transaction> transactions = new Application().readCSV(inputStream)) {
            Map<String, Double> averageAmounts = transactionService.calculateAverageAmountByType(transactions);

            assertEquals(5, averageAmounts.size(), "Unexpected size of average amounts map");

            assertEquals(150.00, averageAmounts.get("Payment for groceries"));
            assertEquals(277.90, averageAmounts.get("Restaurant bill"));
            assertEquals(300.00, averageAmounts.get("Monthly rent"));
            assertEquals(45.00, averageAmounts.get("Transportation fee"));
            assertEquals(500.00, averageAmounts.get("Salary deposit"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCountUniqueUsers() {
        Application.TransactionService transactionService = new Application.TransactionService();
        InputStream inputStream = new ByteArrayInputStream(
                ("""
                        1,2023-12-01,150.00,Payment for groceries,201665
                        2,2023-12-02,55.80,Restaurant bill,438972
                        3,2023-12-03,300.00,Monthly rent,438972
                        4,2023-12-04,45.00,Transportation fee,581324
                        5,2023-12-05,500.00,Salary deposit,970661
                        """).getBytes()
        );

        try (Stream<Application.Transaction> transactions = new Application().readCSV(inputStream)) {
            long uniqueUsers = transactionService.countUniqueUsers(transactions);

            assertEquals(4, uniqueUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAsyncProcessing() {
        Application.TransactionService transactionService = new Application.TransactionService();
        InputStream inputStream = new ByteArrayInputStream(
                ("""
                        1,2023-12-01,150.00,Payment for groceries,201665
                        2,2023-12-02,55.80,Restaurant bill,438972
                        3,2023-12-03,300.00,Monthly rent,438972
                        4,2023-12-04,45.00,Transportation fee,581324
                        5,2023-12-05,500.00,Salary deposit,970661
                        """).getBytes()
        );

        try (Stream<Application.Transaction> transactions = new Application().readCSV(inputStream)) {
            CompletableFuture<Void> asyncResult = transactionService.processAsync(transactions);
            asyncResult.get();
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
    }
}
