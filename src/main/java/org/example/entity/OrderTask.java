package org.example.entity;

import java.util.concurrent.Callable;

public class OrderTask implements Callable<String> {
    private final Order order;
    private final Warehouse warehouse;

    public OrderTask(Order order, Warehouse warehouse) {
        this.order = order;
        this.warehouse = warehouse;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(500);

        boolean isSuccessful = warehouse.processOrderItems(order.getItems());

        if (isSuccessful) {
            return "✅ Buyurtma #" + order.getOrderId() + " (" + order.getCustomerName() + ") muvaffaqiyatli bajarildi!";
        } else {
            return "❌ Buyurtma #" + order.getOrderId() + " (" + order.getCustomerName() + ") rad etildi: Omborda mahsulot yetarli emas!";
        }
    }
}
