package code;

import java.awt.image.BufferedImage;

public class MyImage extends BufferedImage{
	
	private boolean isSaved = true;
	
	public MyImage(int width, int height, int imageType) {
		super(width, height, imageType);
		// TODO Auto-generated constructor stub
		this.getGraphics().fillRect(0,0,width,height);
	}
	
	/*
	 * 
	 * …Ë÷√ «∑Ò±£¥Ê*/

	public void setIsSaved(boolean b){
		this.isSaved = b;
	}
	
	public boolean isSaved(){
		return this.isSaved;
	}
}
