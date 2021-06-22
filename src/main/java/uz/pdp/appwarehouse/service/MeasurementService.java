package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    @Autowired
    MeasurementRepository measurementRepository;

    public Result add(Measurement measurement) {
        boolean existsByName = measurementRepository.existsByName(measurement.getName());
        if (existsByName) {
            return new Result("Such a unit of measurement already exists", false);
        }
        measurementRepository.save(measurement);
        return new Result("Measurement saved", true);
    }

    public List<Measurement> getAll() {
        return measurementRepository.findAll();
    }


    public Measurement getOne(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (optionalMeasurement.isEmpty()) {
            return new Measurement();
        }
        return optionalMeasurement.get();
    }


    public Result edit(Integer id, Measurement measurement){
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (optionalMeasurement.isEmpty()) {
            return new Result("Measurement not found",false);
        }

        boolean existsByName = measurementRepository.existsByName(measurement.getName());
        if (existsByName) {
            return new Result("Such a unit of measurement already exists",false);
        }
        Measurement editingMeasurement = optionalMeasurement.get();
        editingMeasurement.setName(measurement.getName());
        measurementRepository.save(editingMeasurement);
        return new Result("Measurement  edited",true);

    }

    public Result delete(Integer id){
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (optionalMeasurement.isEmpty()) {
            return new Result("Measurement not found",false);
        }
        measurementRepository.deleteById(id);
        return new Result("Measurement deleted",true);
    }
}
