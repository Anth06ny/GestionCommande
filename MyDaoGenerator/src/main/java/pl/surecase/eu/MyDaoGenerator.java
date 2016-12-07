package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {

        Schema schema = new Schema(10, "greendao");

        Entity categorie = schema.addEntity("Categorie");
        categorie.addIdProperty().autoincrement();
        categorie.addStringProperty("nom");
        categorie.addStringProperty("couleur");
        categorie.setHasKeepSections(true);

        Entity produit = schema.addEntity("Produit");
        produit.addIdProperty().autoincrement();
        produit.addStringProperty("nom");
        produit.addFloatProperty("prix");
        produit.addIntProperty("lot");
        produit.addIntProperty("consommation");
        produit.addBooleanProperty("favori");
        produit.setHasKeepSections(true);

        Entity commande = schema.addEntity("Commande");
        commande.addIdProperty().autoincrement();
        commande.addDateProperty("date");
        // ---------------------------- Ajout du code dans la nuit ------------------------------- //
        //Création de l'entité
        Entity consomme = schema.addEntity("Consomme");
        consomme.addIdProperty().autoincrement();
        consomme.addLongProperty("quantite");

        //Récupération des propriétés des ID
        Property produitId = consomme.addLongProperty("produit").getProperty();
        Property commandeId = consomme.addLongProperty("commande").getProperty();

        //Création des liens des propriétés
        commande.addToMany(consomme, produitId).setName("commandeRef");
        produit.addToMany(consomme, commandeId).setName("produitRef");

        // ------------------------------Fin Ajout du code dans la nuit -------------------------- //

        //Création des clés étrangère et ajout de leur contenu
        Property categorieID = produit.addLongProperty("CategorieID").notNull().getProperty();
        produit.addToOne(categorie, categorieID);

        categorie.addToMany(produit, categorieID);

        new DaoGenerator().generateAll(schema, args[0]);
    }
}
