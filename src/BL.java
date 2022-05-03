import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.*;

public class BL implements IBL {
    @Override
    public Product getProductById(long productId) {
        return DataSource.allProducts.stream()
                .filter(product -> product.getProductId() == productId)
                .findFirst().orElse(null);
    }

    @Override
    public Order getOrderById(long orderId) {
        return DataSource.allOrders.stream()
                .filter(order -> order.getOrderId() == orderId)
                .findFirst().orElse(null);
    }

    @Override
    public Customer getCustomerById(long customerId) {
        return DataSource.allCustomers.stream()
                .filter(customer -> customer.getId() == customerId)
                .findFirst().orElse(null);
    }


    @Override
    public List<Product> getProducts(ProductCategory cat, double price) {
        return DataSource.allProducts.stream()
                .filter(product -> product.getCategory() == cat && product.getPrice() <= price)
                .sorted(Comparator.comparing(Product::getProductId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> popularCustomers() {
        return DataSource.allCustomers.stream()
                .filter(customer -> customer.getTier() ==3
                        && DataSource.allOrders.stream()
                        .filter(order -> order.getCustomrId() ==customer.getId())
                        .count() >10)
                .sorted(Comparator.comparing(Customer::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getCustomerOrders(long customerId) {
        return DataSource.allOrders.stream()
                .filter(order -> order.getCustomrId() ==customerId)
                .sorted(Comparator.comparing(Order::getOrderId))
                .collect(Collectors.toList());
    }

    @Override
    public long numberOfProductInOrder(long orderId) {
        return DataSource.allOrderProducts.stream()
                .filter(orderProduct -> orderProduct.getOrderId() == orderId)
                .count();
    }

    @Override
    public List<Product> getPopularOrderedProduct(int orderedtimes) {
        return DataSource.allProducts.stream()
                .filter(product -> DataSource.allOrderProducts.stream()
                        .filter(orderProduct -> orderProduct.getProductId() ==product.getProductId())
                        .count() >= orderedtimes)
                .sorted(Comparator.comparing(Product::getProductId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getOrderProducts(long orderId)
    {
        return DataSource.allOrderProducts.stream()
                .filter(orderProduct -> orderProduct.getOrderId() == orderId)
                .map(orderProduct -> getProductById(orderProduct.getProductId()))
                .sorted(Comparator.comparing(Product::getProductId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> getCustomersWhoOrderedProduct(long productId) {
        return DataSource.allOrderProducts.stream()
                .filter(orderProduct -> orderProduct.getProductId() == productId)
                .map(orderProduct ->  getOrderById(orderProduct.getOrderId()).getCustomrId())
                .map(this::getCustomerById)
                .distinct()
                .sorted(Comparator.comparing(Customer::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Product getMaxOrderedProduct() {
        return DataSource.allProducts.stream()
                .max(Comparator.comparing(product -> DataSource.allOrderProducts.stream()
                        .filter(orderProduct -> orderProduct.getProductId()==product.getProductId())
                        .count()))
                .orElse(null);

    }
    @Override
    public double sumOfOrder(long orderID) {
        return DataSource.allOrderProducts.stream()
                .filter(orderProduct -> orderProduct.getOrderId() == orderID)
                .map(orderProduct ->orderProduct.getQuantity() * getProductById(orderProduct.getProductId()).getPrice())
                .reduce(0.0, Double::sum);
    }

    @Override
    public List<Order> getExpensiveOrders(double price) {
        return DataSource.allOrders.stream()
                .filter(order -> sumOfOrder(order.getOrderId()) > price)
                .sorted(Comparator.comparing(Order::getOrderId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> ThreeTierCustomerWithMaxOrders() {
         Map<Long, Long> countMap = DataSource.allOrders.stream().collect(groupingBy(Order::getCustomrId, counting()));

        return DataSource.allCustomers.stream()
                .filter(customer -> customer.getTier()==3
                        && countMap.get(customer.getId()) == countMap.entrySet().stream().max(comparingByValue()).get().getValue())
                .sorted(Comparator.comparing(Customer::getId))
                .collect(Collectors.toList());

    }

}
