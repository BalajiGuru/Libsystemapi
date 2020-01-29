package com.full.sample;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

//import com.google.api.client.util.Base64;
import com.google.api.client.util.IOUtils;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@SessionAttributes({"User","userImage"})
@Controller
public class BooksController {

	@RequestMapping(value="/Verify",method = RequestMethod.POST)
	public ModelAndView verifyUser(@RequestParam("username") String username,@RequestParam("password") String password) throws UnsupportedEncodingException
	{
		DatastoreService ds=DatastoreServiceFactory.getDatastoreService();
		
		
		ModelAndView mv=new ModelAndView();
		Entity e=new Entity("User");
		Filter f=new FilterPredicate("username",FilterOperator.EQUAL,username);
		Query q=new Query("User").setFilter(f);
		List<Entity> al=ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		String base64Encoded="";
		try
		{
			
		boolean flag=false;
		for(Entity e1:al)
		{
			
			
			String passcheck=e1.getProperty("password").toString();
			flag=BCrypt.checkpw(password,passcheck);
			Blob file=(Blob)e1.getProperty("Image");
		    byte[] b=file.getBytes();
			byte[] encodeBase64 = Base64.encodeBase64(b);
		     base64Encoded = new String(encodeBase64, "UTF-8");
	
			System.out.println(base64Encoded);
		}
		if(flag==true)
		{
			mv.setViewName("index.jsp");
			mv.addObject("User",username);
		    mv.addObject("userImage", base64Encoded );
			
			return mv;
		}
		else
		{
			mv.setViewName("loginerror.jsp");
			return mv;
			
		}
		}
		catch(IllegalArgumentException exception)
		{
			mv.setViewName("loginerror.jsp");
			return mv;
		
		}
		
	}
		@RequestMapping(value="/register",method=RequestMethod.POST,produces = "application/json")
		public @ResponseBody Map  addUser(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("image") MultipartFile image) throws IOException
		{
			DatastoreService ds=DatastoreServiceFactory.getDatastoreService();
			Map m=new HashMap();
			Entity e=new Entity("User");
			
			//Filter f=new FilterPredicate("username",FilterOperator.EQUAL,username);
			//Query q=new Query("User").setFilter(f);		
			//List<Entity> al=ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
			Blob file=new Blob(image.getBytes());
	//		if(al.size()==0)
			{
				
				e.setProperty("username",username);
				e.setProperty("password",BCrypt.hashpw(password,BCrypt.gensalt()));
				e.setProperty("Image",file);
				ds.put(e);
				m.put("success",true);
				m.put("message","Registered Successfully");
			}
		/*	else
			{
				m.put("success",false);
				m.put("message","User already registered");
			
			}
			
		*/	
			return m;
		}
		
	
		@RequestMapping(value="/add",method = RequestMethod.POST,produces = "application/json")
		public @ResponseBody Map addBooks(@RequestBody Book b ) 
		{
			DatastoreService ds=DatastoreServiceFactory.getDatastoreService();
			
			Map m=new HashMap();
			List<String> genre=new ArrayList();
			for(int i=0,l=b.getGenre().size();i<l;i++)
			{
				genre.add(b.getGenre().get(i).trim());
			}

	        for (int i = 0; i < genre.size(); i++) {
	        
	        	for(int j=i+1;j<genre.size();j++)
	        	{
	        		String a1 = genre.get(i);
	        		String a2=genre.get(j);
	        		if (a1.equalsIgnoreCase(a2)) {
	                genre.remove(a2);
	        		}
	        	}
	        }


			
			Filter f=new FilterPredicate("isbn",FilterOperator.EQUAL,b.getIsbn());
			Query q=new Query("Book").setFilter(f);
			List<Entity> al=ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
			if(al.size()==0)
			{
				Entity e=new Entity("Book");
				
				e.setProperty("bname",b.getBname());
				e.setProperty("author",b.getAuthor());
				e.setProperty("pages",b.getPages());
				e.setProperty("publisher",b.getPublisher());
				e.setProperty("isbn",b.getIsbn());
				e.setProperty("genre",genre);
				
				ds.put(e);
				
				b.setId(e.getKey().getId());
				b.setGenre(genre);
				m.put("book",b);
				m.put("success",true);
			}
			else
			{
				m.put("success",false);
				m.put("reason","Isbn value already available!");
			}
			
			return m;
			
		}
		@RequestMapping(value="/view",method = RequestMethod.GET,produces = "application/json")
		public @ResponseBody List viewBooks( ) 
		{
			
			
			
			DatastoreService ds=DatastoreServiceFactory.getDatastoreService();
				
			
			Query q=new Query("Book");
			
			List<Entity> al=ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
			List<Book> bl=new ArrayList<Book>();
				
			for(Entity e:al)
			{
				Book b=new Book();
				ArrayList<String> al1=(ArrayList<String>)e.getProperty("genre");
				System.out.println(e.getProperty("genre"));
				b.setBname(e.getProperty("bname").toString());
				b.setAuthor(e.getProperty("author").toString());
				b.setPages(e.getProperty("pages").toString());
				b.setGenre(al1);
				b.setIsbn(e.getProperty("isbn").toString());
				b.setPublisher(e.getProperty("publisher").toString());
				bl.add(b);
				
			}

			
			return bl;
			
		}
		@RequestMapping(value="/search",method = RequestMethod.POST,produces = "application/json")
		public @ResponseBody List searchBooks(@RequestBody String searchby) 
		{
			
		
			DatastoreService ds=DatastoreServiceFactory.getDatastoreService();
			Map<String,String> mp=new HashMap<String, String>();
			ObjectMapper mapper=new ObjectMapper();
			try
			{
				
				 mp=mapper.readValue(searchby,Map.class);
			
			}
			catch (IOException e) {
				
				e.printStackTrace();
			
			}
		
			Filter f=new FilterPredicate(mp.get("searchby"),FilterOperator.EQUAL,mp.get("searchtext"));
			Query q=new Query("Book").setFilter(f);
		
			List<Entity> al=ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
			
			List<Book> bl=new ArrayList<Book>();
			
			for(Entity e:al)
			{
				
					Book b=new Book();
					ArrayList<String> al1=(ArrayList<String>)e.getProperty("genre");
					b.setBname(e.getProperty("bname").toString());
					b.setAuthor(e.getProperty("author").toString());
					b.setPages(e.getProperty("pages").toString());
					b.setGenre(al1);
					b.setIsbn(e.getProperty("isbn").toString());
					b.setPublisher(e.getProperty("publisher").toString());
			 		
					bl.add(b);
			
			}
			
			
			return bl;
			
		}
		
		@RequestMapping("/logout")
		public ModelAndView log() 
		{
			ModelAndView mv=new ModelAndView("logout.jsp");
			return mv;
		}
		
		/*
		@RequestMapping(value="/UploadImage",method = RequestMethod.POST)
		public  ModelAndView uploaddp(@RequestParam("dp") MultipartFile img,HttpServletRequest request) throws IOException
		{
			
			DatastoreService ds=DatastoreServiceFactory.getDatastoreService();
			
			Blob file=new Blob(img.getBytes());
			HttpSession session=request.getSession();
			String username=session.getAttribute("User").toString();
			Filter f=new FilterPredicate("username",FilterOperator.EQUAL,username);
			Query q=new Query("User").setFilter(f);
			List<Entity> al=ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
			for(Entity e:al)
			{
				e.setProperty("Image",file);
				ds.put(e);
			}
			
			System.out.println(img);
			ModelAndView mv=new ModelAndView();
			mv.setViewName("home.jsp");
			return mv;
			
		
		}
		*/
		@RequestMapping("/")
		public ModelAndView homepage()
		{
			ModelAndView mv=new ModelAndView();
			mv.setViewName("home.jsp");
			return mv;
			
		}
		
}
