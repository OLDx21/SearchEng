import javafx.application.Platform;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class SearchClientWin extends Controller{

    static class Action extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {

            if ((!textField.getText().trim().equals("")) && (textField.getText().length() >= 3)) {

                NameList.clear();
                UKLList.clear();

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        jButton.setEnabled(false);
                        jLabel.setVisible(true);
                        try {
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

                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                            ArrayList<String[]> info = new ArrayList<>();
                            List<FutureTask> list = new ArrayList<>();
                            for (int i = 0; i < UKLList.size(); i++) {

                                Callable<ArrayList<String[]>> callable15 = new MyCallable(textField.getText().toLowerCase(), (String) UKLList.get(i), (String) NameList.get(i));
                                FutureTask futureTask15 = new FutureTask(callable15);
                                new Thread(futureTask15).start();

                                list.add(futureTask15);
                            }
                            for (FutureTask f : list) {
                                info.addAll((ArrayList<String[]>) f.get());
                            }

                            if (info.size() == 0) {
                                JOptionPane.showMessageDialog(null, "Совпадений не найдено");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {

                                        jLabel.setVisible(false);
                                        jButton.setEnabled(true);
                                    }
                                });

                            } else {
                                arttable(info);
                                jButton.setEnabled(true);
                                jLabel.setVisible(false);
                            }


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }


                    }
                }).start();

            } else {
                JOptionPane.showMessageDialog(null, "Поле фильтра не может быть пустым! Или иметь меньше 3-х символов");

            }


        }
    }
    public static void arttable(ArrayList<String[]> data) {


        defaultTableModel.setRowCount(0);

        for (int i = 0; i < data.size(); i++) {
            data.get(i)[0] = String.valueOf(i+1);
            defaultTableModel.addRow(data.get(i));

        }


        System.gc();
    }
}
class MyCallable implements Callable<ArrayList<String[]>> {
    String shop;
    String keyword;
    String magaz;

    String id;
    String name;
    String phone;
    String date;


    MyCallable(String keyword, String magaz, String shop) {
        this.shop = shop;
        this.keyword = keyword;
        this.magaz = magaz;

    }


    @Override
    public ArrayList<String[]> call() throws Exception {


        ArrayList<String[]> items = new ArrayList<>();


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

                    id = eElement.getAttribute("id");
                    name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    phone = eElement.getElementsByTagName("phone").item(0).getTextContent();
                    date = eElement.getElementsByTagName("date").item(0).getTextContent();

                    if (id.contains(keyword) || name.toLowerCase().contains(keyword) || phone.contains(keyword) || date.contains(keyword)) {

                        items.add(new String[]{"", shop, id, name, phone, date});


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


        return items;
    }
}
