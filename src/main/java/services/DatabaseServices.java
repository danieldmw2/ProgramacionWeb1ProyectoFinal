package services;

/**
 * Created by Daniel's Laptop on 6/20/2016.
 */

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.*;

public class DatabaseServices<T>
{
    private static EntityManagerFactory emf;
    private Class<T> claseEntidad;

    public DatabaseServices(Class<T> claseEntidad)
    {
        if (emf == null)
        {
            try
            {
                emf = Persistence.createEntityManagerFactory("Validation");
            }
            catch (Exception e)
            {
                emf = Persistence.createEntityManagerFactory("Creation");
            }
        }
        this.claseEntidad = claseEntidad;
    }

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void insert(T entidad)
    {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try
        {
            em.persist(entidad);
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            em.getTransaction().rollback();
            System.out.println(ex);
            throw ex;
        }
        finally
        {
            em.close();
        }
    }

    public void update(T entidad)
    {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try
        {
            em.merge(entidad);
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            em.getTransaction().rollback();
            System.out.println(ex);
        }
        finally
        {
            em.close();
        }
    }

    public void delete(T entidad)
    {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try
        {
            em.remove(em.contains(entidad) ? entidad : em.merge(entidad));
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            em.getTransaction().rollback();
            System.out.println(ex);
        }
        finally
        {
            em.close();
        }
    }

    public T selectByID(Object id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(claseEntidad, id);
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        finally
        {
            em.close();
        }

        return null;
    }

    public List<T> select()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidad);
            criteriaQuery.select(criteriaQuery.from(claseEntidad));
            return em.createQuery(criteriaQuery).getResultList();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        finally
        {
            em.close();
        }
        return null;
    }
}
