import static spark.Spark.exception;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;


public class ErrorHandler {
	
	
	
	public ErrorHandler(){

		
	}

	public void handle(Exception e, Request request, Response response) {
		Gson gson = new Gson();
		response.status(500);
		response.body(gson.toJson(e));
		e.printStackTrace();
	}
	
	public void register(){
		exception(RuntimeException.class, (e, request, response) -> {
			this.handle(e, request, response);
		});
	}


	
}
