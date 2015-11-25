import static spark.Spark.exception;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;


public class ErrorHandler {
	
	private String renderdErrorMessage;
	
	public ErrorHandler(){
		FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
		ModelAndView errorModelAndView = new ModelAndView(null, "error.ftl");
		renderdErrorMessage = freeMarkerEngine.render(errorModelAndView);
		
		exception(RuntimeException.class, (e, request, response) -> {
			this.handle(e, request, response);
		});
	}

	public void handle(Exception e, Request request, Response response) {
		response.status(500);
		response.body(renderdErrorMessage);
	}


	
}
