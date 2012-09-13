package com.dd.platform.service.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dd.platform.data.Person;

public class PersonDao {

	protected DataSource dataSource;
	
	protected JdbcTemplate jdbcTemplate;

	public void create(Person person) throws Exception {
		this.jdbcTemplate.update("insert into person (id, member_id, person_id, name, party, constituency) values (?, ?, ?, ?, ?, ?)", 
				new Object[] {
					person.getId(),
					person.getMemberId(),
					person.getPersonId(),
					person.getFullName(),
					person.getPartyName(),
					person.getConstituencyName()
				}
		);
		System.out.println("Inserted Person [" + person.toString() + "] into database");
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
