import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    static JTable table;
    static DefaultTableModel defaultTableModel;
    static JLabel jLabel = new JLabel();
    static JButton jButton;
    static ArrayList<String> NameList = new ArrayList<>();
    static JTextField textField;
    static JTextField textFieldid;


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
        defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("№");
        defaultTableModel.addColumn("Магазин");
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Цена");
        defaultTableModel.addColumn("Ссылка");
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
        table.getColumnModel().getColumn(0).setPreferredWidth(29);
        table.getColumnModel().getColumn(1).setPreferredWidth(96);
        table.getColumnModel().getColumn(2).setPreferredWidth(112);
        table.getColumnModel().getColumn(3).setPreferredWidth(84);
        table.getColumnModel().getColumn(4).setPreferredWidth(478);


        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(45, 94, 800, 400);


        URL url = this.getClass().getResource("loading.gif");
        Icon myImgIcon = new ImageIcon(url);
        jLabel = new JLabel(myImgIcon);
        jLabel.setBounds(0, 0, 50, 50);
        jLabel.setVisible(false);





        panel.add(textFieldid);
        panel.add(jLabel);
        panel.add(textField);
        panel.add(jButton);
        jFrame.setJMenuBar(jMenuBar);
        panel.add(scrollPane);
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


                            ArrayList<String[]> items = new ArrayList<>();
                            List<FutureTask> list = new ArrayList<>();
                            for (int i = 0; i < NameList.size(); i++) {
                                Callable<ArrayList<String[]>> callable5 = new MyCallablee(path.toLowerCase(), lst.get(i).toString(), NameList.get(i).toString());

                                FutureTask futureTask15 = new FutureTask(callable5);
                                new Thread(futureTask15).start();

                                list.add(futureTask15);
                            }
                            for (FutureTask f : list) {
                                items.addAll((ArrayList<String[]>) f.get());
                            }
                            if (items.size() == 0) {

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        JOptionPane.showMessageDialog(null, "Совпадений не найдено");
                                        jLabel.setVisible(false);
                                        jButton.setEnabled(true);
                                    }
                                });
                            } else {
                                System.out.println(items.size());
                                arttable(items);
                                jLabel.setVisible(false);
                                items = null;
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


    public static void arttable(ArrayList<String[]> data) {

        defaultTableModel.setRowCount(0);

        for (int i = 0; i < data.size(); i++) {
            data.get(i)[0] = String.valueOf(i+1);
            defaultTableModel.addRow(data.get(i));

        }


        System.gc();
    }


}

class MyCallablee implements Callable<ArrayList<String[]>> {
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
    public ArrayList<String[]> call() throws Exception {

        ArrayList<String[]> items = new ArrayList<>();

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


                    items.add(new String[]{"", shop, code.toUpperCase(), price, name});

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
        return items;
    }
}