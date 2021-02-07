package com.football.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "football")
public class Football {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long footballId;

	@Column(name = "productname")
	private String countryId;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "league_name")
	private String leagueName;

	@Column(name = "league_id")
	private String leagueId;

	@Column(name = "team_name")
	private String teamName;

	@Column(name = "team_id")
	private String teamId;

	@Column(name = "overall_league_position")
	private Integer overallPosition;

	public Football() {
		countryId = UUID.randomUUID().toString();
		leagueId = UUID.randomUUID().toString();
		teamId = UUID.randomUUID().toString();
	}

	public Long getId() {
		return footballId;
	}

	public void setId(Long id) {
		this.footballId = id;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public Integer getOverallPosition() {
		return overallPosition;
	}

	public void setOverallPosition(Integer overallPosition) {
		this.overallPosition = overallPosition;
	}

}
