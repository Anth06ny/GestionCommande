package greendao;

import greendao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CONSOMME.
 */
public class Consomme {

    private Long id;
    private Long quantite;
    private Long produit;
    private Long commande;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ConsommeDao myDao;

    private Produit produitRef;
    private Long produitRef__resolvedKey;


    public Consomme() {
    }

    public Consomme(Long id) {
        this.id = id;
    }

    public Consomme(Long id, Long quantite, Long produit, Long commande) {
        this.id = id;
        this.quantite = quantite;
        this.produit = produit;
        this.commande = commande;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getConsommeDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Long getProduit() {
        return produit;
    }

    public void setProduit(Long produit) {
        this.produit = produit;
    }

    public Long getCommande() {
        return commande;
    }

    public void setCommande(Long commande) {
        this.commande = commande;
    }

    /** To-one relationship, resolved on first access. */
    public Produit getProduitRef() {
        Long __key = this.produit;
        if (produitRef__resolvedKey == null || !produitRef__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProduitDao targetDao = daoSession.getProduitDao();
            Produit produitRefNew = targetDao.load(__key);
            synchronized (this) {
                produitRef = produitRefNew;
            	produitRef__resolvedKey = __key;
            }
        }
        return produitRef;
    }

    public void setProduitRef(Produit produitRef) {
        synchronized (this) {
            this.produitRef = produitRef;
            produit = produitRef == null ? null : produitRef.getId();
            produitRef__resolvedKey = produit;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
