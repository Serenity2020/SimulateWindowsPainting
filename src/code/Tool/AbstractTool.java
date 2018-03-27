package code.Tool;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.tools.Tool;

import code.ImageService;
import code.MyImage;
import code.ViewFrame;

public abstract class AbstractTool implements Tool{
	private ViewFrame frame = null;
	public static int drawWidth = 0;
	public static int drawHeight = 0;
	private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);//定义默认鼠标指针
	private int pressX = -1;//按下鼠标的x坐标
	private int pressY = -1;//按下鼠标的y坐标
	public static Color color = Color.BLACK;
	
	public AbstractTool(ViewFrame frame){
		this.frame = frame;
		AbstractTool.drawHeight = frame.getBufferedImage().getHeight();
		AbstractTool.drawWidth = frame.getBufferedImage().getWidth();
	}
	
	public AbstractTool(ViewFrame frame,String path){
		this(frame);
		//创建工具鼠标图形
		this.defaultCursor = ImageService.createCursor(path);
	}
	
	public ViewFrame getFrame(){
		return this.frame;
	}
	
	public Cursor getDefaultCursor(){
		return this.defaultCursor;
	}
	
	public void setDefaultCursor(Cursor cursor){
		this.defaultCursor = cursor;
	}
	
	public void setPressX(int x){
		this.pressX = x;
	}
	
	public void setPressY(int y){
		this.pressY = y;
	}
	
	public int getPressX(){
		return this.pressX;//返回上次鼠标按下时的x坐标
	}
	
	public int getPressY(){
		return this.pressY;//返回上次鼠标按下时的y坐标
	}
	
	public void mouseDragged(MouseEvent e){
		dragBorder(e);
		//画图
		Graphics g = getFrame().getDrawSpace().getGraphics();
		createShape(e,g);
	}
	
	public void mouseMoved(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		
		//获取默认鼠标指针
		Cursor cursor = getDefaultCursor();
		//如果鼠标指针在右下角
		if(x > AbstractTool.drawWidth - 4 && x<AbstractTool.drawWidth + 4 
				&& y > AbstractTool.drawHeight - 4 && y < AbstractTool.drawHeight + 4){
			//鼠标指针改为右下拖动状态
			cursor = new Cursor(Cursor.NW_RESIZE_CURSOR);
		}
		
		if(x > AbstractTool.drawWidth - 4 && x < AbstractTool.drawWidth + 4
				&& y > (int) AbstractTool.drawHeight/2 - 4 && y < (int)AbstractTool.drawHeight/2 + 4){
			cursor = new Cursor(Cursor.W_RESIZE_CURSOR);
		}
		
		if(y > AbstractTool.drawHeight - 4 && y < AbstractTool.drawHeight + 4 
				&& x > (int) AbstractTool.drawWidth/2 - 4 && (int) x > AbstractTool.drawWidth/2 + 4){
			cursor = new Cursor(Cursor.S_RESIZE_CURSOR);
		}
		
		//设置鼠标指针类型
		getFrame().getDrawSpace().setCursor(cursor);
	}
	
	public void mouseReleased(MouseEvent e){
		//画图
		Graphics g = getFrame().getBufferedImage().getGraphics();
		createShape(e,g);
		
		//把pressX和pressY设置为初始值
		setPressX(-1);
		setPressY(-1);
		
		//重绘图
		getFrame().getDrawSpace().repaint();
	}
	
	private void createShape(MouseEvent e,Graphics g){
		//如果位置在画布内
		if(getPressX() > 0 && getPressY() > 0 && e.getX() > 0 && e.getX() < AbstractTool.drawWidth
				&& e.getY() > 0 && e.getY() < AbstractTool.drawHeight){
			g.drawImage(getFrame().getBufferedImage(), 0, 0,AbstractTool.drawWidth,AbstractTool.drawHeight,null);
			
			//设置颜色
			g.setColor(AbstractTool.color);
			getFrame().getBufferedImage().setIsSaved(false);
			
			//画图形
			draw(g,getPressX(),getPressY(),e.getX(),e.getY());
		}
	}
	
	public void mousePressed(MouseEvent e){
		if(e.getX() > 0 && e.getX() < AbstractTool.drawWidth && e.getY() > 0 && e.getY() < AbstractTool.drawHeight){
			setPressX(e.getX());
			setPressY(e.getY());
		}
	}
	
	/*
	 * mouseClicked方法用于捕捉鼠标单击动作
	 * */
	public void mouseClicked(MouseEvent e){
		
	}
	
	public void draw(Graphics g,int x1,int y1,int x2,int y2){
		
	}
	
	private void dragBorder(MouseEvent e){
		getFrame().getBufferedImage().setIsSaved(false);
		int cursorType = getFrame().getDrawSpace().getCursor().getType();
		
		//获取鼠标现在的x与y坐标
		int x = cursorType == Cursor.S_RESIZE_CURSOR ? AbstractTool.drawWidth:e.getX();
		int y = cursorType == Cursor.W_RESIZE_CURSOR ? AbstractTool.drawHeight:e.getY();
		
		MyImage img = null;
		
		//如果鼠标指针是拖动状态
		if((cursorType == Cursor.NW_RESIZE_CURSOR || cursorType == Cursor.W_RESIZE_CURSOR || cursorType == Cursor.S_RESIZE_CURSOR)
				&& (x>0 && y>0)){
			img = new MyImage(x, y, BufferedImage.TYPE_INT_RGB);
			Graphics g = img.getGraphics();
			g.setColor(Color.WHITE);
			g.drawImage(getFrame().getBufferedImage(), 0, 0, AbstractTool.drawWidth,AbstractTool.drawHeight,null);
			
			getFrame().setBufferedImage(img);
			
			//设置画布的大小
			AbstractTool.drawWidth = x;
			AbstractTool.drawHeight = y;
			
			//设置viewport
			ImageService.setViewport(frame.getScroll(),frame.getDrawSpace(),x,y);
		}
	}
}
