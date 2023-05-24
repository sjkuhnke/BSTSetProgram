import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Driver implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6651871957967502739L;
	private static final String FILE_PATH = "set.dat";
	private BSTSet set;
	
	public static void main(String[] args) {
        Driver driver = new Driver();
        driver.loadSetFromFile();
        driver.runUI();
    }
	
	private void runUI() {
		
		JFrame frame = new JFrame("ArraySet UI");
		JPanel panel = new JPanel();
		JTextField tf = new JTextField(20);
		JButton remove = new JButton("Remove");
		JButton print = new JButton("Print");
		JButton clear = new JButton("Clear");
		JButton count = new JButton("Count: " + set.count());
		remove.setBackground(Color.YELLOW);
		print.setBackground(Color.GREEN);
		clear.setBackground(Color.RED);
		count.setBackground(new Color(100, 100, 200));
		
		panel.add(tf);
		panel.add(remove);
		panel.add(print);
		panel.add(count);
		panel.add(clear);
		
		tf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = set.toProperCase(tf.getText());
				if (set.add(text)) {
                	System.out.println("Added " + text);
                } else {
                	System.out.println(text + " already in set");
                }
				tf.selectAll();
				count.setText("Count: " + set.count());
				frame.pack();
			}
		});
		
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = set.toProperCase(tf.getText());
				if (set.remove(text)) {
					System.out.println("Removed " + text);
				} else {
					System.out.println(text + " not present in set");
				}
				count.setText("Count: " + set.count());
				frame.pack();
			}
		});
		
		print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(set.toString());
            }
        });
		
		clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to clear the set?", "Confirm Clear", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    set.clear();
                    System.out.println("\nSet cleared.\n");
                }
                count.setText("Count: " + set.count());
                frame.pack();
            }
        });
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                saveSetToFile(set);
            }
        });
	}
	
	private static void saveSetToFile(BSTSet set) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(set);
            System.out.println("Set saved to " + FILE_PATH);
        } catch (IOException ex) {
            System.out.println("Error saving set to file: " + ex.getMessage());
        }
    }
	
	private void loadSetFromFile() {
        try (FileInputStream fileIn = new FileInputStream(FILE_PATH);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            set = (BSTSet) objectIn.readObject();
            System.out.println("Set loaded from set.dat successfully.");
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error loading from set.dat: " + ex.getMessage());
            set = new BSTSet();
        }
    }
}
