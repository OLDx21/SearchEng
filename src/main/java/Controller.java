import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import javafx.application.Platform;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Controller extends JFrame {


    static JLabel jLabel = new JLabel();
    static JTable table;
    static DefaultTableModel defaultTableModel;
    static JButton jButton;
    static ArrayList<String> UKLList = new ArrayList<>();
    static ArrayList<String> NameList = new ArrayList<>();
    static JTextField textField;

    JFrame jFrame;
    static JPanel panel;

    Controller() {

        try {

            FlatDarkPurpleIJTheme.install();

            UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        AbstractAction action = new Action();
        jFrame = new JFrame();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        jFrame.setBounds(dimension.width / 3 - 100, dimension.height / 3 - 100, 900, 550);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();

        panel.setLayout(null);
        jFrame.setResizable(false);
        jFrame.setTitle("Поиск заказа");
        jFrame.setContentPane(panel);
        //Button
        jButton = new JButton(new Action());
        jButton.setText("Поиск");
        jButton.setBounds(469, 10, 100, 33);

        try {
            jFrame.setIconImage(ImageIO.read(Controller.class.getResourceAsStream("prom2.ico")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        KeyStroke keyStroke = KeyStroke.getKeyStroke("ctrl F10");
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(keyStroke, "start");
        ActionMap actionMap = panel.getActionMap();
        actionMap.put("start", action);

        //jFrame.setIconImage(image2);

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "clickButton");

        panel.getActionMap().put("clickButton", new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                jButton.doClick();

            }
        });


        //Menu
        JMenuBar jMenuBar = MenuClass.getMenu();

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem jmenuItem = new JMenuItem("Go", 'G');
        jmenuItem.addActionListener(new Action());
        jmenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl l"));
        popupMenu.add(jmenuItem);
        popupMenu.add(new JMenuItem("Exit")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        //TextField

        textField = new HintTextField("Любое ключевое слово");
        textField.setBounds(246, 12, 212, 30);

        textField.setToolTipText("Любое ключевое слово");

//
        defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("№");
        defaultTableModel.addColumn("Магазин");
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Имя Фамилия");
        defaultTableModel.addColumn("Телефончик");
        defaultTableModel.addColumn("Дата");


        table = new JTable(defaultTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setBounds(45, 94, 800, 400);
        table.setForeground(Color.decode("#0ed9e0"));
        table.setGridColor(Color.lightGray);
        table.setRowHeight(25);
        table.setSelectionBackground(Color.decode("#005797"));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getColumnModel().getColumn(0).setPreferredWidth(31);
        table.getColumnModel().getColumn(1).setPreferredWidth(133);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(233);
        table.getColumnModel().getColumn(4).setPreferredWidth(165);
        table.getColumnModel().getColumn(5).setPreferredWidth(117);

        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(45, 94, 800, 400);


        URL url = this.getClass().getResource("loading.gif");
        Icon myImgIcon = new ImageIcon(url);
        jLabel = new JLabel(myImgIcon);
        jLabel.setBounds(400, 43, 50, 50);
        jLabel.setVisible(false);


        panel.setComponentPopupMenu(popupMenu);
        panel.add(scrollPane);
        panel.add(jLabel);
        panel.add(textField);
        panel.add(jButton);
        jFrame.setJMenuBar(jMenuBar);

        JFrame.setDefaultLookAndFeelDecorated(true);
        panel.revalidate();

        jFrame.setVisible(true);


    }

    public static void arttable(ArrayList<String[]> data) {


        defaultTableModel.setRowCount(0);

        for (int i = 0; i < data.size(); i++) {
            data.get(i)[0] = String.valueOf(i+1);
            defaultTableModel.addRow(data.get(i));

        }


        System.gc();
    }

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
                                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Program Files\\new/nameshops.txt")));
                                String line;
                                while ((line = br.readLine()) != null) {
                                    if (!line.isEmpty()) {
                                        NameList.add(line);
                                    }
                                }


                                br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Program Files\\new/URL.txt")));
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

