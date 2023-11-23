package multithread;

public interface DispatcherRunnable {
    void placeOrder(String order);
    void taxiCompleted(Taxi taxi);
}