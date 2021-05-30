import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;

public class Settings extends JFrame {
    public JButton jButton;
    public StringBuilder stringBuilder = new StringBuilder();

    Settings() {
        String g =System.getProperty("user.dir");

        File file = new File(g+"\\URL.txt");

        if (!file.isFile()) {
            try {
                FileOutputStream fileOut = new FileOutputStream(g+"\\URL.txt");
                fileOut.close();
                fileOut = new FileOutputStream(g+"\\nameshops.txt");
                fileOut.close();
                fileOut = new FileOutputStream(g+"\\Otzovik.txt");
                fileOut.close();
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

        jFrame.setBounds(dimension.width / 3 - 100, dimension.height / 3 - 100, 685, 550);

        JPanel panel = new JPanel();

        panel.setLayout(null);
        jFrame.setResizable(false);
        jFrame.setTitle("Настройки");
        jFrame.setContentPane(panel);
        //Button
        jButton = new JButton();
        jButton.setText("Сохранить");
        jButton.setBounds(290, 460, 100, 33);
        URL url2 = this.getClass().getResource("prom2.ico");
        try {
            jFrame.setIconImage(ImageIO.read(url2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JMenuBar jMenuBar = new JMenuBar();


        //textarea1


        JTextArea textArea1 = new JTextArea();
        JLabel jLabel1 = new JLabel("Названия магазинов");
        jLabel1.setBounds(30, 0, 150, 40);
        textArea1.setToolTipText("Названия магазинов");


        JScrollPane scrollPane = new JScrollPane(textArea1);
        scrollPane.setBounds(30, 30, 300, 200);
        scrollPane.setViewportView(textArea1);
        textArea1.setCaretPosition(textArea1.getDocument().getLength());
        //textarea2
        JTextArea textArea2 = new JTextArea();
        JLabel jLabel2 = new JLabel("Ссылки на сайт с покупателями");
        jLabel2.setBounds(350, 0, 180, 40);
        textArea2.setToolTipText("Ссылки на сайт с покупателями");


        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        scrollPane2.setBounds(350, 30, 300, 200);
        scrollPane2.setViewportView(textArea2);
        textArea2.setCaretPosition(textArea2.getDocument().getLength());
        //textarea3
        JTextArea textArea3 = new JTextArea();
        JLabel jLabel3 = new JLabel("Ссылки на сайт с отзывами");
        jLabel3.setBounds(30, 220, 180, 40);
        textArea3.setToolTipText("Ссылки на сайт с отзывами");


        JScrollPane scrollPane3 = new JScrollPane(textArea3);
        scrollPane3.setBounds(30, 250, 300, 200);
        scrollPane3.setViewportView(textArea3);
        textArea3.setCaretPosition(textArea3.getDocument().getLength());

//

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(g+"\\nameshops.txt")));
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            textArea1.setText(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());

            br = new BufferedReader(new InputStreamReader(new FileInputStream(g+"\\URL.txt")));
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            textArea2.setText(stringBuilder.toString());
            br.close();
            stringBuilder.delete(0, stringBuilder.length());
            br = new BufferedReader(new InputStreamReader(new FileInputStream(g+"\\Otzovik.txt")));
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            textArea3.setText(stringBuilder.toString());
            br.close();
            stringBuilder.delete(0, stringBuilder.length());
            stringBuilder = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter fileWriter = new FileWriter(g+"\\nameshops.txt");
                    fileWriter.write(textArea1.getText());
                    fileWriter.flush();
                    fileWriter = new FileWriter(g+"\\URL.txt");
                    fileWriter.write(textArea2.getText());
                    fileWriter.flush();
                    fileWriter = new FileWriter(g+"\\Otzovik.txt");
                    fileWriter.write(textArea3.getText());
                    fileWriter.flush();

                    JOptionPane.showMessageDialog(null, "Успешно сохранено!");
                    fileWriter.close();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "clickButton");

        panel.getActionMap().put("clickButton", new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                jButton.doClick();

            }
        });

        //
        panel.add(scrollPane3);
        panel.add(jLabel3);
        panel.add(scrollPane2);
        panel.add(jLabel2);
        panel.add(jLabel1);
        panel.add(scrollPane);
        jFrame.setJMenuBar(jMenuBar);
        panel.add(jButton);
        jFrame.setVisible(true);
    }
}
