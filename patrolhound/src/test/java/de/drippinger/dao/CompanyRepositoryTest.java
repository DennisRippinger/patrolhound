package de.drippinger.dao;

import de.drippinger.PatrolHound;
import de.drippinger.dto.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * CompanyDaoTest
 *
 * @author Dennis Rippinger
 * @since 08.11.15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PatrolHound.class)
public class CompanyRepositoryTest {

	@Resource
	CompanyRepository companyDao;

	@Test
	public void test() {
		List<Company> companies = companyDao.findAll();
		companies.stream().forEach((company) -> System.out.println(company));
	}
}
