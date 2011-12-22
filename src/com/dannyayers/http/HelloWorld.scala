package com.dannyayers.http

import javax.ws.rs._
import javax.ws.rs.core._

@Path("/hello-world")
class HelloWorld {

  //instead of returning a String we could have a MEssageBodyWriter for scala.xml.Elem.
  @GET
  @Produces(Array("application/xhtml+xml"))
  def sayHello = <html xmlns="http://www.w3.org/1999/xhtml">
    <body>
    <p>Hello Danny</p>
    </body>
  </html>.toString
}
