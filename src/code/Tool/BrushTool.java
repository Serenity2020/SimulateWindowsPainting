package code.Tool;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.lang.model.SourceVersion;

import code.ViewFrame;

public  class BrushTool extends AbstractTool implements Tool{
	private static Tool tool = null;
	
	public BrushTool(ViewFrame frame) {
		super(frame,"img/brushcursor.gif");
		// TODO Auto-generated constructor stub
	}
	
	public static Tool getInstance(ViewFrame frame){
		if(tool == null){
			tool = (Tool) new BrushTool(frame);
		}
		return tool;
	}
	

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseDragged(e);
		Graphics g = getFrame().getBufferedImage().getGraphics();
		int x = 0;
		int y = 0;
		
		//画笔大小
		int size = 4;
		if(getPressX() > 0 && getPressY() > 0 && e.getX() <AbstractTool.drawWidth && e.getY() < AbstractTool.drawHeight){
			g.setColor(AbstractTool.color);
			
			x = e.getX() - getPressX() > 0 ? getPressX() : e.getX();
			y = e.getY() - getPressY() > 0 ? getPressY() : e.getY();//这两步是寻找画笔起点x和y。往后画，就取新的x和y。往前画，取旧的x和y
			
			g.fillRect(x-size,y-size,Math.abs(e.getX()-getPressX())+size,Math.abs(e.getY()-getPressY())+size);
			
			setPressX(e.getX());
			setPressY(e.getY());
			
			getFrame().getDrawSpace().repaint();
		}
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
