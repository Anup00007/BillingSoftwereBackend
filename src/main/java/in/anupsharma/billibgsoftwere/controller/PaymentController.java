package in.anupsharma.billibgsoftwere.controller;

import com.razorpay.RazorpayException;
import in.anupsharma.billibgsoftwere.io.OrderResponse;
import in.anupsharma.billibgsoftwere.io.PaymentRequest;
import in.anupsharma.billibgsoftwere.io.PaymentVarificationRequest;
import in.anupsharma.billibgsoftwere.io.RazorPayOrderResponse;
import in.anupsharma.billibgsoftwere.service.OrderService;
import in.anupsharma.billibgsoftwere.service.RazorpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private  final RazorpayService razorpayService;
    private final OrderService orderService;
    @PostMapping("create-order")
    @ResponseStatus(HttpStatus.CREATED)
    public RazorPayOrderResponse createRazorPayOrder(@RequestBody PaymentRequest request)throws RazorpayException
    {
     return razorpayService.createOrder(request.getAmount(), request.getCurrency());
    }
    @PostMapping("/varify")
    public OrderResponse varifyPayment(@RequestBody PaymentVarificationRequest request){
return orderService.varifyPayment(request);
    }
}
