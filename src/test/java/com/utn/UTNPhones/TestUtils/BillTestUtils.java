package com.utn.UTNPhones.TestUtils;

import com.utn.UTNPhones.config.Conf;
import com.utn.UTNPhones.domain.Bill;
import com.utn.UTNPhones.dto.BillDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BillTestUtils {

    public static List<Bill> getBillList() {
        return List.of(
                Bill.builder()
                        .id(1)
                        .dni(11111111)
                        .number("2235429130")
                        .totalcalls(11)
                        .costprice(100.00)
                        .totalprice(121.00)
                        .datecreation(LocalDate.of(2022,03,01))
                        .expiration(LocalDate.of(2022,03,15))
                        .paid(false)
                        .build(),
                Bill.builder()
                        .id(2)
                        .dni(11111111)
                        .number("2235429131")
                        .totalcalls(22)
                        .costprice(200.00)
                        .totalprice(242.00)
                        .datecreation(LocalDate.of(2022,03,01))
                        .expiration(LocalDate.of(2022,03,15))
                        .paid(false)
                        .build()
        );
    }

    public static List<BillDto> getBillDtoList(){
        return Conf.listBillsToDto(getBillList());
        /*return getBillList().stream().
                map(x -> BillDto.from(x)).
                collect(Collectors.toList());*/
    }
}
