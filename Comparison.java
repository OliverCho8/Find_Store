import java.util.ArrayList;
import java.util.*;
import java.lang.*;
import java.io.*;


public class Comparison{

	private double minDist;//variable holds distance to closest location
	private double userLatitude;//variable holds the user entered locations latitude
	private double userLongitude;//variable holds the user entered locations longitude
	private Location closest;//variable holds the closest location to user entered location
	private ArrayList<Location> locationList;//varaible holds list of store locations from csv
	private Boolean miOrKm;//varialbe holds whether we want mi or km
	private Boolean txtOrJson;//variable holds whether we want txt or Json

	//blank constructor
	public Comparison()
	{
		minDist=Double.MAX_VALUE;
		userLongitude=0;
		userLatitude=0;
		closest=new Location();
		locationList=new ArrayList<Location>();
		miOrKm=false;
		txtOrJson=false;
	}

	//constructor given location list, user location latitude, user location longitude, and user code
	public Comparison(ArrayList<Location> userlocationList, double enteredLatitude, double enteredLongitude, int[] userCode)
	{
		minDist=Double.MAX_VALUE;
		userLongitude=enteredLongitude;
		userLatitude=enteredLatitude;
		closest=new Location();
		locationList=userlocationList;

		//if we want km set minOrKm to true
		if(userCode[2]==1)
		{
			miOrKm=true;
		}
		else 
		{
			miOrKm=false;
		}

		//if we want Json set txtOrJson to true
		if(userCode[3]==1)
		{
			txtOrJson=true;
		}
		else
		{
			txtOrJson=false;
		}
	}

	//getter to get minDist
	public double getMinDist()
	{
		return minDist;
	}

	//getter to get userLatitude
	public double getUserLatitude()
	{
		return userLatitude;
	}

	//getter to the userLongitude
	public double getUserLongitude()
	{
		return userLongitude;
	}

	//getter to get closest
	public Location getClosest()
	{
		return closest;
	}

	//getter to get locationList
	public ArrayList<Location> getLocationList()
	{
		return locationList;
	}

	//getter to get miOrKm
	public Boolean getMiOrKm()
	{
		return miOrKm;
	}

	//getter to get txtOrJson
	public Boolean getTxtOrJson()
	{
		return txtOrJson;
	}
	//fucntion runs through location list and finds the closest location
	public void search()
	{
		//for each location in location list
		for(int i=0;i<locationList.size();i++)
		{
			//calculate distance from user location to store location
			double temp=distance(userLatitude, userLongitude,locationList.get(i).getLatitude(), locationList.get(i).getLongitude());
			//if the location is closer than the current closest location replace the closest location
			if (temp<minDist)
			{
				minDist=temp;
				closest=locationList.get(i);
			}
		}
		result();

	}

	//function calculates the distance betweeen two latitdue and longitude points in miles
	public double distance(double lat1, double lon1, double lat2, double lon2)
    {
    	//if they two points match the distance is 0
    	if ((lat1 == lat2) && (lon1 == lon2)) 
    	{
			return 0;
		}
		else 
		{
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			return dist;
		}
		
    }

    //function coverts from miles to Km
    public double milesToKm(double mi)
    {
    	double dist= mi *1.609344;
    	return dist;
    }

    //function returns the proper input based on what the user wants
    public void result()
    {
    	//if we want a json output run the json function
    	if(txtOrJson==true)
    	{
    		json();
    	}
    	//otherwise we want txt output
    	else
    	{
    		System.out.println(closest.getStore_name()+ "," + closest.getStore_location()+ "," +  closest.getAddress()+ "," + closest.getCity()+ "," + closest.getState()+ "," + closest.getZip_code()+ "," + closest.getLatitude()
    		+ "," + closest.getLongitude()+ "," + closest.getCounty());

    	}
    	
    	//if we want km output we run the km output
    	if(miOrKm==true)
    	{
    		minDist=milesToKm(minDist);
    		System.out.println("The distance in km is " + minDist);
    	}
    	//otherwise we want the miles output
    	else
    	{
    		System.out.println("The distance in miles is " + minDist);
    	}
    }

    //function reutrns a json output in the command line
    public void json()
    {
    	System.out.println("var location = {");
    	System.out.println("	\"store_name\" : " + "\"" + closest.getStore_name() + "\"" );
    	System.out.println("	\"store_location\" : " + "\"" + closest.getStore_location()+ "\"");
    	System.out.println("	\"address\" : " + "\"" + closest.getAddress()+ "\"");
    	System.out.println("	\"city\" : " + "\"" + closest.getCity()+ "\"");
    	System.out.println("	\"state\" : " + "\"" + closest.getState()+ "\"");
    	System.out.println("	\"zip_code\" : " + "\"" + closest.getZip_code()+ "\"");
    	System.out.println("	\"latitude\" : " + "\"" + closest.getLatitude()+ "\"");
    	System.out.println("	\"longitude\" : " + "\"" + closest.getLongitude()+ "\"");
    	System.out.println("	\"county\" : " + "\"" + closest.getCounty()+ "\"");
    	System.out.println("};");

    }
  
}