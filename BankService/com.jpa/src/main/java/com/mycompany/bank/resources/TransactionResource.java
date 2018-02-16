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
 *
 * @author x14111187
 */
@Path("/tran")
public class TransactionResource {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction(); 

    //Get - all transactions
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Transaction> getTransactions() {
       return allEntries();
    }
    
     public List<Transaction> allEntries() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);
        Root<Transaction> rootEntry = cq.from(Transaction.class);
        CriteriaQuery<Transaction> all = cq.select(rootEntry);
        TypedQuery<Transaction> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
     
    //Get - transactions by id 
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{tranId}")
    public Transaction getTransaction(@PathParam("tranId") int tranId) {
        Transaction test = em.find(Transaction.class, tranId);

        tx.begin();
        em.persist(test);
        Transaction aList =new Transaction(); 
        aList.setTransactionId(tranId);
        aList.setDebitOrCredit(test.isDebitOrCredit());
	aList.setTranDate(test.getTranDate());
	aList.setDescription(test.getDescription());
	aList.setPostBalance(test.getPostBalance());
        tx.commit();
            
        em.close();
        
        System.out.println("tran: " +aList); //Return proxy
        return aList;
    }

    //Save - a transaction 
    @POST
    @Path("/saveTran")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Transaction save(Transaction t) {
        Transaction test = em.find(Transaction.class, t.getTransactionId());
        if (test == null) {
            tx.begin();
            em.persist(t);
            tx.commit();
            em.close();
        }
        return t;   
    }
    
    
    //Update transaction details by id
    //http://localhost:49001/api/tran/{tranId}
    @POST
    @Path("/{tranId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Transaction update(@PathParam("tranId") int tranId, Transaction t) {
        Transaction test = em.find(Transaction.class, tranId);

        tx.begin();
        em.merge(t);
        tx.commit();
        em.close();
        
        return t;   
    }
    
    //Delete - Transaction by id    
    @DELETE
    @Path("/{tranId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void deleteItem(@PathParam("tranId") int tranId) {
        Transaction test = em.find(Transaction.class, tranId);
        if (test !=null) {
            tx.begin();
            em.remove(test);
            tx.commit();
            em.close();
        }
    }
}

