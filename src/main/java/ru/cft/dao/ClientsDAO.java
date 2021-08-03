package ru.cft.dao;

import ru.cft.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientsDAO {

    List<Client> getAllClients();
    Optional<Client> getClientByPassport(String passport);
    void deleteByPassport(String passport);
    void editClient(Client client);
    void addClient(Client client);
    void deleteClient(Client client);
}
