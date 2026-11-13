package org.example.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Warehouse {
    private final Map<String, Product> inventory = new ConcurrentHashMap<>();
    private final Lock lock = new ReentrantLock();

    public void addProduct(Product product) {
        inventory.put(product.getId(), product);
    }

    // Buyurtma ajratish va ombordan ayirish
    public boolean processOrderItems(Map<String, Integer> orderItems) {
        lock.lock(); // Race condition uchun
        try {
            // Hamma mahsulot yetarlimi ?
            for (Map.Entry<String, Integer> entry : orderItems.entrySet()) {
                String productId = entry.getKey();
                Integer value = entry.getValue();

                Product product = inventory.get(productId);
                if (product == null || product.getStock() < value) {
                    return false;
                }
            }

            // Ombordan ajratib olish
            for (Map.Entry<String, Integer> entry : orderItems.entrySet()) {
                String productId = entry.getKey();
                Integer value = entry.getValue();

                Product product = inventory.get(productId);
                product.setStock(product.getStock() - value);
            }
            return true;
        } finally {
            lock.unlock();
        }
    }
}
