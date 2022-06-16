/*


     --------------------------------------------------------------
     [Class Name      : Letter                                    ]
     [Project         : Evil Hand Man                             ]
     [Description : Handles the storage of the individual letter, ]
     [along, with information about that letter, like if its in   ]
     [the word, the index of it, if it is a duplicate in the word ]
     [and how many times it's in the word                         ]
     --------------------------------------------------------------


 */


public class Letter
{
    private int index;
    private char storedLetter;
    private boolean inWord;
    private boolean isDuplicate;
    private int occurrenceInWord;




// Constructor takes in inputIndex, and inputChar, Chained
    Letter(int inputIndex, char inputLetter)
    {
     if(inputIndex >= 0)
         new Letter(inputIndex, inputLetter, true);
     else
         new Letter(inputIndex,inputLetter, false);

    }

    // Constructor takes in inputIndex, and inputChar, and inputInWord
    Letter(int inputIndex, char inputLetter, boolean inputInWord)
    {
        occurrenceInWord = 0;
        index = inputIndex;
        storedLetter = inputLetter;
        inWord = inputInWord;
    }


    /*

 --------------------------------------------------------------
 [Method      : updateLetterCount                             ]
 [Args        : int                                           ]
 [Description : updates the letter occurrenceInWord variable  ]
 [to the inputted arg                                          ]
 --------------------------------------------------------------
 */
    public void updateLetterCount(int input)
    {
        occurrenceInWord = input;
    }


    /*

--------------------------------------------------------------
[Method         : letterCount                                ]
[Returns        : int                                        ]
[Description    : Returns the letter Occurrence              ]
--------------------------------------------------------------
*/
    public int letterCount()
    {

        return occurrenceInWord;
    }


    // Sets Duplicate State
    public void setDuplicateState(boolean state)
    {
        isDuplicate = state;
    }


    // gets Duplicate State
    public boolean getisDuplicate()
    {
        return isDuplicate;
    }
    

    // gets index
    public int getIndex()
    {
        return index;
    }


    // gets Letter
    public char getLetter()
    {
        return storedLetter;
    }


    // Gets In Word Status
    public boolean getInWord()
    {
        return inWord;
    }

    @Override
    public String toString()
    {
        return "Letter{" +
                "index=" + index +
                ", storedLetter=" + storedLetter +
                ", inWord=" + inWord +
                '}';
    }
}
