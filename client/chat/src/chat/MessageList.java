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

public class MessageList extends JPanel {

    private JButton buttonId;
    private JLabel lblMsg;
    private JLabel lblAuth;
    private JLabel lblTime;

    public MessageList(String name, String messageTxt, String time, String id) {

        buttonId = new JButton(id);
        lblAuth = new JLabel(name.concat("      "));
        lblMsg = new JLabel(messageTxt.concat("     "));
        lblTime = new JLabel(time.concat("    "));
        GuiList gl = new GuiList();
        buttonId.addActionListener(gl);
        lblAuth.setPreferredSize(new Dimension(180, 20));
        lblMsg.setPreferredSize(new Dimension(180, 20));
        lblTime.setPreferredSize(new Dimension(180, 20));
        lblAuth.setToolTipText("Message is by "+name);
        lblMsg.setToolTipText("Message sent at "+time);
        //add(buttonId);
        add(lblMsg);
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

        }
    }
}
