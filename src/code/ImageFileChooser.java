package code;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class ImageFileChooser extends JFileChooser{
	public ImageFileChooser(){
		super();
		setAcceptAllFileFilterUsed(false);
		addFilter();
	}
	
	public ImageFileChooser(String currentDirectoryPath){
		super(currentDirectoryPath);
		setAcceptAllFileFilterUsed(false);
		addFilter();
	}
	
	public String getSuf(){
		//获取文件过滤对象
		FileFilter fileFilter = this.getFileFilter();
		String desc = fileFilter.getDescription();
		String[] sufarr = desc.split(" ");
		String suf = sufarr[0].equals("所有图形文件")? "" : sufarr[0];
		return suf.toLowerCase();
	}
	
	private void addFilter(){
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".BMP" },
				"BMP (*.BMP)"));
		this
				.addChoosableFileFilter(new MyFileFilter(new String[] { ".JPG",
						".JPEG", ".JPE", ".JFIF" },
						"JPEG (*.JPG;*.JPEG;*.JPE;*.JFIF)"));
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".GIF" },
				"GIF (*.GIF)"));
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".TIF",
				".TIFF" }, "TIFF (*.TIF;*.TIFF)"));
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".PNG" },
				"PNG (*.PNG)"));
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".ICO" },
				"ICO (*.ICO)"));
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".BMP",
				".JPG", ".JPEG", ".JPE", ".JFIF", ".GIF", ".TIF", ".TIFF",
				".PNG", ".ICO" }, "所有图形文件"));
	}
	
	class MyFileFilter extends FileFilter{
		//后缀名数组
		String[] suffarr;
		
		//描述
		String description;
		
		public MyFileFilter(){
			super();
		}
		
		public MyFileFilter(String[] suffarr,String description){
			super();
			this.suffarr = suffarr;
			this.description = description;
		}
		
		public boolean accept(File f){
			//如果文件的后缀名合法，返回true
			for(String s:suffarr){
				if(f.getName().toUpperCase().endsWith(s)){
					return true;
				}
			}
			return f.isDirectory();
		}
		
		public String getDescription(){
			return this.description;
		}
	}
}
