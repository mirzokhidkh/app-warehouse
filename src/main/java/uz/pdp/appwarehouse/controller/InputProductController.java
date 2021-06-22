package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.InputProductService;

import java.util.List;

@RestController
@RequestMapping("/input-product")
public class InputProductController {

    @Autowired
    InputProductService inputProductService;

    @GetMapping
    public List<InputProduct> getAll() {
        return inputProductService.getAll();
    }

    @GetMapping("/by-input-id/{id}")
    public List<InputProduct> getAllByInputId(@PathVariable Integer id) {
        return inputProductService.getAllByInputId(id);
    }

    @GetMapping("/{id}")
    public InputProduct getOne(@PathVariable Integer id) {
        return inputProductService.getOne(id);
    }

    @PostMapping()
    public Result addInput(@RequestBody InputProductDto inputProductDto) {
        return inputProductService.add(inputProductDto);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody InputProductDto inputProductDto) {
        return inputProductService.edit(id, inputProductDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return inputProductService.delete(id);
    }
}
