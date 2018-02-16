/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank.resources;

import com.mycompany.bank.model.Account;
import com.mycompany.bank.model.Transaction;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author x14111187
 */
//http://localhost:49001/api/customers/{id}/accounts
@Path("/")
public class AccountResource {
    
        private EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        private EntityManager em = emf.createEntityManager();
        private EntityTransaction tx = em.getTransaction(); 

    //Get - all accounts
    //http://localhost:49001/api/customers/{id}/accounts    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Account> getAccounts() {
       return allEntries();
    }
    
     public List<Account> allEntries() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> rootEntry = cq.from(Account.class);
        CriteriaQuery<Account> all = cq.select(rootEntry);
        TypedQuery<Account> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
     
    //Get - accounts by id
    //http://localhost:49001/api/customers/{id}/accounts/{accountId} 
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{accountId}")
    public Account getAccount(@PathParam("accountId") int accountId) {
        Account test = em.find(Account.class, accountId);

        tx.begin();
        em.persist(test);
        Account aList =new Account(); 
        aList.setAccountId(accountId);
        aList.setSortcode(test.getSortcode());
	aList.setCurrentBalance(test.getCurrentBalance());
	aList.setAccountType(test.getAccountType());
        tx.commit();
            
        em.close();
        
        System.out.println("a:" +aList); //Return proxy
        return aList;
    }

    //Save - an account (with 2 hard coded transactions)
    //http://localhost:49001/api/customers/{id}/accounts/saveAccount
    @POST
    @Path("/saveAccount")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Account save(Account a) {
        Account test = em.find(Account.class, a.getAccountId());
        if (test == null) {

            Transaction t1 = new Transaction();
            t1.setDebitOrCredit(false);

            Transaction t2 = new Transaction();
            t2.setDebitOrCredit(true);
            
            a.getTransaction().add(t1);
            a.getTransaction().add(t2);
            
            tx.begin();
            em.persist(a);
            em.persist(t1);
            em.persist(t2);
            tx.commit();
            em.close();
        }
        return a;   
    }
    
    //Update account details by id
    //http://localhost:49001/api/customers/{id}/accounts/{accountId} 
    @POST
    @Path("/{accountId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Account update(@PathParam("accountId") int accountId, Account a) {
        Account test = em.find(Account.class, accountId);

        tx.begin();
        em.merge(a);
        tx.commit();
        em.close();
        
        return a;   
    }
    
    //Delete - Account by id    
    @DELETE
    @Path("/{accountId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void deleteItem(@PathParam("accountId") int accountId) {
        Account test = em.find(Account.class, accountId);
        if (test !=null) {
            tx.begin();
            em.remove(test);
            tx.commit();
            em.close();
        }
    }
}