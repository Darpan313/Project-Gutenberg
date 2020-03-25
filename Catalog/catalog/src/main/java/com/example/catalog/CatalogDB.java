package com.example.catalog;
import java.util.ArrayList;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
public class CatalogDB {
    public ArrayList<Catalog> getAllCatalogWithKeyword(String keyword){
        ArrayList<Catalog> res = new ArrayList<>();
        /*String template = "mongodb://%s:%s@%s/sample-database?replicaSet=rs0&readpreference=%s";
        String username = "darpan";
        String password = "password";
        String clusterEndpoint = "sample-cluster.node.us-east-1.docdb.amazonaws.com:27017";
        String readPreference = "secondaryPreferred";
        String connectionString = String.format(template, username, password, clusterEndpoint, readPreference);

        MongoClientURI clientURI = new MongoClientURI(connectionString);
        MongoClient mongoClient = new MongoClient(clientURI);*/
        Catalog c =  null;
        MongoClient mongoClient = null;
        try{
            Block<Document> printBlock = new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    Catalog c = new Catalog(document.getString("Author"),document.getString("Title"));
                    //System.out.println(document.toJson());
                    res.add(c);
                }
            };
            mongoClient = new MongoClient("ec2-35-173-247-4.compute-1.amazonaws.com",27018);
            MongoDatabase gtbrgDB = mongoClient.getDatabase("gutenberg");
            MongoCollection<Document> catalogueCollection = gtbrgDB.getCollection("catalogue");
            catalogueCollection.createIndex(Indexes.text("Author"));
            catalogueCollection.find(Filters.text(keyword)).forEach(printBlock);
            catalogueCollection.dropIndex(Indexes.text("Author"));
            catalogueCollection.createIndex(Indexes.text("Title"));
            catalogueCollection.find(Filters.text(keyword)).forEach(printBlock);
            catalogueCollection.dropIndex(Indexes.text("Title"));

        }catch(MongoException e){
            e.printStackTrace();
        }
        finally {
            if(mongoClient != null){
                mongoClient.close();
            }
        }
        return res;
    }
}
