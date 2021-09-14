package com.myapp.spring.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.spring.model.User1;

@Repository
public interface User1Repository extends JpaRepository<User1, Integer> {

	Optional<List<User1>> findByBaggageWeightGreaterThanEqual(Integer baggageWeight);
	Optional<List<User1>> findByPnr(Integer pnr);
	Optional<List<User1>> findByPnrOrEmail(Integer pnr, String email);
	Optional<List<User1>> findByEmail(String email);
	
	
	
}
