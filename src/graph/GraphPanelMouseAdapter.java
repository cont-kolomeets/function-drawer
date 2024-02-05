package graph;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraphPanelMouseAdapter extends MouseAdapter
{
	private float x = 0;
	private float y = 0;
	private float dx = 0;
	private float dy = 0;
	private GraphPanel graphPanel;
	private GController C;

	public GraphPanelMouseAdapter(GraphPanel graphPanel, GController C)
	{
		this.graphPanel = graphPanel;
		this.C = C;
	}
	
	public void mouseMoved(MouseEvent e)
	{
		double[] point = graphPanel.globalToLocal(e.getX(), e.getY());
		String xString = StringUtil.cutToFixed("" + point[0], Constants.MOUSE_LABEL_PRECISION);
		String yString = StringUtil.cutToFixed("" + -point[1], Constants.MOUSE_LABEL_PRECISION);
		
		graphPanel.mouseLabel.setText("x:" + xString + " y:" + yString);
	}

	public void mousePressed(MouseEvent e)
	{
		x = e.getX();
		y = e.getY();
	}

	public void mouseDragged(MouseEvent e)
	{
		dx = x - e.getX();
		dy = y - e.getY();

		x = e.getX();
		y = e.getY();

		graphPanel.dInfo.xFrom += dx / graphPanel.dInfo.xScale;
		graphPanel.dInfo.xTo += dx / graphPanel.dInfo.xScale;
		graphPanel.dInfo.yFrom -= dy / graphPanel.dInfo.yScale;
		graphPanel.dInfo.yTo -= dy / graphPanel.dInfo.yScale;

		C.repaintGraphPanel();
	}
}
