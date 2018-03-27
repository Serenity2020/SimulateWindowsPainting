package code.Tool;

import java.awt.Graphics;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.lang.model.SourceVersion;

import code.ViewFrame;

public class RectTool extends AbstractTool implements Tool{

	private static Tool tool = null;
	public RectTool(ViewFrame frame) {
		super(frame);
		// TODO Auto-generated constructor stub
	}
	
	public static Tool getInstance(ViewFrame frame){
		if(tool == null){
			tool = (Tool) new RectTool(frame);
		}
		return tool;
	}
	

	public void draw(Graphics g, int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		//计算起点
		int x = x2 > x1 ? x1 : x2;
		int y = y2 > y1 ? y1 : y2;
		//画矩形
		g.drawRect(x, y, Math.abs(x2-x1), Math.abs(y2-y1));
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
