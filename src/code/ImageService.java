package code;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import code.Tool.AbstractTool;

public class ImageService {
	private ImageFileChooser fileChooser = new ImageFileChooser();
	
	//获取屏幕分辨率
	public Dimension getScreenSize(){
		Toolkit dt = Toolkit.getDefaultToolkit();
		return dt.getScreenSize();
	}
	
	public void initDrawSpace(ViewFrame frame){
		Graphics g = frame.getBufferedImage().getGraphics();
		Dimension d = frame.getDrawSpace().getPreferredSize();
		int drawWidth = (int) d.getWidth();
		int drawHeight = (int) d.getHeight();
		g.setColor(Color.WHITE);
		g.fillRect(0,0,drawWidth,drawHeight);
	}
	
	public void repaint(Graphics g,BufferedImage bufferedImage){
		int drawWidth = bufferedImage.getWidth();
		int drawHeight = bufferedImage.getHeight();
		Dimension screenSize = getScreenSize();
		
		//设置非绘画区的颜色
		g.setColor(Color.GRAY);
		g.fillRect(0,0,(int)screenSize.getWidth()*10,(int)screenSize.getHeight()*10);
		//设置拖动点颜色
		g.setColor(Color.BLUE);
		g.fillRect(drawWidth,drawHeight,4,4);
		g.fillRect(drawWidth,(int)drawHeight/2-2,4,4);
		g.fillRect((int)drawWidth/2 - 2,drawHeight,4,4);
		
		//画出缓冲图片
		g.drawImage(bufferedImage, 0, 0, drawWidth,drawHeight,null);
	}
	
	//创建鼠标图形
	public static Cursor createCursor(String path){
		Image cursorImage = Toolkit.getDefaultToolkit().createImage(path);
		return Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(10,10), "mycursor");
	}
	
	/*
	 * JViewport很少在JScrollPane之外使用。
	 * 通常情况下他位于JScrollPane的中间并且使用ViewportLayout管理器来响应在小空间内显示大Component的定位请求。
	 * 除了位于JScrollPane的中间以外，JViewport也可以用于JScrollPane的行头与列头。
	 */
	public static void setViewport(JScrollPane scroll,JPanel panel,int width,int height){
		JViewport viewport = new JViewport();
		
		panel.setPreferredSize(new Dimension(width+50,height+50));
		
		//JViewport只有一个无参数的构造函数：public JViewport()。一旦我们创建了JViewport，我们可以通过setView(Component)向其中添加组件。
		viewport.setView(panel);
		
		scroll.setViewport(viewport);
	}
	
	public void save(boolean b,ViewFrame frame){
		if(b){
			//如果选择保存
			if(fileChooser.showSaveDialog(frame) == ImageFileChooser.APPROVE_OPTION){
				//获取当前路径
				File currentDirectory = fileChooser.getCurrentDirectory();
				//获取文件名
				String fileName = fileChooser.getSelectedFile().getName();
				//获取后缀名
				String suf = fileChooser.getSuf();
				//组合保存路径
				String savePath = currentDirectory + "\\" + fileName + "." + suf;
				
				try{
					ImageIO.write(frame.getBufferedImage(),suf,new File(savePath));
				}catch(java.io.IOException ie){
					ie.printStackTrace();
				}
				
				//设置保存后的窗口标题
				frame.setTitle(fileName + "." + suf + "-画图");
				
				//已保存
				frame.getBufferedImage().setIsSaved(true);
			}
		}else if(! frame.getBufferedImage().isSaved()){
			JOptionPane option = new JOptionPane();
			
			int checked = option.showConfirmDialog(frame, "保存改动？", "画图", option.YES_NO_OPTION, option.WARNING_MESSAGE);
			
			if(checked == option.YES_OPTION){
				save(true, frame);
			}
		}
	}
	
	public void open(ViewFrame frame){
		save(false, frame);
		
		if(fileChooser.showOpenDialog(frame) == ImageFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();//获取选择的文件
			
			fileChooser.setCurrentDirectory(file);//设置当前的文件夹
			
			BufferedImage image = null;
			
			try{
				//从文件读取图片
				image = ImageIO.read(file);
			}catch(java.io.IOException e){
				e.printStackTrace();
				return;
			}
			
			int width = image.getWidth();
			int height = image.getHeight();
			AbstractTool.drawWidth = width;
			AbstractTool.drawHeight = height;
			
			MyImage myImage = new MyImage(width, height, BufferedImage.TYPE_INT_RGB);
			myImage.getGraphics().drawImage(image, 0, 0, width,height,null);
			frame.setBufferedImage(myImage);
			frame.getDrawSpace().repaint();
			
			ImageService.setViewport(frame.getScroll(),frame.getDrawSpace(),width,height);
			
			//设置保存后的窗口标题
			frame.setTitle(fileChooser.getSelectedFile().getName() + "- 画图");
		}
	}
	
	public void createGraphics(ViewFrame frame){
		save(false, frame);
		
		int width = (int) getScreenSize().getWidth()/2;
		int height = (int) getScreenSize().getHeight()/2;
		AbstractTool.drawWidth = width;
		AbstractTool.drawHeight = height;
		
		//创建一个MyImage
		MyImage myImage = new MyImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = myImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		frame.setBufferedImage(myImage);
		
		//repaint
		frame.getDrawSpace().repaint();
		
		//重新设置viewport
		ImageService.setViewport(frame.getScroll(),frame.getDrawSpace(),width,height);
		
		//设置保存后的窗口标题
		frame.setTitle("未命名-画图");
	}
	
	public void editColor(ViewFrame frame){
		//获取颜色
		Color color = JColorChooser.showDialog(frame.getColorChooser(), "编辑颜色", Color.BLACK);
		color = color == null? AbstractTool.color : color;
		
		//设置工具的颜色
		AbstractTool.color = color;
		
		//设置目前显示的颜色
		frame.getCurrentColorPanel().setBackground(color);
	}
	
	public void exit(ViewFrame frame){
		save(false, frame);
		System.exit(0);
	}
	
	public void setVisible(JPanel panel){
		boolean b = panel.isVisible()? false:true;
		panel.setVisible(b);
	}
	
	public void menuDo(ViewFrame frame,String cmd){
		if(cmd.equals("编辑颜色")){
			editColor(frame);
		}
		
		if(cmd.equals("工具箱(T)")){
			setVisible(frame.getToolPanel());
		}
		
		if(cmd.equals("颜料盒(C)")){
			setVisible(frame.getColorPanel());
		}
		if(cmd.equals("新建(N)")){
			createGraphics(frame);
		}
		if(cmd.equals("打开(O)")){
			open(frame);
		}
		if(cmd.equals("保存(S)")){
			save(true, frame);
		}
		if(cmd.equals("退出(X)")){
			exit(frame);
		}
	}
}
