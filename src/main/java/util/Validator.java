package util;

import DAO.DAOImpl;
import entities.ProductsEntity;
import exceptions.WrongArgumentException;
import models.Product;
import static util.ExceptionsUtil.*;

public class Validator {
    public static void validate(Product product, boolean toCheckId) throws WrongArgumentException{
        // Coordinates
        if (product.getCoordinates() == null)
            throw new WrongArgumentException(getCouldNotBeNullException("Coordinates"));
        if (product.getCoordinates().getX() <= -965)
            throw new WrongArgumentException(getShouldBeGreaterException("Coordinates.X", "-965"));

        // Owner
        if (product.getOwner() != null) {
            if (product.getOwner().getLocation() == null)
                throw new WrongArgumentException(getCouldNotBeNullException("Owner.Location"));
            if (product.getOwner().getLocation().getY() == null)
                throw new WrongArgumentException(getCouldNotBeNullException("Owner.Location.Y"));
            if (product.getOwner().getName() == null)
                throw new WrongArgumentException(getCouldNotBeNullException("Owner.Name"));
            if (product.getOwner().getName().equals(""))
                throw new WrongArgumentException(getCouldNotBeEmptyException("Owner.Name"));
            if (product.getOwner().getWeight() != null &&
                    product.getOwner().getWeight() <= 0)
                throw new WrongArgumentException(getShouldBeGreaterException("Owner.Weight", "0"));
            if (product.getOwner().getNationality() == null)
                throw new WrongArgumentException(getCouldNotBeNullException("Owner.Nationality"));
        }

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
    }
}