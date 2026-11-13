package org.example;

import org.example.entity.Order;
import org.example.entity.OrderTask;
import org.example.entity.Product;
import org.example.entity.Warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class LogisticsHubApp {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();

        // Omborni maxsulotlar bn toldiramiz
        warehouse.addProduct(new Product("P1", "Noutbuk", 1200.0, 5));
        warehouse.addProduct(new Product("P2", "Telefon", 800.0, 10));
        warehouse.addProduct(new Product("P3", "Quloqchin", 50.0, 20));

        List<Order> orders = new ArrayList<>();

        // 1-buyurtma (Yetarli)
        orders.add(new Order(101, "Anvar", Map.of("P1", 1, "P3", 2)));
        // 2-buyurtma (Omborda buncha noutbuk yo'q - rad etiladi)
        orders.add(new Order(102, "Malika", Map.of("P1", 10)));
        // 3-buyurtma (Yetarli)
        orders.add(new Order(103, "Jasur", Map.of("P2", 2, "P3", 5)));
        // 4-buyurtma (Yetarli)
        orders.add(new Order(104, "Zuhra", Map.of("P2", 3)));

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        List<Future<String>> futures = new ArrayList<>();

        System.out.println("----- Buyurtmani paralel qayta ishlash boshlandi ------");

        // har bir vazifa uchun callable vazifani bajarib executorga beramiz
        for (Order order : orders) {
            Callable<String> orderTask = new OrderTask(order, warehouse);
            Future<String> submit = executorService.submit(orderTask);
            futures.add(submit);
        }

        // Barcha threadlar ishini tugatishini kutamiz va natijalarni olamiz
        for (Future<String> future : futures) {
            try {
                String s = future.get();
                System.out.println(s);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        executorService.shutdown();

        System.out.println("----- Barcha jaryonlar yakunlandi -----");
    }
}
