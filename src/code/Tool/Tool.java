package code.Tool;

import java.awt.event.MouseEvent;

public interface Tool {
	//工具类型
	public static final String ARROW_TOOL = "ArrowTool";
	public static final String PENCIL_TOOL = "PencilTool";
	public static final String BRUSH_TOOL = "BrushTool";
	public static final String CUT_TOOL = "CutTool";
	public static final String ERASER_TOOL = "EraserTool";
	public static final String LINE_TOOL = "LineTool";
	public static final String RECT_TOOL = "RectTool";
	public static final String POLYGON_TOOL = "PolygonTool";
	public static final String ROUND_TOOL = "RoundTool";
	public static final String ROUNDRECT_TOOL = "RoundRectTool";
	public static final String ATOMIZER_TOOL = "AtomizerTool";
	public static final String COLORPICKED_TOOL = "ColorPickedTool";
	
	public void mouseDragged(MouseEvent e);
	
	public void mouseMoved(MouseEvent e);
	
	public void mouseReleased(MouseEvent e);
	
	public void mousePressed(MouseEvent e);
	
	public void mouseClicked(MouseEvent e);
}
