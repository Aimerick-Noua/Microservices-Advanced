package com.noua.loans.service.impl;

import com.noua.loans.constants.LoansConstants;
import com.noua.loans.dto.LoansDto;
import com.noua.loans.entity.Loans;
import com.noua.loans.exception.LoanAlreadyExistsException;
import com.noua.loans.exception.ResourceNotFoundException;
import com.noua.loans.mapper.LoansMapper;
import com.noua.loans.repository.LoansRepository;
import com.noua.loans.service.ILoansService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements ILoansService {
    private final LoansRepository loansRepository;


    /**
     * @param mobileNumber - mobile number of the customer
     */
    @Override
    public void createLoan(String mobileNumber) {
       Optional<Loans> loans = loansRepository.findByMobileNumber(mobileNumber);
       if (loans.isPresent()){
           throw new LoanAlreadyExistsException("Loan already exists with the given mobile number "+mobileNumber);
       }
       loansRepository.save(createNewLoan(mobileNumber));
    }
    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }
    /**
     * Fetches the loan details.
     *
     * @param mobileNumber The Mobile Number of the Customer
     * @return The loan details
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        return LoansMapper.mapToLoansDto(loans,new LoansDto());
    }

    /**
     * Updates the loan details.
     *
     * @param loansDto Loan to be updated
     * @return {@code true} if the update is successful, {@code false} otherwise
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return  true;
    }


    /**
     * Deletes a loan
     *
     * @param mobileNumber The mobile number of the loan to be deleted
     * @return - {@code true} if the update is successful, {@code false}
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }


}
