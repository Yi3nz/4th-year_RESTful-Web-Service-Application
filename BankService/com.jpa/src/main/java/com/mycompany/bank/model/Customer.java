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
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "cus") //Test: SELECT * FROM test.cus;
@XmlRootElement
public class Customer implements Serializable {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "cus_id", nullable = false) //Column name changed
    private int id;
    private String name;
    private String address;
    private String email;
    private int securityCredentials;
    //private Account account;
    
    @OneToMany//(mappedBy="customer") work with manyToOne in Account.java
    private Collection<Account> account = new ArrayList<Account>();
    
    /*
    //Working table
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="cus_accccccc",
    joinColumns={@JoinColumn(name="c_id", referencedColumnName="cus_id")},
    inverseJoinColumns={@JoinColumn(name="a_id", referencedColumnName="acc_id")})
    private Collection<Account> account = new ArrayList<Account>();
*/
    public Customer() {
    }

    public Customer(int id, String name, String address, String email, int securityCredentials, Account account) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.securityCredentials = securityCredentials;
        //this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSecurityCredentials() {
        return securityCredentials;
    }

    public void setSecurityCredentials(int securityCredentials) {
        this.securityCredentials = securityCredentials;
    }
/*
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }*/

    public Collection<Account> getAccount() {
        return account;
    }

    public void setAccount(Collection<Account> account) {
        this.account = account;
    }

    
}
