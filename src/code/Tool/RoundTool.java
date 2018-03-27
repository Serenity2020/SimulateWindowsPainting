package code.Tool;

import java.awt.Graphics;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.lang.model.SourceVersion;

import code.ViewFrame;

public class RoundTool extends AbstractTool implements Tool{
	private static Tool tool = null;
	
	private RoundTool(ViewFrame frame){
		super(frame);
	}
	
	public static Tool getInstance(ViewFrame frame){
		if(tool == null){
			tool = (Tool)new RoundTool(frame);
		}
		return tool;
	}
	
	public void draw(Graphics g,int x1,int y1,int x2,int y2){
		//º∆À„∆µ„
		int x = x2 > x1 ? x1 : x2;
		int y = y2 > y1 ? y1 : y2;
		//ª≠Õ÷‘≤
		g.drawOval(x,y, Math.abs(x1-x2), Math.abs(y1-y2));
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
