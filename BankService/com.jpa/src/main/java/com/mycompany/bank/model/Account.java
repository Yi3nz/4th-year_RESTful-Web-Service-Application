/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 *
 * @author Group N
 */
@Entity
@Table(name="acc")
@XmlRootElement
public class Account implements Serializable{
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "acc_id", nullable = false)
    private int accountId;
    private int sortcode;
    private double currentBalance;
    private String accountType;

    /* Working with Account account in Customer class for foreign key
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="fk_cus_id")//, insertable=false, updatable=false)
    private Customer customer;*/
    
//Reference - https://medium.com/@milenko_52829/how-to-build-a-good-api-relationships-and-endpoints-8b07aa37097c
    @OneToMany//Wrong (cascade = CascadeType.ALL, targetEntity = Customer.class)
    private Collection<Transaction> transaction = new ArrayList<Transaction>();

    /*
    //Working table
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinTable(name="cus_accccccc",
    joinColumns={@JoinColumn(name="a_id", referencedColumnName="acc_id")},
    inverseJoinColumns={@JoinColumn(name="c_id", referencedColumnName="c_id")})
    private Customer customer;
 */

/**************************************************************/
    
    public Account() {
    }

    public Account(int accountId, int custId, int sortcode, double currentBalance, String accountType, Customer customer) {
        this.accountId = accountId;
        //this.custId = custId;
        this.sortcode = sortcode;
        this.currentBalance = currentBalance;
        this.accountType = accountType;
        //this.customer = customer;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getSortcode() {
        return sortcode;
    }

    public void setSortcode(int sortcode) {
        this.sortcode = sortcode;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
/*
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }*/

    public Collection<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(Collection<Transaction> transaction) {
        this.transaction = transaction;
    }

}
