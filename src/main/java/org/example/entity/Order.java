package org.example.entity;

import java.util.List;
import java.util.Map;

public class Order {
    private int orderId;
    private String customerName;
    // Map<Mahsulot_id, soni>
    private Map<String, Integer> items;

    public Order(int orderId, String customerName, Map<String, Integer> items) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = items;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Map<String, Integer> getItems() {
        return items;
    }
}
