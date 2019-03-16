import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TestAssertions {
	Location loc1=new Location();
	Location loc2=new Location("Crystal","SWC Broadway & Bass Lake Rd","5537 W Broadway Ave",
			"Crystal","MN","55428-3507",45.0521539,-93.364854,"Hennepin County");
	Location loc3=new Location("Duluth","SEC Hwy 53 & Burning Tree Rd","1902 Miller Trunk Hwy",
			"Duluth","MN","55811-1810",46.808614,-92.1681479);
	
	@Test
	//test methods from location class
	public void testLocation() 
	{
		
		//test empty constructor
		assertEquals(null,loc1.getStore_name());
		assertEquals(null,loc1.getStore_location());
		assertEquals(null,loc1.getAddress());
		assertEquals(null,loc1.getCity());
		assertEquals(null,loc1.getState());
		assertEquals(null,loc1.getZip_code());
		assertEquals(Double.NaN,loc1.getLatitude());
		assertEquals(Double.NaN,loc1.getLongitude());
		assertEquals(null,loc1.getCounty());
		
		//test constructor with full info
		assertEquals("Crystal",loc2.getStore_name());
		assertEquals("SWC Broadway & Bass Lake Rd",loc2.getStore_location());
		assertEquals("5537 W Broadway Ave",loc2.getAddress());
		assertEquals("Crystal",loc2.getCity());
		assertEquals("MN",loc2.getState());
		assertEquals("55428-3507",loc2.getZip_code());
		assertEquals(45.0521539,loc2.getLatitude());
		assertEquals(-93.364854,loc2.getLongitude());
		assertEquals("Hennepin County",loc2.getCounty());
		
		//test constructor with missing county
    	assertEquals("Duluth",loc3.getStore_name());
		assertEquals("SEC Hwy 53 & Burning Tree Rd",loc3.getStore_location());
		assertEquals("1902 Miller Trunk Hwy",loc3.getAddress());
		assertEquals("Duluth",loc3.getCity());
		assertEquals("MN",loc3.getState());
		assertEquals("55811-1810",loc3.getZip_code());
		assertEquals(46.808614,loc3.getLatitude());
		assertEquals(-92.1681479,loc3.getLongitude());
		assertEquals(null,loc3.getCounty());
	}
	
	@Test
	//test methods from parser class
	public void testParser()
	{
		//test constructor
		Parser parse=new Parser();
		assertEquals(null,parse.getLocationList());
		
		assertEquals(true,parse.isNumeric("1"));;
		assertEquals(false,parse.isNumeric("a"));
		
	}
	
	@Test
	//test methods from input class
	public void testInput()
	{
		//test blank constructor
		Input in1=new Input();
		assertEquals("",in1.getUserInput());
		String[] in1Parsed=in1.getUserInputParsed();
		assertEquals(1,in1Parsed.length);
		int[] in1UserCode=in1.getUserCode();
		assertEquals(0,in1UserCode[0]);
		assertEquals(0,in1UserCode[1]);
		assertEquals(0,in1UserCode[2]);
		assertEquals(0,in1UserCode[3]);
		
		//test constructor
		Input in2=new Input("find_store --address=1770 Union St, San Francisco, CA 94123");
		assertEquals("find_store --address=1770 Union St, San Francisco, CA 94123",in2.getUserInput());
		String[] in2Parsed=in2.getUserInputParsed();
		assertEquals("find_store ",in2Parsed[0]);
		assertEquals("address=1770 Union St, San Francisco, CA 94123",in2Parsed[1]);
		int[] in2UserCode=in2.getUserCode();
		assertEquals(0,in2UserCode[0]);
		assertEquals(0,in2UserCode[1]);
		assertEquals(0,in2UserCode[2]);
		assertEquals(0,in2UserCode[3]);
		
		//test constructor
		Input in3=new Input("find_store --zip=94115 --units=km --output=json");
		assertEquals("find_store --zip=94115 --units=km --output=json",in3.getUserInput());
		String[] in3Parsed=in3.getUserInputParsed();
		assertEquals("find_store ",in3Parsed[0]);
		assertEquals("zip=94115 ",in3Parsed[1]);
		assertEquals("units=km ",in3Parsed[2]);
		assertEquals("output=json", in3Parsed[3]);
		int[] in3UserCode=in3.getUserCode();
		assertEquals(0,in3UserCode[0]);
		assertEquals(0,in3UserCode[1]);
		assertEquals(0,in3UserCode[2]);
		assertEquals(0,in3UserCode[3]);
		
		//test inputHandler
		in2.inputHandler();
		int[] in2UserCodeUpdate=in2.getUserCode();
		assertEquals(0,in2UserCodeUpdate[0]);
		assertEquals(0,in2UserCodeUpdate[1]);
		assertEquals(0,in2UserCodeUpdate[2]);
		assertEquals(0,in2UserCodeUpdate[3]);
	
		//test inputHandler
		in3.inputHandler();
		int[] in3UserCodeUpdate=in3.getUserCode();
		assertEquals(0,in3UserCodeUpdate[0]);
		assertEquals(1,in3UserCodeUpdate[1]);
		assertEquals(1,in3UserCodeUpdate[2]);
		assertEquals(1,in3UserCodeUpdate[3]);
		
		in2.inputReducer();
		String[] in2ParsedUpdate=in2.getUserInputParsed();
		assertEquals("1770 Union St, San Francisco, CA 94123",in2ParsedUpdate[1]);
		
		in3.inputReducer();
		String[] in3ParsedUpdate=in3.getUserInputParsed();
		assertEquals("94115 ",in3ParsedUpdate[1]);
	}
	
	@Test
	//test methods from comparison class
	public void testComparison()
	{
		//test blank constructor
		Comparison comp1=new Comparison();
		assertEquals(Double.MAX_VALUE,comp1.getMinDist());
		assertEquals(0,comp1.getUserLatitude());
		assertEquals(0,comp1.getUserLongitude());
		assertEquals(null,comp1.getClosest().getStore_name());
		ArrayList<Location> loclist1=new ArrayList<Location>();
		assertEquals(loclist1,comp1.getLocationList());
		assertEquals(false,comp1.getMiOrKm());
		assertEquals(false,comp1.getTxtOrJson());
		
		//setup for next test 2
		ArrayList<Location> loclist2=new ArrayList<Location>();
		loclist2.add(loc1);
		loclist2.add(loc2);
		loclist2.add(loc3);
		int[] userCode2= {0,0,0,0};
		
		//test constructor
		Comparison comp2=new Comparison(loclist2,10,10,userCode2);
		assertEquals(Double.MAX_VALUE,comp2.getMinDist());
		assertEquals(10,comp2.getUserLatitude());
		assertEquals(10,comp2.getUserLongitude());
		assertEquals(null,comp2.getClosest().getStore_name());
		assertEquals(loclist2,comp2.getLocationList());
		assertEquals(false,comp2.getMiOrKm());
		assertEquals(false,comp2.getTxtOrJson());
		
		//setup for next test 2
		ArrayList<Location> loclist3=new ArrayList<Location>();
		loclist3.add(loc1);
		loclist3.add(loc2);
		loclist3.add(loc3);
		int[] userCode3= {0,0,1,1};
		
		//test constructor
		Comparison comp3=new Comparison(loclist3,10,10,userCode3);
		assertEquals(Double.MAX_VALUE,comp3.getMinDist());
		assertEquals(10,comp3.getUserLatitude());
		assertEquals(10,comp3.getUserLongitude());
		assertEquals(null,comp3.getClosest().getStore_name());
		assertEquals(loclist3,comp3.getLocationList());
		assertEquals(true,comp3.getMiOrKm());
		assertEquals(true,comp3.getTxtOrJson());
		
		//test search
		//command line output from here, not sure how to get rid of it
		comp2.search();
		assertEquals(loc3,comp2.getClosest());
		assertEquals(6279.353377537287,comp2.getMinDist());
		
		//test search
		//command line out from here, not sure how to get rid of it
		comp3.search();
		assertEquals(loc3,comp3.getClosest());
		assertEquals(10105.639682019368,comp3.getMinDist());
		
		//test distance
		assertEquals(0,comp3.distance(0, 0, 0, 0));
		assertEquals(974.5865979625319, comp3.distance(0, 0, 10,10));
		
		//test milesToKm
		assertEquals(0,comp3.milesToKm(0));
		assertEquals(1.609344,comp3.milesToKm(1));
		
	}
	
	@Test
	//test methods from address
	public void testAddress() throws Exception
	{
		//test empty constructor
		Address ad1=new Address();
		assertEquals(0,ad1.getLatitude());
		assertEquals(0,ad1.getLongitude());
		String[] ad1UserInput1=ad1.getUserInputParsed();
		assertEquals(null,ad1UserInput1[0]);
		assertEquals(null,ad1UserInput1[1]);
		assertEquals(null,ad1UserInput1[2]);
		assertEquals(null,ad1UserInput1[3]);
		int[] ad1UserCode1=ad1.getUserCode();
		assertEquals(0,ad1UserCode1[0]);
		assertEquals(0,ad1UserCode1[1]);
		assertEquals(0,ad1UserCode1[2]);
		assertEquals(0,ad1UserCode1[3]);
		
		//setup
		String[] ad2ParsedInput= {"find_store", "94115", "--units=km" , "--output=json"};
		int [] ad2UserCode= {0,1,1,1};
		//test constructor
		Address ad2=new Address(ad2ParsedInput, ad2UserCode);
		assertEquals(0,ad2.getLatitude());
		assertEquals(0,ad2.getLongitude());
		assertEquals(ad2ParsedInput,ad2.getUserInputParsed());
		assertEquals(ad2UserCode,ad2.getUserCode());
		
		//test latlng
		ad2.latlng();
		assertEquals(37.7877522,ad2.getLatitude());
		assertEquals(-122.4382307,ad2.getLongitude());
		
	}

}
