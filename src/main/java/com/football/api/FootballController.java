package com.football.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.football.exception.FootballNotFoundException;
import com.football.model.Football;
import com.football.model.FootballResponse;
import com.football.service.FootballService;

@RequestMapping("/api/ranking")
@RestController
public class FootballController {

	@Autowired
	private FootballService footballService;
	
	@GetMapping(value = "/fetch/{filter}/{value}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Football>> getFootballsByFilter(@PathVariable String filter, @PathVariable String value) {
		List<Football> footballs = footballService.getFootballs(filter, value);
		if(footballs==null || footballs.isEmpty()) {
			throw new FootballNotFoundException("Rankings not found!");
			
		}
		return new ResponseEntity<List<Football>>(footballs, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Football> getFootball(@PathVariable Long id) {
		Football football = footballService.getFootball(id);
		return new ResponseEntity<>(football, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<FootballResponse> addfootball(@RequestBody Football football) {
		FootballResponse productResponse = footballService.addfootball(football);
		return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<FootballResponse> updateFootball(@RequestBody Football football) {
		FootballResponse productResponse = footballService.updateFootball(football);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<FootballResponse> deleteFootball(@PathVariable Long id) {
		FootballResponse productResponse = footballService.deleteFootball(id);
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}


}
