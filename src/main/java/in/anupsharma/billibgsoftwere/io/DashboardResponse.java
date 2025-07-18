package in.anupsharma.billibgsoftwere.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardResponse {
    private double todaySale;
    private Long todayOrderCount;
    private List<OrderResponse> recentOrders;


}
