package com.dd.platform.service.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.dd.platform.data.Constituency;

public class ConstituencyDao {

	protected DataSource dataSource;
	
	protected JdbcTemplate jdbcTemplate;
	
	public Constituency create(Constituency constituency) throws Exception {
		return null;
	}

	public Constituency findById(Long id) throws Exception {
		return null;
	}

	public List<Constituency> findAll() throws Exception {
		List<Constituency> results = new ArrayList<Constituency>();
		results = this.jdbcTemplate.query("select * from constituency",
				new RowMapper<Constituency>() {
					public Constituency mapRow(ResultSet rs, int rowNum) throws SQLException {
						// System.out.println("rowMapper() called for rn = " + rowNum);
						int id = rs.getInt("id");
						String name = rs.getString("name");
						Constituency constituency = new Constituency(new Integer(id).toString(), name);
						return constituency;
					}
				});
		return results;
	}

	public Constituency update(Constituency constituency) throws Exception {
		return null;
	}

	public void delete(Long id) throws Exception {
		
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		// this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
