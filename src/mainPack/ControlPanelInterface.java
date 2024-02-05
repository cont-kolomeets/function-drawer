package mainPack;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ControlPanelInterface extends JFrame
{
	private static final long serialVersionUID = -1869783337374973065L;

	public JButton runButton = new JButton("Run");

	public JTextField xFromField = new JTextField("-10");
	public JTextField xToField = new JTextField("10");
	public JTextField yFromField = new JTextField("-10");
	public JTextField yToField = new JTextField("10");
	public JTextField functionField = new JTextField("x");

	public JLabel functionLabel = new JLabel(" y =");
	public JLabel xFromLabel = new JLabel(" X: from");
	public JLabel xToLabel = new JLabel(" to");
	public JLabel yFromLabel = new JLabel(" Y: from");
	public JLabel yToLabel = new JLabel(" to");

	public JLabel outputMessageLabel = new JLabel("Input a function, or type dragon(1-20) or flake(1-12).");
	
	private JPanel mainPanel = new JPanel();

	public ControlPanelInterface()
	{
		createMainFrame();
		createComponents();
		addComponentsToFrame();
		this.repaint();
	}

	private void createMainFrame()
	{
		this.setTitle("Function Graph");
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(390, 160);
		this.setLocation((int) ((Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2),
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2));
		this.setLayout(null);
		this.setVisible(true);
	}

	private void createComponents()
	{
		mainPanel.setBounds(0, 0, this.getSize().width, this.getSize().height);
		mainPanel.setLayout(null);
		mainPanel.setBackground(new Color(50, 50, 50));

		functionLabel.setBounds(10, 10, 30, 20);
		functionLabel.setForeground(new Color(200, 200, 200));

		functionField.setBounds(30, 10, 200, 20);

		xFromLabel.setBounds(10, 35, 50, 20);
		xFromLabel.setForeground(new Color(200, 200, 200));

		xFromField.setBounds(60, 35, 40, 20);

		xToLabel.setBounds(110, 35, 50, 20);
		xToLabel.setForeground(new Color(200, 200, 200));

		xToField.setBounds(140, 35, 40, 20);

		yFromLabel.setBounds(10, 60, 50, 20);
		yFromLabel.setForeground(new Color(200, 200, 200));

		yFromField.setBounds(60, 60, 40, 20);

		yToLabel.setBounds(110, 60, 50, 20);
		yToLabel.setForeground(new Color(200, 200, 200));

		yToField.setBounds(140, 60, 40, 20);

		runButton.setBounds(290, 10, 90, 80);

		outputMessageLabel.setBounds(10, 100, this.getSize().width - 20, 20);
		outputMessageLabel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.DARK_GRAY, 1), ""));
		outputMessageLabel.setForeground(new Color(180, 100, 180));
	}

	private void addComponentsToFrame()
	{
		this.add(mainPanel);
		mainPanel.add(functionLabel);
		mainPanel.add(functionField);
		mainPanel.add(xFromLabel);
		mainPanel.add(xToLabel);
		mainPanel.add(xFromField);
		mainPanel.add(xToField);
		mainPanel.add(yFromLabel);
		mainPanel.add(yToLabel);
		mainPanel.add(yFromField);
		mainPanel.add(yToField);

		mainPanel.add(runButton);
		mainPanel.add(outputMessageLabel);
	}

}
