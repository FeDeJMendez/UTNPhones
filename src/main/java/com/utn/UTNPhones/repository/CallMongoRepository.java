package com.utn.UTNPhones.repository;

import com.utn.UTNPhones.domain.CallMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface CallMongoRepository extends MongoRepository<CallMongo, Integer> {
}
