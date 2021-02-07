package com.football.service;

import java.util.List;

import com.football.model.Football;
import com.football.model.FootballResponse;

public interface FootballService {

	List<Football> getFootballs(String filter, String value);

	Football getFootball(Long id);

	FootballResponse addfootball(Football football);

	FootballResponse updateFootball(Football football);

	FootballResponse deleteFootball(Long id);

}
