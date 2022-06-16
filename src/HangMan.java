/*


     --------------------------------------------------------------
     [Class Name      : HandMan                                   ]
     [Project         : Evil Hand Man                             ]
     [Description : Handles the logic of the HangMan class        ]
     [accepts input from the HangManGUI manipulates arrays, and   ]
     objects to properly handle the evil hangman logic            ]
     --------------------------------------------------------------



 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class HangMan
{
    private static String word = "test";
    private static ArrayList<String> wordList = new ArrayList<String>();
    private static ArrayList<Letter> guessedList = new ArrayList<Letter>();
    private static StorageLetter storeLetter;



    /*

     --------------------------------------------------------------
     [Method      : HandMan                                       ]
     [Args        : int                                           ]
     [Throws      : FileNotFoundException                         ]
     [Description : Starts the hangman program by                 ]
     [calling the setupWordList method                            ]
     --------------------------------------------------------------

     */

     public HangMan(int letterCount) throws FileNotFoundException
     {
         System.out.println("[---------------------]\n       Welcome to \n       Hang-Man \n[---------------------]");
         setupWordList(letterCount);
         storeLetter = new StorageLetter(letterCount);
     }

    /*

     --------------------------------------------------------------
     [Method      : evilPlay                                      ]
     [Description : Calls the updateWordList Method, and the      ]
     [getNewWord Method                                           ]
     --------------------------------------------------------------
     */

    public void evilPlay()
    {
      updateWordList();
      getNewWord();
      if(wordList.size() == 1 && checkWinStatus())
          HangManGUI.displayMessage("You Win!");
      System.out.println("Current Word : " + word);
    }

    /*

     --------------------------------------------------------------
     [Method      : getGuessedList                                ]
     [Return      : ArrayList<Letter>                             ]
     [Description : Returns the GuessedList                       ]
     --------------------------------------------------------------
     */
    public ArrayList<Letter> getGuessedList()
    {
        return guessedList;
    }
    


    /*

     --------------------------------------------------------------
     [Method      : inputGuess                                    ]
     [Args        : String                                        ]
     [Throws      :
     [Returns     : Void                                          ]
     [Description : The Main Purpose of this method is to accept  ]
     [a string as import, and be able to determine if its a valid ]                                           ]
     [letter, along with confirming that its in the hangman word  ]                                                            ]
     [ if it is, it adds it to storeLetter array                  ]                                          ]
     --------------------------------------------------------------
     */

    public boolean checkWinStatus()
    {
        System.out.println("Final Word Win Status - " +  storeLetter.returnCurrentLetterToString() + "\n");
        return storeLetter.returnCurrentLetterToString().equals(word);
    }

    // Checks the input to see if its a valid letter ot not, and informs user via message
    public static void inputGuess(String input)
    {
        if(input.trim().length() > 1 || input.trim().length() == 0)
        {
            HangManGUI.displayMessage("Invalid Input String!");
            return;
        }


        int count = 0;
        if(word.indexOf(input.charAt(0)) == -1)
        {
            HangManGUI.updateHangManImageStatus();
            HangManGUI.displayMessage("Sorry, The Letter is not in there");
            guessedList.add(new Letter(-1,input.charAt(0),false));
            return;
        }

        for(int i = 0; i < word.length(); i++)
        {
            if(word.charAt(i) == input.charAt(0))
            {
                HangManGUI.displayMessage("Good Job!");
                guessedList.add(new Letter(i,input.charAt(0),true));
                if (count > 0)
                      guessedList.get(guessedList.size() - 1).setDuplicateState(true);
                storeLetter.getArray()[i] = guessedList.get(guessedList.size() - 1);

                count++;
            }
        }
    }


    /*

     --------------------------------------------------------------
     [Method      : setupWordList                                  ]
     [Description : Scans through a set list of words, and adds   ]
     [them to an Arraylist (wordList)                             ]
     --------------------------------------------------------------
     */

    private static void setupWordList(int letterCount)  throws FileNotFoundException
    {
        System.out.println("Getting Ready!");
        Scanner inputText = new Scanner(new File("src/misc/words2000.txt"));
        String temp;
        while(inputText.hasNext())
        {
            temp = inputText.nextLine();
             
             // If the Word is the same length as the length selected at the start
             if(temp.length() == letterCount)
                 wordList.add(temp.toLowerCase());
        }
        System.out.println("Ready!\n");
    }

    /*

     --------------------------------------------------------------
     [Method      : updateWordList                                ]
     [Description : Calls the three remove methods, that remove   ]
     [any invalid word from the Array.                            ]
     --------------------------------------------------------------
     */

    private static int updateWordList()
    {
        if(wordList.size() >  1)
        {
            removeDoesNotContainLetters();
            removeNotInIndex();
            removeInvalidDupSize();
        }

        return wordList.size();
    }


    /*
     --------------------------------------------------------------
     [Method      : getInvalidLetter                               ]
     [Returns     : int                                            ]
     [Description : returns the amount of times, a char, has       ]
     [been used in a word                                          ]
     --------------------------------------------------------------
     */
    public ArrayList<Character> getInvalidLetter()
    {
        ArrayList<Character> output = new ArrayList<Character>();
        for(Letter singleLetter : guessedList)
        {
            if(!singleLetter.getInWord())
                output.add(singleLetter.getLetter());
        }
        return output;
    }
    /*

     --------------------------------------------------------------
     [Method      : countChar                                      ]
     [Args        : String, char                                   ]
     [Returns     : int                                            ]
     [Description : returns the amount of times, a char, has       ]
     [been used in a word                                          ]
     --------------------------------------------------------------
     */

    public static int countChar(String inputWord, char inputChar)
    {
        int output = 0;

        for(char singleLetter : inputWord.toCharArray())
            if(singleLetter == inputChar)
                output++;

        return output;
    }

    /*
     --------------------------------------------------------------
     [Method      : removeNotInIndex                               ]
     [Returns     : Void                                           ]
     [Description : removes words from the WordList Array that     ]
     [do not have letter in the storeLetter array index            ]
     --------------------------------------------------------------
     */

    private static void removeNotInIndex()
    {
        for(int i = 0; i < wordList.size(); i++)
            for(int k = 0; k < storeLetter.getArray().length; k++)
                if(!(storeLetter.getArray()[k] == null) && !(wordList.get(i).charAt(k) == storeLetter.getArray()[k].getLetter()))
                {
                  wordList.remove(i);
                  if(i != 0)
                    i--;
                }
    }


    /*
         --------------------------------------------------------------
         [Method      : removeInvalidDupSize                           ]
         [Returns     : Void                                           ]
         [Description : Removes all the words from the WordList Array  ]
         [that contain, more of a valid letter, then the initial       ]
         --------------------------------------------------------------
         */
    private static void removeInvalidDupSize()
    {
        storeLetter.updateCount();

        for(int i = 0; i < wordList.size(); i++)
            for(int k = 0; k < storeLetter.getArray().length; k++)
                if(storeLetter.getArray()[k] != null )
                    if(storeLetter.getArray()[k].letterCount() != countChar(wordList.get(i), storeLetter.getArray()[k].getLetter()))
                    {
                        wordList.remove(i);
                        if(i != 0)
                            i--;
                    }



    }


    /*
     --------------------------------------------------------------
     [Method      : removeDoesNotContainLetters                    ]
     [Returns     : Void                                           ]
     [Description : Removes all the words from the WordList Array  ]
     [that contain, a letter that is a "Wrong guess"               ]
     --------------------------------------------------------------
     */
    private static void removeDoesNotContainLetters()
    {
        for(int i = 0; i < wordList.size(); i++)
            for(int k = 0; k < guessedList.size(); k++)
                if((!guessedList.get(k).getInWord()) && (wordList.get(i).indexOf(guessedList.get(k).getLetter()) > -1))
                {
                    wordList.remove(i);
                    if(wordList.size() != 0)
                        i--;
                }
    }


    /*
     --------------------------------------------------------------
     [Method      : getWordList                                    ]
     [Returns     : ArrayList<String>                              ]
     [Description : Returns the WordList                           ]
     --------------------------------------------------------------
     */
    public ArrayList<String> getWordList()
    {
        return wordList;
    }

    /*
     --------------------------------------------------------------
     [Method      : getNewWord                                     ]
     [Returns     : boolean                                        ]
     [Description : uses a random number generator to pick a new   ]
     [ word from the list                                          ]
     --------------------------------------------------------------
     */
    public boolean getNewWord()
    {
        if(wordList.size() == 0)
            return false;

        word = wordList.get((int)(Math.random() * wordList.size()));
        return true;
    }

}
