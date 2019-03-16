import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStreamReader;

public class Parser
{
	private String COMMA_DELIMITER;//variable to hold the split character
	private ArrayList<Location> locationList;//variable to hold location list

	//blank constructor
	public Parser()
	{
		COMMA_DELIMITER=",";
		ArrayList<Location> locationList = new ArrayList<Location>();
	}

	//getter to get location list
	public ArrayList<Location> getLocationList()
	{
		return locationList;
	}

	//function to parse the csv given the csv
	public void parseCSV(String filename) 
	{
		//create a buffereader
		BufferedReader br = null;

		//int to keep track of csv line number
		int lineNumber=2;

		//Create an arraylist holding locations objects
		ArrayList<Location> temp = new ArrayList<Location>();
        try
        {
            //Reading the csv file
            br = new BufferedReader(new FileReader(filename));
          
            //Skip first line
            String line = null;
            br.readLine();

            //Reading from the second line
            while ((line = br.readLine()) != null) 
            {	
            	//split each line into 9 division based on commas
                String[] locationInfo = line.split(COMMA_DELIMITER); 

                //if full object
                if(locationInfo.length == 9 )
                {
                    //Save the location detail in a Location object
                    Location loc = new Location(locationInfo[0], locationInfo[1], locationInfo[2], locationInfo[3],
                    	locationInfo[4], locationInfo[5], Double.parseDouble(locationInfo[6]),
                    	Double.parseDouble(locationInfo[7]), locationInfo[8]);
                    //add location info to arraylist locationList
                    temp.add(loc);

                }
                // else if object is missing county
                else if(locationInfo.length ==8)
                {
                	Location loc = new Location(locationInfo[0], locationInfo[1], locationInfo[2], locationInfo[3],
                    	locationInfo[4], locationInfo[5], Double.parseDouble(locationInfo[6]),
                    	Double.parseDouble(locationInfo[7]));
                	//add location info to arraylist locationList
                    temp.add(loc);
                }
                // else if there is an extra comma in the address or store location
                // ex:Crystal Lake,"N of US 14, W of Terra Cotta",5580 Northwest Hwy,Crystal Lake,IL,60014-8016,42.2291924,-88.3053582,McHenry County
                // ex:West Hollywood,SWC LaBrea Ave & Santa Monica Blvd,"7100 Santa Monica Blvd, STE 201",West Hollywood,CA,90046-5896,34.0902545,-118.3447075,Los Angeles County
                else if(locationInfo.length ==10)
                {
                	//if all locationInfo[1-3] values start with number or only the last starts with numer
                    if((isNumeric(locationInfo[1]) && isNumeric(locationInfo[2]) && isNumeric(locationInfo[3]))||
                    	(!isNumeric(locationInfo[1]) && !isNumeric(locationInfo[2]) && isNumeric(locationInfo[3])))
                    {
                    	locationInfo[1]=locationInfo[1]+locationInfo[2];
                    	Location loc = new Location(locationInfo[0], locationInfo[1], locationInfo[3], locationInfo[4],
                    	locationInfo[5], locationInfo[6], Double.parseDouble(locationInfo[7]),
                    	Double.parseDouble(locationInfo[8]), locationInfo[9]);
                    }
                    //else if amongst locationInfo[1-3] only 2 and 3 start with numbers
                    else if(!isNumeric(locationInfo[1]) && isNumeric(locationInfo[2]) && isNumeric(locationInfo[3]))
                    {
                    	locationInfo[2]=locationInfo[2]+locationInfo[3];
                    	Location loc = new Location(locationInfo[0], locationInfo[1], locationInfo[2], locationInfo[4],
                    	locationInfo[5], locationInfo[6], Double.parseDouble(locationInfo[7]),
                    	Double.parseDouble(locationInfo[8]), locationInfo[9]);
                    }
                }
                // else location doesnt match 
                else
                {
                	System.out.println("--------------------------");
                	System.out.println("Warning: Store Information in line " + lineNumber + " does not have the correct format");
                	System.out.println(line);
                	System.out.println("The location will not be included in the data set when searching for the nearest location");
                	System.out.println("Format must be:Store Name,Store Location,Address,City,State,Zip Code,Latitude,Longitude,County");
                	System.out.println("--------------------------");
                }
                //iterate lineNumber
                lineNumber++;
            }
        }
        catch(Exception ee)
        {
            ee.printStackTrace();
        }
        finally
        {
            try
            {
                br.close();
                locationList=temp;
            }
            catch(IOException ie)
            {
                System.out.println("Error occured while closing the BufferedReader");
                ie.printStackTrace();
            }
        }
        
    }

    //function to check if string is a number
    public boolean isNumeric(String str) 
    { 
	  	try
	  	{  
	    	Integer.parseInt(str);  
	    	return true;
	 	} 
	 	catch(NumberFormatException e)
	 	{  
	    return false;  
  		}  
	}
}