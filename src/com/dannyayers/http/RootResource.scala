package com.dannyayers.http



import com.hp.hpl.jena.tdb._
import com.dannyayers.core._
import scala.xml.Elem
import com.hp.hpl.jena.rdf.model._
import com.dannyayers.rdf._
import com.dannyayers.render._
import com.dannyayers.users._
import com.dannyayers.util._
import javax.ws.rs._
import javax.ws.rs.core._
import org.apache.commons.codec.binary._
import org.apache.http.protocol._
import org.apache.http.client.protocol._
import org.apache.http.auth._
import org.apache.http.impl.auth._
import org.apache.http.impl.client._
import org.apache.http.client.methods._
import org.apache.http.util._
import org.apache.http._
import org.apache.http.impl.client._
import org.apache.http.client._
import org.apache.http.impl.client.BasicAuthCache._


@Path("/")
class RootResource(model : Model) {

	@GET
	@Path("index.html")
	@Produces(Array("text/html"))
	def indexAlias = {
		renderIndexPage
	}

	// tags HTML
	@GET
    @Path("tag/{path:.+}")
    @Produces(Array("text/html"))
	def renderTagPage(@Context uriInfo : UriInfo) = {
    	val requestUri = uriInfo.getAbsolutePath() // yuck - refactor
		val uri = Main.BASE_URI+requestUri.getPath
		(new PageHandler()).renderTagPage(uri)
	}
	
	// items
    @GET
	@Path("{path:.+}")
	@Produces(Array("text/html"))
	def renderItem(@Context uriInfo : UriInfo) = {
		(new PageHandler()).renderItem(uriInfo)
	}
    
    // edit item
    
    @GET
	@Path("edit/{path:.+}")
	@Produces(Array("text/html"))
	def renderItemForm(@Context uriInfo : UriInfo) = {
    	println("THIS IS EDIT")
    	val requestUri = uriInfo.getAbsolutePath() // yuck - refactor
		var uri = requestUri.getPath
		uri = uri.replace("/edit/", "/") // yuck again
		uri = Main.BASE_URI + uri
		println("URI= "+uri)
		(new PageHandler()).renderItemForm(uri) 
	}
	
    
    // main index page
    @GET
    @Produces(Array("text/html"))
	def renderIndexPage = {
		(new PageHandler()).renderIndexPage
	}
   
    // main feed page 
	@GET
	@Path("feed")
	@Produces(Array("application/rdf+xml"))
	def renderFeed = {
		(new FeedHandler()).renderFeed(Queries.recentItems)
	}
	
	/* took too long
	@GET
	@Path("sitemap")
	@Produces(Array("application/xml"))
	def renderSitemap = {
		(new SitemapHandler()).renderSitemap(Queries.allItems)
	}
  */
	
	@GET
	@Path("feed/rdf")
	@Produces(Array("application/rdf+xml"))
	def feedAlias1 = {
		renderFeed
	}
 
	@GET
	@Path(".rss")
	@Produces(Array("application/rdf+xml"))
	def feedAlias2 = {
		renderFeed
	}
	
// tags RDF
	@GET
    @Path("feed/tag/{path:.+}")
    @Produces(Array("application/rdf+xml"))
	def renderTagFeed(@Context uriInfo : UriInfo) = {
		System.out.println("trying to render tag feed")
    	val requestUri = uriInfo.getAbsolutePath() // yuck - refactor
		var uri = "http://dannyayers.com"+requestUri.getPath
		uri = uri.replace("feed/tag/", "tag/") // yuck again
		(new FeedHandler()).renderFeed(Queries.taggedItems, uri)
	}	
	
	
	@GET
	@Path("index.rdf")
	@Produces(Array("application/rdf+xml"))
	def feedAlias3 = {
		renderFeed
	}
    
// *** User Stuff *** //
	@POST
	@Path("register")
	@Produces(Array("text/plain")) 
	def register(@FormParam("user") user : String, @FormParam("password") password : String, 
			@FormParam("email") email : String, @FormParam("foaf") foaf : String) = {
		(new LoginHandler()).register(user, password, email, foaf)
	}
	
	@GET
	@Path("loginp")
	@Produces(Array("text/plain"))
	def login(@Context uriInfo : UriInfo) = {
		(new LoginHandler()).login(uriInfo)
	}
	
	@GET
	@Path("auth")
	@Produces(Array("text/plain")) 
	def auth(@HeaderParam("Authorization") auth : String) = {
		(new LoginHandler()).auth(auth)
	}
	
	@POST
	@Path("auth")
	@Produces(Array("text/plain")) 
	def authProxy(@FormParam("user") user : String, @FormParam("password") password : String) = {
		(new LoginHandler()).authProxy(user,password)
	}

	@GET
	@Path("sparql{.+}") // {path:.+}
	@Produces(Array("text/plain")) //application/sparql-results+xml
	def sparql(@Context uriInfo : UriInfo) = {
		(new QueryHandler()).sparql(uriInfo)
	}
    
 
    
	@POST
	@Path("addpost")
	@Produces(Array("text/html"))
	def handle(@FormParam("user") user : String, @FormParam("password") password : String,
			   @FormParam("title") title: String, @FormParam("uri") uri: String, @FormParam("delete") delete: String, @FormParam("content") content: String, 
			@FormParam("tags") tags : String, @FormParam("date") date : String) = {
		(new PostContentHandler()).handle(user, password, title, uri, delete, content, tags, date)
	}

	@POST
	@Path("upload")
	@Consumes(Array("multipart/form-data")) // ??
	@Produces(Array("text/html"))
      def handleUpload(@FormParam("file") file : java.io.InputStream)  {
		var b = file.read()
		System.out.println("Printing Stream")
while ( b != -1)
 {
     System.out.println("b="+b)
     b = file.read()
 }

      }
	
	@POST
	@Path("shutdown")
	@Produces(Array("text/html"))
	def shutdown(@FormParam("user") user : String, @FormParam("password") password : String) = {
		(new AdminHandler()).shutdown(user, password)
	}
	
	// Admin
	
	@POST
	@Path("backupuserdb")
	@Produces(Array("text/html"))
	def backupuserdb() = {
		(new AdminHandler()).backupUserDB
	}
	
	@POST
	@Path("generatesitemap")
	@Produces(Array("text/html"))
	def generatesitemap() = {
		(new AdminHandler()).generateSitemap
	}
	
		@POST
	@Path("generatearchives")
	@Produces(Array("text/html"))
	def generatearchives() = {
		(new AdminHandler()).generateArchives
	}
	
	@POST
	@Path("revertuserdb")
	@Produces(Array("text/html"))
	def revertuserdb() = {
		(new AdminHandler()).revertUserDB
	}
	
		@POST
	@Path("backupcontentdb")
	@Produces(Array("text/html"))
	def backupcontentdb() = {
		(new AdminHandler()).backupContentDB
	}
	
	@POST
	@Path("revertcontentdb")
	@Produces(Array("text/html"))
	def revertcontentdb() = {
		(new AdminHandler()).revertContentDB
	}
}
