package de.fussballmanager.db.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmFactory {

	static EntityManager entityManager;
	
	public static EntityManager getEntityManager(){
		if(entityManager == null){
			
			String persistenceUnitName = "my-app";
			String databaseUrl = System.getenv("DATABASE_URL");
			StringTokenizer st = new StringTokenizer(databaseUrl, ":@/");
			String dbVendor = st.nextToken(); //if DATABASE_URL is set
			String userName = st.nextToken();
			String password = st.nextToken();
			String host = st.nextToken();
			String port = st.nextToken();
			String databaseName = st.nextToken();
			String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory", host, port, databaseName);
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.url", jdbcUrl );
			properties.put("javax.persistence.jdbc.user", userName );
			properties.put("javax.persistence.jdbc.password", password );
			properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName, properties);
			entityManager = entityManagerFactory.createEntityManager();
		}
		return entityManager;
	}
}
