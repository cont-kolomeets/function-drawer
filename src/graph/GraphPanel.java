package graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GraphPanel extends JPanel
{
	private static final long serialVersionUID = -5391976766565182778L;
	private static Font font1 = new Font("Arial", 0, 13);
	
	public DrawingInfo dInfo;
	public Slider xSlider = new Slider(true);
	public Slider ySlider = new Slider(false);
	public JLabel mouseLabel = new JLabel("x:0 y:10");
	public boolean showGrid = false;

	private double xCenter;
	private double yCenter;

	public GraphPanel()
	{
		mouseLabel.setBounds(5, 450, 200, 20);
		mouseLabel.setForeground(new Color(50, 50, 50));
		mouseLabel.setFont(font1);
		add(mouseLabel);
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height)
	{
		mouseLabel.setBounds(5, height - 20, 300, 20);
		super.setBounds(x, y, width, height);
	}

	public void addSliders()
	{
		this.add(xSlider);
		this.add(ySlider);

		xSlider.setLocation(10, 10);
		ySlider.setLocation(10, 30);

		this.repaint();
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		calcCenter();

		Graphics2D g2d = (Graphics2D) g;
		if (showGrid)
			drawGrid(g2d);
		drawGraph(g2d);
	}

	public void calcCenter()
	{
		xCenter = this.getSize().width * (-dInfo.xFrom) / (dInfo.xTo - dInfo.xFrom);
		yCenter = (dInfo.yTo) * this.getSize().height / (dInfo.yTo - dInfo.yFrom);

		dInfo.recalcScale();
	}

	public void drawGrid(Graphics2D g2d)
	{
		g2d.setPaint(Color.black);
		g2d.draw(new Line2D.Double(0, yCenter, this.getSize().width, yCenter));
		g2d.draw(new Line2D.Double(xCenter, 0, xCenter, this.getSize().height));

		drawHorizontalTicks(g2d);
		drawVerticalTicks(g2d);
	}

	public void drawHorizontalTicks(Graphics2D g2d)
	{
		double val = Math.max(Math.abs(dInfo.xFrom), Math.abs(dInfo.xTo));

		int steps = (int) Math.round(val / dInfo.getTickStep(val));

		for (int i = -steps; i <= steps; i++)
		{
			double x1 = xCenter + dInfo.getTickStep(val) * i * dInfo.xScale;
			double y1 = yCenter - 5;
			double x2 = x1;
			double y2 = yCenter + 5;

			String s = "" + dInfo.getTickStep(val) * i;

			g2d.draw(new Line2D.Double(x1, y1, x2, y2));
			g2d.drawString(s, (int) x1, (int) (y1 - 10));
		}
	}

	public void drawVerticalTicks(Graphics2D g2d)
	{
		double val = Math.max(Math.abs(dInfo.yFrom), Math.abs(dInfo.yTo));

		int steps = (int) Math.round(val / dInfo.getTickStep(val));

		for (int i = -steps; i <= steps; i++)
		{

			double y1 = yCenter - dInfo.getTickStep(val) * i * dInfo.yScale;
			double x1 = xCenter - 5;
			double y2 = y1;
			double x2 = xCenter + 5;

			String s = "" + dInfo.getTickStep(val) * i;

			g2d.draw(new Line2D.Double(x1, y1, x2, y2));

			if (i != 0)
				g2d.drawString(s, (int) (x2 + 10), (int) y1);
		}
	}

	public void drawGraph(Graphics2D g2d)
	{
		g2d.setPaint(Color.red);
		if (dInfo.pointsArray != null)
			for (int i = 1; i < dInfo.pointsArray.size(); i++)
			{

				double x1 = xCenter + dInfo.pointsArray.get(i - 1)[0] * dInfo.xScale;
				double y1 = yCenter - dInfo.pointsArray.get(i - 1)[1] * dInfo.yScale;
				double x2 = xCenter + dInfo.pointsArray.get(i)[0] * dInfo.xScale;
				double y2 = yCenter - dInfo.pointsArray.get(i)[1] * dInfo.yScale;

				if (!Double.isNaN(x1) && !Double.isNaN(x2))
					g2d.draw(new Line2D.Double(x1, y1, x2, y2));
			}
	}
	
	public double[] globalToLocal(double x, double y)
	{
		return dInfo.globalToLocal(this, x, y);
	}

}
