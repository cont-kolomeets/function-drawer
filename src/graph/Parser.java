package graph;

public class Parser
{

	public int parseInt(String s)
	{

		int sign = 1;

		if (s.charAt(0) == '-')
		{
			sign = -1;
			s = s.substring(1, s.length());
		}

		if (s.charAt(0) == '+')
		{
			s = s.substring(1, s.length());
		}

		if (s.length() > 10)
		{
			throw new RuntimeException("Number is out of bounds");
		}

		int[] array = new int[s.length()];

		for (int i = 0; i < s.length(); i++)
		{
			array[i] = convertCharToInt(s.charAt(i)) * (int) Math.pow(10, s.length() - i - 1);
		}

		return sign * sumArray(array);
	}

	public float parseFloat(String s)
	{

		float sign = 1;
		int pointPos = s.indexOf(".");

		if (pointPos == -1)
		{
			try
			{
				return parseInt(s);
			} catch (Exception e)
			{
			}

		} else
		{

			if (s.charAt(0) == '-')
			{
				sign = -1;
				s = s.substring(1, s.length());
				pointPos = s.indexOf(".");
			}

			if (s.charAt(0) == '+')
			{
				s = s.substring(1, s.length());
				pointPos = s.indexOf(".");
			}

			String s1 = s.substring(0, pointPos);
			String s2 = s.substring(pointPos + 1, s.length());

			float[] beforePointArray = new float[s1.length()];
			float[] afterPointArray = new float[s2.length()];

			for (int i = 0; i < s1.length(); i++)
			{
				beforePointArray[i] = convertCharToInt(s1.charAt(i)) * (int) Math.pow(10, s1.length() - i - 1);
			}

			for (int i = 0; i < s2.length(); i++)
			{
				afterPointArray[i] = convertCharToInt(s2.charAt(i)) * (float) Math.pow(10, -i - 1);
			}
			// System.out.println("sign = " + sign);

			return sign * (sumArray(beforePointArray) + sumArray(afterPointArray));
		}
		return 0;
	}

	private int convertCharToInt(char c)
	{

		switch (c)
		{
			case '1':
				return 1;
			case '2':
				return 2;
			case '3':
				return 3;
			case '4':
				return 4;
			case '5':
				return 5;
			case '6':
				return 6;
			case '7':
				return 7;
			case '8':
				return 8;
			case '9':
				return 9;
		}

		return 0;

	}

	private int sumArray(int[] array)
	{

		int result = 0;

		for (int v : array)
		{
			result += v;
		}

		return result;
	}

	private float sumArray(float[] array)
	{

		float result = 0;

		for (float v : array)
		{
			result += v;
		}

		return result;
	}

}
