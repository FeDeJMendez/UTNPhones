package com.utn.UTNPhones.service.roles;

import com.utn.UTNPhones.domain.Call;
import com.utn.UTNPhones.domain.CallMongo;
import com.utn.UTNPhones.domain.Phoneline;
import com.utn.UTNPhones.exceptions.*;
import com.utn.UTNPhones.repository.CallMongoRepository;
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
    private final ClientService clientService;

    private final CallMongoRepository callMongoRepository;

    @Autowired
    public CallService(CallRepository callRepository, PhonelineService phonelineService, ClientService clientService, CallMongoRepository callMongoRepository) {
        this.callRepository = callRepository;
        this.phonelineService = phonelineService;
        this.clientService = clientService;
        this.callMongoRepository = callMongoRepository;
    }


    public Call addCall(Call newCall)
            throws PhonelineRequiredException, PhonelineEqualException, CallStarttimeIsRequiredException,
            CallDurationIsRequiredException, PhonelineOriginLowException, PhonelineDestinationLowException,
            PhonelineNotExistsException {
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

        /*Call addedCall = callRepository.save(newCall);

        CallMongo callMongo = CallMongo.builder()
                .originNumber(addedCall.getOrigin().getNumber())
                .originCityName(addedCall.getOrigin().getCity().getName())
                .destinationNumber(addedCall.getDestination().getNumber())
                .destinationCityName(addedCall.getDestination().getCity().getName())
                .total(addedCall.getTotal())
                .duration(addedCall.getDuration())
                .starttime(addedCall.getStarttime())
                .build();
        callMongoRepository.save(callMongo);*/

        return callRepository.save(newCall);
        /*try {
            return callRepository.save(newCall);
        } catch (GenericJDBCException ex) {
            throw new SQLException(ex.getSQLException());
        }*/
    }

    public Page getAll(Pageable pageable) {
        return callRepository.findAll(pageable);
    }

    public List<Call> getByClientBetweenDates(Integer idClient, LocalDate startDate, LocalDate endDate)
            throws ClientNotExistsException {
        if (!clientService.existsById(idClient))
            throw new ClientNotExistsException();
        return callRepository.findByClientBetweenDates(idClient,startDate,endDate);
    }

    public List<Call> getUnbilledByPhonelineNumber(String number)
            throws PhonelineNotExistsException {
        Phoneline phoneline = phonelineService.getByNumber(number);
        return callRepository.findUnbilledByPhonelineId(phoneline.getId());
    }

}
