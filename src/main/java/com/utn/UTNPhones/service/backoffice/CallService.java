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
            throws LineRequiredException, LineEqualException, CallStartIsRequiredException,
            CallDurationIsRequiredException, LineOriginLowException, LineDestinationLowException {
        if ((newCall.getOrigin() == null) || (newCall.getDestination() == null))
            throw new LineRequiredException();
        if (newCall.getOrigin().getNumber() == newCall.getDestination().getNumber())
            throw new LineEqualException();
        if (newCall.getStart() == null)
            throw new CallStartIsRequiredException();
        if (newCall.getDuration() == null)
            throw new CallDurationIsRequiredException();
        if (!newCall.getOrigin().getStatus())
            throw new LineOriginLowException();
        if (!newCall.getDestination().getStatus())
            throw new LineDestinationLowException();
        Double total = 0.0;
        newCall.setTotal(total);
        return callRepository.save(newCall);
    }

    public Page getAll(Pageable pageable) {
        return callRepository.findAll(pageable);
    }
}
