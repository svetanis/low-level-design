package com.svetanis.api.health;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.svetanis.api.health.domain.SimpleCard;

@RestController
public class IncentiveMeasureController {

	private static final String MAPPING = "/api/v1/incentives";

	private final IncentiveMeasureService service;

	@Inject
	public IncentiveMeasureController(IncentiveMeasureService service) {
		this.service = checkNotNull(service);
	}

	@GetMapping(MAPPING + "/{year}")
	public ResponseEntity<?> get(@PathVariable int year) {
		try {
			List<SimpleCard> list = service.incentives(year);
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Throwable e) {
			return new ResponseEntity<>(e, BAD_REQUEST);
		}
	}
}