import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Statist extends JFrame {

    static double pricezaden;
    static int zakazisovsexmagazov;
    static int i = 0;
    static JLabel jLabel = new JLabel();


    static AreaChart<?, ?> areaChart;
    static JButton zalazikibtn;
    static JButton moneykibtn;
    static JButton reset;
    static ArrayList UKLList = new ArrayList();
    static JButton btn1;
    static JButton btn2;
    static JButton btn3;
    static JButton btn4;
    static JButton btn5;
    static JButton btn6;
    static JButton btn7;
    static JButton btn8;
    static JButton btn9;
    static JButton btn10;
    static String date2;
    static JLabel datelabel;
    static JLabel srchecklabel;
    static JLabel zakazlabel;

    static JButton[] jButtons = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10};
    static ArrayList NameList = new ArrayList();


    Statist() {

        String g =System.getProperty("user.dir");
        try {
            //   FlatDarkPurpleIJTheme.install();
            //  FlatDarculaLaf.install();
            UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        JFrame jFrame = new JFrame();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        jFrame.setBounds(dimension.width / 3 - 100, dimension.height / 3 - 100, 1200, 800);
        //  jFrame. setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        panel.setLayout(null);
        jFrame.setResizable(false);
        jFrame.setTitle("Поиск заказа");
        jFrame.setContentPane(panel);
        URL url2 = this.getClass().getResource("prom2.ico");
        UKLList.clear();
        NameList.clear();
        try {
            jFrame.setIconImage(ImageIO.read(url2));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Color[] colors = {Color.decode("#DC143C"), Color.decode("#FFFF00"), Color.decode("#008000"), Color.decode("#00FFFF"), Color.BLUE, Color.decode("#C71585"), Color.RED, Color.GRAY, Color.decode("#8B4513"), Color.decode("#9370DB")};
        //Button
        int xax = 352;
        int yay = 655;
        for (int i = 0; i < NameList.size(); i++) {
            jButtons[i] = new JButton();
            jButtons[i].setBounds(xax, yay, 85, 35);
            xax += 135;
            if (i == 4) {
                xax = 352;
                yay = 705;
            }

            jButtons[i].setBackground(colors[i]);
            jButtons[i].setForeground(Color.decode("#000000"));
        }

        zalazikibtn = new JButton();
        zalazikibtn.setText("Заказы");
        zalazikibtn.setBounds(79, 210, 79, 39);
        moneykibtn = new JButton("Ср.Чек");
        moneykibtn.setBounds(79, 305, 79, 39);
        reset = new JButton();
        reset.setBounds(1030, 26, 90, 27);
        reset.setText("Обновить");
//Date
        UtilDateModel model = new UtilDateModel();

// Need this...
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DataLabelFormatter());
        datePicker.setBounds(17, 610, 192, 53);

        //Menu
        JMenuBar jMenuBar = new JMenuBar();


        //Table
        JFXPanel jfxPanel = new JFXPanel();


        jfxPanel.setBounds(217, 87, 907, 528);
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        areaChart = new AreaChart<String, Number>(xAxis, yAxis);


        areaChart.setMinHeight(528);
        areaChart.setMinWidth(907);


        moneykibtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        moneykibtn.setEnabled(false);
                        zalazikibtn.setEnabled(true);
                        Statist.artzakaziki(30, 1);
                        i = 1;
                    }
                });

            }
        });
        zalazikibtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        moneykibtn.setEnabled(true);
                        zalazikibtn.setEnabled(false);
                        Statist.artzakaziki(30, 0);
                        i = 0;
                    }
                });
            }
        });

        URL url = this.getClass().getResource("loading.gif");
        Icon myImgIcon = new ImageIcon(url);
        jLabel = new JLabel(myImgIcon);


        jLabel.setBounds(0, 0, 50, 50);
        jLabel.setVisible(false);

        //label

        datelabel = new JLabel();

        datelabel.setFont(new Font("Serif", Font.PLAIN, 20));
        datelabel.setBounds(504, 30, 500, 60);


        srchecklabel = new JLabel();
        zakazlabel = new JLabel();
        zakazlabel.setBounds(17, 645, 192, 43);
        srchecklabel.setBounds(17, 700, 192, 43);
        zakazlabel.setBackground(Color.gray);
        srchecklabel.setBackground(Color.gray);
        zakazlabel.setOpaque(true);
        srchecklabel.setOpaque(true);
        zakazlabel.setForeground(Color.WHITE);
        srchecklabel.setForeground(Color.WHITE);
        srchecklabel.setFont(new Font("Serif", Font.PLAIN, 14));
        zakazlabel.setFont(new Font("Serif", Font.PLAIN, 14));


        Group group = new Group();
        Scene scene = new Scene(group);
        group.getChildren().addAll(areaChart);
        jfxPanel.setScene(scene);
        datePanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

                Date date = (Date) datePanel.getModel().getValue();
                date2 = dateFormat.format(date);
                zakazlabel.setText("");
                srchecklabel.setText("");
                HashMap[] hashMaps = Info.getHashMaps();


                for (int hp = 0; hp < NameList.size(); hp++) {

                    if (i == 0) {
                        if (hashMaps[hp].containsKey(date2) == true) {

                            zakazisovsexmagazov += (int) hashMaps[hp].get(date2);

                        } else {

                            zakazisovsexmagazov += 0;
                        }
                    } else {
                        if ((hashMaps[hp].containsKey(date2 + " price")) == true) {

                            pricezaden += (double) hashMaps[hp].get(date2 + " price");

                        } else {
                            pricezaden += 0;
                        }

                    }

                }

                if (i == 0) {
                    zakazlabel.setText("Всего заказов: " + round(zakazisovsexmagazov, 2));
                } else {
                    zakazlabel.setText("Средяя прибыль: " + round(pricezaden, 2));
                    srchecklabel.setText("Средний чек: " + round(pricezaden / 5, 2));
                }
                zakazisovsexmagazov = 0;
                pricezaden = 0;
            }
        });


        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Statist.start(1);
                    }
                });

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Info.getHashMaps()[0] != null) {
                    moneykibtn.setEnabled(true);
                    zalazikibtn.setEnabled(false);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Statist.artzakaziki(30, 0);
                            i = 0;
                        }
                    });


                } else {
                    Statist.start(0);

                }
            }
        }).start();


        for (int hp = 0; hp < NameList.size(); hp++) {
            jButtons[hp].setText(NameList.get(hp).toString());


            int finalHp1 = hp;
            jButtons[hp].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (i == 0) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Statist.knopki1(jButtons[finalHp1], (String) NameList.get(finalHp1), 30, 0, finalHp1);
                            }
                        });


                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Statist.knopki1(jButtons[finalHp1], (String) NameList.get(finalHp1), 30, 1, finalHp1);
                            }
                        });

                    }
                }
            });

            panel.add(jButtons[hp]);
        }
        panel.add(zakazlabel);
        panel.add(srchecklabel);
        panel.add(datelabel);
        panel.add(reset);
        panel.add(datePicker);
        panel.add(jLabel);
        panel.add(moneykibtn);
        panel.add(zalazikibtn);
        jFrame.setJMenuBar(jMenuBar);
        panel.add(jfxPanel);
        JFrame.setDefaultLookAndFeelDecorated(true);
        panel.revalidate();
        jFrame.setVisible(true);


    }

    public static void artzakaziki(int kolvo, int checked) {

        for (int i = 0; i < NameList.size(); i++) {
            jButtons[i].setEnabled(true);


        }
        Scene scene = areaChart.getScene();
        scene.getStylesheets().clear();


        areaChart.getData().clear();

        for (int i = 0; i < UKLList.size(); i++) {

            XYChart.Series liv = artgr(Info.getHashMaps()[i], kolvo, checked);
            liv.setName((String) NameList.get(i));
            areaChart.getData().add(liv);
            liv = null;
        }


        datelabel.setText("Дата обновления: " + Info.getHashMaps()[0].get("date").toString());


        ObservableList<XYChart.Data> dataList;
        final String[] load = new String[1];
        for (int i = 0; i < 5; i++) {
            dataList = ((XYChart.Series) areaChart.getData().get(i)).getData();
            dataList.forEach(data -> {
                javafx.scene.Node node = data.getNode();
                load[0] = "Value: " + data.getYValue().toString() + "\n" + "Date: " + data.getXValue().toString();
                Tooltip tooltip = new Tooltip(load[0]);
                Tooltip.install(node, tooltip);
            });

        }


        dataList = null;


        System.gc();

    }

    public static void knopki1(JButton cli, String magaz, int kolvo, int checked, int numbershop) {

        Scene scene = areaChart.getScene();
        scene.getStylesheets().clear();
        for (int i = 0; i < NameList.size(); i++) {
            jButtons[i].setEnabled(true);


        }
        cli.setEnabled(false);

        areaChart.getData().clear();


        HashMap tos = new HashMap();

        switch (numbershop) {
            case 0:
                tos.putAll(Info.hashMaps[0]);

                break;
            case 1:
                tos.putAll(Info.hashMaps[1]);
                scene.getStylesheets().add("CSSstyle/style.css");
                break;
            case 2:
                scene.getStylesheets().add("CSSstyle/style1.css");
                tos.putAll(Info.hashMaps[2]);
                break;
            case 3:
                scene.getStylesheets().add("CSSstyle/style2.css");
                tos.putAll(Info.hashMaps[3]);
                break;
            case 4:
                scene.getStylesheets().add("CSSstyle/style3.css");
                tos.putAll(Info.hashMaps[4]);
                break;
            case 5:
                scene.getStylesheets().add("CSSstyle/style4.css");
                tos.putAll(Info.hashMaps[5]);
                break;
            case 6:
                scene.getStylesheets().add("CSSstyle/style5.css");
                tos.putAll(Info.hashMaps[6]);
                break;
            case 7:
                scene.getStylesheets().add("CSSstyle/style6.css");
                tos.putAll(Info.hashMaps[7]);
                break;
            case 8:
                scene.getStylesheets().add("CSSstyle/style7.css");
                tos.putAll(Info.hashMaps[8]);
                break;
            case 9:
                scene.getStylesheets().add("CSSstyle/style8.css");
                tos.putAll(Info.hashMaps[9]);
                break;
        }

        XYChart.Series liva = artgr(tos, kolvo, checked);
        liva.setName(magaz);


        areaChart.getData().addAll(liva);
        tos = null;
        zalazikibtn.setEnabled(true);
        moneykibtn.setEnabled(true);

        ObservableList<XYChart.Data> dataList = ((XYChart.Series) areaChart.getData().get(0)).getData();
        dataList.forEach(data -> {
            javafx.scene.Node node = data.getNode();
            String load = "Value: " + data.getYValue().toString() + "\n" + "Date: " + data.getXValue().toString();
            Tooltip tooltip = new Tooltip(load);
            Tooltip.install(node, tooltip);
        });


    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static XYChart.Series artgr(HashMap hashMap, int kolvo, int chekced) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        Calendar c = Calendar.getInstance();
        XYChart.Series liva = new XYChart.Series();
        String output1 = "";
        boolean check1;
        double add1;


        for (int i = kolvo; i >= 0; i--) {
            c.setTime(new Date());
            c.add(Calendar.DATE, -i);
            output1 = sdf.format(c.getTime());
            check1 = chekced == 0 ? hashMap.containsKey(output1) : hashMap.containsKey(output1 + " price");
            if (check1) {
                add1 = chekced == 0 ? (int) hashMap.get(output1) : (double) hashMap.get(output1 + " price");
            } else {
                add1 = 0;
            }

            liva.getData().add(new XYChart.Data(output1, add1));

        }
        output1 = null;

        return liva;
    }

    public static void start(int che) {

        new Thread(new Runnable() {


            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        areaChart.getData().clear();
                        if (che == 0) {
                            jLabel.setVisible(true);
                        } else {
                            jLabel.setVisible(true);
                        }
                    }
                });
                for (int i = 0; i < NameList.size(); i++) {
                    jButtons[i].setEnabled(true);


                }

                zalazikibtn.setEnabled(false);
                moneykibtn.setEnabled(true);


                int h = 0;
                List<FutureTask> list = new ArrayList<>();
                for (int i = 0; i < UKLList.size(); i++) {
                    Callable<HashMap> callable15 = new MyCallableee((String) UKLList.get(i));
                    FutureTask futureTask15 = new FutureTask(callable15);
                    new Thread(futureTask15).start();

                    list.add(futureTask15);
                }
                for (FutureTask f : list) {

                    try {
                        Info.setHashMaps((HashMap) f.get(), h);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    h += 1;
                }


                System.gc();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy k:mm");
                        Calendar c = Calendar.getInstance();
                        for (int i = 0; i < UKLList.size(); i++) {

                            XYChart.Series liv = artgr(Info.getHashMaps()[i], 30, 0);
                            liv.setName((String) NameList.get(i));
                            areaChart.getData().add(liv);
                            liv = null;
                        }


                        jLabel.setVisible(false);


                        datelabel.setText("Дата обновления: " + sdf.format(c.getTime()));
                        for (int i = 0; i < NameList.size(); i++) {
                            jButtons[i].setEnabled(true);


                        }

                        ObservableList<XYChart.Data> dataList;
                        final String[] load2 = new String[1];
                        for (int i = 0; i < 5; i++) {
                            dataList = ((XYChart.Series) areaChart.getData().get(i)).getData();
                            dataList.forEach(data -> {
                                javafx.scene.Node node = data.getNode();
                                load2[0] = "Value: " + data.getYValue().toString() + "\n" + "Date: " + data.getXValue().toString();
                                Tooltip tooltip = new Tooltip(load2[0]);
                                Tooltip.install(node, tooltip);
                            });

                        }

                    }
                });
                i = 0;
            }
        }).start();
    }
}

