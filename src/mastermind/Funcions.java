package mastermind;

import java.util.Random;
import java.util.Scanner;

public class Funcions
{
    // scanner
    static Scanner keyboard = new Scanner(System.in);
    
    //base settings
    private static int maxGuesses = 10;

    private static int codeLength = 4;

    public int languageIndex = 0;

    static String colors[][] =
    { // English, Dutch, French
	    { "green", "groen", "verte" },
	    { "red", "rood", "rouge" },
	    { "blue", "blauw", "bleu" },
	    { "yellow", "geel", "jaune" },
	    { "orange", "oranje", "orange" },
	    { "purple", "paars", "violette" } };

    private static int[] codes = new int[codeLength];
    private static int[] inputs = new int[codeLength];
    private static String[] checks = new String[codeLength];

    public Funcions()
    {
    }

    public Funcions(int newCodeLength, int newMaxGuess)// hier kan je de base lengte, aantalgokken van de code aanpassen
    {
	maxGuesses = newMaxGuess;
	codeLength = newCodeLength;

	codes = new int[codeLength];
	inputs = new int[codeLength];
	checks = new String[codeLength];
    }

    void setColors(String[][] newColors)// als je de kleuren wil veranderen moet je dit aanroepen.
    {
	colors = newColors;
    }

    String getColors()// vraag naar alle kleuren
    {
	String allColors = "";
	for (int i = 0; i < colors.length; i++)
	{
	    allColors += "- " + colors[i][languageIndex] + "\n";
	}
	return allColors;
    }

    String getColors(int i)// vraag voor specefieken kleur 
    {
	return colors[i][languageIndex];
    }

    void codeMaker()
    {
	Random rand = new Random();
	for (int i = 0; i < codes.length; i++)
	{
	    codes[i] = rand.nextInt(6);
	}
    }

    public String[] codeCreaker(int correctInput)
    {
	String input = keyboard.nextLine();
	String[] output = new String[codeLength];

	while (input.isEmpty())
	{
	    input = keyboard.nextLine();// line zodat ik het in EEN keer kan behandelen.
	}

	String[] tokens = input.split("\\s+"); // \s means blackspace / space. dan splits het het bij die /s

	int scanGuess = -1;

	for (int i = 0; i < Math.min(tokens.length, codeLength); i++)//math.min is er zodat ik minder dan 4 inputs kan geven
	{
	    if (i + correctInput >= codeLength)// kijkt of we niet al klaar waaren 
	    {
		break;
	    }
	    scanGuess = colorToCode(tokens[i]);// code is een int maar input is een string dus hier fix ik dat

	    if (scanGuess != -1)
	    {
		inputs[i + correctInput] = scanGuess;
		output[i + correctInput] = "" + inputs[i]; //output is een sting dus moet de "" + gebruiken om de int als sting te zien
	    }
	    else
	    {
		output[i + correctInput] = "" + -1;
	    }
	}

	return output;
    }
    // hier zodat ik het in een andere funcie kan vragen
    String messages = "";
    int correctInput = 0;

    public boolean processGuesses()
    {
	String[] output = codeCreaker(correctInput);

	for (int i = correctInput; i < codeLength; i++)
	{
	    // take errors
	    if (output[i] == null)
	    {
		messages += ("guess " + (i + 1) + ": no input. \n");

		return false;
	    }
	    else if ("-1".equals(output[i]))
	    {
		messages += ("guess " + (i + 1) + ": unknown color try again. \n");

		return false;
	    }
	    // no errors
	    messages += ("guess " + (i + 1) + ": " + getColors(Integer.parseInt(output[i])) + "\n"); // guess 1: green
	    correctInput++;// save correct 
	}
	
	correctInput = 0;
	return true;
    }
    // vraag hier
    public String getGuessOutput(){
	String oldMassages;
	
	oldMassages = messages; //save laatste massage voordat die verwijderd wordt
	
	messages = ""; // verwijder massage
	
	return oldMassages; // stuur de massage
    }

    // zet alles mooi in een rij neer --> green green green green : black black wit wit
    public String getOutput(int guess)
    {
	String output = "";

	// print eerst de intput
	for (int input : inputs)
	{
	    if (input != -1)
	    {
		output += colors[input][languageIndex];
	    }
	    else
	    {
		output += "...";
	    }
	    output += " ";
	} // daarna de output

	output += ": ";
	
	codeCheck();

	for (String check : checks)
	{
	    output += check;
	}

	return output;
    }

    public boolean codeCheck()
    {
	boolean won = true;
	for (int i = 0; i < codeLength; i++)
	{
	    checks[i] = "... "; // defult 
	    if (codes[i] == inputs[i]) // als het goed is
	    {
		checks[i] = "black ";
	    }
	    else
	    {
		won = false; // omdat het niet goed is

		//door deze loop is het geen switch
		for (int checker = 0; checker < codeLength; checker++) // kijk of die ergens anders is
		{
		    if (inputs[i] == codes[checker])
		    {
			checks[i] = "white ";
		    }
		}
	    }
	}
	return won;
    }

    static int colorToCode(String color)
    {
	for (int i = 0; i < colors.length; i++) //loop door elke kleur
	{
	    for (String option : colors[i]) // loop door elke taal van de kleur
	    {
		if (color.equalsIgnoreCase(option))
		    return i;
	    }
	}
	return -1;
    }

    public int getMaxGuesses()
    {
	return maxGuesses;
    }

    public int getCodeLength()
    {
	return codeLength;
    }

    public String setLanguage()
    {
	String language = keyboard.next();

	// ik wilde elke tekst per taal veranderen. en dat kan maar dat is te veel werk
	if (language.equalsIgnoreCase("English"))
	{
	    languageIndex = 0;
	    return "language set to English";
	}
	else if (language.equalsIgnoreCase("Nederlands"))
	{
	    languageIndex = 1;
	    return "language set to Nederlands";
	}
	else if (language.equalsIgnoreCase("Français"))
	{
	    languageIndex = 2;
	    return "language set to Français";
	}
	languageIndex = -1;
	return "language unknown language try again";
    }
}