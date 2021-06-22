package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @PostMapping
    public Result add(@RequestBody Currency currency) {
        return currencyService.add(currency);
    }

    @GetMapping
    public List<Currency> getAll() {
        return currencyService.getAll();
    }

    @GetMapping("/{id}")
    public Currency getOne(@PathVariable Integer id) {
        return currencyService.getOne(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody Currency currency) {
        return currencyService.edit(id, currency);
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return currencyService.delete(id);
    }
}
