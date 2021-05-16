import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Tovar extends JFrame {


    static JLabel jLabel = new JLabel();


    static TableColumn<Allzakaz, String> clmnidtovara;
    static TableColumn<Allzakaz, String> clmncost;
    static TableColumn<Allzakaz, String> clmnssilka;

    static JButton jButton;
    static TableColumn<Allzakaz, String> clshop;

    static ArrayList NameList = new ArrayList();
    static JTextField textField;
    static JTextField textFieldid;
    static TableView<Allzakaz> tableView;
    static TableColumn<Allzakaz, Number> clmnid = new TableColumn<Allzakaz, Number>("№");

    Tovar() {

        try {
            FlatDarkPurpleIJTheme.install();

            UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        JFrame jFrame = new JFrame();
        jFrame.setResizable(false);
        jFrame.setTitle("Поиск товара");
        URL url2 = this.getClass().getResource("prom2.ico");
        try {
            jFrame.setIconImage(ImageIO.read(url2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width / 3 - 100, dimension.height / 3 - 100, 900, 550);

        JPanel panel = new JPanel();
        jFrame.add(panel);
        panel.setLayout(null);
        jFrame.setVisible(false);
        File dir = new File("C:\\Program Files\\new\\XMLfiles"); //path указывает на директорию
        NameList.clear();

        File[] arrFiles = dir.listFiles();
        List<File> lst = Arrays.asList(arrFiles);

        for (int i = 0; i < lst.size(); i++) {
            NameList.add(arrFiles[i].getName().replace(".xml", ""));
        }

        //Button
        jButton = new JButton(new Myaction(NameList));
        jButton.setText("Поиск");
        jButton.setBounds(469, 24, 100, 33);
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "clickButton");

        panel.getActionMap().put("clickButton", new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                jButton.doClick();

            }
        });
        //Menu
        JMenuBar jMenuBar = new JMenuBar();


        //TextField
        textFieldid = new HintTextField("Поиск по коду");
        textFieldid.setBounds(246, 12, 212, 30);
        textField = new HintTextField("Поиск по названию");
        textField.setBounds(246, 43, 212, 30);
        textFieldid.setToolTipText("Код");
        textField.setToolTipText("Название");
        //Table
        JFXPanel jfxPanel = new JFXPanel();
        tableView = new TableView();

        jfxPanel.setBounds(45, 94, 800, 400);


        tableView.setMaxHeight(400);
        tableView.setMaxWidth(800);


        clshop = new TableColumn<Allzakaz, String>("Магазин");
        clmnidtovara = new TableColumn<Allzakaz, String>("ID");
        clmncost = new TableColumn<Allzakaz, String>("Цена");

        clmnssilka = new TableColumn<Allzakaz, String>("Ссылка");


        clmnid.setPrefWidth(29);
        clshop.setPrefWidth(96);
        clmnidtovara.setPrefWidth(112);
        clmncost.setPrefWidth(84);
        clmnssilka.setPrefWidth(478);


        tableView.getStylesheets().add("CSSstyle/styletable.css");

        URL url = this.getClass().getResource("loading.gif");
        Icon myImgIcon = new ImageIcon(url);
        jLabel = new JLabel(myImgIcon);
        jLabel.setBounds(0, 0, 50, 50);
        jLabel.setVisible(false);
        tableView.getColumns().addAll(clmnid, clshop, clmnidtovara, clmncost, clmnssilka);
        Group group = new Group();
        Scene scene = new Scene(group);
        group.getChildren().addAll(tableView);
        jfxPanel.setScene(scene);


        panel.add(textFieldid);
        panel.add(jLabel);
        panel.add(textField);
        panel.add(jButton);
        jFrame.setJMenuBar(jMenuBar);
        panel.add(jfxPanel);
        JFrame.setDefaultLookAndFeelDecorated(true);
        panel.revalidate();


        jFrame.setVisible(true);
    }

    static class Myaction extends AbstractAction {
        ArrayList<String> NameList = new ArrayList<>();
        File dir = new File("C:\\Program Files\\new\\XMLfiles");
        File[] arrFiles = dir.listFiles();
        List<File> lst = Arrays.asList(arrFiles);

        Myaction(ArrayList NameList) {
            this.NameList.addAll(NameList);

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (textField.getText().equals("Поиск по названию") && textFieldid.getText().equals("Поиск по коду") || textField.getText().length() <= 2 && textField.getText().length() > 0
                    || textField.getText().equals("Поиск по названию") && textFieldid.getText().equals("") || textField.getText().equals("") && textFieldid.getText().equals("Поиск по коду")
            ) {
                JOptionPane.showMessageDialog(null, "Должно быть заполнено лишь одно поле! или более 2-х букв");


            } else if (!(textField.getText().equals("")) && !(textField.getText().matches("^\\D+$"))
            ) {
                JOptionPane.showMessageDialog(null, "КАКИЕ ЦИФРЫ В ПОЛЕ ДЛЯ НАЗВАНИЯ?!?!?!?!");


            } else if (!textField.getText().equals("Поиск по названию") && textFieldid.getText().equals("Поиск по коду") || textField.getText().equals("Поиск по названию") && !textField.getText().equals("Поиск по коду")) {
                jLabel.setVisible(true);
                jButton.setEnabled(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path;


                        if (!textFieldid.getText().equals("Поиск по коду")) {
                            path = textFieldid.getText();


                        } else {
                            path = textField.getText();

                        }
                        try {

                            ObservableList<Allzakaz> info = FXCollections.observableArrayList();


                            List<FutureTask> list = new ArrayList<>();
                            for (int i = 0; i < NameList.size(); i++) {
                                Callable<ObservableList<Allzakaz>> callable5 = new MyCallablee(path.toLowerCase(), lst.get(i).toString(), NameList.get(i).toString());

                                FutureTask futureTask15 = new FutureTask(callable5);
                                new Thread(futureTask15).start();

                                list.add(futureTask15);
                            }
                            for (FutureTask f : list) {
                                info.addAll((ObservableList<Allzakaz>) f.get());
                            }
                            if (info.size() == 0) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        JOptionPane.showMessageDialog(null, "Совпадений не найдено");
                                        jLabel.setVisible(false);
                                        jButton.setEnabled(true);
                                    }
                                });
                            } else {
                                arttable(info);
                                jLabel.setVisible(false);
                                info = null;
                                jButton.setEnabled(true);
                                System.gc();

                            }

                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }


                    }

                }).start();
            }
        }
    }

    public static void arttable(ObservableList<Allzakaz> info) {
        clmnidtovara.setCellFactory(TextFieldTableCell.forTableColumn());
        clmnidtovara.setCellValueFactory(cellData -> cellData.getValue().getId());

        clmnssilka.setCellFactory(TextFieldTableCell.forTableColumn());
        clmnssilka.setCellValueFactory(cellData -> cellData.getValue().getSsilka());

        clmncost.setCellFactory(TextFieldTableCell.forTableColumn());
        clmncost.setCellValueFactory(cellData -> cellData.getValue().getPrice());

        clshop.setCellFactory(TextFieldTableCell.forTableColumn());
        clshop.setCellValueFactory(cellData -> cellData.getValue().getShopp());
        clmnid.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Number>(tableView.getItems().indexOf(cellData.getValue()) + 1));
        tableView.setItems(info);

    }


}

