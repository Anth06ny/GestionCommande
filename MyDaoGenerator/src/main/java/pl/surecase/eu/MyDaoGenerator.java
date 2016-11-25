package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "greendao");
        Entity produit = schema.addEntity("Produit");
        produit.addIdProperty().autoincrement();
        produit.addStringProperty("nom");
        produit.addFloatProperty("prix");
        produit.addIntProperty("lot");
        produit.addIntProperty("cosommation");


        Entity categorie = schema.addEntity("Categorie");
        categorie.addIdProperty().autoincrement();
        categorie.addStringProperty("nom");
        categorie.addStringProperty("couleur");


        Entity consomme = schema.addEntity("Consomme");
        consomme.addIdProperty().autoincrement();
        consomme.addDateProperty("date");
        consomme.addIntProperty("quantite");

        //Création des clés étrangère et ajout de leur contenu
        Property produitID = categorie.addLongProperty("produitID").getProperty();
        categorie.addToMany(produit, produitID);

        Property categorieID = produit.addLongProperty("CategorieID").getProperty();
        produit.addToOne(categorie, categorieID);
        new DaoGenerator().generateAll(schema, args[0]);
    }
}
