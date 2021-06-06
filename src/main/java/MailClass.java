import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import javafx.application.Platform;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MailClass extends JFrame {
    static int check = 0;
    static ArrayList<String> NameList = new ArrayList<>();
    static ArrayList<String> UKLList = new ArrayList<>();
    static ArrayList<String> Ssilki = new ArrayList<>();
    static int kolvo;
    public JLabel load = new JLabel();
    public JButton jButton1;
    public JButton jButton2;
    public JLabel jLabel;
    public JCheckBox checkBox1 = new JCheckBox();
    public JCheckBox checkBox2 = new JCheckBox();
    public JCheckBox checkBox3 = new JCheckBox();
    public JCheckBox checkBox4 = new JCheckBox();
    public JCheckBox checkBox5 = new JCheckBox();
    public JCheckBox checkBox6 = new JCheckBox();
    public JCheckBox checkBox7 = new JCheckBox();
    public JCheckBox checkBox8 = new JCheckBox();
    public JCheckBox checkBox9 = new JCheckBox();
    public JCheckBox checkBox10 = new JCheckBox();
    public JCheckBox[] checkBoxes = {checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10};
    ArrayList<InfoMail> vrem;
    MailClass() {
        NameList.clear();
        UKLList.clear();
        Ssilki.clear();
        String g =System.getProperty("user.dir");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(g+"\\nameshops.txt")));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    NameList.add(line);
                }
            }


            br = new BufferedReader(new InputStreamReader(new FileInputStream(g+"\\URL.txt")));
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    UKLList.add(line);
                }
            }

            br = new BufferedReader(new InputStreamReader(new FileInputStream(g+"\\Otzovik.txt")));
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    Ssilki.add(line);
                }
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {

            FlatDarkPurpleIJTheme.install();

            UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        JFrame jFrame = new JFrame();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        jFrame.setBounds(dimension.width / 3 - 100, dimension.height / 3 - 100, 600, 400);

        JPanel panel = new JPanel();

        panel.setLayout(null);
        jFrame.setResizable(false);
        jFrame.setTitle("Отправь mail");
        jFrame.setContentPane(panel);
        //Button
        jButton1 = new JButton();
        jButton1.setText("Старт");
        jButton1.setBounds(256, 305, 88, 38);
        URL url2 = this.getClass().getResource("prom2.ico");
        try {
            jFrame.setIconImage(ImageIO.read(url2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        jButton2 = new JButton();
        jButton2.setText("Выбрать все");
        jButton2.setBounds(14, 234, 110, 25);
        //Label
        jLabel = new JLabel();
        jLabel.setText("Отправлено: ");

        jLabel.setBounds(387, 20, 150, 50);
        jLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        jLabel.setForeground(Color.WHITE);
//Date
        UtilDateModel model = new UtilDateModel();
        JMenuBar jMenuBar = new JMenuBar();

// Need this...
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DataLabelFormatter());
        datePicker.setBounds(213, 234, 200, 37);
        Color[] colors = {Color.decode("#DC143C"), Color.decode("#FFFF00"), Color.decode("#008000"), Color.decode("#00FFFF"), Color.BLUE, Color.decode("#C71585"), Color.RED, Color.GRAY, Color.decode("#8B4513"), Color.decode("#9370DB")};
        int xax = 14;
        int yay = 25;

        for (int i = 0; i < NameList.size(); i++) {

            checkBoxes[i].setForeground(Color.decode("#000000"));
            checkBoxes[i].setBounds(xax, yay, 90, 25);
            checkBoxes[i].setText(NameList.get(i).toString());
            checkBoxes[i].setFont(new Font("Serif", Font.PLAIN, 18));
            checkBoxes[i].setBackground(colors[i]);
            yay += 40;
            checkBoxes[i].setVisible(true);
            panel.add(checkBoxes[i]);
            if (i == 4) {
                xax = 177;
                yay = 25;

            }
        }
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (check == 0) {
                    for (int i = 0; i <= NameList.size(); i++) {
                        checkBoxes[i].setSelected(true);

                    }
                    jButton2.setText("Убрать все");
                    check = 1;
                } else {
                    for (int i = 0; i <= NameList.size(); i++) {
                        checkBoxes[i].setSelected(false);

                    }
                    jButton2.setText("Выбрать все");
                    check = 0;
                }
            }
        });
        URL url = this.getClass().getResource("loading.gif");
        Icon myImgIcon = new ImageIcon(url);
        load = new JLabel(myImgIcon);

        ArrayList[] ar = new ArrayList[NameList.size()];
        load.setBounds((jFrame.getWidth() / 2) - 35, 0, 50, 50);
        load.setVisible(false);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = (Date) datePanel.getModel().getValue();

                if (datePanel.getModel().getValue() == null || date.after(new Date()) == true) {
                    JOptionPane.showMessageDialog(null, "Выберите корректную дату!");
                    return;
                }
                kolvo = 0;
                load.setVisible(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
                        Date date = (Date) datePanel.getModel().getValue();
                        String date2 = dateFormat.format(date);




                        List<FutureTask> list = new ArrayList<>();

                        for (int i = 0; i < UKLList.size(); i++) {
                            Callable<ArrayList<InfoMail>> callable15 = new MyCallables(date2, (String) UKLList.get(i));
                            FutureTask futureTask15 = new FutureTask(callable15);
                            new Thread(futureTask15).start();

                            list.add(futureTask15);
                        }
                        int forchecked = 0;

                        for (FutureTask f : list) {

                            try {
                                ar[forchecked] = ((ArrayList<InfoMail>) f.get());
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                            forchecked += 1;
                        }





                        for (int n = 0; n < NameList.size(); n++) {
                            vrem = new ArrayList<>();
                            vrem.addAll(ar[n]);

                            if (!checkBoxes[n].isSelected()) {
                                continue;
                            }

                            for (int i = 0; i < ar[n].size(); i++) {



                               sendmail(vrem.get(i).getName(), vrem.get(i).getMail(), vrem.get(i).getItemName(), Ssilki.get(n));
                            }
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                load.setVisible(false);
                                jLabel.setText("Отправлено: " + kolvo);
                            }
                        });


                    }
                }).start();
            }
        });
        panel.add(load);
        panel.add(datePicker);
        panel.add(jButton1);
        panel.add(jButton2);
        panel.add(jLabel);
        jFrame.setJMenuBar(jMenuBar);
        JFrame.setDefaultLookAndFeelDecorated(true);
        panel.revalidate();

        jFrame.setVisible(true);

    }

    public void sendmail(String name, String mail, String nametovarr, String ssilka) {
        kolvo += 1;
        final Properties properties = new Properties();
        try {
            properties.load(Controller.class.getClassLoader().getResourceAsStream("email.properties"));

            Session mailsesion = Session.getDefaultInstance(properties);
            MimeMessage message = new MimeMessage(mailsesion);
            message.setFrom(new InternetAddress("internetmagazin070@gmail.com"));
            message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(mail)));
            message.setSubject("Получите 10% скидку за отзыв!");
            message.setText(
                    name + ", здравствуйте!\n" +
                            "Спасибо за Ваш заказ!\n" +
                            "(" + nametovarr + ")\n" +
                            "Можем Вас пожалуйста попросить, оставить отзыв о нашем магазине. Для нас это очень важно)\n" +
                            "После,  как оставите отзыв, дарим 10% скидку !\n" +
                            "Отзыв можно оставить по ссылке - " + ssilka);
            Transport tr = mailsesion.getTransport();
            tr.connect(null, Paswords.PASSWORD);
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }
}

