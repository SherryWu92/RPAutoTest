package cn.edu.nju.software.rp.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/helloworld")  
public class HelloRS {
   
   @GET
   @Produces(MediaType.TEXT_PLAIN)  
   public String getClichedMessage() {
       return "Hello World";
   }
}