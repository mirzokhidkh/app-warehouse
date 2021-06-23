package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class InputService {

    @Autowired
    InputRepository inputRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    CurrencyRepository currencyRepository;


    public List<Input> getAll() {
        return inputRepository.findAll();
    }

    public Input getOne(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isEmpty()) {
            return new Input();
        }
        return optionalInput.get();
    }

    public Result add(InputDto inputDto) {

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (optionalWarehouse.isEmpty()) {
            return new Result("Such a warehouse doesn't exist", false);
        }
        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (optionalSupplier.isEmpty()) {
            return new Result("Such a supplier doesn't exist", false);

        }
        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (optionalCurrency.isEmpty()) {
            return new Result("There is no such currency unit", false);

        }

        boolean existsByFactureNumber = inputRepository.existsByFactureNumber(inputDto.getFactureNumber());
        if (existsByFactureNumber) {
            return new Result("A input with such a facture number already exist", false);
        }

        Input input = new Input();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        input.setDate(timestamp);
//        input.setDate(inputDto.getDate());
        input.setFactureNumber(inputDto.getFactureNumber());
        List<Input> inputList = getAll();
        input.setCode(generateCode(inputList.size())); //todo generate code automatically
        input.setWarehouse(optionalWarehouse.get());
        input.setSupplier(optionalSupplier.get());
        input.setCurrency(optionalCurrency.get());

        inputRepository.save(input);
        return new Result("Input saved", true);
    }

    public Result edit(Integer id, InputDto inputDto) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isEmpty()) {
            return new Result("Input not found", false);
        }

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (optionalWarehouse.isEmpty()) {
            return new Result("Such a warehouse doesn't exist", false);
        }
        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (optionalSupplier.isEmpty()) {
            return new Result("Such a supplier doesn't exist", false);

        }
        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (optionalCurrency.isEmpty()) {
            return new Result("There is no such currency unit", false);

        }

        Input input = optionalInput.get();

        boolean existsByFactureNumber = inputRepository.existsByFactureNumberAndIdNot(inputDto.getFactureNumber(), input.getId());
        if (existsByFactureNumber) {
            return new Result("A input with such a facture number already exist", false);
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        input.setDate(inputDto.getDate());
        input.setDate(timestamp);
        input.setFactureNumber(inputDto.getFactureNumber());
        input.setWarehouse(optionalWarehouse.get());
        input.setSupplier(optionalSupplier.get());
        input.setCurrency(optionalCurrency.get());

        inputRepository.save(input);
        return new Result("Input edited", true);
    }

    public Result delete(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isEmpty()) {
            return new Result("Input not found", false);
        }
        inputRepository.deleteById(id);
        return new Result("Input deleted", true);
    }

    private String generateCode(Integer n) {
        return String.valueOf((n + 1));
    }

}
