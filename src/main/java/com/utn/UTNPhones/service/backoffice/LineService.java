package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.Line;
import com.utn.UTNPhones.exceptions.LineExistsException;
import com.utn.UTNPhones.exceptions.LineNoExistsException;
import com.utn.UTNPhones.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LineService {
    private LineRepository lineRepository;

    @Autowired
    public LineService(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }


    public Line addLine(Line newLine)
            throws LineExistsException {
        if (!lineRepository.existsByNumber(newLine.getNumber())){
            newLine.setStatus(false);
            return lineRepository.save(newLine);
        }
        else throw new LineExistsException();
    }

    public Page<Line> getAll(Pageable pageable) {
        return lineRepository.findAll(pageable);
    }

    public Line getById (Integer id)
            throws LineNoExistsException {
        return lineRepository.findById(id).orElseThrow(LineNoExistsException::new);
    }

    public Line getByNumber (String number)
            throws LineNoExistsException {
        return lineRepository.findByNumber(number)
                .orElseThrow(LineNoExistsException::new);
    }
}
