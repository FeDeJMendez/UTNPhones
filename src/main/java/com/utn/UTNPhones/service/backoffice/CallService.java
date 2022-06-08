package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.Call;
import com.utn.UTNPhones.exceptions.*;
import com.utn.UTNPhones.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CallService {
    private final CallRepository callRepository;
    private final PhonelineService phonelineService;

    @Autowired
    public CallService(CallRepository callRepository, PhonelineService phonelineService) {
        this.callRepository = callRepository;
        this.phonelineService = phonelineService;
    }


    public Call addCall(Call newCall)
            throws PhonelineRequiredException, PhonelineEqualException, CallStarttimeIsRequiredException,
            CallDurationIsRequiredException, PhonelineOriginLowException, PhonelineDestinationLowException, PhonelineNoExistsException {
        if ((newCall.getOrigin() == null) || (newCall.getDestination() == null))
            throw new PhonelineRequiredException();
        newCall.setOrigin(phonelineService.getById(newCall.getOrigin().getId()));
        newCall.setDestination(phonelineService.getById(newCall.getDestination().getId()));
        if (newCall.getOrigin().getNumber() == newCall.getDestination().getNumber())
            throw new PhonelineEqualException();
        if (newCall.getStarttime() == null)
            throw new CallStarttimeIsRequiredException();
        if (newCall.getDuration() == null)
            throw new CallDurationIsRequiredException();
        if (!newCall.getOrigin().getStatus())
            throw new PhonelineOriginLowException();
        if (!newCall.getDestination().getStatus())
            throw new PhonelineDestinationLowException();
        Double total = 0.0;
        newCall.setTotal(total);
        return callRepository.save(newCall);
    }

    public Page getAll(Pageable pageable) {
        return callRepository.findAll(pageable);
    }

    public List<Call> getByClientBetweenDates(Integer idClient, LocalDate startDate, LocalDate endDate) {
        return callRepository.findByClientBetweenDates(idClient,startDate,endDate);
    }
}
