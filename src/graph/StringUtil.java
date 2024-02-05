package graph;

public class StringUtil
{
	public static String cutToFixed(String s, int nChars)
	{
		String newS = s.replace(",", ".");
		
		if(newS.indexOf('.') == -1)
			return s;

		String partBeforeDot = s.substring(0, s.indexOf('.') + 1);
		String partAfterDot = s.substring(s.indexOf('.') + 1);

		if(partAfterDot.length() <= nChars)
			return s;
		
		if(partAfterDot.indexOf('E') == -1)
			return  partBeforeDot + partAfterDot.substring(0, nChars);
		else
		{
			String partBeforeE = partAfterDot.substring(0, partAfterDot.indexOf('E') + 1);
			String partAfterE = partAfterDot.substring(partAfterDot.indexOf('E') + 1);
			
			if(partBeforeE.length() <= nChars)
				return s;
			else
				return partBeforeDot + partBeforeE.substring(0, nChars) + "E" + partAfterE;	
		}
	}
}
