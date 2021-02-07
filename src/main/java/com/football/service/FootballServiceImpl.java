package com.football.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.football.exception.FootballNotFoundException;
import com.football.model.Football;
import com.football.model.FootballResponse;
import com.football.repository.FootballRepository;

@Service
public class FootballServiceImpl implements FootballService {

	private static final Logger log = LoggerFactory.getLogger(FootballServiceImpl.class);

	private FootballRepository footballRepository;

	@Autowired
	public FootballServiceImpl(FootballRepository footballRepository) {
		this.footballRepository = footballRepository;
	}

	@Override
	public List<Football> getFootballs(String filter, String value) {
		if (Arrays.stream(FilterBy.values()).noneMatch(t -> t.name().equalsIgnoreCase(filter))) {
			throw new FootballNotFoundException("Incorrect filter applied");
		} else {
			FilterBy filterBy = FilterBy.valueOf(filter.toUpperCase());
			switch (filterBy) {
			case COUNTRY:
				log.info("Fetching football ranking by country");
				Predicate<Football> countryPredicate = p -> value.equalsIgnoreCase(p.getCountryName());
				return findProductByPredicate(countryPredicate);
			case LEAGUE:
				log.info("Fetching football ranking by league");
				Predicate<Football> leaguePredicate = p -> value.equalsIgnoreCase(p.getLeagueName());
				return findProductByPredicate(leaguePredicate);
			case TEAM:
				log.info("Fetching football ranking by team");
				Predicate<Football> teamPredicate = p -> value.equalsIgnoreCase(p.getTeamName());
				return findProductByPredicate(teamPredicate);
			default:
				log.info("No filter been passed");
				return null;
			}
		}
	}

	private List<Football> findProductByPredicate(Predicate<Football> predicate) {
		List<Football> collect = footballRepository.findAll().stream().filter(predicate).sorted(Comparator.comparingInt(Football::getOverallPosition))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public Football getFootball(Long id) {
		Football football = footballRepository.findByFootballId(id);
		if (football == null)
			throw new FootballNotFoundException("Invalid Football id!");

		return football;
	}

	@Override
	public FootballResponse addfootball(Football football) {
		footballRepository.save(football);
		return new FootballResponse("Successful", "Football added successfully");
	}

	@Override
	public FootballResponse updateFootball(Football football) {
		footballRepository.save(football);
		return new FootballResponse("Successful", "Football added successfully");
	}

	@Override
	public FootballResponse deleteFootball(Long id) {
		Football football = footballRepository.findByFootballId(id);
		if (football != null)
			footballRepository.delete(football);
		else
			throw new FootballNotFoundException("No record exist with provided id");
		return new FootballResponse("Successful", "Football deleted successfully");
	}

	enum FilterBy {

		COUNTRY("country"), LEAGUE("league"), TEAM("team");
		String value;

		FilterBy(String name) {
			value = name;
		}

		String getValue() {
			return value;
		}
	}
}
