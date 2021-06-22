package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.entity.Output;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.InputProductRepository;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.repository.OutputProductRepository;
import uz.pdp.appwarehouse.repository.OutputRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DashboardService {
    @Autowired
    InputRepository inputRepository;

    @Autowired
    OutputRepository outputRepository;

    @Autowired
    InputProductRepository inputProductRepository;

    @Autowired
    OutputProductRepository outputProductRepository;

    // Kunlik kirim bo’lgan mahsulotlar (qiymati, umumiy summasi)

    public List<InputProduct> getDailyInputProducts() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(timestamp.getTime());
        Date startOfDay = getStartOfDay(date);
        Date endOfDay = getEndOfDay(date);


        List<Input> inputs = inputRepository.getAllInputIdBetweenDates(startOfDay, endOfDay);
        List<InputProduct> dailyInputProducts = new ArrayList<>();
        for (Input input : inputs) {
            Integer id = input.getId();
            List<InputProduct> inputProducts = inputProductRepository.findAllByInputId(id);
            dailyInputProducts.addAll(inputProducts);
        }

        return dailyInputProducts;
    }


    // Kunlik eng ko’p chiqim qilingan mahsulotlar

    public List<OutputProduct> getDailyTheMostOutputProducts() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(timestamp.getTime());
        Date startOfDay = getStartOfDay(date);
        Date endOfDay = getEndOfDay(date);

        List<Output> outputs = outputRepository.getOutputIdBetweenDates(startOfDay, endOfDay);
        List<OutputProduct> dailyOutputProducts = new ArrayList<>();
        for (Output output : outputs) {
            Integer id = output.getId();
            List<OutputProduct> outputProducts = outputProductRepository.findAllByOutputIdOrderByAmountDesc(id);
            dailyOutputProducts.addAll(outputProducts);
        }

        return dailyOutputProducts;
    }


//    Yaroqlilik muddati yetib qolgan mahsulotlarni olish.

    public List<InputProduct> getAllExpiringInputProduct() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(timestamp.getTime());
        return inputProductRepository.findAllExpiring(date);
    }


/**
 * 11 Notification
 */

//    Mahsulotlarning yaroqlilik muddatining tugashiga ma’lum bir vaqt qolganida
//    tizim ogohlantirishi kerak. Bunda ogohlantiriladigan vaqtni admin kiritib qo’yadi.

    public List<InputProduct> warnToWillBeExpiredProduct(Integer certainDay) {
        LocalDate localDate = LocalDate.now();
        LocalDate certainDate = localDate.plusDays(certainDay);
        java.sql.Date start = java.sql.Date.valueOf(localDate);
        java.sql.Date end = java.sql.Date.valueOf(certainDate);
        return inputProductRepository.findAllByWillBeExpired(start, end);
    }


    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime();
    }

    private Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.set(year, month, day, 23, 59, 59);
        return calendar.getTime();
    }
}
