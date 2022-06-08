package com.utn.UTNPhones.config;

import com.utn.UTNPhones.domain.Call;
import com.utn.UTNPhones.dto.CallDto;
import com.utn.UTNPhones.utils.URIInterface;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class Conf {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static <T extends URIInterface> URI getLocation (T Obj){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(Obj.getId())
                .toUri();
    }

    ///// Structure for return a lot of data /////
    public static ResponseEntity response(Page page) {
        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }

    ///// Convert a List of Calls to CallsDTO /////
    public static List<CallDto> listCallsToDto (List<Call> list){
        return list.stream().
                map(x -> CallDto.from(x)).
                collect(Collectors.toList());
    }
}
