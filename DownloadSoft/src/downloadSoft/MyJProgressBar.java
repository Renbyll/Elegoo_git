package downloadSoft;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class MyJProgressBar extends JProgressBar {
	
	private static final long serialVersionUID = 51671657321365467L;
	private Color color = Color.BLUE;

    private class GyrProgressUI extends BasicProgressBarUI {
        private JProgressBar jProgressBar;

        private GyrProgressUI(JProgressBar jProgressBar) {
            this.jProgressBar = jProgressBar;
        }

        @Override
        protected void paintDeterminate(Graphics g, JComponent c) {
        	this.jProgressBar.setForeground(MyJProgressBar.this.color);
            super.paintDeterminate(g, c);
        }

    }
    
    public MyJProgressBar() {
        init();
    }

    public MyJProgressBar(int orient) {
        super(orient);
        init();
    }

    public MyJProgressBar(int min, int max) {
        super(min, max);
        init();
    }

    public MyJProgressBar(int orient, int min, int max) {
        super(orient, min, max);
        init();
    }

    public MyJProgressBar(BoundedRangeModel newModel) {
        super(newModel);
        init();
    }

    private void init(){
    	// 设置进度条边框不显示  
        this.setBorderPainted(false);
        // 显示当前进度值信息  
        this.setStringPainted(true);
        this.setUI(new GyrProgressUI(this));
        
    }

    public void setProgressBarColor(Color c)
    {
    	this.color = c;
    }
    
}
