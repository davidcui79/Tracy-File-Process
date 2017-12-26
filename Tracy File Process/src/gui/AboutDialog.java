package gui;

import javax.swing.JPanel;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Dimension;

public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JTextArea textAreaDesc = null;

	private JButton btnOK = null;

	/**
	 * @param owner
	 */
	public AboutDialog(Frame owner) {
		//set modal to true
		super(owner, true);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setResizable(false);
		this.setTitle("About Tracy File Convert");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.ipadx = 0;
			gridBagConstraints1.anchor = GridBagConstraints.EAST;
			gridBagConstraints1.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.ipadx = 108;
			gridBagConstraints.ipady = 60;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridwidth = 2;
			gridBagConstraints.gridx = 0;
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getTextAreaDesc(), gridBagConstraints);
			jContentPane.add(getBtnOK(), gridBagConstraints1);
		}
		return jContentPane;
	}

	/**
	 * This method initializes textAreaDesc	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTextAreaDesc() {
		if (textAreaDesc == null) {
			textAreaDesc = new JTextArea();
			textAreaDesc.setText("Tracy File Convert\n\nOctober 2011");
			textAreaDesc.setEditable(false);
		}
		return textAreaDesc;
	}

	/**
	 * This method initializes btnOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setText("OK");
			btnOK.setHorizontalAlignment(SwingConstants.CENTER);
			btnOK.setHorizontalTextPosition(SwingConstants.RIGHT);
			/*
			 * allow internal class for this one liner thing
			 */
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					AboutDialog.this.dispose();
				}
			});
		}
		return btnOK;
	}

}
