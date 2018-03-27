package code;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.tools.Tool;

import code.Tool.AbstractTool;
import code.Tool.ToolFactory;

public class ImageAction extends AbstractAction{
	private String name = "";
	private ViewFrame frame = null;
	private Color color = null;
	private Tool tool = null;
	private JPanel colorPanel = null;
	
	public ImageAction(Color color,JPanel colorPanel){
		super();
		this.color = color;
		this.colorPanel = colorPanel;
	}
	
	public ImageAction(ImageIcon icon,String name,ViewFrame frame){
		//∏∏ππ‘Ï∆˜
		super("",icon);
		this.name = name;
		this.frame = frame;
	}
	
	public void actionPerformed(ActionEvent e){
		//…Ë÷√tool
		
		tool = name != "" ? (Tool) ToolFactory.getToolInstance(frame,name) : tool;
		
		if(tool != null){
			frame.setTool(tool);
		}
		
		if(color != null){
			AbstractTool.color = color;
			colorPanel.setBackground(color);
		}
	}
}
