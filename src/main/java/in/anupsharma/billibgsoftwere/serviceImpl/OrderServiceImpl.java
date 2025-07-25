package in.anupsharma.billibgsoftwere.serviceImpl;

import in.anupsharma.billibgsoftwere.entity.OrderEntity;
import in.anupsharma.billibgsoftwere.entity.OrderItemEntity;
import in.anupsharma.billibgsoftwere.io.*;
import in.anupsharma.billibgsoftwere.repository.OrderEntityRepository;
import in.anupsharma.billibgsoftwere.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderEntityRepository orderEntityRepository;
    @Override
    public OrderResponse createOrder(OrderRequest request) {
    OrderEntity newOrder= convertToOrderEntity(request);
        PaymentDetails paymentDetails=new PaymentDetails();
        paymentDetails.setStatus(newOrder.getPaymentMethod()== PaymentMethod.CASH?
                PaymentDetails.PaymentStatus.COMPLETED:PaymentDetails.PaymentStatus.PENDING);
        newOrder.setPaymentDetails(paymentDetails);


        List<OrderItemEntity> orderItems = Optional.ofNullable(request.getCardItems())
                                                   .orElse(Collections.emptyList())
                                                   .stream()
                                                   .map(this::convertToOrderItemEntity)
                                                   .collect(Collectors.toList());


        newOrder.setItems(orderItems);
        newOrder = orderEntityRepository.save(newOrder);

        return convertToResponse(newOrder);
    }

    private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemRequest orderItemRequest) {
       return OrderItemEntity.builder().itemId(orderItemRequest.getItemId())
                .name(orderItemRequest.getName())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity()).build();


    }

    private OrderResponse convertToResponse(OrderEntity newOrder) {
     return   OrderResponse.builder().orderId(newOrder.getOrderId())
                .customerName(newOrder.getCustomerName())
                .phoneNumber(newOrder.getPhoneNumber())
                .subTotal(newOrder.getSubTotal())
                .tax(newOrder.getTax())
                .grandTotal(newOrder.getGrandTotal())
             .paymentMethod(newOrder.getPaymentMethod())
             .items(newOrder.getItems().stream().map(this::converToItemResponse).collect(Collectors.toList()))
             .paymentDetails(newOrder.getPaymentDetails()).createdAt(newOrder.getCreatedAt())
                .build();



    }

    private OrderResponse.OrderItemResponse converToItemResponse(OrderItemEntity orderItemEntity) {
      return  OrderResponse.OrderItemResponse.builder().itemId(orderItemEntity.getItemId())
              .name(orderItemEntity.getName()).price(orderItemEntity.getPrice())
              .quantity(orderItemEntity.getQuantity()).build();

    }

    private OrderEntity convertToOrderEntity(OrderRequest request) {
   return     OrderEntity.builder().customerName(request.getCustomerName())
                .phoneNumber(request.getPhoneNumber())
                .subTotal(request.getSubTotal())
                .tax(request.getTax())

                .grandTotal(request.getGrandTotal())
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .build();
    }

    @Override
    public void deleteOrder(String orderId) {
      OrderEntity existingOrder=orderEntityRepository.findByOrderId(orderId).orElseThrow(()->new RuntimeException("order not found"));
      orderEntityRepository.delete(existingOrder);

    }

    @Override
    public List<OrderResponse> getLatestOrders() {
     return orderEntityRepository.findAllByOrderByCreatedAtDesc().stream().map(this::convertToResponse).collect(Collectors.toList());


    }

    @Override
    public OrderResponse varifyPayment(PaymentVarificationRequest request) {
     OrderEntity existingOrder= orderEntityRepository.findByOrderId(request.getOrderId()).orElseThrow(()-> new RuntimeException("order not found"));
   if (!varifyRazorpaySignature(request.getRazorpayOrderId(),request.getRazorpayPymentId()
           ,request.getRazorpaySignatureId()))

   {

       throw  new RuntimeException("payment varification failed");
   }
   PaymentDetails paymentDetails =existingOrder.getPaymentDetails();
paymentDetails.setRazorpayOrderId(request.getRazorpayOrderId());
paymentDetails.setRazorpayPaymentId(request.getRazorpayPymentId());
paymentDetails.setRazorPaySignature(request.getRazorpaySignatureId());
paymentDetails.setStatus(PaymentDetails.PaymentStatus.COMPLETED);

existingOrder=orderEntityRepository.save(existingOrder);
return convertToResponse(existingOrder);
    }

    @Override
    public Double sumSalesByDate(LocalDate date) {
      return  orderEntityRepository.sumSalesByDate(date);
    }

    @Override
    public Long countByOrderDate(LocalDate date) {
      return  orderEntityRepository.countByOrderDate(date);
    }

    @Override
    public List<OrderResponse> findRecentOrders() {
      return  orderEntityRepository.findRecentOrders(PageRequest.of(0,5))
                             .stream().map(orderEntity ->convertToResponse(orderEntity) ).
                collect(Collectors.toList());
    }


    private boolean varifyRazorpaySignature(String razorpayOrderId, String razorpayPymentId, String razorpaySignatureId) {
   return true;


    }
}
