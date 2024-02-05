package graph;

public class GMath
{

	ExpressionParser eParser = new ExpressionParser();
	DrawingInfo dInfo = new DrawingInfo();

	public GMath()
	{
	}

	public void fillPointsArrayFromExpression()
	{
		double xStep = 0;
		double deltaX = dInfo.xTo - dInfo.xFrom;
		xStep = deltaX / dInfo.nPointsInCurve;

		dInfo.pointsArray.clear();

		for (int i = 0; i <= dInfo.nPointsInCurve; i++)
		{
			double[] temp = new double[2];

			temp[0] = dInfo.xFrom + i * xStep;
			temp[1] = calcValueFromExpression(temp[0]);

			dInfo.pointsArray.add(temp);
		}
	}

	public double calcValueFromExpression(double x)
	{
		if (dInfo.expression != null)
		{
			String we = eParser.createStringWithoutE(x);

			String e = dInfo.expression;

			e = e.replaceAll("exp", "EXP");

			if (x >= 0)
				e = e.replaceAll("x", we);
			if (x < 0)
			{
				e = e.replaceAll("-x", "+" + we.substring(1, we.length()));
				e = e.replaceAll("x", "" + we);
			}

			e = e.replaceAll("EXP", "exp");

			return eParser.parseExpression(e);
		}
		
		return 0f;
	}

}
