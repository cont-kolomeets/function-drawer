package mainPack;
import graph.GraphicsFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphController
{
	ControlPanelInterface cpi;
	GraphicsFrame gf;

	public GraphController(ControlPanelInterface cpi, GraphicsFrame gf)
	{
		this.cpi = cpi;
		this.gf = gf;

		addListeners();
	}

	public void addListeners()
	{
		cpi.runButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drawGraph();
			}
		});
	}

	public void drawGraph()
	{
		try
		{
			float xFrom = Float.parseFloat(cpi.xFromField.getText());
			float xTo = Float.parseFloat(cpi.xToField.getText());
			float yFrom = Float.parseFloat(cpi.yFromField.getText());
			float yTo = Float.parseFloat(cpi.yToField.getText());
			gf.setExtent(xFrom, xTo, yFrom, yTo);

		} catch (NumberFormatException e)
		{
			cpi.outputMessageLabel.setText("Can't read range numbers!");
			return;
		}

		String ex = cpi.functionField.getText();

		if (getNumberForSpecialFeature(ex, "dragon") != -1)
		{
			gf.clearExpression();
			gf.setPoints(MathUtils.drawDragonCurve(getNumberForSpecialFeature(ex, "dragon")), true);
			cpi.outputMessageLabel.setText("Dragon curve is drawn.");
			gf.showFrame();
			return;
		}

		if (getNumberForSpecialFeature(ex, "flake") != -1)
		{
			gf.clearExpression();
			gf.setPoints(MathUtils.drawSelfLikeTriangle(getNumberForSpecialFeature(ex, "flake")), true);
			cpi.outputMessageLabel.setText("Flake curve is drawn.");
			gf.showFrame();
			return;
		}

		gf.setExpression(ex);
		gf.showFrame();
		cpi.outputMessageLabel.setText("Graph is drawn.");
	}

	private int getNumberForSpecialFeature(String ex, String feature)
	{
		if (ex.length() > feature.length() && ex.substring(0, feature.length()).equals(feature))
			return Integer.parseInt(ex.substring(feature.length() + 1, ex.length() - 1));

		return -1;
	}
}
