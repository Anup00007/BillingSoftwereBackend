package in.anupsharma.billibgsoftwere.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentVarificationRequest {


    private String razorpayOrderId;
    private String  razorpayPymentId;
    private String razorpaySignatureId;
    private String orderId;


}
