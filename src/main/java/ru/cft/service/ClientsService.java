package ru.cft.service;

import ru.cft.dao.ClientsDAO;
import ru.cft.dao.FactoryDAO;
import ru.cft.model.Client;

import java.util.List;
import java.util.Optional;

public class ClientsService {

    public List<Client> getAllClients() {
        List<Client> clients = getClientsDAO().getAllClients();
        return clients;
    }

    public Optional<Client> getClientByPassport(String passport) {
        Optional<Client> client = getClientsDAO().getClientByPassport(passport);
        return client;
    }

    public void deleteClientByPassport(String passport) {
        getClientsDAO().deleteByPassport(passport);
    }

    public void editClient(Client client) {
        getClientsDAO().editClient(client);
    }

    public void addNewClient(Client client) {
        getClientsDAO().addClient(client);
    }

    public void deleteClient(Client client) {
        getClientsDAO().deleteClient(client);
    }

    private ClientsDAO getClientsDAO() {
        return FactoryDAO.getInstance().getClientsDAO();
    }
}
