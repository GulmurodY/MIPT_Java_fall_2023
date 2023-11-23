package multithread;

public interface TaxisRunnable extends Runnable {
    void placeOrder(String order);
    void completeOrder();
}