class MyCallablee implements Callable<ObservableList<Allzakaz>> {
    String shop;
    String keyword;
    String path;
    String code;
    Integer a = 0;
    Integer i = 0;
    Integer vd = 0;
    Integer vd2 = 0;
    Integer f = 0;
    Integer g = 0;
    Integer b = 0;
    Integer c = 0;
    String name;
    String price;


    MyCallablee(String keyword, String path, String shop) {

        this.shop = shop;
        this.keyword = keyword;
        this.path = path;

    }


    @Override
    public ObservableList<Allzakaz> call() throws Exception {

        ObservableList<Allzakaz> info = FXCollections.observableArrayList();
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String line;

            while ((line = in.readLine()) != null) {
                if (line.contains("<name>") || line.contains("<url>") || line.contains("<vendorCode>") || line.contains("<price>")) {
                    stringBuffer.append(line + "\n");
                }

            }
            in.close();


            String output = stringBuffer.toString().toLowerCase().intern();
            stringBuffer = null;


            i = output.length();


            while (true) {
                a = output.lastIndexOf(keyword, i);

                if (a == -1) {

                    break;

                } else {


                    if (keyword.matches("^\\D*$")) {


                        vd = output.lastIndexOf("</vendorcode>", a + 150);

                        vd2 = output.lastIndexOf("<vendorcode>", vd);

                        code = output.substring(vd2 + 12, vd).intern();


                    } else {


                        if (output.substring(a + keyword.length(), a + keyword.length() + 15).contains("ven")) {
                            vd = output.lastIndexOf("</vendorcode>", a + 30);

                            vd2 = output.lastIndexOf("<vendorcode>", vd);
                            code = output.substring(vd2 + 12, vd).intern();

                        } else {

                            i = a - 5;
                            continue;
                        }


                    }
                    f = output.lastIndexOf("</price>", a);
                    g = output.lastIndexOf("<price>", f);

                    price = output.substring(g + 7, f).intern();


                    b = output.lastIndexOf("</url>", a);
                    c = output.lastIndexOf("<url>", b);

                    name = output.substring(c + 5, b).intern();


                    info.add(new Allzakaz(name, shop, code.toUpperCase(), price));
                    i = a - 5;
                    a = null;
                    vd = null;
                    vd2 = null;
                    f = null;
                    g = null;
                    b = null;
                    c = null;

                    name = null;
                    price = null;

                    code = null;
                    System.gc();

                }


            }
            i = null;
            output = null;
            System.gc();

        } catch (FileNotFoundException e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFO");
                    alert.setHeaderText(null);
                    alert.setContentText("Кажется файлы не найдены :(");
                    alert.showAndWait();

                }
            });


        }
        return info;
    }
}