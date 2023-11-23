package multithread;

public class Main {
    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();

        TaxisRunnable taxi1 = new Taxi("John", dispatcher);
        TaxisRunnable taxi2 = new Taxi("Peter", dispatcher);

        dispatcher.addTaxi(taxi1);
        dispatcher.addTaxi(taxi2);

        dispatcher.placeOrder("Order 1");
        dispatcher.placeOrder("Order 2");
        dispatcher.placeOrder("Order 3");
    }
}