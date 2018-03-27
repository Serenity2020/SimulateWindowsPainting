package code.Tool;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.lang.model.SourceVersion;

import code.ViewFrame;

public class PencilTool extends AbstractTool implements Tool{
	private static Tool tool = null;
	
	private PencilTool(ViewFrame frame){
		super(frame,"img/pencilcursor.gif");
	}
	
	public static Tool getInstance(ViewFrame frame){
		if(tool == null){
			tool = new PencilTool(frame);
		}
		return (Tool) tool;
	}
	
	public void mouseDragged(MouseEvent e){
		super.mouseDragged(e);
		Graphics g = getFrame().getBufferedImage().getGraphics();
		if(getPressX() > 0 && getPressY() > 0){
			g.setColor(AbstractTool.color);
			g.drawLine(getPressX(), getPressY(), e.getX(), e.getY());
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
