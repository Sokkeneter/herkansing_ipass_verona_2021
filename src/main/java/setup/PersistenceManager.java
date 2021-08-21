package setup;

import assets.Restaurant;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import java.io.*;

public class PersistenceManager implements Serializable {
    private final static String ENDPOINT = "https://veronabepblobstorage.blob.core.windows.net/";
    private final static String SASTOKEN = "?sv=2020-02-10&ss=bfqt&srt=sco&sp=rwdlacuptfx&se=2021-08-31T23:00:26Z&st=2021-06-22T15:00:26Z&spr=https&sig=nqRhb366nanNR6ESY%2FzWP%2Bmtk9nCUqP0ij%2BSa8OxoMQ%3D";
    private final static String CONTAINER = "restaurantcontainer";

    private static BlobContainerClient blobcontainer = new BlobContainerClientBuilder()
                                                           .endpoint(ENDPOINT)
                                                           .sasToken(SASTOKEN)
                                                           .containerName(CONTAINER)
                                                           .buildClient();


        public static void LoadRestaurantFromAzure() throws IOException, ClassNotFoundException {
            if(blobcontainer.exists()){
                BlobClient blobClient = blobcontainer.getBlobClient("restaurant_blob");

                if(blobClient.exists()){

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    blobClient.download(baos);

                    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                    ObjectInputStream ois = new ObjectInputStream(bais);

                    Object loadedObject = ois.readObject();
                    if(loadedObject instanceof Restaurant){
                        Restaurant loadedRestaurant = (Restaurant) loadedObject;
                        Restaurant.setCurrentRestaurant(loadedRestaurant);
                        System.out.println("restaurant loaded: "+ loadedRestaurant)
                        ;}

                    bais.close();
                    baos.close();
                }
            }else throw new IllegalArgumentException("container not found :'(");
        }

        public static void saveRestaurantToAzure()throws IOException {
        if(!blobcontainer.exists()){
            blobcontainer.create();
        }

        BlobClient blob = blobcontainer.getBlobClient("restaurant_blob");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(Restaurant.getCurrentRestaurant());
            System.out.println("restaurant being saved: "+Restaurant.getCurrentRestaurant().getTables());

        byte[] bytez = baos.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(bytez);
        blob.upload(bais, bytez.length, true);

        bais.close();
        baos.close();

        }
    }

