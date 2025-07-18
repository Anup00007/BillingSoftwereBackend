package in.anupsharma.billibgsoftwere.service;

import com.razorpay.RazorpayException;
import in.anupsharma.billibgsoftwere.io.RazorPayOrderResponse;

public interface RazorpayService {
  RazorPayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;
}
