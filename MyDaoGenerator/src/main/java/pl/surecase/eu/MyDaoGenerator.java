package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema2 = new Schema(3, "greendao");
        Entity produit = schema2.addEntity("Produit");
        produit.addIdProperty().autoincrement();
        produit.addStringProperty("nom");
        produit.addFloatProperty("prix");
        produit.addIntProperty("lot");
        produit.addIntProperty("cosommation");
        new DaoGenerator().generateAll(schema2, args[0]);

        Schema schema3 = new Schema(3, "greendao");
        Entity categorie = schema3.addEntity("Categorie");
        categorie.addIdProperty().autoincrement();
        categorie.addStringProperty("nom");
        categorie.addStringProperty("couleur");
        new DaoGenerator().generateAll(schema3, args[0]);

        Schema schema4 = new Schema(3, "greendao");
        Entity consomme = schema4.addEntity("Consomme");
        consomme.addIdProperty().autoincrement();
        consomme.addDateProperty("date");
        consomme.addIntProperty("quantite");
        new DaoGenerator().generateAll(schema4, args[0]);
    }
}
