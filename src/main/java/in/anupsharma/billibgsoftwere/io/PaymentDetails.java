package in.anupsharma.billibgsoftwere.io;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetails {
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorPaySignature;
    private PaymentStatus status;
    public enum PaymentStatus{
        PENDING,COMPLETED,FAILED

    }
}
