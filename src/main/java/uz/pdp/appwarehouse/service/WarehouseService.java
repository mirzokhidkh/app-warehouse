package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    public Result add(Warehouse warehouse) {
        boolean existsByName = warehouseRepository.existsByName(warehouse.getName());
        if (existsByName) {
            return new Result("A warehouse with such a name already exists", false);
        }
        warehouseRepository.save(warehouse);
        return new Result("Warehouse saved", true);
    }

    public List<Warehouse> getAll() {
        return warehouseRepository.findAll();
    }


    public Warehouse getOne(Integer id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (optionalWarehouse.isEmpty()) {
            return new Warehouse();
        }
        return optionalWarehouse.get();
    }


    public Result edit(Integer id, Warehouse warehouse){
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (optionalWarehouse.isEmpty()) {
            return new Result("Warehouse not found",false);
        }

        boolean existsByName = warehouseRepository.existsByName(warehouse.getName());
        if (existsByName) {
            return new Result("A warehouse with such a name already exists",false);
        }
        Warehouse editingWarehouse = optionalWarehouse.get();
        editingWarehouse.setName(warehouse.getName());
        warehouseRepository.save(editingWarehouse);
        return new Result("Warehouse  edited",true);

    }

    public Result delete(Integer id){
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (optionalWarehouse.isEmpty()) {
            return new Result("Warehouse not found",false);
        }
        warehouseRepository.deleteById(id);
        return new Result("Warehouse deleted",true);
    }
}
