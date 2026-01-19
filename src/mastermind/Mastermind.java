package mastermind;

public class Mastermind
{
    static boolean won = false;

    static Funcions function = new Funcions();

    public static void main(String[] args)
    {
	do
	{
	    System.out.println("set your language your options are English, Nederlands and Français");
	    System.out.println(function.setLanguage());
	}
	while (function.languageIndex == -1);

	function.codeMaker();

	guessLoop();

	// als je gewonnen hebt dan zie je deze niet
	if (!won)
	{
	    System.out.println("you don't have any guesses left");
	}
    }

    static void guessLoop()
    {
	System.out.println("Code is set! \nCode length is: " + function.getCodeLength());

	System.out.println("You have " + function.getMaxGuesses() + " guesses");

	for (int guess = 0; guess < function.getMaxGuesses(); guess++)
	{
	    System.out.println("Your options are");

	    System.out.println(function.getColors());

	    System.out.println("Start guess: " + (guess + 1));
	    
	    boolean isInputCorrect;
	    do {
		isInputCorrect = function.processGuesses();
		
		System.out.println(function.getGuessOutput());
		
	    }while(!isInputCorrect);
	    
	    won = function.codeCheck();

	    System.out.println(function.getOutput(guess));

	    if (won)
	    {
		for (int i = 100; i > 0; i--)
		{
		    System.out.println("you won!!!!");
		}
		break;
	    }
	}
    }
}
