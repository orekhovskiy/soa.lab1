package util;

import DAO.DAOImpl;
import entities.ProductsEntity;
import exceptions.WrongArgumentException;
import models.Person;
import models.Product;
import static util.ExceptionsUtil.*;

public class Validator {
    public static void validateProduct(Product product, boolean toCheckId)
            throws WrongArgumentException {

        // Id
        if (toCheckId) {
            if (product.getId() == null)
                throw new WrongArgumentException(getCouldNotBeNullException("Id"));
            if (product.getId() <= 0)
                throw new WrongArgumentException(getShouldBeGreaterException("Id", "0"));
        }

        // Name
        if (product.getName() == null)
            throw new WrongArgumentException(getCouldNotBeNullException("Name"));
        if (product.getName().equals(""))
            throw new WrongArgumentException(getCouldNotBeEmptyException("Name"));

        // Coordinates
        if (product.getCoordinates() == null)
            throw new WrongArgumentException(getCouldNotBeNullException("Coordinates"));
        if (product.getCoordinates().getX() <= -965)
            throw new WrongArgumentException(getShouldBeGreaterException("Coordinates.X", "-965"));

        // CreationDate
        if (product.getCreationDate() == null)
            throw new WrongArgumentException(getCouldNotBeNullException("CreationDate"));

        // Price
        if (product.getPrice() != null && product.getPrice() <= 0)
            throw new WrongArgumentException(getShouldBeGreaterException("Price", "0"));

        // PartNumber
        if (product.getPartNumber() != null &&
            product.getPartNumber().length() <= 26)
            throw new WrongArgumentException(getShouldBeGreaterException("PartNumber.length()", "26"));
        DAOImpl dao = new DAOImpl();
        if (!toCheckId) {
            if (dao.getProductByPartNumber(product.getPartNumber()) != null)
                throw new WrongArgumentException(getArgumentIsNotUniqueException("PartNumber"));
        }
        else {
            ProductsEntity entity = dao.getProductByPartNumber(product.getPartNumber());
            if (entity != null && entity.getId() != product.getId())
                throw new WrongArgumentException(getArgumentIsNotUniqueException("PartNumber"));
       }

        // Owner
        validatePerson(product.getOwner());
    }

    public static void validatePerson(Person owner)
            throws WrongArgumentException{
        if (owner != null) {
            // Location
            if (owner.getLocation() == null)
                throw new WrongArgumentException(getCouldNotBeNullException("Owner.Location"));
            if (owner.getLocation().getY() == null)
                throw new WrongArgumentException(getCouldNotBeNullException("Owner.Location.Y"));

            // Name
            if (owner.getName() == null)
                throw new WrongArgumentException(getCouldNotBeNullException("Owner.Name"));
            if (owner.getName().equals(""))
                throw new WrongArgumentException(getCouldNotBeEmptyException("Owner.Name"));

            // Weight
            if (owner.getWeight() != null && owner.getWeight() <= 0)
                throw new WrongArgumentException(getShouldBeGreaterException("Owner.Weight", "0"));

            // Nationality
            if (owner.getNationality() == null)
                throw new WrongArgumentException(getEnumException("Owner.Nationality"));
        }
    }
}
