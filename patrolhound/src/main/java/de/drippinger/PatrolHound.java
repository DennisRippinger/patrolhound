package de.drippinger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Init Class for Sprint Boot services.
 *
 * @author Dennis Rippinger
 */
@SpringBootApplication
public class PatrolHound {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PatrolHound.class, args);
	}

}
