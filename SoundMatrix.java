/*
read current user-made song (including chords by adding "," to the string of notes) and save it to the preSongs arrayList (can't just save it by saving it, gotta read it into a temporary thing and save that thing
cause object reference will fuck it up) and also be able to access the song with the "Load Song" button

so basically rn fix the "save song" button's method

cause basically all the user made songs are always the same - i have to save the user made songs (and then later read them) the exact same way i read and write the premade songs

"Save Song" button's save method needs to be exactly the same as the code in lines 115-136 and save to the ArrayList as well





--------------------------------

in the save button method, make it write to the text file immediately - all load song calls should be pulling straight from the text file instead of calling preSongsArray
use superLoadSong method, receive an integer, look through text file for that song number, do read write and load from that
text file --> array of String notes --> readSong (returns jtogglebuttons} --> loadSong

*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.MalformedURLException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.math.*;
import java.awt.image.*;
import java.applet.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.MalformedURLException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.geom.Line2D;
import java.lang.Object;
import javafx.scene.shape.Line;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public class SoundMatrix extends JFrame implements ActionListener, AdjustmentListener{

	private JToggleButton buttons[][];
	private JToggleButton tempButtons[][];
	private JButton[] menuButtons;
	JScrollBar timeBar, colorBar;
	JPanel scrollPanel, labelPanel, infoPanel;
	JLabel timeLabel, colorLabel;
	private String fileNames[] = {"C4.wav", "D4.wav", "E4.wav", "F4.wav", "G4.wav", "A4.wav", "B4.wav", "C5.wav", "D5.wav", "E5.wav", "F5.wav", "G5.wav", "A5.wav", "B5.wav", "C6.wav"};
	//private ArrayList<JToggleButton[][]> preSongs;
	//private ArrayList<String[]> preSongsArray;
	private Container container;
	int time = 500;
	int colorNum = 1;
	String colorChoice = "Default";
	private JPanel menuPanel;
	private JPanel gridPanel;
	private boolean playing = false;
	MusicPlayer t1;
	JPopupMenu popup;
	//int numUserSongs = 0;
	int numSongs = 2;
	ArrayList<String> superSafeSongStorage;
	int songFileCount;
	Color grayColor;
	Color whiteColor;

	public SoundMatrix() {

		grayColor = new Color(206, 213, 224);
		whiteColor = new Color(234, 242, 255);

		/*
		//PRE-SONGS
		preSongsArray = new ArrayList<String[]>();

		File name = new File("Songs.txt");
		String str = "";
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));

			String text,output="";
			while( (text=input.readLine())!= null)
			{
				String[] temp = text.split(",");
				preSongsArray.add(temp);
			}
		}
		catch (IOException io)
		{
			System.err.println("File does not exist");
		}
		for (int i = 0; i < preSongsArray.size(); i ++) {
			System.out.println("" + i + ": " + preSongsArray.get(i));
		}
		*/


		//popup menu
		songFileCount = 0;
		File name = new File("Songs.txt");
		String str = "";
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));

			String text,output="";
			while( (text=input.readLine())!= null)
			{
				songFileCount ++;    //count how many songs are currently in the file to make appropriate number of buttons
			}
		}
		catch (IOException io)
		{
			System.err.println("File does not exist");
		}

        popup = new JPopupMenu();

        for (int i = 0; i < songFileCount; i ++) {
			JButton tempButton = new JButton("Song " + (i+1));                  //make dem buttons
			tempButton.addActionListener(this);
			popup.add(tempButton);
		}


        /*popup.add(new JMenuItem(new AbstractAction("Song 1") {
			public void actionPerformed(ActionEvent e) {

				//superLoadSong(0);
				this.addActionListener(this);
				if (popup.isVisible()) {
					popup.setVisible(false);
				}
			}
		}));
		popup.add(new JMenuItem(new AbstractAction("Song 2") {
			public void actionPerformed(ActionEvent e) {
				//loadSong(readSong(preSongsArray.get(1)));
				superLoadSong(1);
				if (popup.isVisible()) {
					popup.setVisible(false);
				}
			}
		}));
		popup.add(new JMenuItem(new AbstractAction("Song 3") {
			public void actionPerformed(ActionEvent e) {
				//loadSong(readSong(preSongsArray.get(2)));
				superLoadSong(2);
				if (popup.isVisible()) {
					popup.setVisible(false);
				}
			}
		}));*/


		/*//preSongs:
		preSongs = new ArrayList<JToggleButton[][]>();

		JToggleButton[][] maryLamb = new JToggleButton[15][29];
		JToggleButton[][] hotBuns = new JToggleButton[15][15];
		JToggleButton[][] cannon = new JToggleButton[15][17];
		String[] maryNotes = {"E5", "D5", "C5", "D5", "E5", "E5", "E5", "", "D5", "D5", "D5", "", "E5", "G5", "G5", "", "E5", "D5", "C5", "D5", "E5", "E5", "E5", "E5", "D5", "D5", "E5", "D5", "C5"};
		String[] bunsNotes = {"B5", "A5", "G5", "", "B5", "A5", "G5", "", "G5", "G5", "A5", "A5", "B5", "A5", "G5"};
		String[] cannonNotes = {"E5", "D5", "C5", "B4", "A4", "G4", "A4", "B4", "E5", "D5", "C5", "B4", "A4", "G4", "A4", "B4", "C5"};
		int counterFill = 0;


		preSongs.add(readSong(maryNotes));
		preSongs.add(readSong(bunsNotes));
		preSongs.add(readSong(cannonNotes));*/
		///////////////////////////////////////////////////////////////////////

		//superSafeSongStorage
		superSafeSongStorage = new ArrayList<String>();  //stores the three songs in the java file, give option to user to reset text file and add only these songs
		superSafeSongStorage.add("E4-E5,D4-D5,C4-C5,D4-D5,E4-E5,E4-E5,E4-E5,,D4-D5,D4-D5,D4-D5,,E4-E5,G4-G5,G4-G5,,E4-E5,D4-D5,C4-C5,D4-D5,E4-E5,E4-E5,E4-E5,E4-E5,D4-D5,D4-D5,E4-E5,D4-D5,C4-C5,,C4-E4-G4-C5-E5-G5-C6");
		superSafeSongStorage.add("B5,A5,G5,,B5,A5,G5,,G5,G5,A5,A5,B5,A5,G5");
		superSafeSongStorage.add("E5,D5,C5,B4,A4,G4,A4,B4,E5,D5,C5,B4,A4,G4,A4,B4,C5");

		container = getContentPane();

		menuButtons = new JButton[8];
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(1, menuButtons.length));
		menuButtons[0] = new JButton("+ Column");
		menuButtons[1] = new JButton("- Column");
		menuButtons[2] = new JButton("Random Song");
		menuButtons[3] = new JButton("Clear Song");
		menuButtons[4] = new JButton("Save Song");
		menuButtons[5] = new JButton("Load Song");
		menuButtons[6] = new JButton("Delete User Songs");
		menuButtons[7] = new JButton("Play/Stop");
		for (int i = 0; i < menuButtons.length; i ++) {
			menuButtons[i].addActionListener(this);
			menuPanel.add(menuButtons[i]);
		}

		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(15, 15));
		buttons = new JToggleButton [15][15];
		int counterFill = 0;
		for (int row = 0; row < buttons.length; row ++) {
			for (int col = 0; col < buttons[row].length; col ++) {
				buttons[row][col] = new JToggleButton(fileNames[counterFill].substring(0, fileNames[counterFill].indexOf(".")));
				buttons[row][col].addActionListener(this);
				gridPanel.add(buttons[row][col]);
			}
			counterFill++;
		}

		container.add(menuPanel, BorderLayout.NORTH);
		container.add(gridPanel, BorderLayout.CENTER);

		//scroll Panel stuff
		labelPanel=new JPanel();
		timeLabel=new JLabel("Time: "+time);
		colorLabel = new JLabel("Color Choice: " + colorChoice);
		labelPanel.add(timeLabel);
		labelPanel.add(colorLabel);
		labelPanel.setLayout(new GridLayout(2,1));

		timeBar=new JScrollBar(JScrollBar.HORIZONTAL,500,0,100,1000);
		timeBar.setPreferredSize(new Dimension(800,20));
		timeBar.addAdjustmentListener(this);
		colorBar=new JScrollBar(JScrollBar.HORIZONTAL,1,0,1,5);
		colorBar.setPreferredSize(new Dimension(800,20));
		colorBar.addAdjustmentListener(this);
		scrollPanel=new JPanel();
		scrollPanel.setLayout(new GridLayout(2,1));
		scrollPanel.add(timeBar);
		scrollPanel.add(colorBar);

		infoPanel=new JPanel();
		infoPanel.add(BorderLayout.WEST,labelPanel);
		infoPanel.add(BorderLayout.CENTER,scrollPanel);

		container.add(infoPanel, BorderLayout.SOUTH);

		setSize(1600,800);
		setVisible(true);

		whiteWash();

	}
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (e.getSource() == timeBar) {
			time = timeBar.getValue();
			timeLabel.setText("Time: " + time);
		}
		if (e.getSource() == colorBar) {
			colorNum = colorBar.getValue();
			colorLabel.setText("Color Choice: " + colorChoice);
			Color tempColor = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
			switch (colorNum) {
				case 1:
					colorChoice = "Default";
					gridPanel.setBackground(null);
					menuPanel.setBackground(null);
					UIManager.put("ToggleButton.select", null);
					for (int row = 0; row < buttons.length; row ++) {
						for (int col = 0; col < buttons[0].length; col ++) {
							SwingUtilities.updateComponentTreeUI(buttons[row][col]);
						}
					}
					break;
				case 2:
					colorChoice = "Red";
					gridPanel.setBackground(Color.RED);
					menuPanel.setBackground(Color.RED);
					UIManager.put("ToggleButton.select", Color.RED);
					for (int row = 0; row < buttons.length; row ++) {
						for (int col = 0; col < buttons[0].length; col ++) {
							SwingUtilities.updateComponentTreeUI(buttons[row][col]);
						}
					}
					break;
				case 3:
					colorChoice = "Blue";
					gridPanel.setBackground(Color.BLUE);
					menuPanel.setBackground(Color.BLUE);
					UIManager.put("ToggleButton.select", Color.BLUE);
					for (int row = 0; row < buttons.length; row ++) {
						for (int col = 0; col < buttons[0].length; col ++) {
							SwingUtilities.updateComponentTreeUI(buttons[row][col]);
						}
					}
					break;
				case 4:
					colorChoice = "Green";
					gridPanel.setBackground(Color.GREEN);
					menuPanel.setBackground(Color.GREEN);
					UIManager.put("ToggleButton.select", Color.GREEN);
					for (int row = 0; row < buttons.length; row ++) {
						for (int col = 0; col < buttons[0].length; col ++) {
							SwingUtilities.updateComponentTreeUI(buttons[row][col]);
						}
					}
					break;
				case 5:
					gridPanel.setBackground(tempColor);
					menuPanel.setBackground(tempColor);
					UIManager.put("ToggleButton.select", tempColor);
					for (int row = 0; row < buttons.length; row ++) {
						for (int col = 0; col < buttons[0].length; col ++) {
							//tempColor = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
							SwingUtilities.updateComponentTreeUI(buttons[row][col]);
						}
					}
					colorChoice = "Random";
					break;
			}
		}
	}

	public void actionPerformed(ActionEvent event) {

		whiteWash();

		if (popup.isVisible() && !((AbstractButton)event.getSource()).getLabel().equals("Load Song")) {
			popup.setVisible(false);
		}

		if (((AbstractButton)event.getSource()).getLabel().equals("+ Column")) {
			addColumn();
		}
		else if (((AbstractButton)event.getSource()).getLabel().equals("- Column")) {
			removeColumn();
		}
		else if (((AbstractButton)event.getSource()).getLabel().contains("Song ")) {
			int songNum = Integer.parseInt(((AbstractButton)event.getSource()).getLabel().substring(5));
			superLoadSong(songNum - 1);                        //finds the song number of the button that was pressed (-1 cause the method starts at 0)
		}
		else if (((AbstractButton)event.getSource()).getLabel().equals("Random Song")) {
			for (int row = 0; row < buttons.length; row ++) {
				for (int col = 0; col < buttons[0].length; col ++) {
					buttons[row][col].setSelected(false);
					if ((int)(Math.random()*100)+1 <= 15) {
						buttons[row][col].setSelected(true);
					}
				}
			}
		}
		else if (((AbstractButton)event.getSource()).getLabel().equals("Clear Song")) {
			for (int row = 0; row < buttons.length; row ++) {
				for (int col = 0; col < buttons[0].length; col ++) {
					buttons[row][col].setSelected(false);
				}
			}
		}
		else if (((AbstractButton)event.getSource()).getLabel().equals("Save Song")) {

			ArrayList<String> fileLines = new ArrayList<String>();

			//Read the text file, add all the lines to a temporary ArrayList
			int count = 0;
			File name = new File("Songs.txt");
			String str = "";
			try
			{
				BufferedReader input = new BufferedReader(new FileReader(name));

				String text,output="";
				while( (text=input.readLine())!= null)
				{
					fileLines.add(text);
				}
			}
			catch (IOException io)
			{
				System.err.println("File does not exist");
			}

			String completeSong = "";
			int firstCount = 0;
			String tempString = "";
			String[] temp = new String[buttons[0].length];
			for (int col = 0; col < buttons[0].length; col ++) {
				firstCount = 0;
				tempString = "";
				for (int row = 0; row < buttons.length; row ++) {
					if (buttons[row][col].isSelected()) {
						if (firstCount == 0) {
							tempString += buttons[row][col].getLabel();
							firstCount ++;
						}
						else {
							tempString += "-" + buttons[row][col].getLabel();
						}
					}
				}
				temp[col] = tempString;
			}
			for (int i = 0; i < temp.length; i ++) {
				if (i == 0) {
					completeSong += temp[i];
				}
				else {
					completeSong += "," + temp[i];
				}
			}
			fileLines.add(completeSong);    //add the current song (converted to a string) to the temporary ArrayList

			try {
				PrintWriter writer = new PrintWriter("Songs.txt", "UTF-8");
				for (int i = 0; i < fileLines.size(); i ++) {
					writer.println(fileLines.get(i));                                   //write the songs from the temporary ArrayList to the text file
				}
				writer.close();
			}
			catch (FileNotFoundException e) {}
			catch (UnsupportedEncodingException e) {}



			numSongs ++;                                                               //add a new button to the popup menu for the new song
			songFileCount++;
			JButton tempButton = new JButton("Song " + songFileCount);
			tempButton.addActionListener(this);
			popup.add(tempButton);
		}
		else if (((AbstractButton)event.getSource()).getLabel().equals("Delete User Songs")) {
			try {
				PrintWriter writer = new PrintWriter("Songs.txt", "UTF-8");             //gets rid of the textfile full of garbage songs that I made, and adds only the three
				for (int i = 0; i < superSafeSongStorage.size(); i ++) {                //high quality songs (that i also made actually) from the superSafeSongStorage list
					writer.println(superSafeSongStorage.get(i));
				}
				writer.close();
			}
			catch (FileNotFoundException e) {}
			catch (UnsupportedEncodingException e) {}


			popup = new JPopupMenu();
			for (int i = 0; i < 3; i ++) {
				JButton tempButton = new JButton("Song " + (i+1));
				tempButton.addActionListener(this);
				popup.add(tempButton);
			}
		}
		else if (((AbstractButton)event.getSource()).getLabel().equals("Load Song")) {
			/*if (popup.isVisible()) {
				popup.setVisible(false);
			}
			else {
				popup.show(null, 1250,50);
			}*/
			if (!popup.isVisible()) {
				popup.show(null, 1090, 50);
				popup.setVisible(true);
			}
			else {
				popup.setVisible(false);
			}

		}
		else if (((AbstractButton)event.getSource()).getLabel().equals("Play/Stop")) {
			if (!playing) {
				t1 = new MusicPlayer();
				t1.start();
			}
			else if (playing) {
				t1.stop();

				for (int row = 0; row < buttons.length; row ++) {              //fixes white background staying after old thread finishes
					for (int col = 0; col < buttons[0].length; col ++) {
						//buttons[row][col].setBackground(null);
						whiteWash();
					}
				}
			}
			playing = !playing;
		}
		/*else {
			if (!colorChoice.equals("Default")) {
				Color buttonColor = new Color(0, 0, 0);
				switch (colorNum) {
					case 2: //red
						buttonColor = new Color(255,0,0);
						break;
					case 3: //blue
						buttonColor = new Color(0,0,255);
						break;
					case 4: //green
						buttonColor = new Color(0,255,0);
						break;
					case 5:
						buttonColor = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
						break;
				}
				((AbstractButton)event.getSource()).setBackground(buttonColor);
			}
		}
		for (int row = 0; row < buttons.length; row ++) {
			for (int col = 0; col < buttons[0].length; col ++) {
				if (buttons[row][col].isSelected()) {
					if (colorNum == 1) {
						buttons[row][col].setBackground(null);
					}
					else {
						buttons[row][col].setBackground(Color.WHITE);
					}
				}
				else {
					buttons[row][col].setBackground(null);
				}
			}
		}*/



		if (false) {   //play sound when clicked on
			System.out.println("Action Performed");
			for (int row = 0; row < buttons.length; row ++) {
				if (((AbstractButton)event.getSource()).getLabel().equals(buttons[row][0].getLabel())) {
					try {
						playSound("New Music Box Tones/" + buttons[row][0].getLabel() + ".wav");
					} catch (MalformedURLException ex) {
					} catch (LineUnavailableException ex) {
					} catch (UnsupportedAudioFileException ex) {
					} catch (IOException ex) {
					}
				}
			}
		}  //delete this


		container.validate();

		whiteWash();
	}

	/*public void playMusic() {
		while (true) {
			for (int col = 0; col < buttons[0].length; col ++) {
				for (int row = 0; row < buttons.length; row ++) {
					buttons[row][col].setBackground(Color.WHITE);   //highlight current column
					if (buttons[row][col].isSelected()) {
						try {
							playSound("New Music Box Tones/" + buttons[row][col].getLabel() + ".wav");
						} catch (MalformedURLException ex) {
						} catch (LineUnavailableException ex) {
						} catch (UnsupportedAudioFileException ex) {
						} catch (IOException ex) {}
					}
				}
				try {                                              //remove highlight from current column and wait
					Thread.sleep(time);
					for (int row = 0; row < buttons.length; row ++) {
						buttons[row][col].setBackground(null);
					}
				} catch(Exception ex) {}
			}
			try {
				Thread.sleep(time);
			} catch(InterruptedException ex) {}
		}
	}*/

	public class MusicPlayer extends Thread {
		public void run() {
			while (true) {
				for (int col = 0; col < buttons[0].length; col ++) {
					for (int row = 0; row < buttons.length; row ++) {
						//buttons[row][col].setBackground(Color.WHITE);   //highlight current column
						if (buttons[row][col].isSelected()) {
							try {
								playSound("New Music Box Tones/" + buttons[row][col].getLabel() + ".wav");
							} catch (MalformedURLException ex) {
							} catch (LineUnavailableException ex) {
							} catch (UnsupportedAudioFileException ex) {
							} catch (IOException ex) {}
						}
						else {   //button is not selected
							buttons[row][col].setBackground(grayColor);
						}
					}
					try {                                              //remove highlight from current column and wait
						Thread.sleep(time);
						for (int row = 0; row < buttons.length; row ++) {
							buttons[row][col].setBackground(whiteColor);
						}
					} catch(Exception ex) {}
				}
				try {
					Thread.sleep(time);
				} catch(InterruptedException ex) {}
			}
		}
	}

	public void addColumn() {

		container.removeAll();

		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(15, buttons[0].length - 1));

		tempButtons = new JToggleButton[buttons.length][buttons[0].length+1];
		for (int row = 0; row < buttons.length; row ++) {
			for (int col = 0; col < buttons[0].length; col ++) {
				tempButtons[row][col] = buttons[row][col];
			}
		}

		int counterFill = 0;
		for (int row = 0; row < tempButtons.length; row ++) {
			tempButtons[row][tempButtons[0].length-1] = new JToggleButton(fileNames[counterFill].substring(0, fileNames[counterFill].indexOf(".")));
			tempButtons[row][tempButtons[0].length-1].addActionListener(this);
			counterFill++;
		}

		buttons = tempButtons;

		for (int row = 0; row < buttons.length; row++) {
			for (int col = 0; col < buttons[0].length; col ++) {
				gridPanel.add(buttons[row][col]);
			}
		}

		container.add(menuPanel, BorderLayout.NORTH);
		container.add(gridPanel, BorderLayout.CENTER);
		container.add(infoPanel, BorderLayout.SOUTH);
		container.validate();

		whiteWash();
	}

	public void removeColumn() {

		container.removeAll();

		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(15, buttons[0].length - 1));

		tempButtons = new JToggleButton[buttons.length][buttons[0].length-1];
		for (int row = 0; row < tempButtons.length; row ++) {
			for (int col = 0; col < tempButtons[0].length; col ++) {
				tempButtons[row][col] = buttons[row][col];
			}
		}

		buttons = tempButtons;

		for (int row = 0; row < buttons.length; row ++) {
			for (int col = 0; col < buttons[0].length; col ++) {
				gridPanel.add(buttons[row][col]);
			}
		}

		container.add(menuPanel, BorderLayout.NORTH);
		container.add(gridPanel, BorderLayout.CENTER);
		container.add(infoPanel, BorderLayout.SOUTH);
		container.validate();

		whiteWash();
	}

	/*public void loadSong(int x) {

		buttons = new JToggleButton[preSongs.get(x).length][preSongs.get(x)[0].length];
		int counterFill = 0;
		for (int row = 0; row < buttons.length; row ++) {
			for (int col = 0; col < buttons[0].length; col ++) {
				buttons[row][col] = new JToggleButton(fileNames[counterFill].substring(0, fileNames[counterFill].indexOf(".")));
				buttons[row][col].addActionListener(this);
				if (preSongs.get(x)[row][col].isSelected()) {
					buttons[row][col].setSelected(true);
				}
			}
			counterFill ++;
		}

		//output preSong array as string
		for (int row = 0; row < preSongs.get(x).length; row ++) {
			for (int col = 0; col < preSongs.get(x)[0].length; col ++) {
				if (preSongs.get(x)[row][col].isSelected()) {
					System.out.print(preSongs.get(x)[row][col].getLabel() + " ");
				}
				else {
					System.out.print("   ");
				}
			}
			System.out.println();
		}


		container.removeAll();

		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(15, buttons[0].length));

		for (int row = 0; row < buttons.length; row ++) {
			for (int col = 0; col < buttons[0].length; col ++) {
				gridPanel.add(buttons[row][col]);
			}
		}

		container.add(menuPanel, BorderLayout.NORTH);
		container.add(gridPanel, BorderLayout.CENTER);
		container.validate();
	}*/
	public void loadSong(JToggleButton[][] listButtons) {   //OVERLOADED LOADSONG

		buttons = new JToggleButton[listButtons.length][listButtons[0].length];
		int counterFill = 0;
		for (int row = 0; row < buttons.length; row ++) {
			for (int col = 0; col < buttons[0].length; col ++) {
				buttons[row][col] = new JToggleButton(fileNames[counterFill].substring(0, fileNames[counterFill].indexOf(".")));
				buttons[row][col].addActionListener(this);
				if (listButtons[row][col].isSelected()) {
					buttons[row][col].setSelected(true);
				}
			}
			counterFill ++;
		}

		container.removeAll();

		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(15, buttons[0].length));

		for (int row = 0; row < buttons.length; row ++) {
			for (int col = 0; col < buttons[0].length; col ++) {
				gridPanel.add(buttons[row][col]);
			}
		}

		container.add(menuPanel, BorderLayout.NORTH);
		container.add(gridPanel, BorderLayout.CENTER);
		container.add(infoPanel, BorderLayout.SOUTH);
		container.validate();

		whiteWash();

	}

	public JToggleButton[][] readSong(String[] song) {  //returns an 2D array of jtogglebuttons from the string of list notes

		//fill maryLamb array of JButtons with nothing selected
		int maryCounter = 0;
		int counterFill = 0;
		JToggleButton[][] maryLamb = new JToggleButton[15][song.length];
		for (int row = 0; row < maryLamb.length; row ++) {
			for (int col = 0; col < maryLamb[0].length; col ++) {
				maryLamb[row][col] = new JToggleButton(fileNames[counterFill].substring(0, fileNames[counterFill].indexOf(".")));
				maryLamb[row][col].addActionListener(this);
			}
			counterFill++;
		}
		//select the maryLamb buttons for song
		for (int col = 0; col < maryLamb[0].length; col ++) {
			String[] tempNotes = song[col].split("-");
			for (int row = 0; row < maryLamb.length; row ++) {
				if (Arrays.asList(tempNotes).contains(maryLamb[row][col].getLabel())) {
					maryLamb[row][col].setSelected(true);
				}
			}
			maryCounter ++;
		}

		return maryLamb;
	}

	public void superLoadSong(int x) {
		//PRE-SONGS
		int count = 0;
		File name = new File("Songs.txt");
		String str = "";
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));

			String text,output="";
			while( (text=input.readLine())!= null)
			{
				if (count == x) {
					String[] temp = text.split(",");
					loadSong(readSong(temp));
				}
				count ++;
			}
		}
		catch (IOException io)
		{
			System.err.println("File does not exist");
		}
	}
	public void whiteWash() {
		for (int row = 0; row < buttons.length; row ++) {
			for (int col = 0; col < buttons[0].length; col++) {
				if (!buttons[row][col].isSelected()) {
					buttons[row][col].setBackground(whiteColor);
				}
			}
		}
	}

	public static void playSound(String fileName) throws MalformedURLException, LineUnavailableException, UnsupportedAudioFileException, IOException {
		    File url = new File(fileName);
		    Clip clip = AudioSystem.getClip();

		    AudioInputStream ais = AudioSystem.getAudioInputStream( url );
		    clip.open(ais);
		    clip.start();
	}

	public static void main(String args[] )
	{
		SoundMatrix application = new SoundMatrix();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
