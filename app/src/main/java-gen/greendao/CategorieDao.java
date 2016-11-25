package greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table CATEGORIE.
*/
public class CategorieDao extends AbstractDao<Categorie, Long> {

    public static final String TABLENAME = "CATEGORIE";

    /**
     * Properties of entity Categorie.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Nom = new Property(1, String.class, "nom", false, "NOM");
        public final static Property Couleur = new Property(2, String.class, "couleur", false, "COULEUR");
        public final static Property ProduitID = new Property(3, Long.class, "produitID", false, "PRODUIT_ID");
    };

    private DaoSession daoSession;


    public CategorieDao(DaoConfig config) {
        super(config);
    }
    
    public CategorieDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'CATEGORIE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'NOM' TEXT," + // 1: nom
                "'COULEUR' TEXT," + // 2: couleur
                "'PRODUIT_ID' INTEGER);"); // 3: produitID
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'CATEGORIE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Categorie entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String nom = entity.getNom();
        if (nom != null) {
            stmt.bindString(2, nom);
        }
 
        String couleur = entity.getCouleur();
        if (couleur != null) {
            stmt.bindString(3, couleur);
        }
 
        Long produitID = entity.getProduitID();
        if (produitID != null) {
            stmt.bindLong(4, produitID);
        }
    }

    @Override
    protected void attachEntity(Categorie entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Categorie readEntity(Cursor cursor, int offset) {
        Categorie entity = new Categorie( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // nom
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // couleur
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // produitID
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Categorie entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNom(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCouleur(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setProduitID(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Categorie entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Categorie entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
