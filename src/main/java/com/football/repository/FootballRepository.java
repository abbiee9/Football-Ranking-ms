package com.football.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.football.model.Football;

@Transactional
public interface FootballRepository extends JpaRepository<Football, Long> {
	
	Football findByFootballId(Long id);

}
