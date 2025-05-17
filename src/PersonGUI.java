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

public class PersonGUI extends JFrame implements ActionListener {
    private JMenu fileMenu, helpMenu;
    private JMenuItem fileMenu_new, fileMenu_open, fileMenu_save, fileMenu_saveAs, fileMenu_exit;
    private JMenuItem helpMenu_about, helpMenu_shortcuts;
    
    private PersonList people = new PersonList();
    private JButton createPersonButton, duplicatePersonButton, deleteButton, clearButton, finishButton;
    //private JComboBox<Person> personCombo;
    private JComboBox personTypeBox;
    private JTextField firstNameField, lastNameField, dayField, monthField, yearField, govIDField, studentIDField;
    private JLabel govIDLabel, studentIDLabel;
    private DefaultListModel<Person> personListModel;
    private JList<Person> personList;

    public PersonGUI() {
        setTitle("Person GUI");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // File Menu Setup - Nathan Nguyen
        JMenuBar menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        fileMenu_new = new JMenuItem("New");
        fileMenu_open = new JMenuItem("Open...");
        fileMenu_save = new JMenuItem("Save");
        fileMenu_saveAs = new JMenuItem("Save as...");
        fileMenu_exit = new JMenuItem("Exit");

        fileMenu.setMnemonic(KeyEvent.VK_ESCAPE);
        fileMenu_new.setMnemonic(KeyEvent.VK_N);
        fileMenu_open.setMnemonic(KeyEvent.VK_O);
        fileMenu_save.setMnemonic(KeyEvent.VK_V);
        fileMenu_saveAs.setMnemonic(KeyEvent.VK_A);
        fileMenu_exit.setMnemonic(KeyEvent.VK_X);

        fileMenu.add(fileMenu_new);
        fileMenu.add(fileMenu_open);
        fileMenu.addSeparator();
        fileMenu.add(fileMenu_save);
        fileMenu.add(fileMenu_saveAs);
        fileMenu.addSeparator();
        fileMenu.add(fileMenu_exit);

        fileMenu_new.addActionListener(this);
        fileMenu_open.addActionListener(this);
        fileMenu_save.addActionListener(this);
        fileMenu_saveAs.addActionListener(this);
        fileMenu_exit.addActionListener(this);

        menuBar.add(fileMenu);

        // Help Menu Setup
        helpMenu = new JMenu("Help");
        helpMenu_about = new JMenuItem("About");
        helpMenu_shortcuts = new JMenuItem("Shortcuts");
        helpMenu_about.setMnemonic(KeyEvent.VK_H);
        helpMenu_shortcuts.setMnemonic(KeyEvent.VK_T);
        helpMenu.add(helpMenu_about);
        helpMenu.addSeparator();
        helpMenu.add(helpMenu_shortcuts);
        menuBar.add(helpMenu);
        
        helpMenu_about.addActionListener(this);
        helpMenu_shortcuts.addActionListener(this);

        setJMenuBar(menuBar);
         
        //GUI - Priyanka Kolekar
        // Initialize Panel 
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.setBorder(BorderFactory.createTitledBorder("CREATE PERSON HERE"));

        // First Name Setup
        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        firstNameField.setEnabled(false);
        formPanel.add(firstNameField);

        // Last Name Setup
        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        lastNameField.setEnabled(false);
        formPanel.add(lastNameField);

        // Birthday Setup
        formPanel.add(new JLabel("Birthday (mm/dd/yyyy):"));
        monthField = new JTextField(2);
        dayField = new JTextField(2);
        yearField = new JTextField(4);
        monthField.setEnabled(false);
        dayField.setEnabled(false);
        yearField.setEnabled(false);
        JPanel birthdayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        birthdayPanel.add(monthField);
        birthdayPanel.add(new JLabel(" / "));
        birthdayPanel.add(dayField);
        birthdayPanel.add(new JLabel(" / "));
        birthdayPanel.add(yearField);
        formPanel.add(birthdayPanel);

        // Person  Type Setup
        formPanel.add(new JLabel("Person Type:"));
        String[] personTypes = { "Person", "RegisteredPerson", "OCCCPerson" };
        personTypeBox = new JComboBox<>(personTypes);
        personTypeBox.setEnabled(false);
        formPanel.add(personTypeBox);
        
        // Government ID Setup
        govIDLabel = new JLabel("Government ID:");
        formPanel.add(govIDLabel);
        govIDField = new JTextField();
        govIDField.setEnabled(false);
        formPanel.add(govIDField);
        //govIDLabel.setVisible(false);
        //govIDField.setVisible(false);

        // Student ID Setup 
        studentIDLabel = new JLabel("Student ID:");
        formPanel.add(studentIDLabel);
        studentIDField = new JTextField();
        studentIDField.setEnabled(false);
        formPanel.add(studentIDField);
        //studentIDLabel.setVisible(false);
        //studentIDField.setVisible(false);

        add(formPanel, BorderLayout.NORTH);

        // Persons List
        personListModel = new DefaultListModel<>();
        personList = new JList<>(personListModel);
        JScrollPane listScrollPane = new JScrollPane(personList);
        listScrollPane.setBorder(BorderFactory.createTitledBorder("Person List"));
        add(listScrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        createPersonButton = new JButton("Create New Person");
        duplicatePersonButton = new JButton("Duplicate Person");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear List");
        finishButton = new JButton("Add Person");
        finishButton.setVisible(false);
        buttonPanel.add(finishButton);
        buttonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPanel.add(createPersonButton);
        buttonPanel.add(duplicatePersonButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);

        createPersonButton.addActionListener(this);
        duplicatePersonButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
        finishButton.addActionListener(this);

        personTypeBox.addActionListener(this);

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitPersonApp();
            }
        });
    }

    public static void main(String[] args) {
        PersonGUI GUI = new PersonGUI();
    }

   //Creates a new empty list and resets personListModel and text fields - Ian Moore
   private void createNewPersonList() 
   {
      people = new PersonList();
      personListModel.clear();
      firstNameField.setText("");
      lastNameField.setText("");
      personTypeBox.setSelectedIndex(0);
      dayField.setText("");
      monthField.setText("");
      yearField.setText("");
      govIDField.setText("");
      studentIDField.setText("");
      
      personList.setEnabled(true);
      firstNameField.setEnabled(false);
      lastNameField.setEnabled(false);
      personTypeBox.setEnabled(false);
      dayField.setEnabled(false);
      monthField.setEnabled(false);
      yearField.setEnabled(false);
      govIDField.setEnabled(false);
      studentIDField.setEnabled(false);
      finishButton.setVisible(false);
      fileMenu_save.setEnabled(true);
      fileMenu_saveAs.setEnabled(true);
   }
   
   private void addPersonToList() 
   {
      String firstName = firstNameField.getText();
      if (firstName == null || firstName.trim().isEmpty())
      {
         JOptionPane.showMessageDialog(null, "First Name is Required!", "Invalid Person", JOptionPane.ERROR_MESSAGE);
         return;
      }

      String lastName = lastNameField.getText();
      if (lastName == null || lastName.trim().isEmpty())
      {
         JOptionPane.showMessageDialog(null, "Last Name is Required!", "Invalid Person", JOptionPane.ERROR_MESSAGE);
         return;
      }

      int birthDay, birthMonth, birthYear;
      OCCCDate birthDate;
      try {
         birthYear = Integer.parseInt(yearField.getText());
         birthMonth = Integer.parseInt(monthField.getText());
         birthDay = Integer.parseInt(dayField.getText());
         birthDate = new OCCCDate(birthDay, birthMonth, birthYear);
      } 
      catch (IllegalArgumentException e) {
         JOptionPane.showMessageDialog(null, "Invalid Date!", "Invalid Person", JOptionPane.ERROR_MESSAGE);
         dayField.setText("");
         monthField.setText("");
         yearField.setText("");
         return;
      }

      if(personTypeBox.getSelectedItem().equals("RegisteredPerson")) {
         String govID = govIDField.getText();
         if (govID == null || govID.trim().isEmpty())
         {
            JOptionPane.showMessageDialog(null, "GovernmentID is Required!", "Invalid Person", JOptionPane.ERROR_MESSAGE);
            return;
         }
         RegisteredPerson newPerson = new RegisteredPerson(firstName, lastName, birthDate, govID);
         people.add(newPerson);
         personListModel.addElement(newPerson);
         personList.setSelectedValue(newPerson, true);
      }
      else if(personTypeBox.getSelectedItem().equals("OCCCPerson")) {
         String govID = govIDField.getText();
         String studentID = studentIDField.getText();
         if (govID == null || govID.trim().isEmpty())
         {
            JOptionPane.showMessageDialog(null, "GovernmentID is Required!", "Invalid Person", JOptionPane.ERROR_MESSAGE);
            return;
         }
         if(studentID == null || studentID.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Student ID is Required!", "Invalid Person", JOptionPane.ERROR_MESSAGE);
            return;
         }
         RegisteredPerson temp = new RegisteredPerson(firstName.trim(), lastName.trim(), birthDate, govID.trim());
         OCCCPerson newPerson = new OCCCPerson(temp, studentID.trim());
         people.add(newPerson);
         personListModel.addElement(newPerson);
         personList.setSelectedValue(newPerson, true);
      }
      else if(personTypeBox.getSelectedItem().equals("Person")) {
         Person newPerson = new Person(firstName.trim(), lastName.trim(), birthDate);
         people.add(newPerson);
         personListModel.addElement(newPerson);
         personList.setSelectedValue(newPerson, true);
      }

      finishButton.setVisible(false);
      firstNameField.setEnabled(false);
      lastNameField.setEnabled(false);
      personTypeBox.setEnabled(false);
      dayField.setEnabled(false);
      monthField.setEnabled(false);
      yearField.setEnabled(false);
      govIDField.setEnabled(false);
      studentIDField.setEnabled(false);
      personList.setEnabled(true);

      firstNameField.setText("");
      lastNameField.setText("");
      dayField.setText("");
      monthField.setText("");
      yearField.setText("");
      govIDField.setText("");
      studentIDField.setText("");
      personTypeBox.setSelectedIndex(0);

      fileMenu_save.setEnabled(true);
      fileMenu_saveAs.setEnabled(true);
   }

   void createNewPerson()
   {
      System.out.println("Create button clicked!");
      finishButton.setVisible(true);
      firstNameField.setEnabled(true);
      lastNameField.setEnabled(true);
      personTypeBox.setEnabled(true);
      dayField.setEnabled(true);
      monthField.setEnabled(true);
      yearField.setEnabled(true); 

      fileMenu_save.setEnabled(false);
      fileMenu_saveAs.setEnabled(false);
   }

   //Sets the values from the selected Person into the text fields - Ian Moore
   void createNewPersonFromTemplate()
   {
      if(personList.getSelectedIndex() < 0) {
         JOptionPane.showMessageDialog(null, "A pre-existing Person must be selected to do this!", "Empty List", JOptionPane.ERROR_MESSAGE);
         return;
      }
      
      personList.setEnabled(false);
      firstNameField.setText(personList.getSelectedValue().getFirstName());
      lastNameField.setText(personList.getSelectedValue().getLastName());
      personTypeBox.setSelectedItem(personList.getSelectedValue().getClass().getName());
      OCCCDate tempDOB = personList.getSelectedValue().getDOB();
      dayField.setText(String.valueOf(tempDOB.getDayOfMonth()));
      monthField.setText(String.valueOf(tempDOB.getMonthNumber()));
      yearField.setText(String.valueOf(tempDOB.getYear()));

      if(personList.getSelectedValue() instanceof RegisteredPerson) {
         RegisteredPerson tempReg = (RegisteredPerson)personList.getSelectedValue();
         govIDField.setText(tempReg.getGovernmentID());
      }
      if(personList.getSelectedValue() instanceof OCCCPerson) {
         OCCCPerson tempStudent = (OCCCPerson)personList.getSelectedValue(); 
         govIDField.setText(tempStudent.getGovernmentID());
         studentIDField.setText(tempStudent.getStudentID());
      }

      firstNameField.setEnabled(true);
      lastNameField.setEnabled(true);
      personTypeBox.setEnabled(true);
      dayField.setEnabled(true);
      monthField.setEnabled(true);
      yearField.setEnabled(true);
      finishButton.setVisible(true);

      fileMenu_save.setEnabled(false);
      fileMenu_saveAs.setEnabled(false);
   }

   private void deletePerson()
   {
      people.delete(personList.getSelectedValue());
      personListModel.removeElement(personList.getSelectedValue());
   }

   private void clearList()
   {
     people.clear();
     personListModel.removeAllElements();
   }

   private void openFile() 
   {
      JFileChooser fileChooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Person Files (*.per)", "per");
      fileChooser.setFileFilter(filter);

      int returnVal = fileChooser.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION)
      {
         try {
            File file = fileChooser.getSelectedFile();
            people = new PersonList();
            people.load(file);
            personListModel.clear();
            JOptionPane.showMessageDialog(this, "File loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
         }
         catch(Exception e) {
           JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
           System.out.println(e);
         }

		   for(Person p : people)
			{
            personListModel.addElement(p);
			}

		   if(!people.isEmpty()) 
         {
			   personList.setSelectedIndex(0);
			}
		}
	}

   private void saveFile()
   {
      if (!people.hasFileName()) {
         saveFileAs();
      } 
      else {
         try {
	         people.save();
            JOptionPane.showMessageDialog(this, "File saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
         }
         catch(IOException e) {
			   JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
		}
	}

   private void saveFileAs()
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

         try {
		      people.save(fileName);
            JOptionPane.showMessageDialog(this, "File saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
         }
         catch(IOException e) {
		      JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
      }
   }
   
   public void exitPersonApp()
   {
      if(people.hasBeenChangedSinceLastSave()) {
         int option = JOptionPane.showConfirmDialog(this, "Save Before Exit?", "Exit Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
         if (option == JOptionPane.NO_OPTION)
         {
            dispose();
            System.exit(0);
         } 
         else if (option == JOptionPane.YES_OPTION) 
         {
            saveFile();
            dispose();
            System.exit(0);
         }
         else if(option == JOptionPane.CANCEL_OPTION) {
            return;
         }
      }
      else {
         dispose();
         System.exit(0);
      }
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      if (e.getSource() == fileMenu_new) {
         if(people.hasBeenChangedSinceLastSave()) {
            int option = JOptionPane.showConfirmDialog(this, "Save First?", "New File Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.NO_OPTION)
            {
               createNewPersonList();
            } 
            else if (option == JOptionPane.YES_OPTION) 
            {
               saveFile();
               createNewPersonList();
            }
            else if(option == JOptionPane.CANCEL_OPTION) {
               return;
            } 
         }
         else {
            createNewPersonList();
         }
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
      if(e.getSource() == helpMenu_shortcuts) {
         JOptionPane.showMessageDialog(this, "<html><body>Help Menu: ALT+H<br><br>Display Shortcuts: ALT+T<br><br>File Menu: ALT+ESC<br><br>"
                             + "New File: ALT+N<br><br>Open File: ALT+O<br><br>Save File: ALT+V<br><br>Save As: ALT+A<br><br>Exit: ALT+X</body></html>",  "Shortcuts", JOptionPane.INFORMATION_MESSAGE);
      }
      if(e.getSource() == createPersonButton) {
         createNewPerson();
      }
      if(e.getSource() == duplicatePersonButton) {
        createNewPersonFromTemplate(); 
      }
      if(e.getSource() == finishButton) {
         addPersonToList();
      }
      if(e.getSource() == deleteButton) {
         deletePerson();
      }
      if(e.getSource() == clearButton) {
         clearList();
      }
      if(e.getSource() == personTypeBox) {
         if(personTypeBox.getSelectedItem().equals("Person")) {
            govIDField.setEnabled(false);
            studentIDField.setEnabled(false);
         }
         if(personTypeBox.getSelectedItem().equals("RegisteredPerson")) {
            govIDField.setEnabled(true);
            studentIDField.setEnabled(false);
         }
         if(personTypeBox.getSelectedItem().equals("OCCCPerson")) {
            govIDField.setEnabled(true);
            studentIDField.setEnabled(true);
         }
      }
  }

}
