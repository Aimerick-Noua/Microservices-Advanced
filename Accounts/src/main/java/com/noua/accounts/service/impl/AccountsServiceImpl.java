package com.noua.accounts.service.impl;

import com.noua.accounts.constants.AccountsConstants;
import com.noua.accounts.dto.AccountsDto;
import com.noua.accounts.dto.CustomerDto;
import com.noua.accounts.entity.Accounts;
import com.noua.accounts.entity.Customer;
import com.noua.accounts.exception.CustomerAlreadyExistsException;
import com.noua.accounts.exception.RessourceNotFoundException;
import com.noua.accounts.mapper.AccountsMapper;
import com.noua.accounts.mapper.CustomerMapper;
import com.noua.accounts.repository.AccountsRepository;
import com.noua.accounts.repository.CustomerRepository;
import com.noua.accounts.service.IAccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto,new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with given mobile number "+customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Ananonymous");
        Customer savedCustomer =customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = (long) (Math.random() * 9000000000L) + 1000000000L;
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }
    /**
     * @param mobileNumber Input mobile number
     * @return accounts details based on a given mobile number
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
       Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()->new RessourceNotFoundException("Customer","mobileNumber",mobileNumber));
       Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(()->new RessourceNotFoundException("Account","customerId",customer.getCustomerId().toString()));
       CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
       customerDto.setAccountInfo(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
       return customerDto;

    }

    /**
     * @param customerDto
     * @return
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountInfo();
        if (accountsDto !=null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(() -> new RessourceNotFoundException("Account", "accountId", accountsDto.getAccountNumber().toString()));

            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts=accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RessourceNotFoundException("Customer", "customerId", customerId.toString()));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;

        }
        return isUpdated;
    }

    /**
     * @param mobileNumber Input mobile number
     * @return boolean
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new RessourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}

