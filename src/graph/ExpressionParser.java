package graph;

import java.util.ArrayList;

public class ExpressionParser extends Parser
{

	private int deepestBraPos = 0;
	private int deepestKetPos = 0;

	public float parseExpression(String s)
	{
		s = filter(s);

		int counter = 0;

		while (true)
		{

			if (++counter > 100)
				break;

			findDeepestBraKetPos(s, 0);
			if (deepestBraPos == -1)
			{
				break;
			} else
			{
				s = treatBrakets(s);
			}
		}

		return parseSimpleExpression(s);

	}

	private String filter(String s)
	{
		s = s.replace(",", ".");
		s = s.replace(" ", "");

		return s;
	}

	private String treatBrakets(String s)
	{
		String pieceOfLine = s.substring(deepestBraPos + 1, deepestKetPos);
		float newValue = parseSimpleExpression(pieceOfLine);

		if (checkForExpressionBeforeBrakets(s, "sin"))
		{
			return replaceCutOff(s, createStringWithoutE(Math.sin(newValue)), deepestBraPos - 3, deepestKetPos + 1);
		}

		if (checkForExpressionBeforeBrakets(s, "cos"))
		{
			return replaceCutOff(s, createStringWithoutE(Math.cos(newValue)), deepestBraPos - 3, deepestKetPos + 1);
		}

		if (checkForExpressionBeforeBrakets(s, "round"))
		{
			return replaceCutOff(s, createStringWithoutE(Math.round(newValue)), deepestBraPos - 5, deepestKetPos + 1);
		}

		if (checkForExpressionBeforeBrakets(s, "abs"))
		{
			return replaceCutOff(s, createStringWithoutE(Math.abs(newValue)), deepestBraPos - 3, deepestKetPos + 1);
		}

		if (checkForExpressionBeforeBrakets(s, "exp"))
		{
			return replaceCutOff(s, createStringWithoutE(Math.pow(Math.E, newValue)), deepestBraPos - 3, deepestKetPos + 1);
		}

		if (checkForExpressionBeforeBrakets(s, "ln"))
		{
			return replaceCutOff(s, createStringWithoutE(Math.log(newValue)), deepestBraPos - 2, deepestKetPos + 1);
		}

		if (checkForExpressionBeforeBrakets(s, "log"))
		{
			return replaceCutOff(s, createStringWithoutE(Math.log10(newValue)), deepestBraPos - 3, deepestKetPos + 1);
		}

		if (checkForExpressionBeforeBrakets(s, "sqrt"))
		{
			return replaceCutOff(s, createStringWithoutE(Math.sqrt(newValue)), deepestBraPos - 3, deepestKetPos + 1);
		}

		return replaceCutOff(s, createStringWithoutE(newValue), deepestBraPos, deepestKetPos + 1);
	}

	private boolean checkForExpressionBeforeBrakets(String s, String expr)
	{
		if (deepestBraPos >= expr.length())
		{
			if (s.substring(deepestBraPos - expr.length(), deepestBraPos).equals(expr))
				return true;
		}

		return false;
	}

	private float parseSimpleExpression(String s)
	{

		ArrayList<String> array = new ArrayList<String>();
		boolean changed = false;

		while (true)
		{
			changed = false;
			for (int i = 1; i < s.length(); i++)
			{

				if ((s.charAt(i) == '+' || s.charAt(i) == '-') && (s.charAt(i - 1) != '*' && s.charAt(i - 1) != '/' && s.charAt(i - 1) != '^'))
				{

					array.add(s.substring(0, i));
					s = s.substring(i, s.length());
					changed = true;
					break;
				}
			}
			if (!changed)
			{
				array.add(s);
				break;
			}
		}

		float result = 0;

		for (String line : array)
		{
			// System.out.println(line);
			result += calcValueFromMultDevExt(line);
		}

		return result;
	}

	private float calcValueFromMultDevExt(String s)
	{

		float scannedValue = 0;
		float result = 1;
		String prevSign = null;
		boolean changed = false;

		while (true)
		{
			changed = false;
			for (int i = 0; i < s.length(); i++)
			{
				if (s.charAt(i) == '*' || s.charAt(i) == '/' || s.charAt(i) == '^')
				{

					scannedValue = this.parseFloat(s.substring(0, i));

					if (prevSign != null)
					{
						if (prevSign.equals("M"))
						{
							result *= scannedValue;
						}
						if (prevSign.equals("D"))
						{
							result /= scannedValue;
						}
						if (prevSign.equals("E"))
						{
							result = (float) Math.pow(result, scannedValue);
						}
					} else
					{
						result = scannedValue;
					}

					if (s.charAt(i) == '*')
					{
						prevSign = "M";
					}
					if (s.charAt(i) == '/')
					{
						prevSign = "D";
					}
					if (s.charAt(i) == '^')
					{
						prevSign = "E";
					}

					s = s.substring(i + 1, s.length());

					changed = true;
					break;
				}
			}
			if (!changed)
			{

				scannedValue = this.parseFloat(s);
				// System.out.println(scannedValue);

				if (prevSign != null)
				{
					if (prevSign.equals("M"))
					{
						result *= scannedValue;
					}
					if (prevSign.equals("D"))
					{
						result /= scannedValue;
					}
					if (prevSign.equals("E"))
					{
						result = (float) Math.pow(result, scannedValue);
					}
				} else
				{
					result = scannedValue;
				}
				break;
			}

		}

		return result;

	}

	private void findDeepestBraKetPos(String s, int start)
	{

		deepestBraPos = -1;

		for (int i = start; i < s.length(); i++)
		{
			if (s.charAt(i) == '(')
			{
				deepestBraPos = i;
				findDeepestKetPos(s, i + 1);
			}
		}
	}

	private void findDeepestKetPos(String s, int start)
	{

		for (int i = start; i < s.length(); i++)
		{
			if (s.charAt(i) == ')')
			{
				deepestKetPos = i;
				return;
			}
			if (s.charAt(i) == '(')
			{
				deepestBraPos = i;
				findDeepestKetPos(s, i + 1);
			}
		}

	}

	private String replaceCutOff(String s, String insert, int from, int to)
	{

		String s1 = s.substring(0, from), s2 = s.substring(to, s.length());

		return s1 + insert + s2;

	}

	public String createStringWithoutE(double x)
	{

		return createStringWithoutE((float) x);
	}

	public String createStringWithoutE(float x)
	{

		String s = "" + x;

		s = s.toUpperCase();

		if (s.indexOf("E") == -1)
		{
			return s;
		} else
		{

			String afterE = s.substring(s.indexOf("E") + 1, s.length());

			int n = Integer.parseInt(afterE);

			String sign = "";

			if (s.indexOf("-") == 0)
			{
				sign = "-";
				s = s.substring(1, s.length());
			}

			int posOfDot = s.indexOf(".");
			String withoutDot = s.substring(0, s.indexOf(".")) + s.substring(s.indexOf(".") + 1, s.indexOf("E"));

			if (n < 0)
			{

				String result = "0.";

				for (int i = 1; i < (-n); i++)
					result += "0";

				return (sign + result + withoutDot);
			}

			if (n > 0)
			{

				String result = withoutDot;

				for (int i = 0; i < (n - (withoutDot.length() - posOfDot)); i++)
					result += "0";

				return (sign + result);
			}

		}

		return null;
	}

}
