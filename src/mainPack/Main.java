package mainPack;

import graph.GraphicsFrame;

public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		GraphicsFrame gf = new GraphicsFrame();
		gf.enableMouse();
		gf.addSliders();
		gf.showGrid(true);

		ControlPanelInterface cpi = new ControlPanelInterface();
		@SuppressWarnings("unused")
		GraphController cp = new GraphController(cpi, gf);
	}

}
