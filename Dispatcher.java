package multithread;

import java.util.LinkedList;
import java.util.Queue;

public class Dispatcher implements DispatcherRunnable {
    private final Queue<TaxisRunnable> TaxisAvailable = new LinkedList<>();
    private final Queue<String> pendingOrders = new LinkedList<>();
    public void addTaxi(TaxisRunnable taxi) {
        TaxisAvailable.add(taxi);
        new Thread(taxi).start();
    }

    @Override
    public synchronized void placeOrder(String curOrder) {
        if (!(TaxisAvailable.isEmpty())) {
            TaxisRunnable assignedTaxi = TaxisAvailable.poll();
            assignedTaxi.placeOrder(curOrder);
        } else {
            System.out.println("No available taxis. Order placed in queue.");
            pendingOrders.add(curOrder);
        }
    }

    @Override
    public synchronized void taxiCompleted(Taxi taxi) {
        TaxisAvailable.add(taxi);

        // Check if there are pending orders
        if (!pendingOrders.isEmpty()) {
            TaxisRunnable nextTaxi = TaxisAvailable.poll();
            String pendingOrder = pendingOrders.poll();
            assert nextTaxi != null;
            nextTaxi.placeOrder(pendingOrder);
        }
    }
}