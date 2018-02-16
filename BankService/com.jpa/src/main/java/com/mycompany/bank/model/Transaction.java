/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank.model;

import java.util.Date;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author x14111187
 */
@Entity
@Table(name="tran")
@XmlRootElement
public class Transaction {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tran_id", nullable = false)
    private int transactionId;
    private boolean debitOrCredit;
    private Date tranDate;
    private String description;
    private double postBalance;
    
    /*
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;*/
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="fk_acc_id")//, insertable=false, updatable=false)
    private Account account;
    
    public Transaction() {
    }

    public Transaction(int transactionId, boolean debitOrCredit, Date tranDate, String description, double postBalance, Account account) {
        this.transactionId = transactionId;
        this.debitOrCredit = debitOrCredit;
        this.tranDate = tranDate;
        this.description = description;
        this.postBalance = postBalance;
        this.account = account;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(boolean debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPostBalance() {
        return postBalance;
    }

    public void setPostBalance(double postBalance) {
        this.postBalance = postBalance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    
    
}
