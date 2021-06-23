package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class OutputService {

    @Autowired
    OutputRepository outputRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CurrencyRepository currencyRepository;


    public List<Output> getAll() {
        return outputRepository.findAll();
    }

    public Output getOne(Integer id) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (optionalOutput.isEmpty()) {
            return new Output();
        }
        return optionalOutput.get();
    }

    public Result add(OutputDto outputDto) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (optionalWarehouse.isEmpty()) {
            return new Result("Such a warehouse doesn't exist", false);
        }
        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (optionalClient.isEmpty()) {
            return new Result("Such a client doesn't exist", false);

        }
        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (optionalCurrency.isEmpty()) {
            return new Result("There is no such currency unit", false);

        }

        boolean existsByFactureNumber = outputRepository.existsByFactureNumber(outputDto.getFactureNumber());
        if (existsByFactureNumber) {
            return new Result("A output with such a facture number already exist", false);
        }

        Output output = new Output();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        output.setDate(outputDto.getDate());
        output.setDate(timestamp);
        output.setFactureNumber(outputDto.getFactureNumber());
        List<Output> outputList = getAll();
        output.setCode(generateCode(outputList.size())); //todo generate code automatically
        output.setWarehouse(optionalWarehouse.get());
        output.setClient(optionalClient.get());
        output.setCurrency(optionalCurrency.get());

        outputRepository.save(output);
        return new Result("Output saved", true);
    }

    public Result edit(Integer id, OutputDto outputDto) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (optionalOutput.isEmpty()) {
            return new Result("Outputt not found", false);
        }

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (optionalWarehouse.isEmpty()) {
            return new Result("Such a warehouse doesn't exist", false);
        }
        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (optionalClient.isEmpty()) {
            return new Result("Such a client doesn't exist", false);

        }
        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (optionalCurrency.isEmpty()) {
            return new Result("There is no such currency unit", false);

        }

        boolean existsByFactureNumber = outputRepository.existsByFactureNumberAndIdNot(outputDto.getFactureNumber(),id);
        if (existsByFactureNumber) {
            return new Result("A output with such a facture number already exist", false);
        }

        Output output = optionalOutput.get();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        output.setDate(outputDto.getDate());
        output.setDate(timestamp);
        output.setFactureNumber(outputDto.getFactureNumber());
        output.setWarehouse(optionalWarehouse.get());
        output.setClient(optionalClient.get());
        output.setCurrency(optionalCurrency.get());

        outputRepository.save(output);
        return new Result("Output edited", true);
    }

    public Result delete(Integer id) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (optionalOutput.isEmpty()) {
            return new Result("Output not found", false);
        }
        outputRepository.deleteById(id);
        return new Result("Output deleted", true);
    }

    private String generateCode(Integer n) {
        return String.valueOf((n + 1));
    }

}
