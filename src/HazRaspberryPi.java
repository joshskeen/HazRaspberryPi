import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author josh check the raspberry pi store to see if the raspberry pi model a
 *         or b has been released yet!
 */

public class HazRaspberryPi extends JFrame {

	private final int minutes = 1;
	JLabel labelLastChecked = new JLabel("last checked: ", JLabel.CENTER);
	JLabel labelRaspberryPiStatus = new JLabel("NOPE.", JLabel.CENTER);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				HazRaspberryPi hazRaspberryPi = new HazRaspberryPi();
				hazRaspberryPi.setVisible(true);
			}
		});
	}

	public HazRaspberryPi() {
		initUI();
	}

	// set up the ui
	private final void initUI() {
		JPanel basic = new JPanel();
		basic.setLayout(new BoxLayout(basic, BoxLayout.Y_AXIS));
		getContentPane().add(basic);

		JPanel topPanel = new JPanel(new BorderLayout(0, 0));
		basic.add(topPanel);

		JPanel midPanel = new JPanel(new BorderLayout(0, 0));
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.gray);
		midPanel.add(separator, BorderLayout.SOUTH);

		basic.add(midPanel);

		Font fontLastChecked = new Font("Verdana", Font.PLAIN, 10);
		labelLastChecked.setFont(fontLastChecked);

		Font fontRaspberryPiStatus = new Font("Verdana", Font.BOLD, 35);
		labelRaspberryPiStatus.setForeground(new Color(99, 99, 99));
		labelRaspberryPiStatus.setFont(fontRaspberryPiStatus);
		topPanel.add(labelRaspberryPiStatus);

		JButton quitButton = new JButton("Quit!");
		quitButton.setBounds(0, 10, 100, 20);
		quitButton.setToolTipText("a tooltip");
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		JButton checkNowButton = new JButton("Check!");
		checkNowButton.setBounds(0, 10, 100, 20);
		checkNowButton.setToolTipText("check the store now!!!");

		final Checker checker = new Checker(labelLastChecked,
				labelRaspberryPiStatus);
		checkNowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checker.checkRaspberryPiIsReleased();
			}
		});

		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottom.add(quitButton);
		bottom.add(checkNowButton);
		basic.add(bottom);
		midPanel.add(labelLastChecked);
		setTitle("HazRaspberryPi?");
		setSize(200, 140);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		checker.start();
	}

}