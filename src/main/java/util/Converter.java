package util;

import entities.ProductsEntity;
import models.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Converter {
    public static ProductsEntity modelToEntity(Product model) {
        ProductsEntity entity = new ProductsEntity();

        if (model.getId() != null) entity.setId(model.getId());
        entity.setName(model.getName());

        entity.setCoordinatesx(model.getCoordinates().getX());
        entity.setCoordinatesy(model.getCoordinates().getY());

        entity.setCreationdate(Timestamp.valueOf(model.getCreationDate()));
        if (model.getPrice() != null) entity.setPrice(model.getPrice());
        if (model.getPartNumber() != null)entity.setPartnumber(model.getPartNumber());
        entity.setManufacturecost(model.getManufactureCost());
        if (model.getUnitOfMeasure() != null)entity.setUnitofmeasure(model.getUnitOfMeasure().name());

        if (model.getOwner() != null) {
            entity.setOwnername(model.getOwner().getName());
            if (model.getOwner().getWeight() != null) entity.setOwnerweight(model.getOwner().getWeight());

            entity.setOwnernationality(model.getOwner().getNationality().name());
            entity.setOwnerlocationx(model.getOwner().getLocation().getX());
            entity.setOwnerlocationy(model.getOwner().getLocation().getY());
            entity.setOwnerlocationz(model.getOwner().getLocation().getZ());
        }
        return entity;
    }

    public static Product entityToModel(ProductsEntity entity) {
        Product model = new Product();
        Coordinates coordinates = new Coordinates();
        Person person = new Person();
        Location location = new Location();
        int personNullFieldsAmount = 0;

        if (entity.getOwnerlocationx() != null) {
            location.setX(entity.getOwnerlocationx());
        }
        else {
            personNullFieldsAmount++;
        }
        if (entity.getOwnerlocationy() != null)  {
            location.setY(entity.getOwnerlocationy());
        }
        else {
            personNullFieldsAmount++;
        }
        if (entity.getOwnerlocationz() != null) {
            location.setZ(entity.getOwnerlocationz());
        }
        else {
            personNullFieldsAmount++;
        }
        if (entity.getOwnername() != null) {
            person.setName(entity.getOwnername());
        }
        else {
            personNullFieldsAmount++;
        }
        if (entity.getOwnerweight() != null) {
            person.setWeight(entity.getOwnerweight());
        }
        else {
            personNullFieldsAmount++;
        }
        if (entity.getOwnernationality() != null) {
            person.setNationality(Country.valueOf(entity.getOwnernationality()));
        }
        else {
            personNullFieldsAmount++;
        }
        person.setLocation(location);
        if (entity.getCoordinatesx() != null) coordinates.setX(entity.getCoordinatesx());
        if (entity.getCoordinatesy() != null) coordinates.setY(entity.getCoordinatesy());
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setCoordinates(coordinates);
        model.setCreationDate(entity.getCreationdate().toLocalDateTime());
        if (entity.getPrice() != null) model.setPrice(entity.getPrice());
        if (entity.getPartnumber() != null) model.setPartNumber(entity.getPartnumber());
        model.setManufactureCost(entity.getManufacturecost());
        if (entity.getUnitofmeasure() != null) model.setUnitOfMeasure(UnitOfMeasure.valueOf(entity.getUnitofmeasure()));
        if (personNullFieldsAmount < 6) model.setOwner(person);

        return  model;
    }

    public static <ModelType> ModelType xmlReaderToModel(BufferedReader reader, Class<ModelType> clazz) throws JAXBException {
        JAXBContext context;
        Unmarshaller unmarshaller;

        context = JAXBContext.newInstance(clazz);
        unmarshaller = context.createUnmarshaller();

        return (ModelType) unmarshaller.unmarshal(reader);
    }

    public static <ModelType> void modelToXmlWriter(ModelType model, Writer writer, Class<ModelType> clazz) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(model, writer);
    }

    public static Predicate[] pathParamsToPredicates(String pathParams, CriteriaBuilder cb, Root<ProductsEntity> root) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        String[] pathParts = pathParams.split("/");
        if (pathParts.length % 2 != 0) return new Predicate[]{};
        for (int i = 0; i * 2 <= pathParts.length; i+= 2) {
            predicates.add(cb.equal(root.get(pathParts[i]), pathParts[i + 1]));
        }
        return (Predicate[]) predicates.toArray();
    }
}
