package graph;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GFrame extends JFrame
{
	private static final long serialVersionUID = -767076821085589347L;
	private static int FRAME_SIZE = 600;
	
	public GraphPanel graphPanel = new GraphPanel();

	public GFrame()
	{
		createFrame();
		createGraphPanel();
	}

	private void createFrame()
	{
		this.setTitle("Graph");
		this.setResizable(false);
		this.setSize(FRAME_SIZE, FRAME_SIZE);
		this.setLocation((int) ((Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2),
				(int) ((Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2));
		this.setLayout(null);
		this.setVisible(false);
	}

	public void createGraphPanel()
	{
		graphPanel.setBounds(0, 0, FRAME_SIZE - 8, FRAME_SIZE - 30);
		graphPanel.setBackground(Color.white);
		graphPanel.setLayout(null);

		this.add(graphPanel);
	}
	
	public void setFrameSize(int size)
	{
		this.setSize(size, size);
		graphPanel.setBounds(0, 0, size - 8, size - 30);
	}
	
}
