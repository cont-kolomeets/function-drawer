package graph;

import java.util.ArrayList;

import javax.swing.JFrame;

public class GraphicsFrame
{
	GMath math;
	GController cont;
	GFrame frame;

	public GraphicsFrame()
	{
		math = new GMath();
		frame = new GFrame();
		cont = new GController(frame, math);
	}

	public void showFrame()
	{
		cont.showFrame();
	}

	public void setPoints(ArrayList<double[]> points, boolean rescale)
	{
		math.dInfo.pointsArray = points;

		if (rescale)
		{
			double[] limits = findLimits(points);
			math.dInfo.xFrom = limits[0] - 2;
			math.dInfo.xTo = limits[1] + 2;
			math.dInfo.yFrom = limits[2] - 2;
			math.dInfo.yTo = limits[3] + 2;
		}

		cont.repaintGraphPanel();
	}

	private double[] findLimits(ArrayList<double[]> points)
	{
		double xMax = Float.MIN_VALUE;
		double yMax = Float.MIN_VALUE;
		double xMin = Float.MAX_VALUE;
		double yMin = Float.MAX_VALUE;

		for (int i = 0; i < points.size(); i++)
		{
			if (xMin > points.get(i)[0])
				xMin = points.get(i)[0];
			if (xMax < points.get(i)[0])
				xMax = points.get(i)[0];

			if (yMin > points.get(i)[1])
				yMin = points.get(i)[1];
			if (yMax < points.get(i)[1])
				yMax = points.get(i)[1];
		}

		double[] result = new double[4];// xmin, xmax, ymin, ymax

		result[0] = xMin;
		result[1] = xMax;
		result[2] = yMin;
		result[3] = yMax;

		return result;

	}

	public void setExpression(String ex)
	{
		if (!ex.equals("") && ex != null)
		{
			math.dInfo.expression = ex;
		}
	}
	
	public void clearExpression()
	{
		math.dInfo.expression = null;
	}

	public void setExtent(double xFrom, double xTo, double yFrom, double yTo)
	{
		math.dInfo.xFrom = xFrom;
		math.dInfo.xTo = xTo;
		math.dInfo.yFrom = yFrom;
		math.dInfo.yTo = yTo;
	}

	public void enableMouse()
	{
		cont.addGraphPanelMouseAdapter();
	}

	public void showGrid(boolean value)
	{

		frame.graphPanel.showGrid = value;
	}

	public void addSliders()
	{
		frame.graphPanel.addSliders();
		cont.enableSliders = true;
	}

	public void exitOnClose()
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setFrameSize(int size)
	{
		frame.setFrameSize(size);
	}

}
