import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

class HangManGUI extends JFrame
{

    private static HangMan game;
    private static JLabel[] textArray;
    private static String[] pictureArray = new String[12];

    // Main Frame
    private static JFrame frame;


    // Main Game Panel Frame
    private static JPanel mainPanel;
    private static JPanel eastSidePanel;
    private static JPanel westSidePanel;
    private static JPanel bottomPanel;

    // Elements on the Main Game Panel
    private static JLabel pictureLabel;
    private static JButton button;
    private static JTextField textField;
    private static JLabel invalidLettersTitle;
    private static JLabel guessesLeftTitle;
    private static JLabel labelGuessesLeft;
    private static JLabel labelInvalidLetters;
    private static ImageIcon hangManImage;

    // Variables
    private static int numberOfGuesses;
    private static int countGuesses = 0;
    private static int currentImageState;



    // Main Method runs the setupGame Method
    public static void main(String args[])
    {
       setupGame();
    }


    /*
     --------------------------------------------------------------
     [Method      : setupGame                                      ]
     [Returns     : void                                           ]
     [Description : Gives the user a chance to input, how many     ]
     [guesses they would like along with the letter count of the   ]
     [final word                                                   ]
     --------------------------------------------------------------
     */

    private static void setupGame()
    {


        JLabel numberOfGuessLabel = new JLabel(("Enter Number of Guesses"));
        JTextField guessesTextField = new JTextField(2);
        JLabel letterCountLabel = new JLabel(("Enter Letter Count"));
        JTextField letterCountTextField = new JTextField(2);
        JButton summitButton = new JButton("Playtime!");
        JFrame setupFrame = new JFrame("Hangman Setup");
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(40,40,40));
        GridBagConstraints c = new GridBagConstraints();

        setupHangManImageStatus();

        setupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupFrame.setSize(720,240);






        // Number of Guesses Label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 30;
        c.ipadx = 30;
        numberOfGuessLabel.setFont(new Font("Courier", Font.BOLD,25));
        numberOfGuessLabel.setForeground(Color.white);
        mainPanel.add(numberOfGuessLabel, c);


        // Guesses Text Field
        c.gridx = 10;
        c.gridy = 0;
        mainPanel.add(guessesTextField, c);


        // Letter Count Label
        c.gridx = 0;
        c.gridy = 1;
        letterCountLabel.setFont(new Font("Courier", Font.BOLD,25));
        letterCountLabel.setForeground(Color.white);
        mainPanel.add(letterCountLabel, c);


        // Letter Count Text Field
        c.gridx = 10;
        c.gridy = 1;
        mainPanel.add(letterCountTextField, c);


        // Summit Button
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 10;
        c.ipadx = 330;
        summitButton.setFont(new Font("Courier", Font.BOLD,25));
        mainPanel.add(summitButton, c);

        setupFrame.getContentPane().add(mainPanel);
        setupFrame.setVisible(true);



