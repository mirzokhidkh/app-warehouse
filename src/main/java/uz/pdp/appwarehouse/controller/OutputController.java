package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Output;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.OutputService;

import java.util.List;

@RestController
@RequestMapping("/output")
public class OutputController {

    @Autowired
    OutputService outputService;

    @GetMapping
    public List<Output> getAll() {
        return outputService.getAll();
    }

    @GetMapping("/{id}")
    public Output getOne(@PathVariable Integer id) {
        return outputService.getOne(id);
    }

    @PostMapping()
    public Result addOutput(@RequestBody OutputDto OutputDto) {
        return outputService.add(OutputDto);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody OutputDto outputDto) {
        return outputService.edit(id, outputDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return outputService.delete(id);
    }
}
