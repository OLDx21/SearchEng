import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Controller extends JFrame {


    static JLabel jLabel = new JLabel();


    static TableColumn<Infoclient, String> clmidzakaz;
    static TableColumn<Infoclient, String> clmnamemale;
    static TableColumn<Infoclient, String> clmnphone;
    static TableColumn<Infoclient, String> clmdate;
    static JButton jButton;
    static TableColumn<Infoclient, String> clshop;
    static ArrayList UKLList = new ArrayList();
    static ArrayList NameList = new ArrayList();
    static JTextField textField;
    static TableView<Infoclient> tableView;
    static TableColumn<Infoclient, Number> clmnid = new TableColumn<Infoclient, Number>("№");

    Controller() {

        try {

            FlatDarkPurpleIJTheme.install();

            UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        AbstractAction action = new Action();
        JFrame jFrame = new JFrame();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        jFrame.setBounds(dimension.width / 3 - 100, dimension.height / 3 - 100, 900, 550);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

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
        //Table
        JFXPanel jfxPanel = new JFXPanel();
        tableView = new TableView();

        jfxPanel.setBounds(45, 94, 800, 400);


        tableView.setMaxHeight(400);
        tableView.setMaxWidth(800);


        clshop = new TableColumn<Infoclient, String>("Магазин");
        clmidzakaz = new TableColumn<Infoclient, String>("ID");
        clmnamemale = new TableColumn<Infoclient, String>("Имя фамилия");
        ;
        clmnphone = new TableColumn<Infoclient, String>("Телефончик");
        clmdate = new TableColumn<Infoclient, String>("Дата");

        clmnid.setPrefWidth(31);
        clshop.setPrefWidth(133);
        clmidzakaz.setPrefWidth(120);
        clmnamemale.setPrefWidth(233);
        clmnphone.setPrefWidth(165);
        clmdate.setPrefWidth(117);

        tableView.getStylesheets().add("CSSstyle/styletable.css");
//
        URL url = this.getClass().getResource("loading.gif");
        Icon myImgIcon = new ImageIcon(url);
        jLabel = new JLabel(myImgIcon);
        jLabel.setBounds(400, 43, 50, 50);
        jLabel.setVisible(false);
        tableView.getColumns().addAll(clmnid, clshop, clmidzakaz, clmnamemale, clmnphone, clmdate);
        Group group = new Group();
        Scene scene = new Scene(group);
        group.getChildren().addAll(tableView);
        jfxPanel.setScene(scene);


        panel.setComponentPopupMenu(popupMenu);
        panel.add(jLabel);
        panel.add(textField);
        panel.add(jButton);
        jFrame.setJMenuBar(jMenuBar);
        panel.add(jfxPanel);

        JFrame.setDefaultLookAndFeelDecorated(true);
        panel.revalidate();

        jFrame.setVisible(true);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Controller controller = new Controller();

            }
        });


    }

    public static void arttable(ObservableList<Infoclient> info, TableView tableView, TableColumn<Infoclient, String> clshop, TableColumn<Infoclient, String> clmidzakaz, TableColumn<Infoclient, String> clmnphone
            , TableColumn<Infoclient, String> clmdate, TableColumn<Infoclient, String> clmnamemale) {

        clshop.setCellFactory(TextFieldTableCell.forTableColumn());
        clshop.setCellValueFactory(cellData -> cellData.getValue().getShop());

        clmnamemale.setCellFactory(TextFieldTableCell.forTableColumn());
        clmnamemale.setCellValueFactory(cellData -> cellData.getValue().getname());

        clmnphone.setCellFactory(TextFieldTableCell.forTableColumn());
        clmnphone.setCellValueFactory(cellData -> cellData.getValue().getPhone());

        clmdate.setCellFactory(TextFieldTableCell.forTableColumn());
        clmdate.setCellValueFactory(cellData -> cellData.getValue().getDate());

        clmidzakaz.setCellFactory(TextFieldTableCell.forTableColumn());
        clmidzakaz.setCellValueFactory(cellData -> cellData.getValue().getid());


        clmnid.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Number>(tableView.getItems().indexOf(cellData.getValue()) + 1));

        tableView.setItems(info);


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


                            ObservableList<Infoclient> info = FXCollections.observableArrayList();

                            List<FutureTask> list = new ArrayList<>();
                            for (int i = 0; i < UKLList.size(); i++) {

                                Callable<ObservableList<Infoclient>> callable15 = new MyCallable(textField.getText().toLowerCase(), (String) UKLList.get(i), (String) NameList.get(i));
                                FutureTask futureTask15 = new FutureTask(callable15);
                                new Thread(futureTask15).start();

                                list.add(futureTask15);
                            }
                            for (FutureTask f : list) {
                                info.addAll((ObservableList<Infoclient>) f.get());
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

                                arttable(info, tableView, clshop, clmidzakaz, clmnphone, clmdate, clmnamemale);
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

    static class ActioNeWindow extends AbstractAction {
        JFrame old;
        JFrame now;

        ActioNeWindow(JFrame old, JFrame now) {
            this.old = old;
            this.now = now;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            old.setVisible(true);
            now.setVisible(true);
        }
    }
}

class MyCallable implements Callable<ObservableList<Infoclient>> {
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
    public ObservableList<Infoclient> call() throws Exception {

        ObservableList<Infoclient> info = FXCollections.observableArrayList();


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


                        info.add(new Infoclient(id, name, phone, date, shop));


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


        return info;
    }
}

