package greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CATEGORIE.
 */
public class Categorie {

    private Long id;
    private String nom;
    private Float couleur;

    public Categorie() {
    }

    public Categorie(Long id) {
        this.id = id;
    }

    public Categorie(Long id, String nom, Float couleur) {
        this.id = id;
        this.nom = nom;
        this.couleur = couleur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Float getCouleur() {
        return couleur;
    }

    public void setCouleur(Float couleur) {
        this.couleur = couleur;
    }

}
