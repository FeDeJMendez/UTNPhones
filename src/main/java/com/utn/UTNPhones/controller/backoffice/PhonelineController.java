package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Phoneline;
import com.utn.UTNPhones.dto.PhonelineDto;
import com.utn.UTNPhones.exceptions.PhonelineExistsException;
import com.utn.UTNPhones.service.backoffice.PhonelineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    ///// Save new line/////
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity addLine(@RequestBody @Validated final PhonelineDto phonelineDto)
            throws PhonelineExistsException {
        Phoneline newPhoneline = phonelineService.addLine(modelMapper.map(phonelineDto, Phoneline.class));
        return ResponseEntity.created(Conf.getLocation(newPhoneline)).build();
    }

    ///// Get all Lines /////
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PhonelineDto>> allPhonelines(Pageable pageable){
        Page<Phoneline> page = phonelineService.getAll(pageable);
        return Conf.response(page);
    }
}
