package in.anupsharma.billibgsoftwere.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private Double amount;
    private String currency;
}
