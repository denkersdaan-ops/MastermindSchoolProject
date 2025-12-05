package mastermind;

// geen .* omdat 3 volgens mij nog te klein is
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Mastermind
{
    static int maxGuesses = 10;

    static int codeLength = 4;

    static int languageIndex = 0;

    static int[] codes = new int[codeLength];

    static int[] inputs = new int[codeLength];

    static String[] checks = new String[codeLength];

    static ArrayList<String> outputHistory = new ArrayList<String>();

    static String colors[][] =
    { // English, Dutch, French
	    { "green", "groen", "verte" },
	    { "red", "rood", "rouge" },
	    { "blue", "blauw", "bleu" },
	    { "yellow", "geel", "jaune" },
	    { "orange", "oranje", "orange" },
	    { "purple", "paars", "violette" } };

    static Scanner keyboard = new Scanner(System.in);

    static boolean won = false;

    public static void main(String[] args)
    {
	settings();

	codeMaker();

	guessLoop();

	// als je gewonnen hebt dan zie je deze niet
	if (!won)
	{
	    System.out.println("you don't have any guesses left");
	}
	keyboard.close();
    }

    static void settings()
    {
	System.out.println("set your language your options are English, Nederlands and Français");

	String language = keyboard.next();

	// ik wilde elke text per taal veranderen. en dat kan maar dat is te veel werk
	if (language.equalsIgnoreCase("English"))
	{
	    languageIndex = 0;
	    System.out.println("language set to English");
	}
	else if (language.equalsIgnoreCase("Nederlands"))
	{
	    languageIndex = 1;
	    System.out.println("language set to Nederlands");
	}
	else if (language.equalsIgnoreCase("Français"))
	{
	    languageIndex = 2;
	    System.out.println("language set to Français");
	}
	else
	{
	    languageIndex = 0;
	    System.out.println("language unknown language set to English");
	}
    }

    static void guessLoop()
    {
	for (int guess = 0; guess < maxGuesses; guess++)
	{
	    System.out.println("Your options are");

	    for (int i = 0; i < colors.length; i++)
	    {
		System.out.println("- " + colors[i][languageIndex]);
	    }

	    System.out.println("Start guess: " + (guess + 1));

	    keyboard = new Scanner(System.in);

	    codeCreaker();

	    codeCheck();

	    String output = "";

	    // print eerst de intput
	    for (int input : inputs)
	    {
		output += colors[input][languageIndex];
		output += " ";
	    } // daarna de output

	    output += ": ";

	    for (String check : checks)
	    {
		output += check;
	    }

	    System.out.println("\ncheck " + (guess + 1) + ": " + output + "\n");

	    outputHistory.add("\ncheck: " + (guess + 1) + ": " + output + "\n");

	    if (won)
	    {
		guess = maxGuesses;
		for (int r = 6; r > 0; r--)
		{
		    System.out.println("you won!!!!");
		}
	    }
	}
    }

    static void codeMaker()
    {
	Random rand = new Random();
	for (int i = 0; i < codes.length; i++)
	{
	    codes[i] = rand.nextInt(6);
	}

	System.out.println("Code is set! \n code length is: " + codeLength);

	System.out.println("You have " + maxGuesses + " guesses");

    }

    static void codeCreaker()
    {
	for (int i = 0; i < inputs.length; i++)
	{
	    String input = keyboard.next();
	    int scanGuess = 0;
	    if (input.equalsIgnoreCase("history"))
	    {
		for (String history : outputHistory)
		{
		    System.out.println(history);
		}
		i--;
	    }
	    else
	    {
		scanGuess = colorToCode(input);

		inputs[i] = scanGuess;

		System.out.println("guess " + (i + 1) + ": " + colors[inputs[i]][0]);
	    }
	}
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

	System.out.println("Onbekende kleur, probeer opnieuw: ");

	return colorToCode(keyboard.next());
    }

    static void codeCheck()
    {
	won = true;
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
    }
}
