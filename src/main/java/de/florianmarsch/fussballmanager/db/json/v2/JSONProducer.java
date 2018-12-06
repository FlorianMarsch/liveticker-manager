package de.florianmarsch.fussballmanager.db.json.v2;

import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.florianmarsch.fussballmanager.db.entity.AbstractEntity;
import de.florianmarsch.fussballmanager.db.json.BindContext;
import de.florianmarsch.fussballmanager.db.json.RequestHandler;
import de.florianmarsch.fussballmanager.db.service.AbstractService;
import spark.Spark;

public abstract class JSONProducer<E extends AbstractEntity> {

	protected String root;
	private RequestHandler<E> handler;
	private ObjectMapper mapper;
	
	public JSONProducer(AbstractService<E> aAbstractService, String aRoot) {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		
		
		register(aAbstractService, aRoot);
	}
	
	private void register(AbstractService<E> aAbstractService, String aRoot) {
		root = "api/"+aRoot;
		System.out.println("Register root : " + root);
		handler = new RequestHandler<E>(aAbstractService);
	}

	public void bindServices(BindContext aBindContext) {
		aBindContext.bind(root, handler);
		
		registerGetAll();
		registerGetById();
	}





	private void registerGetById() {
		Spark.get("/" + root + "/:id", (request, response) -> {


			List<E> found = handler.get(request);
			response.status(200);
			response.type("application/json");
			return mapper.writeValueAsString(found);
		});
	}

	private void registerGetAll() {
		Spark.get("/" + root, (request, response) -> {
			List<E> all = handler.getAll();
			response.status(200);
			response.type("application/json");
			return mapper.writeValueAsString(all);
		});
	}

}
