package servlets;

import DAO.DAOImpl;
import entities.ProductsEntity;
import exceptions.WrongArgumentException;
import models.Product;
import util.Converter;
import util.ExceptionsUtil;
import util.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "Products")
public class ProductsServlet extends HttpServlet {
    private static final DAOImpl dao = new DAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // добавление нового
        Writer out = response.getWriter();
        BufferedReader reader = request.getReader();
        Product product;
        try {
            product = Converter.xmlReaderToModel(reader);
            product.setCreationDate(LocalDateTime.now());
            Validator.validate(product, false);
            ProductsEntity productsEntity = Converter.modelToEntity(product);
            DAOImpl dao = new DAOImpl();
            dao.addProduct(productsEntity);
        }
        catch (JAXBException | WrongArgumentException e) {
            response.setStatus(400);
            out.write(e.getMessage());
        }
        response.setStatus(200);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DAOImpl dao = new DAOImpl();
        Writer out = response.getWriter();
        // получение всех: GET /products
        if (request.getPathInfo() == null || request.getPathInfo().equals("/")) {

        }
        // расчет средней цены производителя : GET /products/manufactureCost/average
        else if (request.getPathInfo().equals("/manufactureCost/average")) {
            response.setStatus(200);
            out.write(String.valueOf(dao.getAverageManufactureCost()));
        }
        // получение по ид: GET /products/id/{id}
        else if (request.getPathInfo().startsWith("/id/")) {
            // TODO: check if product exists

        }
        else {
            response.setStatus(404);
            out.write(ExceptionsUtil.getPageNotFoundException());
        }


        /*Writer out = response.getWriter();
        ProductsEntity productEntity = dao.getProductById(id);
        Product product = Converter.entityToModel(productEntity);
        try {
            response.setContentType("application/xml; charset=UTF-8");
            Converter.modelToXmlWriter(product, out);
        }
        catch (JAXBException e) {
            response.setStatus(400);
            out.write(e.getMessage());
        }*/
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // удаление: DELETE /products (в боди объект)
        // удаление всех где есть овнер: DELETE /products/owner (в боди объект)
        // удалить любой где есть прайс: DELETE /products/price/{price}
        super.doDelete(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // обновление
        Product product;
        BufferedReader reader = request.getReader();
        Writer out = response.getWriter();
        try {
            product = Converter.xmlReaderToModel(reader);
            Validator.validate(product, true);
            DAOImpl dao = new DAOImpl();
            if (dao.getProductById(product.getId()) != null) {
                ProductsEntity productsEntity = Converter.modelToEntity(product);
                dao.updateProduct(productsEntity);
                response.setStatus(200);
            }
            else {
                throw new WrongArgumentException(ExceptionsUtil.getEntityWithGivenIdDoesNotExist());
            }
        }
        catch (JAXBException | WrongArgumentException e) {
            response.setStatus(400);
            out.write(e.getMessage());
        }
    }
}
