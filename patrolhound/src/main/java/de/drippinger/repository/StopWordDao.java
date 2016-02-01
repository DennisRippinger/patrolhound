package de.drippinger.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class StopWordDao {

	@Inject
	private DSLContext jooq;
}
