import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;


public class Address
{
	//api key
	public static final String key="AIzaSyAH1rOikQsMIrG05tpAk9v8nawaVG9V1t0"; 
	//url address for searching with an address
	public static final String apiAddress="https://maps.googleapis.com/maps/api/geocode/xml?address=";
	//url address for searching with a zip code
	public static final String apiZip="https://maps.googleapis.com/maps/api/geocode/xml?postal_code=";

	private String[] userInputParsed;
	private int[] userCode;
	private double latitude;
	private double longitude;

	//blank constructor
	public Address()
	{
		userInputParsed=new String[4];
		userCode=new int[4];
		latitude=0;
		longitude=0;
	}

	//constructor given parsed user input and user code
	public Address(String[] userInputParsed1, int[] userCode1)
	{	
		userInputParsed=userInputParsed1;
		userCode=userCode1;
		latitude=0;
		longitude=0;
	}

	//function to return latitude
	public double getLatitude()
	{
		return latitude;
	}

	//function to return longitude
	public double getLongitude()
	{
		return longitude;
	}

	//getter to get userInputParsed
	public String[] getUserInputParsed()
	{
		return userInputParsed;
	}

	//getter to get userCode
	public int[] getUserCode()
	{
		return userCode;
	}

	//function to find latitude and longitude of location
	public void latlng() throws Exception
	{
		//if usercode signifies we are using zipcode
     	if(userCode[1]==1)
     	{
	     	int responseCode = 0;

	     	//create the necessary url
			String api= apiZip + URLEncoder.encode(userInputParsed[1],"UTF-8")+ "&key="+key;
			URL url=new URL(api);

			//connect the url to the internet
			HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
	        httpConnection.connect();
	        responseCode = httpConnection.getResponseCode();

	        if(responseCode == 200)
		    {
		    	//create and read document
			    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
			    Document document = builder.parse(httpConnection.getInputStream());
			    XPathFactory xPathfactory = XPathFactory.newInstance();
			    XPath xpath = xPathfactory.newXPath();
			    XPathExpression expr = xpath.compile("/GeocodeResponse/status");
			    String status = (String)expr.evaluate(document, XPathConstants.STRING);

			    if(status.equals("OK"))
			    {
			    	//get and assign latitude
			    	expr = xpath.compile("//geometry/location/lat");
			        latitude = Double.parseDouble((String)expr.evaluate(document, XPathConstants.STRING));

			        //get and assign longitude
			        expr = xpath.compile("//geometry/location/lng");
			        longitude = Double.parseDouble((String)expr.evaluate(document, XPathConstants.STRING));

			    }
			    else
			    {
			    	throw new Exception("Error from the API - response status: "+status);
			    }
	    	}

     	}

     	//otherwise use address
     	int responseCode = 0;

     	//create necessary url
		String api= apiAddress + URLEncoder.encode(userInputParsed[1],"UTF-8")+ "&key="+key;
		URL url=new URL(api);

		//connect url to internet
		HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
        httpConnection.connect();
        responseCode = httpConnection.getResponseCode();

        if(responseCode == 200)
	    {
	    	//build document
		    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
		    Document document = builder.parse(httpConnection.getInputStream());
		    XPathFactory xPathfactory = XPathFactory.newInstance();
		    XPath xpath = xPathfactory.newXPath();
		    XPathExpression expr = xpath.compile("/GeocodeResponse/status");
		    String status = (String)expr.evaluate(document, XPathConstants.STRING);
		    if(status.equals("OK"))
		    {
		    	//get and assign latitude
		    	expr = xpath.compile("//geometry/location/lat");
		        latitude = Double.parseDouble((String)expr.evaluate(document, XPathConstants.STRING));

		        //get and assign longitude
		        expr = xpath.compile("//geometry/location/lng");
		        longitude = Double.parseDouble((String)expr.evaluate(document, XPathConstants.STRING));

		    }
		    else
		    {
		    	throw new Exception("Error from the API - response status: "+status);
		    }
	    }
	}

}