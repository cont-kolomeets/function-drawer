package mainPack;

import java.util.ArrayList;

public class MathUtils
{

	private static int nOfCuts = 0;
	private static int FROM_BEGINING = 0;
	private static int ONE_OFFSET = 1;

	public static boolean isPrime(int n)
	{

		int a = (int) Math.round(Math.sqrt(n) / 2) + 1;

		if (a % 2 == 0)
			a++;

		if (n % 2 == 0)
			return false;

		for (int i = 3; i < (a + 3); i += 2)
		{

			// System.out.println(i);

			if (n % i == 0)
				return false;

		}

		return true;
	}

	public static void factorize(int n)
	{

		ArrayList<Integer> list = new ArrayList<Integer>();

		int count = 0;

		while (!isPrime(n) && ++count < 100)
		{

			for (int i = 2; i < ((float) n / 2f + 1); i++)
			{

				if (n % i == 0)
				{
					// System.out.println(i);
					list.add(i);
					n = Math.round((float) n / (float) i);
					break;
				}
			}

		}

		list.add(n);

		String line = "";
		for (int i = 0; i < list.size(); i++)
		{

			line += "" + list.get(i) + " ";
		}

		System.out.println(line);

	}

	// calculates n of knots of a gird enveloped by a circle

	public static int calcNOfKnotsInCircle(float r)
	{

		int result = 0;

		for (int i = 0; i <= (int) r; i++)
		{

			result += (int) Math.sqrt(r * r - i * i);
		}

		return result * 4 + 1;
	}

	// calculates n of cuts. Squares with max size are cut off the initial rect
	// untill a square remains.

	public static int calcNOfCutFromRectToGetSquare(int n, int m)
	{

		nOfCuts = 0;

		getNewRect(n, m);

		return nOfCuts;

	}

	private static void getNewRect(int n, int m)
	{

		if (n == m)
			return;

		nOfCuts++;

		if (n > m)
			getNewRect(n - m, m);
		else
			getNewRect(m - n, n);
	}

	// two boxes. At one time you can move from the 1st box to the 2nd one the
	// number of carrots equal to that in the 2nd box.
	// Determines the possibility to empty one of the boxes

	public static boolean ifCanEmptyABox(int a, int b)
	{

		int c, d;
		ArrayList<int[]> list = new ArrayList<int[]>();

		while (true)
		{

			// System.out.println(a + "   " + b);

			if (a == b)
				return true;

			if (!addToList(list, a, b))
				return false;

			if (a > b)
			{

				c = a - b;
				d = 2 * b;
			} else
			{

				c = b - a;
				d = 2 * a;
			}

			a = c;
			b = d;
		}
	}

