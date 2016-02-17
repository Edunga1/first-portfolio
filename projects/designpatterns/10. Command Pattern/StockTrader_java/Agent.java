package pdu.commandpattern.StockTrader;

import java.util.ArrayList;

class Agent {
    private ArrayList ordersQueue = new ArrayList();

    public Agent() {
    }
    
    public void placeOrder(Order order) {
        ordersQueue.add(order);
        order.execute();
        ordersQueue.remove(order);
    }    
}
