package in.anupsharma.billibgsoftwere.controller;

import in.anupsharma.billibgsoftwere.io.DashboardResponse;
import in.anupsharma.billibgsoftwere.io.OrderResponse;
import in.anupsharma.billibgsoftwere.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")

public class DashboardController {

    private  final OrderService orderService;
    
@GetMapping
  public DashboardResponse getDashboardDate(){
  LocalDate today=LocalDate.now();
 Double todaySale=orderService.sumSalesByDate(today);
Long todayorderCount= orderService.countByOrderDate(today);
List<OrderResponse> recentOrders=orderService.findRecentOrders();
return new DashboardResponse(
     todaySale != null ? todaySale:0.0 ,
        todayorderCount!=null?todayorderCount: 0,
     recentOrders


);
    }
}