class MyCallableee implements Callable<HashMap> {


    String magaz;

    String id;
    String name;
    String phone;
    String date;
    String price;
    String datnow;


    MyCallableee(String magaz) {


        this.magaz = magaz;

    }


    @Override
    public HashMap call() throws Exception {

        HashMap hashMap = new HashMap();
        int schetchik = 0;
        double summa = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy k:mm");
        Calendar c = Calendar.getInstance();


        datnow = sdf.format(c.getTime());
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(magaz);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("order");
            Node nNode;
            Element eElement;


            for (int temp = 0; temp < nList.getLength(); temp++) {


                nNode = nList.item(temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    eElement = (Element) nNode;


                    date = eElement.getElementsByTagName("date").item(0).getTextContent();
                    date = date.substring(0, 8);

                    price = eElement.getElementsByTagName("priceUAH").item(0).getTextContent();


                    if (datnow.equals(date)) {
                        schetchik++;
                        summa += Double.parseDouble(price);


                    } else {
                        hashMap.put(datnow, schetchik);
                        hashMap.put(datnow + " price", summa / schetchik);

                        hashMap.put("date", sdf.format(c.getTime()));

                        schetchik = 1;
                        datnow = date;
                        summa = Double.parseDouble(price);

                    }

                }
            }


            doc = null;
            nList = null;
            id = null;
            name = null;
            phone = null;
            date = null;
            magaz = null;
            dBuilder = null;
            dbFactory = null;

            System.gc();


        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored) {


        }


        return hashMap;
    }
}
