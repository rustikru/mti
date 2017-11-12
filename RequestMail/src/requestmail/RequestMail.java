/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requestmail;

import static com.sun.javafx.application.PlatformImpl.exit;
import java.io.IOException;
import static java.lang.System.exit;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static javafx.application.Platform.exit;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.SubjectTerm;

/**
 *
 * @author user
 */
public class RequestMail {

    public static void check()
                      throws SQLException,NoSuchProviderException,
                                                  MessagingException,
                                                  IOException
   {
       
       //File path = new File( directory );
       //String[] list = path.list();
       String element = "";
       //String f = String.valueOf(path.canWrite());
 
       //#sql { DELETE FROM TEST_DIR_LIST };
 
       //for(int i = 0; i < list.length; i++)
       //{
          // element = new File( directory +"/"+list[i]+"/file" );
 
       //   element = list[i];
 
 
 
           //#sql { INSERT INTO TEST_DIR_LIST (FILENAME)
           //       VALUES (:element) };
       //}
 
       Properties properties = System.getProperties();
 
        properties.put("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(properties);
        Store store = session.getStore();
        store.connect("imap.yandex.ru", "ru.bekmansurov@yandex.ru", "rust@m1204");
 
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
 
 
 
        Message msg = inbox.getMessage(1);
 
        Address[] in = msg.getFrom();
        for (Address address: in) {
            System.out.println("FROM:" + address.toString());
        }
 
        //Multipart mp = (Multipart)msg.getContent();
 
 
        //BodyPart bp = mp.getBodyPart(0);
 
 
 
        //System.out.println("11111");
 
 
        //String el = bp.getContent().toString(); //inbox.getMessageCount();
 
        //#sql{begin DBMS_OUTPUT.PUT_LINE ( '1111 '||:el ); end;};
 
        /*
        System.out.println("SUBJECT:" + msg.getSentDate());
        System.out.println("SUBJECT:" + msg.getSubject());
        System.out.println("CONTENT:" + bp.getContent());
 
        System.out.println(inbox.getMessageCount());
        */
       Message[] messages = inbox.search(new AndTerm(new FlagTerm(new Flags(Flags.Flag.FLAGGED), false), new SubjectTerm("request")));
 
       System.out.println(messages.length);
       //получаем последнее сообщение (самое старое будет под номером 1)
        Message mm = inbox.getMessage(inbox.getMessageCount());
        Multipart mp = (Multipart) mm.getContent();
        BodyPart bp = mp.getBodyPart(0);
       // System.out.println(bp.getContent());
        Message m1 = inbox.getMessage(inbox.getMessageCount());
        Multipart mp1 = (Multipart) m1.getContent();
        BodyPart bp1;
        bp = mp1.getBodyPart(0);
        String tt = (String) bp.getContent();
        //Выводим содержимое на экран
        System.out.println("ttt="+tt);
        for (Message message : messages) {
            if (message.getSubject().contains("request")) {
                //ArrayList subj = new Arrays.a(messages[i].getSubject().split("|"));
                List<String> subj = new ArrayList<>(Arrays.asList(message.getSubject().split("\\|")));
                //System.out.println("Length:" + subj.size());
                System.out.println(subj);
                if (subj.size() == 3) {
                    System.out.println("ID:" + subj.get(1));
                    System.out.println("Accept:" + subj.get(2));
                    String id = subj.get(1);
                    String approve = subj.get(2);
                    Message m = message;
                    InternetAddress dd = new InternetAddress(m.getFrom()[0].toString());
                    //System.out.println("From:" + dd.getAddress());
                    //System.out.println("SUBJECT:" + m.getSubject());
                    String email = dd.getAddress();
                    //m.setFlag(Flags.Flag.FLAGGED, true);
                }
            }
        }
   }
 public static void main(String[] args) throws SQLException, NoSuchProviderException, MessagingException, IOException {
        // TODO code application logic here
        check();
    }   
}
