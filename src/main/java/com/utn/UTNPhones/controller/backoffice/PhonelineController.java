package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Phoneline;
import com.utn.UTNPhones.dto.PhonelineDto;
import com.utn.UTNPhones.exceptions.PhonelineAssociatedCallsException;
import com.utn.UTNPhones.exceptions.PhonelineExistsException;
import com.utn.UTNPhones.exceptions.PhonelineLengthException;
import com.utn.UTNPhones.exceptions.PhonelineNotExistsException;
import com.utn.UTNPhones.service.roles.PhonelineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/backoffice/phonelines")
public class PhonelineController {

    private PhonelineService phonelineService;
    private ModelMapper modelMapper;

    @Autowired
    public PhonelineController(PhonelineService phonelineService, ModelMapper modelMapper) {
        this.phonelineService = phonelineService;
        this.modelMapper = modelMapper;
    }

    ///// Save new Line/////
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity addLine(@RequestBody @Validated final PhonelineDto phonelineDto)
            throws PhonelineExistsException, SQLException, PhonelineLengthException {
        Phoneline newPhoneline = phonelineService.addPhoneline(modelMapper.map(phonelineDto, Phoneline.class));
        return ResponseEntity.created(Conf.getLocation(newPhoneline)).build();
    }

    ///// Get all Lines /////
    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<List<PhonelineDto>> allPhonelines(Pageable pageable){
        Page<Phoneline> page = phonelineService.getAll(pageable);
        return Conf.response(page);
    }

    ///// Delete Line by Id /////
    @DeleteMapping(value = "/{number}", produces = "application/json")
    public ResponseEntity deletePhonelineById(@PathVariable(value = "number") String number)
            throws PhonelineNotExistsException, PhonelineAssociatedCallsException {
        phonelineService.deletePhonelineByNumber(number);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    ///// Low Line /////
    @PutMapping(path = "/low/{number}")
    public ResponseEntity lowPhoneline (@PathVariable String number)
            throws PhonelineNotExistsException {
        phonelineService.lowPhoneline(number);
        return ResponseEntity.ok().build();
    }

    ///// High Line /////
    @PutMapping(path = "/high/{number}")
    public ResponseEntity highPhoneline (@PathVariable String number)
            throws PhonelineNotExistsException {
        phonelineService.highPhoneline(number);
        return ResponseEntity.ok().build();
    }

    /*///// Low Line With Data in Body /////
    @PutMapping(path = "/low")
    public ResponseEntity lowPhoneline (@RequestBody PhonelineDto phonelineDto)
            throws PhonelineNoExistsException, PhonelineBadDataException {
        if ((phonelineDto.getNumber() != null)) {
            Phoneline phoneline = phonelineService.getByNumber(phonelineDto.getNumber());
            phonelineService.lowPhoneline(phoneline.getId());
        }
        else if ((phonelineDto.getId() != null)) {
            phonelineService.lowPhoneline(phonelineDto.getId());
        }
        else throw new PhonelineBadDataException();

        return ResponseEntity.ok().build();
    }

    ///// High Line With Data in Body /////
    @PutMapping(path = "/high")
    public ResponseEntity highLine (@RequestBody PhonelineDto phonelineDto)
            throws PhonelineNoExistsException, PhonelineBadDataException {
        if ((phonelineDto.getNumber() != null)) {
            Phoneline phoneline = phonelineService.getByNumber(phonelineDto.getNumber());
            phonelineService.highPhoneline(phoneline.getId());
        }
        else if ((phonelineDto.getId() != null)) {
            phonelineService.highPhoneline(phonelineDto.getId());
        }
        else throw new PhonelineBadDataException();

        return ResponseEntity.ok().build();
    }*/

}
