package chatting_app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;

//import java.awt.event.ActionListener;when we use actionlistener for any article to receive action from user
// we need override the action listener into your program * atlast
public class server implements ActionListener {
    JTextField text1;

    JPanel a1;
    static DataOutputStream dou;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();

    server() {
        f.setLayout(null);//we are going to create our own layout

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84)); //rather giving getbackground we use custom rgb )
        p1.setBounds(0, 0, 450, 70);//givig panel position
        p1.setLayout(null);
        f.add(p1);


        //we need action on this image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);//we call over p1 as we want our image over the label else it will be under the label

        back.addMouseListener(new MouseAdapter() {
            @Override//here we use click action on arrow to exit the frame
            public void mouseClicked(MouseEvent e) {
                System.exit(0);//exiting the chatting gui
            }
        });

        //code for image is same the previous one as the variables shows duplicates change the variable name and img path
        //for profile pic
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);


        //for video icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        //for telephone image
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel telephone = new JLabel(i12);
        telephone.setBounds(360, 20, 35, 30);
        p1.add(telephone);

        //for more options:
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 30, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel moreverts = new JLabel(i15);
        moreverts.setBounds(410, 20, 10, 25);
        p1.add(moreverts);

        //for the name of the user
        JLabel name = new JLabel("Gaitonde");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        //for active status of the user
        JLabel status = new JLabel("Active now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p1.add(status);

        //creating another panel for text area
        a1 = new JPanel();
        a1.setBounds(5, 75, 442, 570);
        f.add(a1);


        //for texting purpose
        text1 = new JTextField();
        text1.setBounds(5, 655, 310, 40);
        text1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text1);


        //creating button
        JButton send = new JButton("send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(5, 94, 52));
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);//here we add action function call  for the send button and we write the function
        // or action taken by the sendbutton in the abstract method of ActionPerformed
        f.add(send);


        //frame size for chatting interface
        f.setSize(450, 750);
        f.setLocation(200, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);


        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){//it is an abstract method under ActionListener

    try{
        String out = text1.getText();

        JPanel p2 = formatLabel(out);

        a1.setLayout(new BorderLayout());

        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));

        a1.add(vertical, BorderLayout.PAGE_START);

        dou.writeUTF(out);
        text1.setText("");

        f.repaint();
        f.invalidate();
        f.validate();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }

}
    public static JPanel formatLabel(String out)
    {
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output=new JLabel("<html><p style= \"width: 150px\">"+out+"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);

        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);
        return panel;
    }

     public static void main(String [] args)
     {
         new server();

         try
         {
             ServerSocket skt=new ServerSocket(6001);
             while(true)
             {
                Socket s =skt.accept();
                 DataInputStream din =new DataInputStream(s.getInputStream());
                 dou=new DataOutputStream(s.getOutputStream());
                 while (true)
                 {
                    String msg=din.readUTF();
                    JPanel panel=formatLabel(msg);

                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                 }

             }
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
     }




}
