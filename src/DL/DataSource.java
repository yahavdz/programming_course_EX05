package DL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DataSource {

    public static List<Customer> allCustomers;
    public static List<Order> allOrders;
    public static List<Product> allProducts;
    public static List<OrderProduct> allOrderProducts;
    public static String basePath = "C:\\Users\\USER\\Desktop\\YAHAV\\design-and-programming\\LABS\\EX05\\files";
    public static String customersPath = basePath +"customers.txt";
    public static String ordersPath = basePath +"orders.txt";
    public static String productsPath = basePath +"products.txt";
    public static String orderProductPath = basePath +"orderProduct.txt";

    static
    {
        try {
            allCustomers = readCustomersfromFile();
            allOrders = readOrdersfromFile();
            allProducts = readProductsfromFile();
            allOrderProducts = readOrderProductsfromFile();
        } catch (IOException e) { e.printStackTrace(); }
    }
    public static List<Customer> readCustomersfromFile() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(customersPath))) {

            stream.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Order> readOrdersfromFile() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(ordersPath))) {

            stream.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Product> readProductsfromFile() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(productsPath))) {

            stream.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<OrderProduct> readOrderProductsfromFile() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(orderProductPath))) {

            stream.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


