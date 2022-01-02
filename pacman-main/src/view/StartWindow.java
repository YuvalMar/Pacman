package view;
/**
 * The first window when running the game. Has the games logo, and 3 buttons:
 * Start game button, takes the player to the nickname picking window.
 * History, watch games history.
 * Manage question, move to question's managing window.
 */

import model.FancyButton;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@SuppressWarnings("serial")
public class StartWindow extends JFrame {
	@SuppressWarnings("unused")
    public StartWindow(){
        setSize(600,300);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon logo = new ImageIcon();
        try {
            logo = new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/images/pacman_logo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Register Custom fonts
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/resources/fonts/crackman.ttf")));
        } catch (IOException|FontFormatException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        getContentPane().add(new JLabel(logo),BorderLayout.NORTH);

        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);
        //buttonsC.setLayout(new FlowLayout(FlowLayout.LEADING,20,10));
        buttonsC.setLayout(new BoxLayout(buttonsC,BoxLayout.Y_AXIS));
        FancyButton startButton = new FancyButton("Start Game");
        FancyButton manageQuestionsButton = new FancyButton("Manage Questions");
        FancyButton highScoreButton = new FancyButton("History");

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageQuestionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				PlayerNameWindow pn = new PlayerNameWindow();
                //new PacWindow();
                dispose();
            }
        });
       
        manageQuestionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageQuestionsWindow mq = new ManageQuestionsWindow();
                dispose();
            }
        });
        
        highScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistoryWindow hb = new HistoryWindow();
                dispose();
            }
        });
        
        buttonsC.add(startButton);
        buttonsC.add(manageQuestionsButton);
        buttonsC.add(highScoreButton);
        

        getContentPane().add(buttonsC);

        setVisible(true);
    }
}

