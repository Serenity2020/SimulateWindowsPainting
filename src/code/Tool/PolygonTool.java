package code.Tool;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.lang.model.SourceVersion;

import code.ViewFrame;

public class PolygonTool extends AbstractTool implements Tool{
	private int firstX = -1;
	private int firstY = -1;
	private int lastX = -1;
	private int lastY = -1;
	
	private static Tool tool = null;
	
	private PolygonTool(ViewFrame frame){
		super(frame);
	}
	
	public static Tool getInstance(ViewFrame frame){
		if(tool == null){
			tool = (Tool)new PolygonTool(frame);
		}
		return tool;
	}
	
	public void mouseReleased(MouseEvent e){
		int pressX = getPressX();
		int pressY = getPressY();
		
		//找到按下去的点pressX和pressY之后，调用父方法画直线
		super.mouseReleased(e);
		//设置第一个和最后一个点的坐标
		if(firstX == -1){
			firstX = pressX;
			firstY = pressY;
		}
		lastX = e.getX();
		lastY = e.getY();
	}
	
	public void mouseClicked(MouseEvent e){
		Graphics g = getFrame().getBufferedImage().getGraphics();
		if(e.getClickCount() == 2 && firstX > 0 && e.getX() > 0
				&& e.getX()<AbstractTool.drawWidth && e.getY() > 0 && e.getY() < AbstractTool.drawHeight){
			g.setColor(AbstractTool.color);
			g.drawImage(getFrame().getBufferedImage(), 0, 0, AbstractTool.drawWidth, AbstractTool.drawHeight, null);
			draw(g, 0, 0, firstX, firstY);
			draw(g, 0, 0, lastX, lastY);
			setPressX(-1);
			setPressY(-1);
			firstX = -1;
			firstY = -1;
			lastX = -1;
			lastY = -1;
			getFrame().getDrawSpace().repaint();
		}
	}

	public void draw(Graphics g, int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		int x = lastX > 0 ? lastX : getPressX();
		int y = lastY > 0 ? lastY :getPressY();
		g.drawLine(x, y, x2, y2);
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
