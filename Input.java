public class Input
{
	private String userInput;// variable contains the user's input
	private String[] userInputParsed;// variable contains the user's input parsed
	private int[] userCode;// variable contains the user's input code

	//blank constructor
	public Input()
	{
		userInput="";
		userInputParsed=inputParser(userInput);
		userCode=new int[4];
	}

	//constructor which takes in user input as string
	public Input(String input)
	{
		userInput=input;
		userInputParsed=inputParser(userInput);
		userCode=new int[4];
	}

	//setter to set userInput
	public void setInput(String input)
	{
		userInput=input;
	}

	//getter to get userInput
	public String getUserInput()
	{
		return userInput;
	}

	//getter to get userCode
	public int[] getUserCode()
	{
		return userCode;
	}

	//getter to the userInputParsed
	public String[] getUserInputParsed()
	{
		return userInputParsed;
	}

	//function to parse user input
	public String[] inputParser(String input)
    {
    	//split the user input by each "--"
        String[] inputParsed=input.split("--",4);
        return inputParsed;
    }

    //function to setup userCode based on the user input, user code will be a 4 int array
    public void inputHandler()
    {
    	//for each element in parsed userInput
        for(int i=0;i<userInputParsed.length; i++)
        {
        	//if "find_store" is first continue
            if(userInputParsed[i].contains("find_store"))
            {
                continue;
            }
            //if zip then we get 0100
            else if(userInputParsed[i].startsWith("zip="))
            {
            	userCode[1]=1;
            }
            //if address then we get 0000
            else if(userInputParsed[i].startsWith("address="))
            {
            	userCode[1]=0;
            }
            //if units=mi then we get 0000
            else if(userInputParsed[i].contains("units=mi"))
            {
            	userCode[2]=0;
            }
            //if units=km then we get 0010
            else if(userInputParsed[i].contains("units=km"))
            {
            	userCode[2]=1;
            }
            //if output=text then we get 0000
            else if(userInputParsed[i].contains("output=text"))
            {
              	userCode[3]=0;
            }
            //if output=json the nwe get 0001
            else if(userInputParsed[i].contains("output=json"))
            {
            	userCode[3]=1;
            }
            else
            {
                System.out.println("Your input does not match the specified format");
                System.out.println("Your output may not be as accurate as you would like");
                break;
            }
        }
        this.inputReducer();
    }

    //function removes "zip=" or "address=" from userInputParsed
    public void inputReducer()
    {
    	//if userInputParsed contains "zip=" then replace with ""
    	if(userInputParsed[1].contains("zip="))
    	{
    		userInputParsed[1]=userInputParsed[1].replaceAll("zip=","");
    		
    	}
    	//if userInputParsed contains "address=" then replace with ""
    	else
    	{
    		userInputParsed[1]=userInputParsed[1].replaceAll("address=","");
    	}
    }


}