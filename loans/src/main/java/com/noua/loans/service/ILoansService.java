package com.noua.loans.service;


import com.noua.loans.dto.LoansDto;
import com.noua.loans.entity.Loans;

public interface ILoansService {

    void createLoan(String mobileNumber);

    LoansDto fetchLoan(String mobileNumber);

    boolean updateLoan (LoansDto loansDto);

    boolean deleteLoan (String mobileNumber);
}
