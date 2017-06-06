package com.locol.db.services;

import javax.ejb.Singleton;
import javax.inject.Inject;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Singleton
public class DBConnectionService {
	private final Morphia morphia;
	private final Datastore datastore;

	@Inject
	public DBConnectionService() {
		morphia = new Morphia();

		morphia.mapPackage("com.locol.db.entities");

		MongoClientURI uri = new MongoClientURI(
				"mongodb://dev-locol:RUDnNuisW20X0ItB@cluster0-shard-00-00-wcouq.mongodb.net:27017,cluster0-shard-00-01-wcouq.mongodb.net:27017,cluster0-shard-00-02-wcouq.mongodb.net:27017/locoldb?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");

		datastore = morphia.createDatastore(new MongoClient(uri), "locoldb");
		getDatastore().ensureIndexes();
	}

	public Datastore getDatastore() {
		return datastore;
	}
}
