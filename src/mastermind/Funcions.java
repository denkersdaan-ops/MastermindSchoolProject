package mastermind;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Funcions
{
    private static int maxGuesses = 10;

    private static int codeLength = 4;

    public int languageIndex = 0;

    static Scanner keyboard = new Scanner(System.in);

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

    public Funcions(int newCodeLength, int newMaxGuess)
    {
	maxGuesses = newMaxGuess;
	codeLength = newCodeLength;

	codes = new int[codeLength];
	inputs = new int[codeLength];
	checks = new String[codeLength];
    }

    void setColors(String[][] newColors)
    {
	colors = newColors;
    }

    String getColors()
    {
	String allColors = "";
	for (int i = 0; i < colors.length; i++)
	{
	    allColors += "- " + colors[i][languageIndex] + "\n";
	}
	return allColors;
    }

    String getColors(int i)
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
	    input = keyboard.nextLine();
	}

	String[] tokens = input.split("\\s+"); // \s means blackspace / space. dan splits het het bij die /s

	int scanGuess = -1;

	for (int i = 0; i < Math.min(tokens.length, codeLength); i++)
	{
	    if (i + correctInput >= codeLength)
	    {
		break;
	    }
	    scanGuess = colorToCode(tokens[i]);

	    if (scanGuess != -1)
	    {
		inputs[i + correctInput] = scanGuess;
		output[i + correctInput] = "" + inputs[i];
	    }
	    else
	    {
		output[i + correctInput] = "" + -1;
	    }
	}

	return output;
    }

    String messages = "";
    int correctInput = 0;

    public boolean processGuesses()
    {
	String[] output = codeCreaker(correctInput);

	for (int i = correctInput; i < codeLength; i++)
	{
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

	    messages += ("guess " + (i + 1) + ": " + getColors(Integer.parseInt(output[i])) + "\n");
	    correctInput++;
	}
	
	correctInput = 0;
	return true;
    }
    
    public String getGuessOutput(){
	return messages;
    }

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
	    checks[i] = "... ";
	    if (codes[i] == inputs[i])
	    {
		checks[i] = "black ";
	    }
	    else
	    {
		won = false;

		for (int checker = 0; checker < codeLength; checker++)
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
	for (int i = 0; i < colors.length; i++)
	{
	    for (String option : colors[i])
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

	// ik wilde elke text per taal veranderen. en dat kan maar dat is te veel werk
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