class MyCallables implements Callable<ArrayList<InfoMail>> {

    String keyword;
    String magaz;

    String date;
    String mail;
    String name;
    String namezakaz;
    int g;
    int a;

    MyCallables(String keyword, String magaz) {

        this.keyword = keyword;
        this.magaz = magaz;

    }


    @Override
    public ArrayList<InfoMail> call() throws Exception {

        ArrayList<InfoMail> infos = new ArrayList<>();


        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(magaz);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("order");
            NodeList nList2 = doc.getElementsByTagName("items");

            Node nNode;
            Element eElement;


            for (int temp = 0; temp < nList.getLength(); temp++) {


                nNode = nList.item(temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    eElement = (Element) nNode;

                    name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    date = eElement.getElementsByTagName("date").item(0).getTextContent();
                    mail = eElement.getElementsByTagName("email").item(0).getTextContent();
                    date = date.substring(0, 8);


                    for (int i = 0; i < name.length() - 1; i++) {
                        if (name.charAt(i) == ' ' && name.charAt(i + 1) == ' ') {
                            name = removeCharAt(name, i);

                        }

                    }

                    name = name + " ";
                    if (date.equals(keyword)) {
                        if (!mail.isEmpty()) {
                            nNode = nList2.item(temp);
                            eElement = (Element) nNode;
                            namezakaz = eElement.getElementsByTagName("name").item(0).getTextContent();


                            g = name.indexOf(" ");
                            a = name.indexOf(" ", g + 1);
                            System.out.println(name +" "+ mail+" "+namezakaz);
                            infos.add(new InfoMail(mail, name.substring(g + 1, a), namezakaz));

                        }
                    }

                }
            }



            doc = null;
            nList = null;
            nList2 = null;
            date = null;
            magaz = null;
            dBuilder = null;
            dbFactory = null;

            System.gc();


        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        } catch (NullPointerException l) {


        }


        return infos;
    }

    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }
}
