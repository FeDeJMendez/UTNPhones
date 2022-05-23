package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.Bill;
import com.utn.UTNPhones.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }


    public Bill addBill(Bill newBill) {
        newBill.setPaid(false);
        newBill.setExpiration(newBill.getDate().plusDays(15));
        newBill.setTotalPrice(newBill.getCostPrice() * 1.21);
        return billRepository.save(newBill);
    }

    public Page getAll(Pageable pageable) {
        return billRepository.findAll(pageable);
    }
}