	private static boolean addToList(ArrayList<int[]> list, int a, int b)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i)[0] == a && list.get(i)[1] == b)
				return false;

			if (list.get(i)[0] == b && list.get(i)[1] == a)
				return false;
		}

		int[] temp = new int[2];
		temp[0] = a;
		temp[1] = b;

		list.add(temp);

		return true;
	}

	// Calculates number of integer-valued points lying on a segment

	public static int calcIntPointsOnSegment(int x1, int y1, int x2, int y2)
	{

		int dx = x2 - x1;
		int dy = y2 - y1;
		int result = 0;

		for (int i = 1; i < dx; i++)
		{

			if ((i * dy) % dx == 0)
				result++;
		}

		return result;
	}

	// Determines the possibility to put Ax1 tiles on a frame X*Y

	public static boolean ifCanPutTileOnFrame(int x, int y, int a)
	{

		if (x < a || y < a)
			return false;

		int left1, left2;

		left1 = calcLeft(FROM_BEGINING, x, y, 1, a, 0);
		left2 = calcLeft(ONE_OFFSET, x, y, 1, a, 0);

		if (left1 == 1 || left2 == 0)
			return true;

		return false;
	}

	private static int calcLeft(int mode, int x, int y, int turn, int A, int sideCount)
	{

		int left = -1;
		int length = -1;
		if (turn == 1)
			length = x;
		else
			length = y;

		if (mode == FROM_BEGINING)
		{
			left = length % A;

			if (sideCount < 3)
			{
				if (left == 0)
					return calcLeft(ONE_OFFSET, x, y, -turn, A, ++sideCount);
				if (left == 1)
					return calcLeft(FROM_BEGINING, x, y, -turn, A, ++sideCount);
			}
		}

		if (mode == ONE_OFFSET)
		{
			left = (length - 1) % A;
			if (sideCount < 3)
			{
				if (left == 0)
					return calcLeft(ONE_OFFSET, x, y, -turn, A, ++sideCount);
				if (left == 1)
					return calcLeft(FROM_BEGINING, x, y, -turn, A, ++sideCount);
			}

		}

		if (left == 0 || left == 1)
			return left;

		return -1;
	}

	public static float[] intersection(float[] segment1, float[] segment2)
	{

		float x1 = segment1[0];
		float x2 = segment1[1];
		float x3 = segment2[0];
		float x4 = segment2[1];

		float resX1, resX2;
		float[] result = null;

		if ((x3 < x1 && x4 < x1) || (x3 > x2 && x4 > x2))
			return null;

		if (x3 >= x1)
			resX1 = x3;
		else
			resX1 = x1;

		if (x4 <= x2)
			resX2 = x4;
		else
			resX2 = x2;

		result = new float[2];
		result[0] = resX1;
		result[1] = resX2;

		return result;
	}

	public static void printArray(int[] array)
	{

		for (int i : array)
			System.out.println(i);
	}

	// Task 3.1. Example

	private static int toFill = 0;
	private static int minFilled = -1;
	private static int[] line;

	public static void task3_1(int n)
	{

		line = new int[n];
		toFill = 0;
		minFilled = -1;

		fillCell();

	}

	private static void fillCell()
	{

		if (toFill == (minFilled + 1))
		{
			line[toFill] = 1;

			// System.out.println("+" + (toFill+1));
			drawArrayCells();

			boolean moved = false;

			if (toFill != (line.length - 1))
				if (line[toFill + 1] != 1)
				{
					if (toFill != 0)
					{
						line[toFill - 1] = 0;
						// System.out.println("-" + (toFill-1+1));
						drawArrayCells();
					}

					minFilled = toFill;
					toFill++;
					moved = true;

				}

			if (!moved)
			{

				toFill = 0;
				minFilled = -1;

				if (line[toFill] == 1)
					return;
			}

			fillCell();

		}
	}

	private static void drawArrayCells()
	{

		String s = "|";

		for (int i : line)
		{
			if (i == 0)
				s += " ";
			if (i == 1)
				s += ".";
		}

		System.out.println(s + "|");

	}

	public static long[] splitInto2withPower(long n)
	{

		ArrayList<Long> array = new ArrayList<Long>();
		long left = n;

		while (true)
		{

			long i = 0;
			long temp = 2;
			while (true)
			{
				i++;
				temp *= 2;
				if (temp > left)
				{
					temp = Math.round(temp / 2);
					break;
				}

			}
			array.add(i);
			left = left - temp;

			// System.out.println(i);
			// System.out.println(left);

			if (left < 2)
				break;

		}

		array.add(left);

		return arrayListToInt(array);
	}

	public static long[] arrayListToInt(ArrayList<Long> list)
	{

		long[] result = new long[list.size()];

		for (int i = 0; i < list.size(); i++)
		{

			result[i] = list.get(i);
		}

		return result;
	}

	public static double calcNPow2Pow(double n, long p)
	{

		double result = n;

		for (long i = 0; i < p; i++)
		{
			result *= result;
		}

		return result;
	}

	public static double calcPow(double n, long p)
	{

		long[] powArray = splitInto2withPower(p);
		double result = 1;

		for (int i = 0; i < (powArray.length - 1); i++)
		{

			result *= calcNPow2Pow(n, powArray[i]);
		}

		result *= Math.pow(n, powArray[powArray.length - 1]);

		return result;

	}

	public static boolean odd(long n)
	{
		if (n % 2 != 0)
			return true;
		return false;
	}

	public static double calcPow2(double a, long p)
	{

		if (p == 0)
			return 1;
		if (odd(p))
			return a * calcPow2(a * a, (long) (p / 2));
		else
			return calcPow2(a * a, (long) (p / 2));
	}

	private static int maxDiv = 0;
	private static int div = 0;

	public static ArrayList<double[]> createSelfLikeFlake(int n)
	{

		maxDiv = n;
		div = 0;
		ArrayList<double[]> points = new ArrayList<double[]>();

		double[] pair1 =
		{ 0, 0 };
		double[] pair5 =
		{ 5, 0 };

		points.add(pair1);
		points.add(pair5);

		divideLine(pair1, pair5, points, 0);

		// System.out.println(points.size());

		return points;
	}

	private static void divideLine(double[] pair1, double[] pair5, ArrayList<double[]> points, int level)
	{

		if (level >= maxDiv)
			return;

		div++;

		double x1 = pair1[0];
		double y1 = pair1[1];

		double x5 = pair5[0];
		double y5 = pair5[1];

		double dx = x5 - x1;
		double dy = y5 - y1;
		double dist = (double) Math.sqrt(dx * dx + dy * dy);

		double x2 = dx / 3 + x1;
		double y2 = dy / 3 + y1;

		double x4 = dx * 2 / 3 + x1;
		double y4 = dy * 2 / 3 + y1;

		double b = dist / 3 * Math.sqrt(3) / 2;

		double x3 = (x2 + x4) / 2 - b * (y4 - y2) / b;

		double y3 = (y2 + y4) / 2 + b * (x4 - x2) / b;

		double[] pair2 = new double[2];
		pair2[0] = x2;
		pair2[1] = y2;

		double[] pair3 = new double[2];
		pair3[0] = x3;
		pair3[1] = y3;

		double[] pair4 = new double[2];
		pair4[0] = x4;
		pair4[1] = y4;

		// System.out.println(points.size());
		points.add(points.indexOf(pair1) + 1, pair2);
		points.add(points.indexOf(pair2) + 1, pair3);
		points.add(points.indexOf(pair3) + 1, pair4);

		// System.out.println(points.size());

		int nextLevel = ++level;
		divideLine(pair1, pair2, points, nextLevel);
		divideLine(pair2, pair3, points, nextLevel);
		divideLine(pair3, pair4, points, nextLevel);
		divideLine(pair4, pair5, points, nextLevel);

	}

	public static ArrayList<double[]> drawSelfLikeTriangle(int n)
	{

		ArrayList<double[]> points = new ArrayList<double[]>();

		maxDiv = n;
		// points.add();

		double[] point1 =
		{ 0, 0 };
		double[] point2 =
		{ 5, 8.7 };
		double[] point3 =
		{ 10, 0 };
		double[] cut =
		{ Double.NaN, Double.NaN };

		points.add(point1);
		points.add(point2);
		points.add(point3);
		points.add(point1);
		points.add(cut);

		drawTriangle(point1, point2, point3, points, 0);

		return points;

	}

	private static void drawTriangle(double[] point1, double[] point2, double[] point3, ArrayList<double[]> points, int level)
	{

		if (level >= maxDiv)
			return;

		double b = (point3[0] - point1[0]) / 4;
		double c = (point3[0] - point1[0]) / 2 * Math.sqrt(3) / 2;

		double[] point4 =
		{ (point1[0] + b), (point1[1] + c) };
		double[] point5 =
		{ point3[0] - b, point3[1] + c };
		double[] point6 =
		{ point2[0], point1[1] };
		double[] cut =
		{ Double.NaN, Double.NaN };

		points.add(point4);
		points.add(point5);
		points.add(point6);
		points.add(point4);
		points.add(cut);

		int nextLevel = ++level;

		drawTriangle(point1, point4, point6, points, nextLevel);
		drawTriangle(point4, point2, point5, points, nextLevel);
		drawTriangle(point6, point5, point3, points, nextLevel);
	}

	public static void intBackward(int n, int fin)
	{
		System.out.println(n);
		if (n < fin)
			intBackward(++n, fin);
	}

	public static ArrayList<double[]> drawDragonCurve(int n)
	{

		maxDiv = n;

		ArrayList<double[]> points = new ArrayList<double[]>();

		double[] point1 =
		{ 0, 0 };
		double[] point2 =
		{ 0, 1 };

		points.add(point1);
		points.add(point2);

		rotateCurve(points, 0);

		return points;
	}

	private static void rotateCurve(ArrayList<double[]> points, int level)
	{

		if (level >= maxDiv)
			return;

		double[] pivot = points.get(points.size() - 1);

		int nPoints = points.size() - 2;

		for (int i = nPoints; i >= 0; i--)
		{

			double xNew = pivot[0] - (pivot[1] - points.get(i)[1]);
			double yNew = pivot[1] + (pivot[0] - points.get(i)[0]);

			double[] newPoint =
			{ xNew, yNew };
			points.add(newPoint);
		}

		rotateCurve(points, ++level);
	}

	public static double calcE(int ac)
	{

		double result = 0;

		for (int i = 0; i < ac; i++)
		{
			result += 1 / factorial(i);
		}

		return result;
	}

	public static double factorial(int n)
	{

		double result = 1;
		for (int i = 1; i <= n; i++)
		{
			result *= i;
		}

		return result;
	}

}
