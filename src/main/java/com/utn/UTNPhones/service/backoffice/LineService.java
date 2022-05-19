package com.utn.UTNPhones.service.backoffice;

import com.utn.UTNPhones.domain.Line;
import com.utn.UTNPhones.exceptions.LineExistsException;
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


    public Line addLine(Line line)
            throws LineExistsException {
        if (!lineRepository.existsByNumber(line.getNumber())){
            return lineRepository.save(line);
        }
        else throw new LineExistsException();
    }

    public Page<Line> getAll(Pageable pageable) {
        return lineRepository.findAll(pageable);
    }
}