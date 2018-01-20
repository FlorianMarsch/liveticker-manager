import static spark.Spark.exception;

import spark.Request;
import spark.Response;


public class ErrorHandler {
	
	
	
	public ErrorHandler(){

		
	}

	public void handle(Exception e, Request request, Response response) {
		response.status(500);
		response.body(e.getMessage());
		e.printStackTrace();
	}
	
	public void register(){
		exception(RuntimeException.class, (e, request, response) -> {
			this.handle(e, request, response);
		});
	}


	
}
