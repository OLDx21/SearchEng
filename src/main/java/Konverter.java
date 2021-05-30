import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import javafx.application.Platform;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Konverter extends JFrame {
    JButton jButton;
    JLabel vseggo;
    JLabel progress;
    JLabel jLabel = new JLabel();

    int finalI = 0;

    Konverter() {
        String g =System.getProperty("user.dir");
        File file5 = new File(g+"\\new2");
        if (!file5.exists()) {
            file5.mkdir();
            file5 = new File(g+"\\excelfiles");
            file5.mkdir();


            try {
                FileOutputStream fileOut = new FileOutputStream(g+"\\new2\\shops.dtd");
                fileOut.close();
                fileOut = new FileOutputStream(g+"\\new2\\wow.xml");
                fileOut.close();
                fileOut = new FileOutputStream(g+"\\zakaz.txt");
                fileOut.close();
                fileOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

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

        jFrame.setBounds(dimension.width / 3 - 100, dimension.height / 3 - 100, 500, 300);

        JPanel panel = new JPanel();

        panel.setLayout(null);
        jFrame.setResizable(false);
        jFrame.setTitle("Конвертер");
        jFrame.setContentPane(panel);
        //Button
        jButton = new JButton();
        jButton.setText("Старт");
        jButton.setBounds(200, 180, 100, 33);

        //label
        URL url = this.getClass().getResource("loading.gif");
        Icon myImgIcon = new ImageIcon(url);
        jLabel = new JLabel(myImgIcon);


        jLabel.setBounds(225, 0, 50, 50);
        jLabel.setVisible(false);

        vseggo = new JLabel();
        vseggo.setBounds(20, 5, 100, 50);
        vseggo.setText("Всего файлов:");

        progress = new JLabel();
        progress.setBounds(20, 40, 150, 50);
        progress.setText("Обработано файлов:");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "clickButton");

        panel.getActionMap().put("clickButton", new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                jButton.doClick();

            }
        });
        //
        URL url2 = this.getClass().getResource("prom2.ico");
        try {
            jFrame.setIconImage(ImageIO.read(url2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> arrayList = new ArrayList<>();

                Info2 info = new Info2();
                jLabel.setVisible(true);


                try {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(new FileInputStream(g+"\\zakaz.txt"), "UTF-8"));


                    String line;
                    int h = 1;
                    while ((line = bufferedReader.readLine()) != null) {

                        arrayList.add(line);


                        h += 1;
                    }
                    bufferedReader.close();
                    vseggo.setText("Всего файлов: " + String.valueOf(arrayList.size()));

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                SXSSFWorkbook workbook = new SXSSFWorkbook();
                SXSSFSheet sheet = workbook.createSheet("FirstSheet");


                SXSSFRow rowhead = sheet.createRow((short) 0);
                rowhead.createCell(0).setCellValue("ID");
                rowhead.createCell(1).setCellValue("Pickup");
                rowhead.createCell(2).setCellValue("Name");
                rowhead.createCell(3).setCellValue("VendorCode");
                info.setWorkbook(workbook);


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        URL url;
                        HttpURLConnection Connection;
                        BufferedReader bufferedReader;
                        StringBuffer sb = null;
                        String line;
                        FileWriter fileWriter;
                        int finalI1;
                        String res = "";
                        //
                        SXSSFWorkbook workbook;
                        SXSSFRow row;
                        int rowchik;
                        DocumentBuilderFactory dbFactory;
                        DocumentBuilder dBuilder;
                        Document doc;
                        NodeList nList;

                        Node nNode;
                        Element eElement;

                        String id;
                        String delivery;
                        String name;
                        String vendor;
                        for (int i = 0; i < arrayList.size(); i++) {

                            try {

                                url = new URL(arrayList.get(i).toString());
                                Connection = (HttpURLConnection) url.openConnection();
                                Connection.setDoInput(true);
                                Connection.setRequestMethod("GET");
                                Connection.addRequestProperty("User-Agent",
                                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

                                bufferedReader = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
                                sb = new StringBuffer();

                                while ((line = bufferedReader.readLine()) != null) {

                                    sb.append(line + "\n");
                                }
                                bufferedReader.close();
                                Connection.disconnect();


                                fileWriter = new FileWriter(g+"\\new2\\wow.xml");

                                fileWriter.write(String.valueOf(sb));
                                fileWriter.flush();
                                fileWriter.close();
                                sb = null;


                                finalI1 = i;

                                finalI = i + 1;

//     konvert(finalI1 +1, arrayList.size(), Info.getRow(), sheet);
                                workbook = info.getWorkbook();
                                rowchik = Info2.getRow();


                                try {


                                    dbFactory = DocumentBuilderFactory.newInstance();
                                    dBuilder = dbFactory.newDocumentBuilder();


                                    doc = dBuilder.parse(g+"\\new2\\wow.xml");


                                    doc.getDocumentElement().normalize();

                                    nList = doc.getElementsByTagName("offer");


                                    for (int temp = 0; temp < nList.getLength(); temp++) {


                                        nNode = nList.item(temp);


                                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                            eElement = (Element) nNode;

                                            id = eElement.getAttribute("id");
                                            delivery = eElement.getAttribute("available");

                                            try {
                                                name = eElement.getElementsByTagName("name").item(0).getTextContent();
                                            } catch (NullPointerException e) {
                                                name = "NONE";

                                            }

                                            try {
                                                vendor = eElement.getElementsByTagName("vendorCode").item(0).getTextContent();
                                            } catch (NullPointerException e) {
                                                vendor = "";

                                            }


                                            row = sheet.createRow((rowchik + 1));

                                            row.createCell(0).setCellValue(id);
                                            row.createCell(1).setCellValue(delivery);
                                            row.createCell(2).setCellValue(name);
                                            row.createCell(3).setCellValue(vendor);

                                        }

                                        rowchik += 1;
                                    }
                                    System.out.println(rowchik);
                                    info.setWorkbook(workbook);
                                    Info2.setRow(rowchik);
                                    if (finalI1 + 1 != arrayList.size()) {
                                        workbook = null;
                                    }
                                    row = null;
                                    id = null;
                                    name = null;
                                    vendor = null;
                                    delivery = null;


                                    nNode = null;
                                    nList = null;
                                    eElement = null;
                                    if (finalI1 + 1 == arrayList.size()) {

                                        FileOutputStream fileOut = new FileOutputStream(g+"\\excelfiles\\" + String.valueOf(finalI1 + 1) + ".xlsx");
                                        workbook.write(fileOut);
                                        fileOut.close();
                                        workbook.close();

                                        doc = null;
                                        dbFactory = null;
                                        dBuilder = null;
                                        nNode = null;
                                        nList = null;
                                        eElement = null;
                                        workbook = null;
                                        info.clear();

                                    }

                                    System.gc();
                                } catch (SAXException | ParserConfigurationException | IOException e) {
                                    e.printStackTrace();
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    progress.setText("Обработано файлов: " + finalI);
                                }
                            });

                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {


                                jLabel.setVisible(false);


                            }
                        });
                    }
                }).start();

            }
        });


        panel.add(progress);
        panel.add(vseggo);
        JMenuBar jMenuBar = new JMenuBar();
        panel.add(jLabel);
        jFrame.setJMenuBar(jMenuBar);
        panel.add(jButton);
        jFrame.setVisible(true);
    }
}
