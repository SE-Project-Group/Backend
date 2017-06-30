package restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Book;
import service.AppService;
import util.SpringContextUtil;

 
@Path("/")
public class RESTfulHelloWorld 
{

	private AppService appService=(AppService) SpringContextUtil.getBean("appService");

	@GET
	@Produces("text/html")
	public Response getStartingPage()
	{
		String output = "<h1>No Zuo No Die!<h1>";
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/getBooks")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book>  AllBooks()
	{
		System.out.println("getBooks");
		List<Book> books = appService.getAllBooks();
		return books;
	}
	
	 @POST
     @Path("/addBook")
	 //@Produces(MediaType.APPLICATION_JSON)
	 //@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces("text/html")
     public String addBook(Book book){
		 System.out.println("addBook");
		 appService.addBook(book);
		 return "success";
     }
	 /*@POST
     @Path("/add")
      public String add(@FormParam("id") String id,
                      @FormParam("name") String name,
                      @FormParam("age") Integer age){
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        map.put(id,student);
        return "success";
    }*/
	 
	 @POST
     @Path("/updateBook")
     public String updateBook(Book book){
		 System.out.println("updateBook");
		 appService.updateBook(book);
		 return "success";
     }
	 
	 @GET
     @Path("/deleteBook/{id}")
     public String deleteById(@PathParam("id") int id){
		 System.out.println("deleteBook");
		 appService.deleteBookById(id);
        return "success";
     }
	
}