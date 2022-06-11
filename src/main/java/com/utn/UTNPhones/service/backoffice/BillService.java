package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.Bill;
import com.utn.UTNPhones.domain.Client;
import com.utn.UTNPhones.exceptions.ClientNotExistsException;
import com.utn.UTNPhones.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillService {
    private final BillRepository billRepository;
    private final ClientService clientService;

    @Autowired
    public BillService(BillRepository billRepository, ClientService clientService) {
        this.billRepository = billRepository;
        this.clientService = clientService;
    }


    public Bill addBill(Bill newBill) {
        newBill.setPaid(false);
        newBill.setExpiration(newBill.getDatecreation().plusDays(15));
        newBill.setCostprice(newBill.getTotalprice() / 1.21);
        return billRepository.save(newBill);
    }

    public Page getAll(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    public List<Bill> getByClientBetweenDates(Integer idClient, LocalDate startDate, LocalDate endDate)
            throws ClientNotExistsException {
        if (!clientService.existsById(idClient))
            throw new ClientNotExistsException();
        return billRepository.findByClientBetweenDates(idClient,startDate,endDate);
    }

    public List<Bill> getByClient(Integer idClient)
            throws ClientNotExistsException {
        Client client = clientService.getById(idClient);
        return billRepository.findByDni(client.getDni());
    }

    public List<Bill> getUnpaidByClient(Integer idClient)
            throws ClientNotExistsException {
        Client client = clientService.getById(idClient);
        return billRepository.findUnpaidByDni(client.getDni());
    }

}
