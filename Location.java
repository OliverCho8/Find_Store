//object to hold each location details
public class Location
{

	private String store_name;//variable to store store name
	private String store_location;//variable to store store location
	private String address;//variable to store address
	private String city;//variable to store city
	private String state;//varaible to store state
	private String zip_code;//variable to store zip code
	private double latitude;//varialbe to store latitude
	private double longitude;//varaible to store longitude
	private String county;//variable to store county

    //blank constructor
    public Location()
    {
        this.store_name = null;;
        this.store_location = null;
        this.address = null;
        this.city = null;
        this.state = null;
        this.zip_code = null;
        this.latitude = Double.NaN;
        this.longitude = Double.NaN;
        this.county = null;
    }

    //constructor given a complete location detail
	public Location(String store_name, String store_location, String address, String city, String state, String zip_code, double latitude, double longitude, String county)
	{
        super();
        this.store_name = store_name;
        this.store_location = store_location;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
        this.latitude = latitude;
        this.longitude = longitude;
        this.county = county;
    }

    //constructor missing county
    public Location(String store_name, String store_location, String address, String city, String state, String zip_code, double latitude, double longitude)
    {
        super();
        this.store_name = store_name;
        this.store_location = store_location;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
        this.latitude = latitude;
        this.longitude = longitude;
        this.county = null;
    }

    //getter to get store name
    public String getStore_name()
    {
    	return store_name;
    }

    //getter to get store location
    public String getStore_location()
    {
    	return store_location;
    }

    //get to get address
    public String getAddress()
    {
    	return address;
    }

    //getter to get city
    public String getCity()
    {
    	return city;
    }	

    //gettter to get state
    public String getState()
    {
    	return state;
    }

    //getter to get zip code
    public String getZip_code()
    {
    	return zip_code;
    }

    //getter to get latitude
    public double getLatitude()
    {
    	return latitude;
    }

    //getter to get longitude
    public double getLongitude()
    {
    	return longitude;
    }

    //getter to get county
    public String getCounty()
    {
    	return county;
    }
}