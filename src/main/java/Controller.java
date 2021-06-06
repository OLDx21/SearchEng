import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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
   public static String g =System.getProperty("user.dir");
    Controller() {

        try {

            FlatDarkPurpleIJTheme.install();

            UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        AbstractAction action = new SearchClientWin.Action();
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
        jButton = new JButton(new SearchClientWin.Action());
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
        jmenuItem.addActionListener(new SearchClientWin.Action());
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


}



