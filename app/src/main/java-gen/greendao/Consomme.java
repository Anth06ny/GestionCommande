package greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CONSOMME.
 */
public class Consomme {

    private Long id;
    private java.util.Date date;
    private Integer quantite;

    public Consomme() {
    }

    public Consomme(Long id) {
        this.id = id;
    }

    public Consomme(Long id, java.util.Date date, Integer quantite) {
        this.id = id;
        this.date = date;
        this.quantite = quantite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

}
