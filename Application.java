package com.myproject.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        return executor;
    }

    // Reading data from CSV file
    public Stream<Transaction> readCSV(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        return reader.lines()
                .map(line -> {
                    // converting the line to a Transaction using an anonymous method
                    String[] fields = line.split(",");

                    long transactionId = Long.parseLong(fields[0]);
                    String date = fields[1];
                    double amount = Double.parseDouble(fields[2]);
                    String description = fields[3];
                    long userId = Long.parseLong(fields[4]);

                    return new Transaction(transactionId, date, amount, description, userId);
                });
    }

    public static class Transaction {
        private final long transactionId;
        private final String date;
        private final double amount;
        private final String description;
        private final long userId;

        public Transaction(long transactionId, String date, double amount, String description, long userId) {
            this.transactionId = transactionId;
            this.date = date;
            this.amount = amount;
            this.description = description;
            this.userId = userId;
        }

        public long getTransactionId() {
            return transactionId;
        }

    }
    public static class TransactionService {
        public Stream<Transaction> filterByAmount(Stream<Transaction> transactions, double threshold) {
            return transactions.filter(transaction -> transaction.amount >= threshold);
        }

        public Map<String, List<Transaction>> groupByType(Stream<Transaction> transactions) {
            return transactions.collect(Collectors.groupingBy(transaction -> transaction.description));
        }

        public Map<String, Double> aggregateAmountByType(Stream<Transaction> transactions) {
            return transactions.collect(Collectors.groupingBy(
                    transaction -> transaction.description,
                    Collectors.summingDouble(transaction -> transaction.amount)
            ));
        }

        public Map<String, Double> calculateAverageAmountByType(Stream<Transaction> transactions) {
            return transactions.collect(Collectors.groupingBy(
                    transaction -> transaction.description,
                    Collectors.averagingDouble(transaction -> transaction.amount)
            ));
        }

        public long countUniqueUsers(Stream<Transaction> transactions) {
            return transactions.map(transaction -> transaction.userId).distinct().count();
        }

        public CompletableFuture<Void> processAsync(Stream<Transaction> transactions) {
            List<CompletableFuture<Void>> futures = transactions.map(this::processTransactionAsync)
                    .toList();

            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        }

        private CompletableFuture<Void> processTransactionAsync(Transaction transaction) {
            return CompletableFuture.runAsync(() -> {
                System.out.println("Processing transaction " + transaction.getTransactionId() + "...");
                // Simulate some time-consuming operation (e.g., database query)
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Transaction " + transaction.getTransactionId() + " processed.");
            });
        }
    }
}
