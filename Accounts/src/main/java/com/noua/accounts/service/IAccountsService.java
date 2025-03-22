package com.noua.accounts.service;

import com.noua.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Method to create account
     * @param customerDto
     * @return
     */

    void createAccount(CustomerDto customerDto);
    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
