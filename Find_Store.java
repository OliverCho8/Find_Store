import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStreamReader;



public class Find_Store{

    /*
    This Function gives the user the information he or she needs to run the program
    It prints out lines of information and reads the users input. 
    */
    public static String setup()
    {
    	System.out.println("Hello I can help you locate a store");
    	System.out.println("You can enter one of the following");
    	System.out.println("find_store --address=<address>");
    	System.out.println("find_store --address=<address> [--units=(mi|km)] [--output=text|json]");
    	System.out.println("find_store --zip=<zip>");
    	System.out.println("find_store --zip=<zip> [--units=(mi|km)] [--output=text|json]");
    	System.out.println("--------------------------");
    	System.out.println("Here are some examples");
    	System.out.println("find_store --address=1770 Union St, San Francisco, CA 94123");
  		System.out.println("find_store --zip=94115 --units=km");
        System.out.println("--------------------------");
        System.out.println("Enter your command here:");

        String input=null;
        BufferedReader reader = null;

  		try
  		{
            //create buffereader to read command line input
	    	reader = new BufferedReader(new InputStreamReader(System.in)); 

            //assign the command line input to string input
	        input = reader.readLine();

	    }
	    catch(IOException ie)
        {
                System.out.println("Error occured while closing the BufferedReader");
                ie.printStackTrace();
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch(IOException ie)
            {
                System.out.println("Error occured while closing the BufferedReader");
                ie.printStackTrace();
            }
        }
        System.out.println("-----------");
        
 		return input;
    }
    
	public static void main(String [] args) throws Exception
	{
        //create a pasrser obejct and use it to parse the csv
        Parser parse=new Parser();
        parse.parseCSV("store-locations.csv");

        //take the user's input
		String input=setup();

        //parse the user's input and create the proper input code
        Input parsedInput=new Input(input);
        parsedInput.inputHandler();

        //use the user's input and input code to get the latitude and longitude
        Address address=new Address(parsedInput.getUserInputParsed(),parsedInput.getUserCode());
        address.latlng();

        //take the user's latitude and longitude and compare it to all locations in csv
        Comparison comp=new Comparison(parse.getLocationList(),address.getLatitude(),address.getLongitude(),parsedInput.getUserCode());
        comp.search();
    }
     
}