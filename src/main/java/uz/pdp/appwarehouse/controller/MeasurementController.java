package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.MeasurementService;

import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    @PostMapping
    public Result add(@RequestBody Measurement measurement) {
        return measurementService.add(measurement);
    }

    @GetMapping
    public List<Measurement> getAll() {
        return measurementService.getAll();
    }

    @GetMapping("/{id}")
    public Measurement getOne(@PathVariable Integer id) {
        return measurementService.getOne(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody Measurement measurement) {
        return measurementService.edit(id, measurement);
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return measurementService.delete(id);
    }
}
