package code.Tool;

import java.util.HashMap;
import java.util.Map;

import code.ViewFrame;

public class ToolFactory {
	private static Map<String, Tool> toolMap = null;
	
	public static Tool getToolInstance(ViewFrame frame,String type){
		if(toolMap == null){
			toolMap = new HashMap<String,Tool>();
			toolMap.put(Tool.PENCIL_TOOL,PencilTool.getInstance(frame));
			toolMap.put(Tool.BRUSH_TOOL,BrushTool.getInstance(frame));
			toolMap.put(Tool.ERASER_TOOL,EraserTool.getInstance(frame));
			toolMap.put(Tool.LINE_TOOL,LineTool.getInstance(frame));
			toolMap.put(Tool.RECT_TOOL,RectTool.getInstance(frame));
			toolMap.put(Tool.POLYGON_TOOL,PolygonTool.getInstance(frame));
			toolMap.put(Tool.ROUND_TOOL,RoundTool.getInstance(frame));
			toolMap.put(Tool.ROUNDRECT_TOOL,RoundRectTool.getInstance(frame));
			toolMap.put(Tool.ATOMIZER_TOOL,AtomizerTool.getInstance(frame));
			toolMap.put(Tool.COLORPICKED_TOOL,ColorPickedTool.getInstance(frame));
		}
		return (Tool)toolMap.get(type);
	}
}
