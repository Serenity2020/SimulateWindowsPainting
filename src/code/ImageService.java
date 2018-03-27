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
	
	//��ȡ��Ļ�ֱ���
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
		
		//���÷ǻ滭������ɫ
		g.setColor(Color.GRAY);
		g.fillRect(0,0,(int)screenSize.getWidth()*10,(int)screenSize.getHeight()*10);
		//�����϶�����ɫ
		g.setColor(Color.BLUE);
		g.fillRect(drawWidth,drawHeight,4,4);
		g.fillRect(drawWidth,(int)drawHeight/2-2,4,4);
		g.fillRect((int)drawWidth/2 - 2,drawHeight,4,4);
		
		//��������ͼƬ
		g.drawImage(bufferedImage, 0, 0, drawWidth,drawHeight,null);
	}
	
	//�������ͼ��
	public static Cursor createCursor(String path){
		Image cursorImage = Toolkit.getDefaultToolkit().createImage(path);
		return Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(10,10), "mycursor");
	}
	
	/*
	 * JViewport������JScrollPane֮��ʹ�á�
	 * ͨ���������λ��JScrollPane���м䲢��ʹ��ViewportLayout����������Ӧ��С�ռ�����ʾ��Component�Ķ�λ����
	 * ����λ��JScrollPane���м����⣬JViewportҲ��������JScrollPane����ͷ����ͷ��
	 */
	public static void setViewport(JScrollPane scroll,JPanel panel,int width,int height){
		JViewport viewport = new JViewport();
		
		panel.setPreferredSize(new Dimension(width+50,height+50));
		
		//JViewportֻ��һ���޲����Ĺ��캯����public JViewport()��һ�����Ǵ�����JViewport�����ǿ���ͨ��setView(Component)��������������
		viewport.setView(panel);
		
		scroll.setViewport(viewport);
	}
	
	public void save(boolean b,ViewFrame frame){
		if(b){
			//���ѡ�񱣴�
			if(fileChooser.showSaveDialog(frame) == ImageFileChooser.APPROVE_OPTION){
				//��ȡ��ǰ·��
				File currentDirectory = fileChooser.getCurrentDirectory();
				//��ȡ�ļ���
				String fileName = fileChooser.getSelectedFile().getName();
				//��ȡ��׺��
				String suf = fileChooser.getSuf();
				//��ϱ���·��
				String savePath = currentDirectory + "\\" + fileName + "." + suf;
				
				try{
					ImageIO.write(frame.getBufferedImage(),suf,new File(savePath));
				}catch(java.io.IOException ie){
					ie.printStackTrace();
				}
				
				//���ñ����Ĵ��ڱ���
				frame.setTitle(fileName + "." + suf + "-��ͼ");
				
				//�ѱ���
				frame.getBufferedImage().setIsSaved(true);
			}
		}else if(! frame.getBufferedImage().isSaved()){
			JOptionPane option = new JOptionPane();
			
			int checked = option.showConfirmDialog(frame, "����Ķ���", "��ͼ", option.YES_NO_OPTION, option.WARNING_MESSAGE);
			
			if(checked == option.YES_OPTION){
				save(true, frame);
			}
		}
	}
	
	public void open(ViewFrame frame){
		save(false, frame);
		
		if(fileChooser.showOpenDialog(frame) == ImageFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();//��ȡѡ����ļ�
			
			fileChooser.setCurrentDirectory(file);//���õ�ǰ���ļ���
			
			BufferedImage image = null;
			
			try{
				//���ļ���ȡͼƬ
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
			
			//���ñ����Ĵ��ڱ���
			frame.setTitle(fileChooser.getSelectedFile().getName() + "- ��ͼ");
		}
	}
	
	public void createGraphics(ViewFrame frame){
		save(false, frame);
		
		int width = (int) getScreenSize().getWidth()/2;
		int height = (int) getScreenSize().getHeight()/2;
		AbstractTool.drawWidth = width;
		AbstractTool.drawHeight = height;
		
		//����һ��MyImage
		MyImage myImage = new MyImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = myImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		frame.setBufferedImage(myImage);
		
		//repaint
		frame.getDrawSpace().repaint();
		
		//��������viewport
		ImageService.setViewport(frame.getScroll(),frame.getDrawSpace(),width,height);
		
		//���ñ����Ĵ��ڱ���
		frame.setTitle("δ����-��ͼ");
	}
	
	public void editColor(ViewFrame frame){
		//��ȡ��ɫ
		Color color = JColorChooser.showDialog(frame.getColorChooser(), "�༭��ɫ", Color.BLACK);
		color = color == null? AbstractTool.color : color;
		
		//���ù��ߵ���ɫ
		AbstractTool.color = color;
		
		//����Ŀǰ��ʾ����ɫ
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
		if(cmd.equals("�༭��ɫ")){
			editColor(frame);
		}
		
		if(cmd.equals("������(T)")){
			setVisible(frame.getToolPanel());
		}
		
		if(cmd.equals("���Ϻ�(C)")){
			setVisible(frame.getColorPanel());
		}
		if(cmd.equals("�½�(N)")){
			createGraphics(frame);
		}
		if(cmd.equals("��(O)")){
			open(frame);
		}
		if(cmd.equals("����(S)")){
			save(true, frame);
		}
		if(cmd.equals("�˳�(X)")){
			exit(frame);
		}
	}
}