        // Button Click
        summitButton.addActionListener(new ActionListener()
        {
            /*
             --------------------------------------------------------------
             [Method      : actionPerformed                                ]
             [Returns     : void                                           ]
             [Description : inputs, the letterCount, and guess limit into  ]
             [the HangMan Class                                            ]
             --------------------------------------------------------------
             */
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    game = new HangMan(Integer.valueOf(letterCountTextField.getText()));
                    setupFrame.setVisible(false); //you can't see me!
                    setupFrame.dispose(); //Destroy the JFrame object
                    mainInterface(Integer.valueOf(guessesTextField.getText()),Integer.valueOf(letterCountTextField.getText()));

                    play();
                }
                catch (FileNotFoundException ex)
                {
                    ex.printStackTrace();
                }

            }
        });

    }

    /*
     --------------------------------------------------------------
     [Method      : mainInterFace                                  ]
     [Returns     : void                                           ]
     [Args        : int numOfPLays, int letterCount                ]
     [Description : the mian code, that controls the looks         ]
     [ of the main interface                                       ]
     --------------------------------------------------------------
*/
    private static void mainInterface(int numOfPlays, int letterCount)
    {

        // Main Frame & Panels
        frame = new JFrame("Hangman");
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        bottomPanel = new JPanel(new GridBagLayout());
        eastSidePanel = new JPanel(new BorderLayout());
        westSidePanel = new JPanel(new BorderLayout());
        
        textArray = new JLabel[letterCount];
        numberOfGuesses = numOfPlays;




        c.gridy = 0;

        // textArrau letter
        for(int i = 0; i < letterCount; i++)
        {
            c.gridx = i;
            textArray[i] = new JLabel("--");
            textArray[i].setFont(new Font("Courier", Font.BOLD,18));
            c.ipadx = 40;
            c.ipady = 40;
            mainPanel.add(textArray[i], c);

        }
        // Text Field
        c.gridy = 1;
        c.gridx = 0;
        c.ipady = 20;
        c.ipadx = 200;
        c.gridwidth = letterCount;
        mainPanel.add(textField = new JTextField(letterCount), c);


        // Ready Button
        c.gridy = 2;
        c.gridx = 0;
        c.ipady = 20;
        c.gridwidth = 10;
        c.ipadx = 200;
        mainPanel.add(button = new JButton("Ready!"),c);


        // Reset
        c.gridy = 8;
        c.gridx = 0;
        c.ipady = 0;
        c.gridheight = 10;
        c.ipadx = 0;

        // East Side Panel Config - Title
        eastSidePanel.add(BorderLayout.NORTH, invalidLettersTitle = new JLabel ("  Invalid Letters  "));
        eastSidePanel.setBackground(Color.darkGray);
        invalidLettersTitle.setFont(new Font("Courier", Font.BOLD,24));
        invalidLettersTitle.setForeground(Color.white);

        // East Side Panel Config - Label
        eastSidePanel.add(BorderLayout.CENTER, labelInvalidLetters = new JLabel ("<html> <center> ____________________ </center>  </html>"));
        labelInvalidLetters.setFont(new Font("Courier", Font.BOLD,18));
        labelInvalidLetters.setForeground(Color.white);

        // West Side Panel Config - Title
        westSidePanel.add(BorderLayout.NORTH,guessesLeftTitle = new JLabel("         Guesses Left      "));
        guessesLeftTitle.setFont(new Font("Courier", Font.BOLD,18));
        guessesLeftTitle.setForeground(Color.white);

        // West Side Panel Config - Label
        westSidePanel.add(BorderLayout.CENTER, labelGuessesLeft =  new JLabel("         "+ numberOfGuesses +"        "));
        labelGuessesLeft.setFont(new Font("Courier", Font.BOLD,40));
        labelGuessesLeft.setForeground(Color.white);
        westSidePanel.setBackground(Color.darkGray);

        // Picture
        pictureLabel = new JLabel(hangManImage = new ImageIcon((pictureArray[0])));


        bottomPanel.add(pictureLabel);
        bottomPanel.setBackground(Color.lightGray);

        // Adds all the Panels to the MainFrame
        frame.getContentPane().add(BorderLayout.CENTER, bottomPanel);
        frame.getContentPane().add(BorderLayout.PAGE_END, mainPanel);
        frame.getContentPane().add(BorderLayout.EAST, eastSidePanel);
        frame.getContentPane().add(BorderLayout.WEST, westSidePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720,720);
        frame.setVisible(true);
    }

    /*
     --------------------------------------------------------------
     [Method      : setupHangManImageStatus                        ]
     [Returns     : void                                           ]
     [Description : stores the location of the images              ]
     --------------------------------------------------------------
*/
    private static void setupHangManImageStatus()
    {
        currentImageState = 0;
        for(int i = 0; i < pictureArray.length; i++)
            pictureArray[i] = "src\\images\\" + i +".png";
    }

    /*
     --------------------------------------------------------------
     [Method      : updateHangManImageStatus                       ]
     [Returns     : void                                           ]
     [Description : updates image, depending on                    ]
     [ numberOfGuesses - countGuesses                              ]
     --------------------------------------------------------------
*/
    public static void updateHangManImageStatus()
    {
        System.out.println("Updating image");

        // if guessesLeft = 0
        if(numberOfGuesses - countGuesses == 0)
        {
            hangManImage = new ImageIcon(pictureArray[11]);
            currentImageState = 11;
            pictureLabel.setIcon(hangManImage);
            return;
        }

        // determines how many pictures to show depending on amount of guesses
        if(numberOfGuesses < 5)
        {
            if (currentImageState + 3 <= 11)
            {
                hangManImage = new ImageIcon(pictureArray[currentImageState + 3]);
                currentImageState = currentImageState + 3;
            }
            else
            {
                hangManImage = new ImageIcon(pictureArray[11]);
                currentImageState = 11;
            }
        }


        if(numberOfGuesses > 5 && numberOfGuesses <= 10)
        {
            if (currentImageState + 2 <= 11)
            {
                hangManImage = new ImageIcon(pictureArray[currentImageState + 2]);
                currentImageState = currentImageState + 1;
            }
            else
            {
                hangManImage = new ImageIcon(pictureArray[11]);
                currentImageState = 11;
            }
        }
        if(numberOfGuesses == 11)
        {
            if (currentImageState + 1 <= 11)
            {
                hangManImage = new ImageIcon(pictureArray[currentImageState + 1]);
                currentImageState = currentImageState + 1;
            }
            else
            {
                hangManImage = new ImageIcon(pictureArray[11]);
                currentImageState = 11;
            }
        }
        if(numberOfGuesses > 11 && countGuesses % 3 == 0)
        {
            if (currentImageState + 1 <= 11)
            {
                hangManImage = new ImageIcon(pictureArray[currentImageState + 1]);
                currentImageState = currentImageState + 1;
            }
            else
            {
                hangManImage = new ImageIcon(pictureArray[11]);
                currentImageState = 11;
            }
        }

        pictureLabel.setIcon(hangManImage);
    }

    /*
     --------------------------------------------------------------
     [Method      : updateInvalidLetterList                        ]
     [Returns     : void                                           ]
     [Description : updates the labelInvalidsLetters label with the]
     [invalid letter, grabbed from HangMan                         ]
     --------------------------------------------------------------
*/

    private static void updateInvalidLetterList()
    {
        String output = "<html><p><center>____________________";
        int count = 0;
        for(Character singleLetter : game.getInvalidLetter())
        {
             if(count % 3 == 0)
                 output += "<br>     ";
            output += " " + singleLetter.toString() + " ";
            count++;
         }
        output += "</center></p></html>";
        labelInvalidLetters.setText(output);
    }

    /*
     --------------------------------------------------------------
     [Method      : displayMessage                                 ]
     [Args        : String                                         ]
     [Description : takes in a string, and displays an alert to the]
     [user.                                                        ]
     --------------------------------------------------------------
*/

    public static void displayMessage(String s)
    {
        JOptionPane.showMessageDialog(frame, s);
    }


    /*
     --------------------------------------------------------------
     [Method      : play                                           ]
     [Args        : void                                           ]
     [Description : grabs a new word, runs the evilPLay method in  ]
     [the HandMan Class, and updates the GUI, with new             ]
     [available information.                                       ]
     --------------------------------------------------------------
*/
    private static void play()
    {
    
        // Grabs a new word from the wordList array
        game.getNewWord();


        button.addActionListener(new ActionListener()
        {

            // Button Click
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                // If there are guesses left
                if (countGuesses < numberOfGuesses)
                {
                    countGuesses++;
                    labelGuessesLeft.setText("         " + (numberOfGuesses - countGuesses) + "        ");
                    
                    // Inputs the guess to the HangMan Class
                    game.inputGuess(textField.getText());
                    textField.setText("");
                    
                    // Runs the Evil Play Method
                    game.evilPlay();
                    
                    // Cheat Mode - Information - Words Left
                    System.out.println("Possible Words Left : " + game.getWordList().size() + "\n");
                    
                  
                    for (Letter singleLetter : game.getGuessedList())
                        if (singleLetter.getInWord())
                            textArray[singleLetter.getIndex()].setText(String.valueOf(singleLetter.getLetter()));
                            
                    // Updates Invalid Letter List        
                    updateInvalidLetterList();
                }
                
                // Game Over
                else
                {
                    displayMessage("Game Over!");
                    System.out.print("Game Over!");
                    updateHangManImageStatus();

                }
            }
        });
    }
}
