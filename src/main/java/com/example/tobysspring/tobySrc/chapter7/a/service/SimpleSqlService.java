package com.example.tobysspring.tobySrc.chapter7.a.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class SimpleSqlService implements SqlService {
    @Autowired
    private Map<String, String> sqlMap;

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        String sql = sqlMap.get(key);
        if (sql == null) {
            throw new SqlRetrievalFailureException(key + "에 대한 SQL을 찾을 수 없습니다.");
        }
        else
            return sql;
    }
}
