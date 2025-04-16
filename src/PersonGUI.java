//Priyanka Kolekar, Ian Moore, Nathan Nguyen
//OCCC Spring 2025
//Advanced Java
//Main class for Person GUI program

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PersonGUI extends JFrame implements ActionListener
{
   public static void main(String[] args)
   {
      PersonGUI pgui = new PersonGUI();
   }

   public PersonGUI() 
   {
      super("Person GUI");
      setSize(300, 300);
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

      setVisible(true);

      addWindowListener(new WindowAdapter(){
         @Override
         public void windowClosing(WindowEvent e)
         {
            exitPersonApp();
         }
      });

   }
   
   public void exitPersonApp()
   {
      dispose();
      System.exit(0);
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {

   }
}
