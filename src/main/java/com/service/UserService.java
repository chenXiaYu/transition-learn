package com.service;

import com.entitty.User;

import java.math.BigDecimal;

public interface UserService {
    void saveUser(User user);
    User getById(Integer id);

    void updateUserAmount(User user);

    void transfer(Integer from , Integer to , BigDecimal amount);
    void transfer2(Integer from , Integer to , BigDecimal amount);
    void transfer3(Integer from , Integer to , BigDecimal amount);
}
