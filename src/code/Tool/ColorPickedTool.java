package code.Tool;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.lang.model.SourceVersion;

import code.ViewFrame;

public class ColorPickedTool extends AbstractTool implements Tool{
	private static Tool tool = null;
	private ColorPickedTool(ViewFrame frame){
		super(frame,"img/colorcursor.gif");
	}
	
	public static Tool getInstance(ViewFrame frame){
		if(tool == null){
			tool = (Tool) new ColorPickedTool(frame);
		}
		return tool;
	}
	
	public void mousePressed(MouseEvent e){
		if(e.getX() > 0 && e.getY() > 0){
			if(e.getX() < AbstractTool.drawWidth && e.getY() < drawHeight){
				setPressX(e.getX());
				setPressY(e.getY());
				
				/*
				 * 利用getRGB()方法返回默认的rgb
				 * Returns an integer pixel in the default RGB color model (TYPE_INT_ARGB) and default sRGB colorspace.
				 *  Color conversion takes place if this default model does not match the image ColorModel. 
				 *  There are only 8-bits of precision for each color component in the returned data when using this method*/
				int rgb = getFrame().getBufferedImage().getRGB(e.getX(),e.getY());
				// 前8位
				int int8 = (int) Math.pow(2, 8);
				// 前16位
				int int16 = (int) Math.pow(2, 16);
				// 前24位
				int int24 = (int) Math.pow(2, 24);
				// 分别取0-7位,8-15位,16-23位
				int r = (rgb & (int24 - int16)) >> 16;
				int g = (rgb & (int16 - int8)) >> 8;
				int b = (rgb & (int8 - 1));
				
				//设置颜色
				AbstractTool.color = new Color(r,g,b);
			}else{
				try{
					AbstractTool.color = new Robot().getPixelColor(e.getX(),e.getY());
				}catch(AWTException ae){
					ae.printStackTrace();
				}
			}
			getFrame().getCurrentColorPanel().setBackground(AbstractTool.color);
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
