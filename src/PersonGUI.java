//Priyanka Kolekar, Ian Moore, Nathan Nguyen
//OCCC Spring 2025
//Advanced Java
//Main class for Person GUI program

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.ArrayList;
import java.io.*;

public class PersonGUI extends JFrame implements ActionListener
{
   private JMenuItem fileMenu_new, fileMenu_open, fileMenu_save, fileMenu_saveAs, fileMenu_exit;
   private JMenuItem helpMenu_about;
   private ArrayList<Person> people = new ArrayList<>();
   private JComboBox<Person> personCombo;
   private String currentFileName = null;
   
   public static void main(String[] args)
   {
      PersonGUI pgui = new PersonGUI();
   }

   public PersonGUI() 
   {
      super("Person GUI");
      setSize(300, 300);
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		JMenuBar bar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu_new = new JMenuItem("New");
		fileMenu_open = new JMenuItem("Open");
		fileMenu_save = new JMenuItem("Save");
		fileMenu_saveAs = new JMenuItem("Save As");
		fileMenu_exit = new JMenuItem("Exit");
		JMenu helpMenu = new JMenu("Help");
		helpMenu_about = new JMenuItem("About");

		fileMenu_new.addActionListener(this);
		fileMenu_open.addActionListener(this);
		fileMenu_save.addActionListener(this);
		fileMenu_saveAs.addActionListener(this);
		fileMenu_exit.addActionListener(this);
		helpMenu_about.addActionListener(this);

		fileMenu.add(fileMenu_new);
		fileMenu.add(fileMenu_open);
		fileMenu.add(fileMenu_save);
		fileMenu.add(fileMenu_saveAs);
		fileMenu.add(fileMenu_exit);
		helpMenu.add(helpMenu_about);
		bar.add(fileMenu);
		bar.add(helpMenu);
		setJMenuBar(bar);
      setVisible(true);

      addWindowListener(new WindowAdapter(){
         @Override
         public void windowClosing(WindowEvent e)
         {
            exitPersonApp();
         }
      });

   }
   
	private void createNewPerson() {
		String firstName = JOptionPane.showInputDialog(this, "Enter first name:", "New Person", JOptionPane.PLAIN_MESSAGE);
		if (firstName == null || firstName.trim().isEmpty())
		{
	        JOptionPane.showMessageDialog(null, "First Name is Required!", "Invalid Person", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String lastName = JOptionPane.showInputDialog(this, "Enter last name:", "New Person", JOptionPane.PLAIN_MESSAGE);
		if (lastName == null || lastName.trim().isEmpty())
		{
	        JOptionPane.showMessageDialog(null, "Last Name is Required!", "Invalid Person", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int birthDay, birthMonth, birthYear;
		OCCCDate birthDate;
		try {
			birthYear = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter birth year:", JOptionPane.PLAIN_MESSAGE));
			birthMonth = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter birth month:", JOptionPane.PLAIN_MESSAGE));
			birthDay = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter birth day:", JOptionPane.PLAIN_MESSAGE));
			birthDate = new OCCCDate(birthDay, birthMonth, birthYear);
        } catch (NumberFormatException e)
		{
        	JOptionPane.showMessageDialog(null, "Invalid Date!", "Invalid Person", JOptionPane.ERROR_MESSAGE);
        	return;
        }

		Person newPerson = new Person(firstName.trim(), lastName.trim(), birthDate);
		people.add(newPerson);
		personCombo.addItem(newPerson);
		personCombo.setSelectedItem(newPerson);
	}
	private void openFile() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Person Files (*.per)", "per");
		fileChooser.setFileFilter(filter);

		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			currentFileName = null;
			File file = fileChooser.getSelectedFile();
			currentFileName = file.getPath();
			loadFromFile(currentFileName);
		}
	}
	private void loadFromFile(String fileName) {
		ArrayList<Person> loadedPeople = new ArrayList<>();

		try (FileInputStream fin = new FileInputStream(fileName); ObjectInputStream oin = new ObjectInputStream(fin))
		{
			while (true)
			{
				try {
					Object o = oin.readObject();
					if (o instanceof Person) {
						loadedPeople.add((Person) o);
					}
				} catch (EOFException e) {
					break;
				}
			}
			people = loadedPeople;
			personCombo.removeAllItems();
			for (int i = 0; i < people.size(); i++)
			{
				Person p = people.get(i);
				personCombo.addItem(p);
			}

			if (!people.isEmpty()) {
				personCombo.setSelectedIndex(0);
			}

			JOptionPane.showMessageDialog(this, "File loaded successfully! Loaded.", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean saveFile()
	{
		if (currentFileName == null) {
			if (saveFileAs()) {
				return true;
			}
		} else {
			saveToFile(currentFileName);
			return true;
		}
		return false;
	}

	private boolean saveFileAs()
	{
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Person Files (*.per)", "per");
		fileChooser.setFileFilter(filter);

		int returnVal = fileChooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			String fileName = file.getPath();

			if (!fileName.toLowerCase().endsWith(".per")) {
				fileName += ".per";
			}

			saveToFile(fileName);
			currentFileName = fileName;
			return true;
		}
		return false;
	}

	private void saveToFile(String fileName)
	{
		try (
			FileOutputStream fout = new FileOutputStream(fileName);
			ObjectOutputStream oout = new ObjectOutputStream(fout))
		{
			for (int i = 0; i < people.size(); ++i)
			{
				Person p = people.get(i);
				oout.writeObject(p);
			}
			JOptionPane.showMessageDialog(this, "File saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
   
   public void exitPersonApp()
   {
		int option = JOptionPane.showConfirmDialog(this, "Save Before Exit?", "Exit Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (option == JOptionPane.NO_OPTION)
		{
			dispose();
			System.exit(0);
		} else if (option == JOptionPane.YES_OPTION) 
		{
			if (saveFile()) 
			{
				dispose();
				System.exit(0);
			}
		}
   }

   @Override
   public
   void actionPerformed(ActionEvent e)
   {
		if (e.getSource() == fileMenu_new) {
			createNewPerson();
		}
		if (e.getSource() == fileMenu_exit) {
			exitPersonApp();
		}
		if (e.getSource() == fileMenu_open) {
			openFile();
		}
		if (e.getSource() == fileMenu_save) {
			saveFile();
		}
		if (e.getSource() == fileMenu_saveAs) {
			saveFileAs();
		}
		if (e.getSource() == helpMenu_about) {
			JOptionPane.showMessageDialog(this, "Person Application\nPriyanka Kolekar, Ian Moore, and Nathan Nguyen", "About",	JOptionPane.INFORMATION_MESSAGE);
		}
   }
}