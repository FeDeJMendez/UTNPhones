package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.Call;
import com.utn.UTNPhones.exceptions.*;
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


    public Call addCall(Call newCall)
            throws PhonelineRequiredException, PhonelineEqualException, CallStarttimeIsRequiredException,
            CallDurationIsRequiredException, PhonelineOriginLowException, PhonelineDestinationLowException {
        if ((newCall.getOrigin() == null) || (newCall.getDestination() == null))
            throw new PhonelineRequiredException();
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
}
