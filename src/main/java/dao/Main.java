package dao;

import dao.config.MainConfiguration;
import dao.entities.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Kostka on 26/03/2020.
 */
public class Main {

    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfiguration.class);

        emf = Persistence.createEntityManagerFactory("default");

        EntityManager em = emf.createEntityManager();

        Environment env = new Environment();
        env.setDescription("blablabla");
        env.setName("test");
        em.getTransaction().begin();
        em.persist(env);
        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
        em.getTransaction().begin();
        Environment retrievedEnv = em.createQuery("select env from Environment env", Environment.class).getSingleResult();
        em.getTransaction().commit();
        em.close();
        System.out.println(retrievedEnv.getId() + " " + retrievedEnv.getName());

    }
}
