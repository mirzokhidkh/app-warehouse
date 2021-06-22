package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    public Result add(Currency currency) {
        boolean existsByName = currencyRepository.existsByName(currency.getName());
        if (existsByName) {
            return new Result("A currency with such a name already exists", false);
        }
        currencyRepository.save(currency);
        return new Result("Currency saved", true);
    }

    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }


    public Currency getOne(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isEmpty()) {
            return new Currency();
        }
        return optionalCurrency.get();
    }


    public Result edit(Integer id, Currency currency){
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isEmpty()) {
            return new Result("Currency not found",false);
        }

        boolean existsByName = currencyRepository.existsByName(currency.getName());
        if (existsByName) {
            return new Result("A currency with such a name already exists",false);
        }
        Currency editingCurrency = optionalCurrency.get();
        editingCurrency.setName(currency.getName());
        currencyRepository.save(editingCurrency);
        return new Result("Currency edited",true);

    }

    public Result delete(Integer id){
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isEmpty()) {
            return new Result("Currency not found",false);
        }
        currencyRepository.deleteById(id);
        return new Result("Currency deleted",true);
    }
}
