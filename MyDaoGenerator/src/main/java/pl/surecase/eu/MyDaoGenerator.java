package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(3, "greendao");
        Entity box = schema.addEntity("Box");
        box.addIdProperty();
        box.addStringProperty("name");
        box.addIntProperty("slots");
        box.addStringProperty("description");
        new DaoGenerator().generateAll(schema, args[0]);

        Schema schema2 = new Schema(3, "greendao");
        Entity produit = schema2.addEntity("Produit");
        produit.addIdProperty();
        produit.addStringProperty("nom");
        produit.addFloatProperty("prix");
        produit.addIntProperty("lot");
        produit.addIntProperty("cosommation");
        new DaoGenerator().generateAll(schema2, args[0]);
    }
}
