package com.utn.UTNPhones.controller.backoffice;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Client;
import com.utn.UTNPhones.domain.Line;
import com.utn.UTNPhones.dto.ClientDto;
import com.utn.UTNPhones.dto.LineDto;
import com.utn.UTNPhones.exceptions.LineExistsException;
import com.utn.UTNPhones.service.backoffice.LineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/backoffice/lines")
public class LineController {

    private LineService lineService;
    private ModelMapper modelMapper;

    @Autowired
    public LineController(LineService lineService, ModelMapper modelMapper) {
        this.lineService = lineService;
        this.modelMapper = modelMapper;
    }

    ///// Save new line/////
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity addLine(@RequestBody @Validated final LineDto lineDto)
            throws LineExistsException {
        Line newLine = lineService.addLine(modelMapper.map(lineDto, Line.class));
        return ResponseEntity.created(Conf.getLocation(newLine)).build();
    }

    ///// Get all Clients /////
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<LineDto>> allLines(Pageable pageable){
        Page<Line> page = lineService.getAll(pageable);
        return Conf.Response(page);
    }
}
