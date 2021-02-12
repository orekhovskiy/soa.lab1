package DAO;

import entities.ProductsEntity;
import exceptions.NotFoundException;
import exceptions.OperationException;
import exceptions.WrongArgumentException;
import models.Person;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.Converter;
import util.ExceptionsUtil;
import util.HibernateUtil;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DAOImpl {
    public ProductsEntity getProductById(long id)
            throws OperationException, NotFoundException {
        Session session= HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ProductsEntity productEntity = session.get(ProductsEntity.class, id);
        session.getTransaction().commit();
        session.close();
        if (productEntity == null) {
            throw new NotFoundException(ExceptionsUtil.getEntityWithGivenIdDoesNotExist());
        }
        return productEntity;

    }

    public void addProduct(ProductsEntity entity) {
        Session session= HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    public List<ProductsEntity> getProducts(String pathParams, Integer pageNumber, Integer pageCapacity, String[] sortBy)
        throws OperationException, WrongArgumentException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ProductsEntity> cr = cb.createQuery(ProductsEntity.class);
        Root<ProductsEntity> root = cr.from(ProductsEntity.class);
        List<Predicate> predicates = Converter.pathParamsToPredicates(pathParams, cb, root);
        if (predicates.size() != 0) {
            cr.select(root).where(predicates.toArray(new Predicate[0]));
        }
        else {
            cr.select(root);
        }
        List<Order> orders = new ArrayList<>();
        for(String column: sortBy) {
            try {
                Path<ProductsEntity> path = root.get(column.toLowerCase(Locale.ROOT).replace("-", ""));
                if (column.startsWith("-")) {
                    orders.add(cb.desc(path));
                }
                else {
                    orders.add(cb.asc(path));
                }
            }
            catch (Exception e) {
                throw new OperationException(ExceptionsUtil.getInvalidOrderArgumentException(column));
            }
        }
        if (orders.size() != 0) {
            cr.orderBy(orders);
        }
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

    public void deleteProductById(long id)
    throws OperationException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            ProductsEntity entity = session.load(ProductsEntity.class, id);
            session.delete(entity);
        }
        catch (EntityNotFoundException e) {
            throw new OperationException(ExceptionsUtil.getEntityWithGivenIdDoesNotExist());
        }
        session.getTransaction().commit();
        session.close();
    }

    public void deleteAllProductWithPerson(Person person)
        throws OperationException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query= session.createQuery(
                "select product from ProductsEntity product where " +
                "product.ownername = :ownerName and " +
                "((:ownerWeight is null and product.ownerweight is null) or product.ownerweight = :ownerWeight) and " +
                "product.ownernationality = :ownerNationality and " +
                "product.ownerlocationx = :ownerLocationX and " +
                "product.ownerlocationy = :ownerLocationY and " +
                "product.ownerlocationz = :ownerLocationZ ")
                .setParameter("ownerName", person.getName())
                .setParameter("ownerWeight", person.getWeight())
                .setParameter("ownerNationality", person.getNationality().name())
                .setParameter("ownerLocationX", person.getLocation().getX())
                .setParameter("ownerLocationY", person.getLocation().getY())
                .setParameter("ownerLocationZ", person.getLocation().getZ());
        List<ProductsEntity> matchedEntities = (List<ProductsEntity>) query.getResultList();
        if (matchedEntities.size() == 0) {
            throw new OperationException(ExceptionsUtil.getNoElementFound());
        }
        for (ProductsEntity entity: matchedEntities) {
            session.delete(entity);
        }
        session.getTransaction().commit();
        session.close();
    }

    public void deleteProductWithPrice(Integer price) throws OperationException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query= session.createQuery(
                "select product from ProductsEntity product where " +
                "((:price is null and product.price is null) or product.price = :price)")
                .setParameter("price", price);
        List<ProductsEntity> matchedEntities = (List<ProductsEntity>) query.getResultList();
        if (matchedEntities.size() == 0) {
            throw new OperationException(ExceptionsUtil.getNoElementFound());
        }
        ProductsEntity entity = matchedEntities.get(0);
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }
}
