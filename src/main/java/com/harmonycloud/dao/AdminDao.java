package com.harmonycloud.dao;

import com.harmonycloud.entity.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao {
    int insert(Admin admin);
}