package com.mycompany.bank.resources;

import com.mycompany.bank.model.Account;
import com.mycompany.bank.model.Customer;
import com.mycompany.bank.model.Transaction;
import java.util.Collection;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/customers")
public class BankResource{
    
        private EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        private EntityManager em = emf.createEntityManager();
        private EntityTransaction tx = em.getTransaction(); 

    //Get - all customers
    //http://localhost:49001/api/customers    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> getCustomers() {
        System.out.println("all:" +allEntries());
       return allEntries();
    }
    
     public List<Customer> allEntries() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> rootEntry = cq.from(Customer.class);
        CriteriaQuery<Customer> all = cq.select(rootEntry);
        TypedQuery<Customer> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
     
    //Get - customer by id 
    //http://localhost:49001/api/customers/{id}
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/{id}")
    public Customer getCustomer(@PathParam("id") int id) {
	Customer cus = em.getReference(Customer.class, id);
        
        tx.begin();
        em.persist(cus);
        Customer cList =new Customer(); 
        cList.setId(id);
        cList.setName(cus.getName());
	cList.setAddress(cus.getAddress());
	cList.setEmail(cus.getEmail());
	cList.setSecurityCredentials(cus.getSecurityCredentials());
        tx.commit();
            
        em.close();
        return cList;
        
    }
    
    //Save - a customer (with 2 hard coded accounts)
    //http://localhost:49001/api/customers/save
    @POST
    @Path("/save")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Customer save(Customer c) {
        Customer test = em.find(Customer.class, c.getId());
        if (test == null) {
            
            //Add account1 with 2 transaction
            Account a1 = new Account();
            a1.setAccountType("Student account");
            Transaction t1 = new Transaction();
            t1.setDebitOrCredit(false);
            Transaction t2 = new Transaction();
            t2.setDebitOrCredit(true);
            //Assign them
            c.getAccount().add(a1);
            a1.getTransaction().add(t1);
            a1.getTransaction().add(t2);
            
            //Add account2
            Account a2 = new Account();
            a2.setAccountType("Saving account");
            c.getAccount().add(a2);
            
            tx.begin();
            em.persist(c);
            em.persist(a1);
            em.persist(t1);
            em.persist(t2);
            em.persist(a2);
            tx.commit();
            em.close();
        }
        System.out.println("c:" +c); //Return proxy
        System.out.println("c.acc:" +c.getAccount()); //Return null
        return c;   
    }
    
    //Update customer details
    //http://localhost:49001/api/customers/{id}
    @POST
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Customer update(@PathParam("id") int id, Customer c) {
        Customer test = em.find(Customer.class, id);

        tx.begin();
        em.merge(c);
        tx.commit();
        em.close();
        
        System.out.println("c:" +c); //Return proxy
        System.out.println("c.acc:" +c.getAccount()); //Return null
        return c;   
    }

    //Delete - Customer by id 
    //http://localhost:49001/api/customers/{id}
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void deleteItem(@PathParam("id") int id) {
        Customer test = em.find(Customer.class, id);
        if (test !=null) {
            tx.begin();
            em.remove(test);
            tx.commit();
            em.close();
        }
    }
    
    //Get a customer's account by enter customer id
    @Path("/{id}/accounts")
    public AccountResource getAccountResource(@PathParam("id") int id){
        AccountResource a = new AccountResource();
        return a;
    }
}