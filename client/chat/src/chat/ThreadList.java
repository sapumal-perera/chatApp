/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;

public class ThreadList extends JPanel {

    private JButton buttonId;
     private JLabel lblSpace;
    private JLabel lblTitle;
    private JLabel lblAuth;
    private JLabel lblTime;
   

    public ThreadList(String name, String title, String time, String id) {

        buttonId = new JButton(id);
        lblSpace = new JLabel("   ");
        lblAuth = new JLabel(name.concat("   "));
        lblTitle = new JLabel(title.concat("   "));
        lblTime = new JLabel(time.concat(" "));
        buttonId.setPreferredSize(new Dimension(60, 20));
        lblSpace.setPreferredSize(new Dimension(120, 20));
          lblAuth.setPreferredSize(new Dimension(180, 20));
          lblTitle.setPreferredSize(new Dimension(180, 20));
          lblTime.setPreferredSize(new Dimension(180, 20));
          buttonId.setToolTipText("Click to Open "+title+" by "+name);
          lblAuth.setToolTipText("Thread Started by "+name);
          lblTitle.setToolTipText("Last Edited at "+time);
        GuiList gl = new GuiList();
        buttonId.addActionListener(gl);
       
        add(buttonId);
        add(lblSpace);
        add(lblTitle);
        add(lblAuth);
        add(lblTime);
        setPreferredSize(new Dimension(1000, 30));
        setBackground(Color.LIGHT_GRAY);
    }

    //We can use this interface to allow us get the item that we just interacted with into the action we've passed in
    public static interface ListItemAction {

        void doAction(ThreadList currentItem);
    }

    public class GuiList implements ActionListener {

        @Override

        public void actionPerformed(ActionEvent e) {
            MessageView mView = new MessageView(e.getActionCommand());
            mView.initChat();
        }
    }
}
