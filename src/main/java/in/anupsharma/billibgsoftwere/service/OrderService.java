package in.anupsharma.billibgsoftwere.service;

import in.anupsharma.billibgsoftwere.entity.OrderEntity;
import in.anupsharma.billibgsoftwere.io.OrderRequest;
import in.anupsharma.billibgsoftwere.io.OrderResponse;
import in.anupsharma.billibgsoftwere.io.PaymentVarificationRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    void deleteOrder(String orderId);

    List<OrderResponse> getLatestOrders();

    OrderResponse varifyPayment(PaymentVarificationRequest request);
Double sumSalesByDate(LocalDate date);
Long countByOrderDate(LocalDate date);
List<OrderResponse>findRecentOrders();
}