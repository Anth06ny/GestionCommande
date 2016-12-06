package greendao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import greendao.Produit;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PRODUIT.
*/
public class ProduitDao extends AbstractDao<Produit, Long> {

    public static final String TABLENAME = "PRODUIT";

    /**
     * Properties of entity Produit.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Nom = new Property(1, String.class, "nom", false, "NOM");
        public final static Property Prix = new Property(2, Float.class, "prix", false, "PRIX");
        public final static Property Lot = new Property(3, Integer.class, "lot", false, "LOT");
        public final static Property Consommation = new Property(4, Integer.class, "consommation", false, "CONSOMMATION");
        public final static Property Favori = new Property(5, Boolean.class, "favori", false, "FAVORI");
        public final static Property CategorieID = new Property(6, long.class, "CategorieID", false, "CATEGORIE_ID");
    };

    private DaoSession daoSession;

    private Query<Produit> categorie_ProduitListQuery;

    public ProduitDao(DaoConfig config) {
        super(config);
    }
    
    public ProduitDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PRODUIT' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'NOM' TEXT," + // 1: nom
                "'PRIX' REAL," + // 2: prix
                "'LOT' INTEGER," + // 3: lot
                "'CONSOMMATION' INTEGER," + // 4: consommation
                "'FAVORI' INTEGER," + // 5: favori
                "'CATEGORIE_ID' INTEGER NOT NULL );"); // 6: CategorieID
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PRODUIT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Produit entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String nom = entity.getNom();
        if (nom != null) {
            stmt.bindString(2, nom);
        }
 
        Float prix = entity.getPrix();
        if (prix != null) {
            stmt.bindDouble(3, prix);
        }
 
        Integer lot = entity.getLot();
        if (lot != null) {
            stmt.bindLong(4, lot);
        }
 
        Integer consommation = entity.getConsommation();
        if (consommation != null) {
            stmt.bindLong(5, consommation);
        }
 
        Boolean favori = entity.getFavori();
        if (favori != null) {
            stmt.bindLong(6, favori ? 1l: 0l);
        }
        stmt.bindLong(7, entity.getCategorieID());
    }

    @Override
    protected void attachEntity(Produit entity) {
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
    public Produit readEntity(Cursor cursor, int offset) {
        Produit entity = new Produit( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // nom
            cursor.isNull(offset + 2) ? null : cursor.getFloat(offset + 2), // prix
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // lot
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // consommation
            cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0, // favori
            cursor.getLong(offset + 6) // CategorieID
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Produit entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNom(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPrix(cursor.isNull(offset + 2) ? null : cursor.getFloat(offset + 2));
        entity.setLot(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setConsommation(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setFavori(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
        entity.setCategorieID(cursor.getLong(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Produit entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Produit entity) {
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
    
    /** Internal query to resolve the "produitList" to-many relationship of Categorie. */
    public List<Produit> _queryCategorie_ProduitList(long CategorieID) {
        synchronized (this) {
            if (categorie_ProduitListQuery == null) {
                QueryBuilder<Produit> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.CategorieID.eq(null));
                categorie_ProduitListQuery = queryBuilder.build();
            }
        }
        Query<Produit> query = categorie_ProduitListQuery.forCurrentThread();
        query.setParameter(0, CategorieID);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getCategorieDao().getAllColumns());
            builder.append(" FROM PRODUIT T");
            builder.append(" LEFT JOIN CATEGORIE T0 ON T.'CATEGORIE_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Produit loadCurrentDeep(Cursor cursor, boolean lock) {
        Produit entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Categorie categorie = loadCurrentOther(daoSession.getCategorieDao(), cursor, offset);
         if(categorie != null) {
            entity.setCategorie(categorie);
        }

        return entity;    
    }

    public Produit loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Produit> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Produit> list = new ArrayList<Produit>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Produit> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Produit> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
