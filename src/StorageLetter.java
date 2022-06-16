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


  public StorageLetter(int letterCount)
  {
      validLetter = new Letter[letterCount];
  }

  public Letter[] getArray()
  {
      return validLetter;
  }

  public Letter getIndex(int index)
  {
      return validLetter[index];
  }


  public void updateCount()
  {
      for(Letter singleLetter : validLetter)
          if(singleLetter != null)
              singleLetter.updateLetterCount(countChar(singleLetter.getLetter()));
  }

  public int countChar(char inputChar)
  {
      int output = 0;

      for(int i = 0; i < validLetter.length; i++)
          if(!(validLetter[i] == null) && validLetter[i].getLetter() == inputChar)
            output++;
      return output;
  }

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
