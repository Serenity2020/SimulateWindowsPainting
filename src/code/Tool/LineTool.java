package code.Tool;

import java.awt.Graphics;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.lang.model.SourceVersion;

import code.ViewFrame;

public class LineTool extends AbstractTool implements Tool{
	private static Tool tool = null;
	private LineTool(ViewFrame frame){
		super(frame);
	}
	
	public static Tool getInstance(ViewFrame frame){
		if(tool == null){
			tool = (Tool)new LineTool(frame);
		}
		return tool;
	}
	
	public void draw(Graphics g,int x1,int y1,int x2,int y2){
		g.drawLine(x1, y1, x2, y2);
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
