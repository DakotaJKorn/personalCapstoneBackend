package com.example.demo.UserAccounts;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class UserAccounts {

    @Id
    private Long id;
    private Long accountsTotal;
    private Long portfolioTotal;

    public UserAccounts(){}

    public UserAccounts(Long id, Long accountsTotal, Long portfolioTotal) {
        this.id = id;
        this.accountsTotal = accountsTotal;
        this.portfolioTotal = portfolioTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountsTotal() {
        return accountsTotal;
    }

    public void setAccountsTotal(Long accountsTotal) {
        this.accountsTotal = accountsTotal;
    }

    public Long getPortfolioTotal() {
        return portfolioTotal;
    }

    public void setPortfolioTotal(Long portfolioTotal) {
        this.portfolioTotal = portfolioTotal;
    }

    @Override
    public String toString() {
        return "UserAccounts{" +
                "id=" + id +
                ", accountsTotal=" + accountsTotal +
                ", portfolioTotal=" + portfolioTotal +
                '}';
    }
}
