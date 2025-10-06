package mastermind;

import java.util.Random;
import java.util.Scanner;

public class Mastermind
{
    static int maxGuesses = 10;

    static int codeLength = 4;

    static int[] code = new int[codeLength];

    static int[] guess = new int[codeLength];

    static int[] check = new int[codeLength];

    static String[] colorList = new String[10];

    static Scanner codeGuess = new Scanner(System.in);

    static int isCodeCorrect = 0;

    public static void main(String[] args)
    {
	setColor();

	codeMaker();

	guessLoop();

	// als je gewonnen hebt dan zie je deze niet
	if (isCodeCorrect != 0)
	{
	    System.out.println("you don't have any guesses left");
	}
	codeGuess.close();
    }

    static void guessLoop()
    {
	for (int i = 0; i < maxGuesses; i++)
	{
	    System.out.println("Your options are \n" + colorList[0] + "\n" + colorList[1] + "\n" + colorList[2] + "\n" + colorList[3] + "\n" + colorList[4] + "\n" + colorList[5]);

	    System.out.println("Start guess: " + (i + 1));

	    codeGuess = new Scanner(System.in);

	    codeCreaker();

	    codeCheck();

	    String output = "";
	    isCodeCorrect = 0;

	    // print eerst de intput
	    for (int r = 0; r < codeLength; r++)
	    {
		output += colorList[guess[r]];
	    } // daarna de output

	    for (int r = 0; r < codeLength; r++)
	    {
		output += colorList[check[r]];

		// zwart zit onder 6 dus als isCodeCorrect 0 is dan zou je alles zwart
		// hebben(een goed code)
		isCodeCorrect += check[r] - 6;
	    }
	    if (isCodeCorrect == 0)
	    {
		i = maxGuesses;
		for (int r = 6; r > 0; r--)
		{
		    System.out.println("you won!!!!");
		}
	    }
	    else
	    {
		System.out.println("\ncheck: " + (i + 1) + ": " + output + "\n");
	    }
	}
    }

    static void setColor()
    {
	// alle kleuren
	colorList[0] = " green ";
	colorList[1] = " red ";
	colorList[2] = " yellow ";
	colorList[3] = " purple ";
	colorList[4] = " orange ";
	colorList[5] = " blue ";
	colorList[6] = " black ";
	colorList[7] = " white ";
	colorList[8] = " ... ";
    }

    static void codeMaker()
    {
	Random rand = new Random();
	for (int i = 0; i < code.length; i++)
	{
	    code[i] = rand.nextInt(0, 6);
	}

	System.out.println("Code is set! \n code length is: " + codeLength);

	System.out.println("You have " + maxGuesses + " guesses");

    }

    static void codeCreaker()
    {
	for (int i = 0; i < guess.length; i++)
	{
	    int scanGuess = colorToCode();

	    if (scanGuess <= 5 && scanGuess >= 0)
	    {
		guess[i] = scanGuess;

		System.out.println("guess " + (i + 1) + ": " + colorList[guess[i]]);
	    }
	    else
	    {
		System.out.println("guess /Error!!! \n" + "try again");
		i--;
	    }
	}
    }

    static int colorToCode()
    {
//	colorList[0] = " green ";
//	colorList[1] = " red ";
//	colorList[2] = " yellow ";
//	colorList[3] = " purple ";
//	colorList[4] = " orange ";
//	colorList[5] = " blue ";
//	colorList[6] = " black ";
//	colorList[7] = " white ";
//	colorList[9] = " ... ";

	String colorCode = codeGuess.next();

	if (colorCode.equalsIgnoreCase("green") || colorCode.equalsIgnoreCase("groen"))
	{
	    return 0;
	}
	if (colorCode.equalsIgnoreCase("red") || colorCode.equalsIgnoreCase("rood"))
	{
	    return 1;
	}
	if (colorCode.equalsIgnoreCase("yellow") || colorCode.equalsIgnoreCase("geel"))
	{
	    return 2;
	}
	if (colorCode.equalsIgnoreCase("purple") || colorCode.equalsIgnoreCase("paars"))
	{
	    return 3;
	}
	if (colorCode.equalsIgnoreCase("orange") || colorCode.equalsIgnoreCase("oranje"))
	{
	    return 4;
	}
	if (colorCode.equalsIgnoreCase("blue") || colorCode.equalsIgnoreCase("blouw"))
	{
	    return 5;
	}

	System.out.println("Color not recognized \n Try again");
	return colorToCode();
    }

    static void codeCheck()
    {
	for (int i = 0; i < code.length; i++)
	{
	    if (guess[i] == code[i])
	    {
		check[i] = 6; // = zwart
	    }
	    else if (guess[i] == code[0] || guess[i] == code[1] || guess[i] == code[2] || guess[i] == code[3])
	    {
		check[i] = 7; // = wit
	    }
	    else
	    {
		check[i] = 8; // = niets
	    }

	}
    }
}
