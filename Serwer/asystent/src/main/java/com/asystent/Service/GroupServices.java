package com.asystent.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by kriscool on 24.10.2017.
 */
@Service
public class GroupServices {
    @Autowired
    JdbcTemplate jdbcTemplate;


}
