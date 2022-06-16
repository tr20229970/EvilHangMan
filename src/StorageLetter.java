/*


     --------------------------------------------------------------
     [Class Name      : StorageLetter                             ]
     [Project         : Evil Hand Man                             ]
     [Description : The Main Purpose of the StorageLetter class,  ]
     [is to handle an array of Letters, and the manipulation and  ]
     [access of that array                                        ]
     --------------------------------------------------------------


 */

public class StorageLetter
{

  private static Letter[] validLetter;


  // Creates the array
  public StorageLetter(int letterCount)
  {
      validLetter = new Letter[letterCount];
  }

  // gets the array
  public Letter[] getArray()
  {
      return validLetter;
  }

  // gets an object from the array
  public Letter getIndex(int index)
  {
      return validLetter[index];
  }

  // updates the letterCount of each Letter Object
  public void updateCount()
  {
      for(Letter singleLetter : validLetter)
          if(singleLetter != null)
              singleLetter.updateLetterCount(countChar(singleLetter.getLetter()));
  }

  // Counts the amount of times, the same letter, is in the array
  public int countChar(char inputChar)
  {
      int output = 0;

      for(int i = 0; i < validLetter.length; i++)
          if(!(validLetter[i] == null) && validLetter[i].getLetter() == inputChar)
            output++;
      return output;
  }

  // Returns the current array, and _ if the object is null
  public String returnCurrentLetterToString()
  {
      String output = "";

      for(Letter singleLetter : validLetter)
      {
          if(singleLetter == null)
              output += "_";
          else
              output += singleLetter.getLetter();
      }
      return output;
  }
}
