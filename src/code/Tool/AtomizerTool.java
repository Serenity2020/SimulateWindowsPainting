package code.Tool;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.Set;

import javax.lang.model.SourceVersion;



import code.ViewFrame;

public class AtomizerTool extends AbstractTool implements Tool{
	private static Tool tool = null;
	
	private AtomizerTool(ViewFrame frame){
		super(frame,"img/atomizercursor.gif");
	}
	
	public static Tool getInstance(ViewFrame frame){
		if(tool == null){
			tool =  (Tool) new AtomizerTool(frame);
		}
		return tool;
	}
	
	public void mousePressed(MouseEvent e){
		if(e.getX() > 0 && e.getX() < AbstractTool.drawWidth && e.getY() > 0 && e.getY() < AbstractTool.drawHeight){
			setPressX(e.getX());
			setPressY(e.getY());
			Graphics g = getFrame().getBufferedImage().getGraphics();
			draw(e, g);
		}
	}
	
	public void mouseDragged(MouseEvent e){
		super.mouseDragged(e);
		Graphics g = getFrame().getBufferedImage().getGraphics();
		draw(e, g);
	}
	
	public void draw(MouseEvent e,Graphics g){
		int x = 0;
		int y = 0;
		//标枪大小
		int size = 8;
		//喷枪点数
		int count = 10;
		if(getPressX()>0 && getPressY() > 0 &&
				e.getX() < AbstractTool.drawWidth && e.getY() < AbstractTool.drawHeight){
			g.setColor(AbstractTool.color);
			for(int i = 0;i<count;i++){
				x = new Random().nextInt(size) + 1;
				y = new Random().nextInt(size) + 1;
				g.fillRect(e.getX() + x,e.getY() + y,1,1);
			}
			
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
