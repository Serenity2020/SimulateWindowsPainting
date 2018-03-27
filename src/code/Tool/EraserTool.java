package code.Tool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.lang.model.SourceVersion;

import code.ViewFrame;

public class EraserTool extends AbstractTool implements Tool{
	private static Tool tool = null;
	
	private EraserTool(ViewFrame frame){
		super(frame,"img/erasercursor.gif");
	}
	
	public static Tool getInstance(ViewFrame frame){
		if(tool == null){
			tool = new EraserTool(frame);
		}
		return tool;
	}
	
	public void mouseDragged(MouseEvent e){
		super.mouseDragged(e);
		Graphics g = getFrame().getBufferedImage().getGraphics();
		int x = 0;
		int y = 0;
		
		//ÏðÆ¤²ÁÍ·´óÐ¡
		int size = 4;
		if(getPressX() > 0 && getPressY() > 0){
			g.setColor(Color.WHITE);
			x = e.getX() - getPressX() > 0 ? getPressX() : e.getX();
			y = e.getY() - getPressY() > 0 ? getPressY() : e.getY();
			
			g.fillRect(x - size,y - size,Math.abs(e.getX() - getPressX()) + size,Math.abs(e.getY() - getPressY()) + size);
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
