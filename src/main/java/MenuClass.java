import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuClass extends JMenuBar {
    static JMenuBar getMenu() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu File = new JMenu("File");
        JMenu Exit = new JMenu("Exit");

        JMenuItem search = new JMenuItem();
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tovar tovar = new Tovar();
            }
        });
        JMenuItem statist = new JMenuItem();
        statist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Statist statist1 = new Statist();

                    }
                });


            }
        });


        JMenuItem settings = new JMenuItem();
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings settings1 = new Settings();
            }
        });
        settings.setText("Настройки");
        File.add(search);
        File.add(statist);
        File.add(new JMenuItem("Почта", 'П')).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MailClass mailClass = new MailClass();
            }
        });
        File.add(new JMenuItem("Конвертер", 'К')).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Konverter konverter = new Konverter();
            }
        });
        statist.setText("Статистика");
        search.setText("Поиск товара");
        File.addSeparator();
        File.add(settings);

        Exit.add(new JMenuItem("Exit", 'E')).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        // File.addSeparator();

        jMenuBar.add(File);
        jMenuBar.add(Exit);

        return jMenuBar;
    }

    static class ActioNeWindow extends AbstractAction {

        JFrame now;

        ActioNeWindow(JFrame now) {

            this.now = now;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            now.setVisible(true);
        }
    }
}
