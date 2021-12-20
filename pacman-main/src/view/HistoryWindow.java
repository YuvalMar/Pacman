package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import controller.SysData;
import model.FancyButton;
import model.Question;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import java.awt.Font;

public class HistoryWindow extends JFrame{
	private JTable table;
	public HistoryWindow() {
        setSize(622,300);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel buttonsC = new JPanel();
        JScrollPane sp = new JScrollPane();
        sp.setBackground(Color.ORANGE);
        
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setSize(557, 203);
        sp.setLocation(23, 13);
        buttonsC.add(sp);
        buttonsC.setBackground(Color.black);
        buttonsC.setLayout(null);  
        FancyButton backBtn = new FancyButton("Back");
        backBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
        backBtn.setBounds(526, 229, 54, 16);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartWindow sw = new StartWindow();
                dispose();
            }
        });
        getContentPane().add(buttonsC);
        SysData.getInstance().readJson();
        buttonsC.add(backBtn);
        
        table = new JTable();
        table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        String[] cn = {"Name","Score"};
        DefaultTableModel model = new DefaultTableModel(null,cn);
        for(int i=0; i<SysData.getInstance().getHistory().size(); i++) {
        	String[] rowData = {SysData.getInstance().getHistory().get(i), SysData.getInstance().getScores().get(i)};
        	model.insertRow(model.getRowCount(), rowData);
        }
        table.setModel(model);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getTableHeader().setBackground(Color.ORANGE);
        table.setBackground(Color.ORANGE);
        table.setForeground(Color.BLACK);
        table.setBounds(23, 13, 741, 388);
        sp.setViewportView(table);

        setVisible(true);
	}
}
