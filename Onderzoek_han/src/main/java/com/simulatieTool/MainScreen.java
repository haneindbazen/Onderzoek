package com.simulatieTool;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Button;
import java.awt.Label;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JTextField;

public class MainScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen frame = new MainScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 706, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 11, 690, 438);
		contentPane.add(panel);
		panel.setLayout(null);
		
		Label label = new Label("Home");
		label.setFont(new Font("Browallia New", Font.PLAIN, 17));
		label.setBounds(10, 10, 47, 22);
		panel.add(label);
		
		Label label_1 = new Label("Tomcat Locatie:");
		label_1.setBounds(34, 38, 79, 22);
		panel.add(label_1);
		
		final TextField textField = new TextField();
		textField.setBounds(119, 38, 329, 22);
		panel.add(textField);
		
		Button button = new Button("Browse...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button.setBounds(454, 38, 70, 22);
		panel.add(button);
		
		final Button button_1 = new Button("Start");
		final Button button_2 = new Button("Stop");
		final String tomcatLocatie = textField.getText();
		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(Tomcat.Start(tomcatLocatie)) button_2.setEnabled(true);
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} button_1.setEnabled(false);	
			}
		});
		button_1.setBounds(34, 87, 70, 22);
		panel.add(button_1);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Tomcat.Stop(tomcatLocatie)) button_1.setEnabled(true); button_2.setEnabled(false); Guvnor.Stop();
			}
		});
		button_2.setBounds(130, 87, 70, 22);
		panel.add(button_2);
	}
}
