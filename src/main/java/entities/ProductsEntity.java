package entities;

import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "products", schema = "public", catalog = "soa")
public class ProductsEntity {
    private long id;
    private String name;
    private Integer coordinatesx;
    private Long coordinatesy;
    private Timestamp creationdate;
    private Integer price;
    private String partnumber;
    private Double manufacturecost;
    private String unitofmeasure;
    private String ownername;
    private Integer ownerweight;
    private String ownernationality;
    private Float ownerlocationx;
    private Double ownerlocationy;
    private Double ownerlocationz;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "coordinatesx", nullable = true)
    public Integer getCoordinatesx() {
        return coordinatesx;
    }

    public void setCoordinatesx(Integer coordinatesx) {
        this.coordinatesx = coordinatesx;
    }

    @Basic
    @Column(name = "coordinatesy", nullable = true)
    public Long getCoordinatesy() {
        return coordinatesy;
    }

    public void setCoordinatesy(Long coordinatesy) {
        this.coordinatesy = coordinatesy;
    }

    @Basic
    @Column(name = "creationdate", nullable = true)
    public Timestamp getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Timestamp creationdate) {
        this.creationdate = creationdate;
    }

    @Basic
    @Column(name = "price", nullable = true)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Basic
    @Column(name = "partnumber", nullable = true, length = -1)
    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
    }

    @Basic
    @Column(name = "manufacturecost", nullable = true, precision = 0)
    public Double getManufacturecost() {
        return manufacturecost;
    }

    public void setManufacturecost(Double manufacturecost) {
        this.manufacturecost = manufacturecost;
    }

    @Basic
    @Column(name = "unitofmeasure", nullable = true, length = -1)
    public String getUnitofmeasure() {
        return unitofmeasure;
    }

    public void setUnitofmeasure(String unitofmeasure) {
        this.unitofmeasure = unitofmeasure;
    }

    @Basic
    @Column(name = "ownername", nullable = true, length = -1)
    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    @Basic
    @Column(name = "ownerweight", nullable = true)
    public Integer getOwnerweight() {
        return ownerweight;
    }

    public void setOwnerweight(Integer ownerweight) {
        this.ownerweight = ownerweight;
    }

    @Basic
    @Column(name = "ownernationality", nullable = true, length = -1)
    public String getOwnernationality() {
        return ownernationality;
    }

    public void setOwnernationality(String ownernationality) {
        this.ownernationality = ownernationality;
    }

    @Basic
    @Column(name = "ownerlocationx", nullable = true, precision = 0)
    public Float getOwnerlocationx() {
        return ownerlocationx;
    }

    public void setOwnerlocationx(Float ownerlocationx) {
        this.ownerlocationx = ownerlocationx;
    }

    @Basic
    @Column(name = "ownerlocationy", nullable = true, precision = 0)
    public Double getOwnerlocationy() {
        return ownerlocationy;
    }

    public void setOwnerlocationy(Double ownerlocationy) {
        this.ownerlocationy = ownerlocationy;
    }

    @Basic
    @Column(name = "ownerlocationz", nullable = true, precision = 0)
    public Double getOwnerlocationz() {
        return ownerlocationz;
    }

    public void setOwnerlocationz(Double ownerlocationz) {
        this.ownerlocationz = ownerlocationz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductsEntity that = (ProductsEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (coordinatesx != null ? !coordinatesx.equals(that.coordinatesx) : that.coordinatesx != null) return false;
        if (coordinatesy != null ? !coordinatesy.equals(that.coordinatesy) : that.coordinatesy != null) return false;
        if (creationdate != null ? !creationdate.equals(that.creationdate) : that.creationdate != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (partnumber != null ? !partnumber.equals(that.partnumber) : that.partnumber != null) return false;
        if (manufacturecost != null ? !manufacturecost.equals(that.manufacturecost) : that.manufacturecost != null)
            return false;
        if (unitofmeasure != null ? !unitofmeasure.equals(that.unitofmeasure) : that.unitofmeasure != null)
            return false;
        if (ownername != null ? !ownername.equals(that.ownername) : that.ownername != null) return false;
        if (ownerweight != null ? !ownerweight.equals(that.ownerweight) : that.ownerweight != null) return false;
        if (ownernationality != null ? !ownernationality.equals(that.ownernationality) : that.ownernationality != null)
            return false;
        if (ownerlocationx != null ? !ownerlocationx.equals(that.ownerlocationx) : that.ownerlocationx != null)
            return false;
        if (ownerlocationy != null ? !ownerlocationy.equals(that.ownerlocationy) : that.ownerlocationy != null)
            return false;
        if (ownerlocationz != null ? !ownerlocationz.equals(that.ownerlocationz) : that.ownerlocationz != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (coordinatesx != null ? coordinatesx.hashCode() : 0);
        result = 31 * result + (coordinatesy != null ? coordinatesy.hashCode() : 0);
        result = 31 * result + (creationdate != null ? creationdate.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (partnumber != null ? partnumber.hashCode() : 0);
        result = 31 * result + (manufacturecost != null ? manufacturecost.hashCode() : 0);
        result = 31 * result + (unitofmeasure != null ? unitofmeasure.hashCode() : 0);
        result = 31 * result + (ownername != null ? ownername.hashCode() : 0);
        result = 31 * result + (ownerweight != null ? ownerweight.hashCode() : 0);
        result = 31 * result + (ownernationality != null ? ownernationality.hashCode() : 0);
        result = 31 * result + (ownerlocationx != null ? ownerlocationx.hashCode() : 0);
        result = 31 * result + (ownerlocationy != null ? ownerlocationy.hashCode() : 0);
        result = 31 * result + (ownerlocationz != null ? ownerlocationz.hashCode() : 0);
        return result;
    }
}
