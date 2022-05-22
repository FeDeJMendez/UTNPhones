package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.Call;
import com.utn.UTNPhones.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CallService {
    private final CallRepository callRepository;

    @Autowired
    public CallService(CallRepository callRepository) {
        this.callRepository = callRepository;
    }


    public Call addCall(Call newCall) {
        return callRepository.save(newCall);
    }

    public Page getAll(Pageable pageable) {
        return callRepository.findAll(pageable);
    }
}
