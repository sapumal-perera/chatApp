/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

/**
 *
 * @author IsuruP
 */
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MessageView {

    private JFrame mainFrame;
    private JPanel root;
    private JPanel listPane;
    private JPanel contentPane;
    private JTextField addAuthor;
    private JTextField addMessage;
    private JTextArea jTextArea;
    private static MessageView INSTANCE;
    private JButton addMessageButton;
    private JButton logoutButton;
    public String threadId = null;

    public void initChat() {
        initUI();
        mainFrame.setVisible(true);
        mainFrame.setTitle("Messages in Thread "+ threadId);
    }

    public MessageView(String id) {
        this.threadId = id;
    }

    public void initUI() {

        HttpClient hcl = new HttpClient(threadId, "", "getMessage");
        hcl.sendRequest();
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        String output = hcl.getOutput();
        JSONParser parser = new JSONParser();

        // MAIN FRAME
        mainFrame = new JFrame("Message View");
        //We need to set the size or we won't see the window
        mainFrame.setSize(new Dimension(750, 900));
        //This will center the window in the center of the screen
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(true);

        root = new JPanel();
        root.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = gridBagConstraints.weighty = 1;

        listPane = new JPanel();
        listPane.setBorder(new LineBorder(Color.black, 1));
        listPane.setPreferredSize(new Dimension(750, 900));

        addMessageButton = new JButton("Add Message");
        logoutButton= new JButton("Logout");
        addAuthor = new JTextField();
        addAuthor.setPreferredSize(new Dimension(300, 50));
        addAuthor.setToolTipText("Insert Your Nickname");
        addMessage = new JTextField();
        addMessage.setPreferredSize(new Dimension(400, 50));
        addMessage.setToolTipText("Type Your Message");
        logoutButton.addActionListener(e -> {
          Login login = new Login();
          login.setVisible(true);
        mainFrame.setVisible(false);
        });
        addMessageButton.addActionListener(e -> {
            String auth = addAuthor.getText();
            String msg = addMessage.getText();
            HttpClient httpc = new HttpClient(auth, msg, threadId, "addMessage");
            httpc.sendRequest();
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            String response = httpc.getOutput();
            if (response.equalsIgnoreCase("Success")) {
                MessageView mView = new MessageView(threadId);
                mainFrame.setVisible(false);
                mView.initChat();
            }

        });

        root.add(listPane, gridBagConstraints);

        JScrollPane scroll = new JScrollPane(root, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        mainFrame.add(scroll);
        mainFrame.pack();
       // JLabel headID = new JLabel("Join");
        JLabel headTitle = new JLabel("Message");
        JLabel headAuth = new JLabel("Sent By");
        JLabel headTime = new JLabel("Recived at");
       // headTitle.setPreferredSize(new Dimension(140, 50));
        headAuth.setPreferredSize(new Dimension(180, 50));
        headTitle.setPreferredSize(new Dimension(180, 50));
        headTime.setPreferredSize(new Dimension(180, 50));
        
        //listPane.add(headID);
        listPane.add(headTitle);
        listPane.add(headAuth);
        listPane.add(headTime);

        try {

            JSONArray json = (JSONArray) parser.parse(output);
            for (int i = 0; i < json.size(); i++) {
                String author = ((JSONObject) json.get(i)).get("author").toString();
                String message = ((JSONObject) json.get(i)).get("message").toString();
                String time = ((JSONObject) json.get(i)).get("time").toString();
                String msgId = ((JSONObject) json.get(i)).get("msgId").toString();
                addListItem(author, message, time, msgId);
            }
        } catch (Exception e) {
            System.out.println("Error in Json Object");
            e.printStackTrace();
        } finally {
            listPane.add(addAuthor);
            listPane.add(addMessage);
            listPane.add(addMessageButton);
            listPane.add(logoutButton);
            listPane.revalidate();
            listPane.repaint();
        }
    }

    public void addListItem(String name, String title, String time, String id) {
        MessageList listItem = new MessageList(name, title, time, id);

        listPane.add(listItem);
        listPane.revalidate();
        listPane.repaint();

    }
}
