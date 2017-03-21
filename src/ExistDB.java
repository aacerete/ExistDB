import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

import java.io.File;

import static org.exist.examples.xmldb.Put.URI;

/**
 * Created by aacerete on 21/03/17.
 */
public class ExistDB {

    private static final String IP = "localhost";
    private static final String PORT = "8080";
    private static String URI = "xmldb:exist://"+IP+":"+PORT+"/exist/xmlrpc/db";
    private static final String driver = "org.exist.xmldb.DatabaseImpl";
    private static final String XMLFile = "Factbook.xml";
    private static final String user = "admin";
    private static final String password = "root";
    private static final String newCollectionName = "novaCollection";

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {


        creaColeccioAfegeixRecurs();

    }

    public static void creaColeccioAfegeixRecurs() throws ClassNotFoundException, XMLDBException, IllegalAccessException, InstantiationException {

        //Creamos el archivo que pasaremos como resource más adelante
        File file = new File(XMLFile);

        // Inicializamos el database Driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        // crear el manager
        DatabaseManager.registerDatabase(database);

        //crear la collection
        Collection parent = DatabaseManager.getCollection(URI, user, password);
        CollectionManagementService c = (CollectionManagementService) parent.getService("CollectionManagementService", "1.0");
        c.createCollection(newCollectionName);
        System.out.println("Coleccion creada");

        //Recuperamos la collection creada
        Collection collection = DatabaseManager.getCollection(URI + "/db/"+newCollectionName, user, password);

        //creamos el resource y lo guardamos
        Resource resource = collection.createResource(XMLFile,"XMLResource");
        resource.setContent(file);
        collection.storeResource(resource);


    }

}
