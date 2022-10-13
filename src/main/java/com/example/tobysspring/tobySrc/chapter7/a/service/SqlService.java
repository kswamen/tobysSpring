package com.example.tobysspring.tobySrc.chapter7.a.service;

public interface SqlService {
    String getSql(String key) throws SqlRetrievalFailureException;
}
