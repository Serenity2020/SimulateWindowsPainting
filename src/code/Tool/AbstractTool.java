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
	private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);//����Ĭ�����ָ��
	private int pressX = -1;//��������x����
	private int pressY = -1;//��������y����
	public static Color color = Color.BLACK;
	
	public AbstractTool(ViewFrame frame){
		this.frame = frame;
		AbstractTool.drawHeight = frame.getBufferedImage().getHeight();
		AbstractTool.drawWidth = frame.getBufferedImage().getWidth();
	}
	
	public AbstractTool(ViewFrame frame,String path){
		this(frame);
		//�����������ͼ��
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
		return this.pressX;//�����ϴ���갴��ʱ��x����
	}
	
	public int getPressY(){
		return this.pressY;//�����ϴ���갴��ʱ��y����
	}
	
	public void mouseDragged(MouseEvent e){
		dragBorder(e);
		//��ͼ
		Graphics g = getFrame().getDrawSpace().getGraphics();
		createShape(e,g);
	}
	
	public void mouseMoved(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		
		//��ȡĬ�����ָ��
		Cursor cursor = getDefaultCursor();
		//������ָ�������½�
		if(x > AbstractTool.drawWidth - 4 && x<AbstractTool.drawWidth + 4 
				&& y > AbstractTool.drawHeight - 4 && y < AbstractTool.drawHeight + 4){
			//���ָ���Ϊ�����϶�״̬
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
		
		//�������ָ������
		getFrame().getDrawSpace().setCursor(cursor);
	}
	
	public void mouseReleased(MouseEvent e){
		//��ͼ
		Graphics g = getFrame().getBufferedImage().getGraphics();
		createShape(e,g);
		
		//��pressX��pressY����Ϊ��ʼֵ
		setPressX(-1);
		setPressY(-1);
		
		//�ػ�ͼ
		getFrame().getDrawSpace().repaint();
	}
	
	private void createShape(MouseEvent e,Graphics g){
		//���λ���ڻ�����
		if(getPressX() > 0 && getPressY() > 0 && e.getX() > 0 && e.getX() < AbstractTool.drawWidth
				&& e.getY() > 0 && e.getY() < AbstractTool.drawHeight){
			g.drawImage(getFrame().getBufferedImage(), 0, 0,AbstractTool.drawWidth,AbstractTool.drawHeight,null);
			
			//������ɫ
			g.setColor(AbstractTool.color);
			getFrame().getBufferedImage().setIsSaved(false);
			
			//��ͼ��
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
	 * mouseClicked�������ڲ�׽��굥������
	 * */
	public void mouseClicked(MouseEvent e){
		
	}
	
	public void draw(Graphics g,int x1,int y1,int x2,int y2){
		
	}
	
	private void dragBorder(MouseEvent e){
		getFrame().getBufferedImage().setIsSaved(false);
		int cursorType = getFrame().getDrawSpace().getCursor().getType();
		
		//��ȡ������ڵ�x��y����
		int x = cursorType == Cursor.S_RESIZE_CURSOR ? AbstractTool.drawWidth:e.getX();
		int y = cursorType == Cursor.W_RESIZE_CURSOR ? AbstractTool.drawHeight:e.getY();
		
		MyImage img = null;
		
		//������ָ�����϶�״̬
		if((cursorType == Cursor.NW_RESIZE_CURSOR || cursorType == Cursor.W_RESIZE_CURSOR || cursorType == Cursor.S_RESIZE_CURSOR)
				&& (x>0 && y>0)){
			img = new MyImage(x, y, BufferedImage.TYPE_INT_RGB);
			Graphics g = img.getGraphics();
			g.setColor(Color.WHITE);
			g.drawImage(getFrame().getBufferedImage(), 0, 0, AbstractTool.drawWidth,AbstractTool.drawHeight,null);
			
			getFrame().setBufferedImage(img);
			
			//���û����Ĵ�С
			AbstractTool.drawWidth = x;
			AbstractTool.drawHeight = y;
			
			//����viewport
			ImageService.setViewport(frame.getScroll(),frame.getDrawSpace(),x,y);
		}
	}
}
