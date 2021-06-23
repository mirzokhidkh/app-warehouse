package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Client;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.payload.ClientDto;
import uz.pdp.appwarehouse.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Result add(ClientDto clientDto) {
        boolean existsByPhoneNumber = clientRepository.existsByPhoneNumber(clientDto.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new Result("A Client with such a phone number already exists", false);
        }

        Client client = new Client();
        client.setName(clientDto.getName());
        client.setPhoneNumber(client.getPhoneNumber());
        clientRepository.save(client);
        return new Result("Client saved", true);
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client getOne(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            return new Client();
        }
        return optionalClient.get();
    }

    public Result edit(Integer id, ClientDto clientDto) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            return new Result("Client not found", false);
        }

        boolean existsByPhoneNumber = clientRepository.existsByPhoneNumberAndIdNot(clientDto.getPhoneNumber(),id);
        if (existsByPhoneNumber) {
            return new Result("A Client with such a phone number already exists", false);
        }

        Client client = optionalClient.get();
        client.setName(clientDto.getName());
        client.setPhoneNumber(client.getPhoneNumber());
        clientRepository.save(client);
        return new Result("Client edited", true);
    }

    public Result delete(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            return new Result("Client not found", false);
        }
        clientRepository.deleteById(id);
        return new Result("Client deleted", true);
    }
}
