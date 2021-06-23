package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.SupplierDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.payload.SupplierDto;
import uz.pdp.appwarehouse.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    public Result add(SupplierDto supplierDto) {
        boolean existsByPhoneNumber = supplierRepository.existsByPhoneNumber(supplierDto.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new Result("A supplier with such a phone number already exists", false);
        }

        Supplier supplier = new Supplier();
        supplier.setName(supplierDto.getName());
        supplier.setPhoneNumber(supplierDto.getPhoneNumber());
        supplierRepository.save(supplier);
        return new Result("Supplier saved", true);
    }

    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    public Supplier getOne(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isEmpty()) {
            return new Supplier();
        }
        return optionalSupplier.get();
    }

    public Result edit(Integer id, SupplierDto supplierDto) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isEmpty()) {
            return new Result("Supplier not found", false);
        }

        boolean existsByPhoneNumber = supplierRepository.existsByPhoneNumberAndIdNot(supplierDto.getPhoneNumber(),id);
        if (existsByPhoneNumber) {
            return new Result("A supplier with such a phone number already exists", false);
        }

        Supplier supplier = optionalSupplier.get();
        supplier.setName(supplierDto.getName());
        supplier.setPhoneNumber(supplierDto.getPhoneNumber());
        supplierRepository.save(supplier);
        return new Result("Supplier edited", true);
    }

    public Result delete(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isEmpty()) {
            return new Result("Supplier not found", false);
        }
        supplierRepository.deleteById(id);
        return new Result("Supplier deleted", true);
    }
}
