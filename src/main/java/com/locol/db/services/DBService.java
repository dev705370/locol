package com.locol.db.services;

import java.util.List;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Named
@Default
public class DBService {
	private final Morphia morphia;
	private final Datastore datastore;

	@Inject
	public DBService() {
		morphia = new Morphia();

		morphia.mapPackage("com.locol.db.entities");

		MongoClientURI uri = new MongoClientURI(
				"mongodb://dev-locol:RUDnNuisW20X0ItB@cluster0-shard-00-00-wcouq.mongodb.net:27017,cluster0-shard-00-01-wcouq.mongodb.net:27017,cluster0-shard-00-02-wcouq.mongodb.net:27017/locoldb?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");

		datastore = morphia.createDatastore(new MongoClient(uri), "locoldb");
		datastore.ensureIndexes();
	}

	public Object save(Object obj) {
		datastore.save(obj);
		return obj;
	}

	public <T> List<T> getAll(Class<T> clazz) {
		Query<T> query = datastore.createQuery(clazz);
		return query.asList();
	}

}
