package com.workintech.mapping.controller;

import com.workintech.mapping.entity.Account;
import com.workintech.mapping.entity.Customer;
import com.workintech.mapping.service.AccountService;
import com.workintech.mapping.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountService accountService;
    private CustomerService customerService;
    @Autowired
    public AccountController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @GetMapping("/")
    public List<Account> get() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable int id) {
        return accountService.find(id);
    }

    @PostMapping("/{customerId}")
    public Account save(@RequestBody Account account, @PathVariable int customerId) {
        Customer customer =  customerService.find(customerId);
        customer.add(account);
        account.setCustomer(customer);
        return accountService.save(account);
    }

    @PutMapping("/{customerId}")
    public Account update(@RequestBody Account account, @PathVariable int customerId) {
        Customer customer =  customerService.find(customerId);
        Account foundAccount = null;
        for(Account account1: customer.getAccountList()) {
            if(account1.getId() == account.getId()) {
                foundAccount = account1;
            }}
            if(foundAccount == null) {
                return null;
            }
            int index = customer.getAccountList().indexOf(foundAccount);
            customer.getAccountList().set(index, account);

            account.setCustomer(customer);
            return accountService.save(account);
    }

    @DeleteMapping("/{id}")
    public Account delete(@PathVariable int id) {
        Account account = accountService.find(id);
        accountService.delete(account);
        return account;
    }

}
