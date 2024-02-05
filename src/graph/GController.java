package graph;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.util.Timer;

public class GController
{
	GFrame graphFrame;
	GMath fgm;
	Timer timer = new Timer();
	MouseAdapter ma;
	boolean enableSliders = false;
	boolean timerStarted = false;

	public GController(GFrame graphFrame, GMath fgm)
	{
		this.graphFrame = graphFrame;
		this.fgm = fgm;

		graphFrame.graphPanel.dInfo = fgm.dInfo;
		fgm.dInfo.frameWidth = graphFrame.graphPanel.getSize().width;
		fgm.dInfo.frameHeight = graphFrame.graphPanel.getSize().height;
	}

	public void addGraphPanelMouseAdapter()
	{
		ma = new GraphPanelMouseAdapter(graphFrame.graphPanel, this);
		graphFrame.graphPanel.addMouseListener(ma);
		graphFrame.graphPanel.addMouseMotionListener(ma);
	}

	public void showFrame()
	{
		if (fgm.dInfo.expression != null)
			fgm.fillPointsArrayFromExpression();
		graphFrame.setVisible(true);
		graphFrame.graphPanel.repaint();
		if (enableSliders && !timerStarted)
			startTimer();
	}

	public void repaintGraphPanel()
	{
		if (fgm.dInfo.expression != null)
			fgm.fillPointsArrayFromExpression();
		graphFrame.graphPanel.repaint();
	}

	public void startTimer()
	{
		timerStarted = true;

		SwingTimerTask updatePanTask = new SwingTimerTask()
		{
			public void doRun()
			{
				float xValue = graphFrame.graphPanel.xSlider.value;
				float yValue = graphFrame.graphPanel.ySlider.value;

				if (xValue != 0 || yValue != 0)
				{
					fgm.dInfo.xFrom *= 1 + xValue / 2;
					fgm.dInfo.xTo *= 1 + xValue / 2;
					fgm.dInfo.yFrom *= 1 + yValue / 2;
					fgm.dInfo.yTo *= 1 + yValue / 2;
					fgm.dInfo.checkMinMaxRanges();
					repaintGraphPanel();

				}
			}
		};

		timer.cancel();
		timer = new Timer();
		timer.schedule(updatePanTask, 0, 50); // task, delay, period
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
