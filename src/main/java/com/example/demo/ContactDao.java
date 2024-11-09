package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ContactDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<Contact> contacts) {
        String sql = "INSERT INTO contact (first_name, last_name, phone_number, email) " +
                "VALUES (:first_name, :last_name, :phone_number, :email)";

        jdbcTemplate.batchUpdate(sql, contacts.stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(BeanPropertySqlParameterSource[]::new));
    }
}
