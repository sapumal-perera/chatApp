package com.emperia.chat;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
public class ChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @RequestMapping("/hello")
    public String sayHello() throws Exception {
        String url = "jdbc:mysql://localhost:3306/users";
        String uname = "root";
        String pass = "";
        String query = "select * from user";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, uname, pass);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        rs.next();

        String name = rs.getString("username");
        return name;
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String login(@RequestParam Map<String, String> reqPar) throws Exception {

        String name = reqPar.get("name");
        String passw = reqPar.get("password");
        String url = "jdbc:mysql://localhost:3306/users";
        String uname = "root";
        String pass = "";
        String query = "select * from user";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, uname, pass);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            String username = rs.getString("username");
            String password = rs.getString("password");
            if (username.equalsIgnoreCase(name)&& password.equalsIgnoreCase(passw)) {
                return "success";
            }
        }

        return "Login Error";


    }


    @RequestMapping(value = "/register.html", method = RequestMethod.GET)
    public String registerUser(@RequestParam Map<String, String> reqPar) throws Exception {
        Boolean existUser = false;
        String name = reqPar.get("name");
        String passwrd = reqPar.get("password");
        String url = "jdbc:mysql://localhost:3306/users";
        String uname = "root";
        String pass = "";
        String selectQuery = "select * from user";
        String instQuery = "INSERT INTO user(nickname,username, password) VALUES ('" + name + "','" + name + "','" + passwrd + "')";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, uname, pass);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(selectQuery);
        while (rs.next()) {
            String username = rs.getString("username");
            if (username.equalsIgnoreCase(name)) {
                existUser = true;
            }
        }
        if(!existUser){
        Statement stm = con.createStatement();
        int rows = stm.executeUpdate(instQuery);
        if (rows > 0) {
            return "Success";
        } else {
            return "Failed";
        }
        }
        else{
            return "User Already Exist";
        }
    }

    @RequestMapping(value = "/createThread.html", method = RequestMethod.GET)
    public String createThread(@RequestParam Map<String, String> reqPar) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dateTime = dateFormat.format(date);
        String title = reqPar.get("title");
        String author = reqPar.get("author");
        String url = "jdbc:mysql://localhost:3306/users";
        String uname = "root";
        String pass = "";
        String query = "INSERT INTO thread(title,author, date_time) VALUES ('" + title + "','" + author + "','" + dateTime + "')";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, uname, pass);
        Statement st = con.createStatement();
        try {
            int rows = st.executeUpdate(query);
            if (rows > 0) {
                return "Success";
            } else {
                return "Failed";
            }
        } catch (Exception e) {
            return "Failed";
        }

    }


    //mainChat
    @RequestMapping(value = "/mainChat.html", method = RequestMethod.GET)
    public ArrayList mainChat(@RequestParam Map<String, String> reqPar) throws Exception {
        String url = "jdbc:mysql://localhost:3306/users";
        String uname = "root";
        String pass = "";
        String query = "select * from thread";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, uname, pass);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);


        JSONArray list = new JSONArray();
        while (rs.next()) {
            JSONObject obj = new JSONObject();

            String title = rs.getString("title");
            String author = rs.getString("author");
            String editedAt = rs.getString("date_time");
            String threadID = rs.getString("id");
            obj.put("t_id", threadID);
            obj.put("title", title);
            obj.put("author", author);
            obj.put("time", editedAt);
            list.add(obj);
        }

        return list;
    }


    @RequestMapping(value = "/getMessages.html", method = RequestMethod.GET)
    public ArrayList getMessages(@RequestParam Map<String, String> reqPar) throws Exception {
        String t_id = reqPar.get("id");
        String url = "jdbc:mysql://localhost:3306/users";
        String uname = "root";
        String pass = "";
        String query = "select * from messages  WHERE thread_id = '" + t_id + "'";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, uname, pass);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);


        JSONArray list = new JSONArray();
        while (rs.next()) {
            JSONObject obj = new JSONObject();
            String m_id = rs.getString("m_id");
            String message = rs.getString("message");
            String author = rs.getString("author");
            String editedAt = rs.getString("last_edited");
            obj.put("msgId", m_id);
            obj.put("message", message);
            obj.put("author", author);
            obj.put("time", editedAt);
            list.add(obj);
        }

        return list;
    }

    @RequestMapping(value = "/addMessage.html", method = RequestMethod.GET)
    public String addMessage(@RequestParam Map<String, String> reqPar) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dateTime = dateFormat.format(date);
        String msg = reqPar.get("message");
        String author = reqPar.get("author");
        String t_id = reqPar.get("threadId");
        String url = "jdbc:mysql://localhost:3306/users";
        String uname = "root";
        String pass = "";
        String query = "INSERT INTO messages(message,last_edited,author, thread_id) VALUES ('" + msg + "','" + dateTime + "','" + author + "','" + t_id + "')";

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, uname, pass);
        Statement st = con.createStatement();
        int rows = st.executeUpdate(query);
        if (rows > 0) {
            return "Success";
        } else {
            return "Failed";
        }
    }


    @RequestMapping("/hei")
    public String saynothing() {
        return "hei all!";
    }
}
