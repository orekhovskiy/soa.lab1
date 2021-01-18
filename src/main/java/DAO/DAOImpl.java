package DAO;

import entities.ProductsEntity;
import models.Product;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class DAOImpl {
    public ProductsEntity getProductById(long id){
        Session session= HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ProductsEntity productEntity = session.get(ProductsEntity.class, id);
        session.getTransaction().commit();
        session.close();
        return productEntity;
    }

    public void addProduct(ProductsEntity entity) {
        Session session= HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    public List<ProductsEntity> getProducts(Predicate[] predicates, Integer pageNumber, Integer pageCapacity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ProductsEntity> cr = cb.createQuery(ProductsEntity.class);
        Root<ProductsEntity> root = cr.from(ProductsEntity.class);
        cr.select(root).where(predicates);

        Query<ProductsEntity> query = session.createQuery(cr);
        List<ProductsEntity> results = query.getResultList();
        session.close();
        return results;
    }

    public void updateProduct(ProductsEntity entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    public double getAverageManufactureCost() {
        Session session= HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query= session.createQuery("select avg(manufacturecost) from ProductsEntity");
        Double avg=  (Double) query.list().get(0);
        session.getTransaction().commit();
        session.close();
        return avg;
    }

    public ProductsEntity getProductByPartNumber(String partNumber) {
        if (partNumber == null) return null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query= session.createQuery("select product from ProductsEntity product where product.partnumber=:partNumber");
        query.setParameter("partNumber", partNumber);
        try {
            return (ProductsEntity) query.getSingleResult();
        }
        catch (Exception e) {
            return null;
        }
    }
}
