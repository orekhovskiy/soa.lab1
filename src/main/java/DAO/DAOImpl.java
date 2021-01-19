package DAO;

import entities.ProductsEntity;
import models.Person;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.Converter;
import util.HibernateUtil;

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

    public List<ProductsEntity> getProducts(String pathParams, Integer pageNumber, Integer pageCapacity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ProductsEntity> cr = cb.createQuery(ProductsEntity.class);
        Root<ProductsEntity> root = cr.from(ProductsEntity.class);
        Predicate[] predicates = Converter.pathParamsToPredicates(pathParams, cb, root);
        cr.select(root).where(predicates);
        Query<ProductsEntity> query = session.createQuery(cr);
        if (pageNumber != null && pageCapacity != null) {
            query.setFirstResult((pageNumber - 1) * pageCapacity);
            query.setMaxResults(pageCapacity);
        }
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
        ProductsEntity entity;
        try {
            entity = (ProductsEntity) query.getSingleResult();
        }
        catch (Exception e) {
            return null;
        }
        session.getTransaction().commit();
        session.close();
        return entity;
    }

    public void deleteProduct(ProductsEntity product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(product);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteAllProductWithPerson(Person person) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query= session.createQuery(
                "select product from ProductsEntity product where " +
                "product.ownername = :ownerName and " +
                "product.ownerweight = :ownerWeight and " +
                "product.ownernationality = :ownerNationality and " +
                "product.ownerlocationx = :ownerLocationX and " +
                "product.ownerlocationy = :ownerLocationY and " +
                "product.ownerlocationz = :ownerLocationZ ")
                .setParameter("ownerName", person.getName())
                .setParameter("ownerWeight", person.getWeight())
                .setParameter("ownerNationality", person.getNationality())
                .setParameter("ownerLocationX", person.getLocation().getX())
                .setParameter("ownerLocationY", person.getLocation().getY())
                .setParameter("ownerLocationZ", person.getLocation().getZ());
        ProductsEntity[] productsEntitiesList = (ProductsEntity[]) query.getResultList().toArray();
        for (ProductsEntity entity: productsEntitiesList) {
            session.delete(entity);
        }
        session.getTransaction().commit();
        session.close();
    }

    public void deleteProductWithPrice(int price) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query= session.createQuery(
                "select product from ProductsEntity product where " +
                "product.price = :price")
                .setParameter("price", price);
        try {
            ProductsEntity entity = (ProductsEntity) query.getResultList().get(0);
            session.delete(entity);

        }
        catch (Exception e) {}
        session.getTransaction().commit();
        session.close();
    }
}
