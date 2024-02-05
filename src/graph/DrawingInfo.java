package graph;

import java.awt.Component;
import java.util.ArrayList;

public class DrawingInfo
{
	public double xFrom = -10;
	public double xTo = 10;
	public double yFrom = -10;
	public double yTo = 10;
	public String expression = null;
	public double xScale = 1;
	public double yScale = 1;
	public double frameWidth = 100;
	public double frameHeight = 100;
	public double xMax = Constants.MAX_X_AVAILABLE;
	public double xMin = Constants.MIN_X_AVAILABLE;
	public double yMax = Constants.MAX_Y_AVAILABLE;
	public double yMin = Constants.MIN_Y_AVAILABLE;

	public int nPointsInCurve = 1000;

	public ArrayList<double[]> pointsArray = new ArrayList<double[]>();

	public DrawingInfo()
	{
	}

	public void recalcScale()
	{
		xScale = frameWidth / (xTo - xFrom);
		yScale = frameHeight / (yTo - yFrom);
	}

	public void checkMinMaxRanges()
	{
		if (Math.abs(xFrom) > xMax)
			xFrom = xFrom / Math.abs(xFrom) * xMax;
		if (Math.abs(xFrom) < xMin)
			xFrom = xFrom / Math.abs(xFrom) * xMin;

		if (xTo > xMax)
			xTo = xMax;
		if (xTo < xMin)
			xTo = xMin;

		if (Math.abs(yFrom) > yMax)
			yFrom = yFrom / Math.abs(yFrom) * yMax;
		if (Math.abs(yFrom) < yMin)
			yFrom = yFrom / Math.abs(yFrom) * yMin;

		if (yTo > yMax)
			yTo = yMax;
		if (yTo < yMin)
			yTo = yMin;
	}

	public float getTickStep(double val)
	{
		float sign = (int) (val / Math.abs(val));

		val = Math.abs(val);

		if (val > 0.002 && val <= 0.005)
			return 0.0005f * sign;
		if (val > 0.005 && val <= 0.02)
			return 0.002f * sign;
		if (val > 0.02 && val <= 0.05)
			return 0.005f * sign;
		if (val > 0.05 && val <= 0.2)
			return 0.02f * sign;
		if (val > 0.2 && val <= 0.5)
			return 0.05f * sign;
		if (val > 0.5 && val <= 2)
			return 0.2f * sign;
		if (val > 2 && val <= 5)
			return 0.5f * sign;
		if (val > 5 && val <= 20)
			return 2 * sign;
		if (val > 20 && val <= 50)
			return 5 * sign;
		if (val > 50 && val <= 200)
			return 20 * sign;
		if (val > 200 && val <= 500)
			return 50 * sign;
		if (val > 500 && val <= 2000)
			return 200 * sign;
		if (val > 2000 && val <= 5000)
			return 500 * sign;
		if (val > 5000 && val <= 20000)
			return 2000 * sign;
		if (val > 20000 && val <= 50000)
			return 5000 * sign;
		if (val > 50000 && val <= 200000)
			return 20000 * sign;
		if (val > 200000)
			return 50000 * sign;

		return 100 * sign;
	}

	public double[] globalToLocal(Component panel, double x, double y)
	{
		double xScale = frameWidth / (xTo - xFrom);
		double yScale = frameHeight / (yTo - yFrom);
		
		double nx = x / xScale + xFrom;
		double ny = y / yScale - yTo;

		double[] array = { nx, ny };

		return array;
	}

}
