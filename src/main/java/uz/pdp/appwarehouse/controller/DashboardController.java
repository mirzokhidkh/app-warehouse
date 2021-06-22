package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.DashboardService;
import uz.pdp.appwarehouse.service.InputProductService;
import uz.pdp.appwarehouse.service.InputService;
import uz.pdp.appwarehouse.service.OutputService;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    // Kunlik kirim bo’lgan mahsulotlar (qiymati, umumiy summasi)

    @GetMapping("/daily-input-product")
    public List<InputProduct> getDailyInputProduct(){
        return dashboardService.getDailyInputProducts();
    }


    // Kunlik eng ko’p chiqim qilingan mahsulotlar

    @GetMapping("/daily-the-most-out-product")
    public List<OutputProduct> getDailyOutputProduct(){
        return dashboardService.getDailyTheMostOutputProducts();
    }



    //    Yaroqlilik muddati yetib qolgan mahsulotlarni olish.

    @GetMapping("/expiring-input-product")
    public List<InputProduct> getAllExpiringInputProduct(){
        return dashboardService.getAllExpiringInputProduct();
    }
}
