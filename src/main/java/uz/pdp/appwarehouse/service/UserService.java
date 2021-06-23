package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.payload.UserDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    private static long n = 0;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getOne(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new User();
        }
        return optionalUser.get();
    }

    public Result add(UserDto userDto) {
        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new Result("A user with such a phone number already exists", false);
        }


        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setCode(generateCode(n++)); //todo generate code automatically
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new Result("User  saved", true);
    }

    public Result edit(Integer id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new Result("User not found", false);
        }


        boolean existsByPhoneNumber = userRepository.existsByPhoneNumberAndIdNot(userDto.getPhoneNumber(),id);
        if (existsByPhoneNumber) {
            return new Result("A user with such a phone number already exists", false);
        }


        User user = optionalUser.get();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new Result("User edited", true);
    }

    public Result delete(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new Result("User not found", false);
        }
        userRepository.deleteById(id);
        return new Result("User deleted", true);
    }


    public Result addToList(Integer id, Integer wId) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new Result("User not found", false);
        }

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(wId);
        if (optionalWarehouse.isEmpty()) {
            return new Result("Warehouse not found", false);
        }

        User user = optionalUser.get();
        user.addToList(optionalWarehouse.get());
        return new Result("Warehouse saved to List", true);
    }

    private String generateCode(Long n) {
        return String.valueOf((n + 1));
    }

}
