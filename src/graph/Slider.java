package graph;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Timer;

import javax.swing.JPanel;

public class Slider extends JPanel
{

	private static final long serialVersionUID = -6725857914932997689L;
	public int thumbSize = 12;
	public int length = 100;
	public int x = 0;
	public int y = 0;
	public Rectangle2D.Float thumbRect;
	private Timer timer = new Timer();
	public float value = 0;
	public boolean horizontal = true;

	public Slider(boolean horizontal)
	{

		this.horizontal = horizontal;
		setLength(100);
		setThumbSize(15);
		createSlider();

	}

	public Slider(int length, int thumbSize, boolean horizontal)
	{

		this.horizontal = horizontal;
		setLength(length);
		setThumbSize(thumbSize);
		createSlider();
	}

	public void setLength(int length)
	{

		this.length = length;
		refreshBounds();
	}

	public void setThumbSize(int size)
	{

		this.thumbSize = size;
		refreshBounds();
	}

	public void setLocation(int x, int y)
	{

		this.x = x;
		this.y = y;
		refreshBounds();
	}

	public void refreshBounds()
	{
		if (horizontal)
			this.setBounds(x, y, length + 1, thumbSize + 1);
		else
			this.setBounds(x, y, thumbSize + 1, length + 1);

	}

	public float getThumbPos()
	{
		if (horizontal)
			return thumbRect.x;
		return thumbRect.y;
	}

	public void setThumbPos(float pos)
	{
		if (horizontal)
		{
			thumbRect.x = pos;
		} else
		{
			thumbRect.y = pos;
		}

	}

	public void createSlider()
	{

		if (horizontal)
			thumbRect = new Rectangle2D.Float(length / 2 - thumbSize / 2, 0, thumbSize, thumbSize);
		else
			thumbRect = new Rectangle2D.Float(0, length / 2 - thumbSize / 2, thumbSize, thumbSize);

		SliderMouseAdapter adapter = new SliderMouseAdapter();
		this.addMouseMotionListener(adapter);
		this.addMouseListener(adapter);
	}

	public void paint(Graphics g)
	{

		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		drawRangeLine(g2d);
		drawThumb(g2d);
	}

	public void drawRangeLine(Graphics2D g2d)
	{

		g2d.setPaint(Color.gray);
		if (horizontal)
		{
			g2d.draw(new Line2D.Float(0, thumbSize / 2, length, thumbSize / 2));
			g2d.draw(new Line2D.Float(0, thumbSize * 1 / 4, 0, thumbSize * 3 / 4));
			g2d.draw(new Line2D.Float(length / 2, 0, length / 2, thumbSize));
			g2d.draw(new Line2D.Float(length, thumbSize * 1 / 4, length, thumbSize * 3 / 4));
		} else
		{
			g2d.draw(new Line2D.Float(thumbSize / 2, 0, thumbSize / 2, length));
			g2d.draw(new Line2D.Float(thumbSize * 1 / 4, 0, thumbSize * 3 / 4, 0));
			g2d.draw(new Line2D.Float(0, length / 2, thumbSize, length / 2));
			g2d.draw(new Line2D.Float(thumbSize * 1 / 4, length, thumbSize * 3 / 4, length));

		}

	}

	public void drawThumb(Graphics2D g2d)
	{

		g2d.setPaint(Color.black);
		g2d.draw(thumbRect);
	}

	class SliderMouseAdapter extends MouseAdapter
	{

		private float val = 0;
		private float dv = 0;
		private boolean buttonPressed = false;

		public void mousePressed(MouseEvent e)
		{

			if (horizontal)
				val = e.getX();
			else
				val = e.getY();

			buttonPressed = true;
		}

		public void mouseReleased(MouseEvent e)
		{

			if (buttonPressed)
			{
				buttonPressed = false;
				startSlidingBack();
			}

		}

		public void mouseDragged(MouseEvent e)
		{

			if (thumbRect.getBounds2D().contains(e.getX(), e.getY()) || buttonPressed)
			{
				if (horizontal)
				{
					dv = e.getX() - val;
					val = e.getX();
				} else
				{
					dv = e.getY() - val;
					val = e.getY();
				}

				setThumbPos(getThumbPos() + dv);
				value = -(length / 2 - thumbSize / 2 - getThumbPos()) / (length / 2 - thumbSize / 2);

				if (getThumbPos() < 1)
					setThumbPos(1);
				if (getThumbPos() > (length - thumbSize + 2))
					setThumbPos(length - thumbSize + 2);
				Slider.this.repaint();
			}

		}

		public void startSlidingBack()
		{

			final float dv = ((length / 2 - thumbSize / 2) - getThumbPos()) / 30;

			SwingTimerTask slidingBackTask = new SwingTimerTask()
			{

				public void doRun()
				{
					if (buttonPressed)
						value = -(length / 2 - thumbSize / 2 - getThumbPos()) / (length / 2 - thumbSize / 2);
					else
						value = 0;
					// System.out.println(value);
					setThumbPos(getThumbPos() + dv);
					Slider.this.repaint();
					if (Math.abs(getThumbPos() - (length / 2 - thumbSize / 2)) < 0.2)
					{
						setThumbPos((length / 2 - thumbSize / 2 + 1));
						value = 0;
						timer.cancel();
					}

				}
			};

			timer.cancel();
			value = 0;
			timer = new Timer();
			timer.schedule(slidingBackTask, 0, 5); // task, delay, period

		}
	}

	private static abstract class SwingTimerTask extends java.util.TimerTask
	{
		public abstract void doRun();

		public void run()
		{
			if (!EventQueue.isDispatchThread())
			{
				EventQueue.invokeLater(this);
			} else
			{
				doRun();
			}
		}
	}

}
