/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MainView {
    private JFrame mainFrame;
    private JPanel root;
    private JPanel listPane;
    private JPanel contentPane;
    private JTextArea jTextArea;
    private static MainView INSTANCE;
    private JButton createNewButton;
    private JButton logoutButton;
  
    private java.util.List<ThreadList> listItems;
    private ThreadList currentItem;
 
    public static void initChat(){
        INSTANCE = new MainView();
        INSTANCE.listItems = new ArrayList<ThreadList>();
        INSTANCE.initUI();
        INSTANCE.mainFrame.setVisible(true);
    }

    public void initUI(){
       
      
        HttpClient hcl = new HttpClient("","","mainChat");
         hcl.sendRequest();
           System.out.println("");
         System.out.println("");
         System.out.println("");
          System.out.println("");
         String output =  hcl.getOutput(); 
          JSONParser parser = new JSONParser();
 
        // MAIN FRAME
        mainFrame = new JFrame("Space Chat All Threads");
        //We need to set the size or we won't see the window
        mainFrame.setSize(new Dimension(800,900));
        //This will center the window in the center of the screen
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(true);

        root = new JPanel();
        root.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = gridBagConstraints.weighty = 1;

        // LIST PANE
        // Will contain a list of ui elements
        listPane = new JPanel();
        listPane.setBorder(new LineBorder(Color.black,1));
        listPane.setPreferredSize(new Dimension(800, 900));

        // CONTENT PANE
        //Will contain the contents of each List item when clicked
        contentPane = new JPanel();
        contentPane.setBorder(new LineBorder(Color.black,1));
        contentPane.setPreferredSize(new Dimension(50, 600));

      //  jTextArea = new JTextArea();
     //   jTextArea.setPreferredSize(new Dimension(500,500));

        createNewButton = new JButton("Create New");
        logoutButton= new JButton("LogOut");

        contentPane.add(createNewButton);
        logoutButton.add(createNewButton);
       

        createNewButton.addActionListener(e -> {
           CreateChat addThread = new CreateChat();
        addThread.setVisible(true);
    mainFrame.setVisible(false);
        });
           logoutButton.addActionListener(e -> {
          Login login = new Login();
          login.setVisible(true);
        mainFrame.setVisible(false);
        });

       // contentPane.add(jTextArea);

        root.add(listPane,gridBagConstraints);
      //  root.add(contentPane, gridBagConstraints);
       JScrollPane scroll = new JScrollPane(root,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        mainFrame.add(scroll);
        mainFrame.pack();
          JLabel headID = new JLabel("Join by click   ");
       JLabel headTitle = new JLabel("Title     ");
      JLabel  headAuth = new JLabel("Author     ");
      JLabel  headTime = new JLabel("Last Edited    ");
      headID.setToolTipText("Click this to Open Chat");
          headID.setPreferredSize(new Dimension(180, 50));
          headAuth.setPreferredSize(new Dimension(180, 50));
          headTitle.setPreferredSize(new Dimension(180, 50));
          headTime.setPreferredSize(new Dimension(180, 50));
      listPane.add(headID);
      listPane.add(headTitle);
      listPane.add(headAuth);
      listPane.add(headTime);
     
      
                try{
        
         JSONArray json = (JSONArray) parser.parse(output);
         for(int i =0 ; i<json.size(); i++){
             String author =  ((JSONObject) json.get(i)).get("author").toString();
        String title =  ((JSONObject) json.get(i)).get("title").toString();
        String time =  ((JSONObject) json.get(i)).get("time").toString();
        String id =  ((JSONObject) json.get(i)).get("t_id").toString();
          addListItem(author,title,time,id);
         }
         }
         catch(Exception e){
             System.out.println("Error in Json Object");
             e.printStackTrace();
         }
         finally{
        listPane.add(createNewButton);
        listPane.add(logoutButton);
        listPane.revalidate();
        listPane.repaint();
         }
    }
//public void addItems(){
//        for (int i = 0; i<str.size();i++){
//            addListItem(str.get(i));}
//}
    
    
    public void addListItem(String name,String title,String time,String id){
        ThreadList listItem = new ThreadList(name,title,time,id);

        //What we want to do when this item is clicked on
//        listItem.onClickAction((thisItem) -> {
//          //  jTextArea.setText(thisItem.getData());
//            currentItem = thisItem;
//        });

        listPane.add(listItem);
        listPane.revalidate();
        listPane.repaint();
        listItems.add(listItem);
    }
}
