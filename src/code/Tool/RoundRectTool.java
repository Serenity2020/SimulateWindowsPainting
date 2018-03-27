package code.Tool;

import java.awt.Graphics;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.lang.model.SourceVersion;

import code.ViewFrame;

public class RoundRectTool extends AbstractTool implements Tool{
	private static Tool tool = null;
	
	private RoundRectTool(ViewFrame frame){
		super(frame);
	}
	
	public static Tool getInstance(ViewFrame frame){
		if(tool == null){
			tool = (Tool)new RoundRectTool(frame);
		}
		return tool;
	}
	
	public void draw(Graphics g,int x1,int y1,int x2,int y2){
		//找出起点的x和y坐标
		int x = x2 > x1 ? x1 : x2;
		int y = y2 > y1 ? y1 : y2;
		//画出圆方形
		g.drawRoundRect(x, y, Math.abs(x2-x1), Math.abs(y2-y1), 20, 20);
	}

	@Override
	public int run(InputStream in, OutputStream out, OutputStream err, String... arguments) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<SourceVersion> getSourceVersions() {
		// TODO Auto-generated method stub
		return null;
	}
}
