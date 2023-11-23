package multithread;

import java.util.Random;

public class Taxi implements TaxisRunnable {
    private final String taxiName;
    private String currentOrder;
    private final DispatcherRunnable dispatcher;

    public Taxi(String taxiName, DispatcherRunnable dispatcher) {
        this.taxiName = taxiName;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(new Random().nextInt(5000)); // Simulating taxi movement
                if (currentOrder != null) {
                    System.out.println(taxiName + " completed order: " + currentOrder);
                    currentOrder = null;
                    dispatcher.taxiCompleted(this);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void placeOrder(String order) {
        System.out.println(taxiName + " received order: " + order);
        currentOrder = order;
    }

    @Override
    public void completeOrder() {
        if (currentOrder != null) {
            System.out.println(taxiName + " completed order: " + currentOrder);
            currentOrder = null;
            dispatcher.taxiCompleted(this);
        } else {
            System.out.println(taxiName + " has no active order to complete.");
        }
    }
}