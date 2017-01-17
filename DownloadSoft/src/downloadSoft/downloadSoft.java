package downloadSoft;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.filechooser.FileNameExtensionFilter;


public class downloadSoft extends JFrame{
	
	private static final long serialVersionUID = -2029566581047632579L;
    private String BordName;//板子名字
    private String[] CommNOPro = new String[8];//已存在的端口号
    private int BordNumber;//烧写的板子数目
    private String Comand;//运行的指令
    private JTextField HexTextField;//Hex文件显示
    private String HexFilePath;//Hex文件路径
    private JTextField[] TextArea = new JTextField[8];//显示各个烧写程序结果
    private JButton SelectButton;//在文件系统中选择Hex文件按钮
    private JButton StartButton;//开始烧写程序按钮
    private String[] BourdInfor_str = {"Arduino UNO", "Arduino Nano", "Arduino Mega2560"};//支持烧写的板子类型, "Arduino Makey-Makey",
    		//"Arduino Pro Mini", "Arduino Micro"
    private String[] BordNum_str = {"1", "2", "3", "4", "5", "6", "7", "8"}; //一次烧写的板子数目
    private MycomboBox BourdInfor = new MycomboBox(BourdInfor_str);
    private MycomboBox BourdNum = new MycomboBox(BordNum_str);
    private CommPort SerialPort = new CommPort();
    private MyJProgressBar bar;
    
    public static void main(String[] args) {
    	//System.out.println(downloadSoft.class.getResource(""));
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	downloadSoft frame = new downloadSoft();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public downloadSoft()
    {
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException 
        		| UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    	
    	setTitle("烧写示例程序软件V2.0");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds(200, 200, 450, 287);
    	setResizable(false);
    	setLayout(null);
    	Container container = getContentPane();
    	
    	JLabel pathLabel = new JLabel("HEX文件路径：");
        pathLabel.setBounds(5, 10, 100, 20);
        container.add(pathLabel);
    	
    	HexTextField = new JTextField();
        HexTextField.setColumns(18);
        HexTextField.setBounds(85, 10, 250, 20);
        container.add(HexTextField);
        
        SelectButton = new JButton("打开Hex文件");
        SelectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                do_openFile_actionPerformed(e);
            }
        });
        SelectButton.setBounds(340, 8, 100, 22);
        container.add(SelectButton);
        
        JLabel bordName = new JLabel("开发板：");
        bordName.setBounds(5, 35, 60, 25);
        container.add(bordName);
        
        JComboBox<MycomboBox> bord = new JComboBox(BourdInfor);
        bord.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		do_BordName_Action(e);
        		//System.out.println(BordName);
        	}
        });
        bord.setBounds(62, 35, 140, 25);
        container.add(bord);
        
        JLabel bordRateLabel =  new JLabel("烧写板子数：");
        bordRateLabel.setBounds(218, 35, 100, 25);
        container.add(bordRateLabel);
        
        JComboBox<MycomboBox> bordNum = new JComboBox(BourdNum);
        bordNum.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		String str = BourdNum.getElementAt(BourdNum.getIndex());
        		char c = str.charAt(0);
        		BordNumber = c - '0';
        		//System.out.println(BordNumber);
        	}
        });
        bordNum.setBounds(295, 35, 50, 25);
        container.add(bordNum);
        
        StartButton = new JButton("开始");
        StartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                do_begin_actionPerformed(e);
            }
        });
        StartButton.setBounds(360, 37, 70, 22);
        container.add(StartButton);
        
        JLabel burnResult_Label = new JLabel("烧写示例程序结果如下：");
        burnResult_Label.setBounds(5, 65, 150, 25);
        container.add(burnResult_Label);
        
        for(int i = 0; i< 8; i++){
        	TextArea[i] = new JTextField();
        }
        
        JLabel label1 =  new JLabel("1：");
        label1.setBounds(5, 95, 150, 25);
        container.add(label1);
        TextArea[0].setBounds(20, 95, 170, 25);
        container.add(TextArea[0]);
        TextArea[0].setColumns(18);
        
        JLabel label2 =  new JLabel("2：");
        label2.setBounds(240, 95, 150, 25);
        container.add(label2);
        TextArea[1].setBounds(260, 95, 170, 25);
        container.add(TextArea[1]);
        TextArea[1].setColumns(18);
        
        JLabel label3 =  new JLabel("3：");
        label3.setBounds(5, 135, 150, 25);
        container.add(label3);
        TextArea[2].setBounds(20, 135, 170, 25);
        container.add(TextArea[2]);
        TextArea[2].setColumns(18);
        
        JLabel label4 =  new JLabel("4：");
        label4.setBounds(240, 135, 150, 25);
        container.add(label4);
        TextArea[3].setBounds(260, 135, 170, 25);
        container.add(TextArea[3]);
        TextArea[3].setColumns(18);
        
        JLabel label5 =  new JLabel("5：");
        label5.setBounds(5, 175, 150, 25);
        container.add(label5);
        TextArea[4].setBounds(20, 175, 170, 25);
        container.add(TextArea[4]);
        TextArea[4].setColumns(18);
        
        JLabel label6 =  new JLabel("6：");
        label6.setBounds(240, 175, 150, 25);
        container.add(label6);
        TextArea[5].setBounds(260, 175, 170, 25);
        container.add(TextArea[5]);
        TextArea[5].setColumns(18);
        
        JLabel label7 =  new JLabel("7：");
        label7.setBounds(5, 215, 150, 25);
        container.add(label7);
        TextArea[6].setBounds(20, 215, 170, 25);
        container.add(TextArea[6]);
        TextArea[6].setColumns(18);
        
        JLabel label8 =  new JLabel("8：");
        label8.setBounds(240, 215, 150, 25);
        container.add(label8);
        TextArea[7].setBounds(260, 215, 170, 25);
        container.add(TextArea[7]);
        TextArea[7].setColumns(18);
        
        bar = new MyJProgressBar(0, 100);
        bar.setBounds(2, 243, 440, 15);
        container.add(bar);
        bar.setBackground(new Color(188, 190, 194));
        bar.setValue(0);
        //bar.setProgressBarColor(Color.blue);
        
        SerialPort.getCommPortName(CommNOPro);
    }
    
    protected void do_openFile_actionPerformed(ActionEvent e) {
    	JFileChooser chooser = new JFileChooser();// 创建文件选择器
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);// 设置仅能选择文件
        chooser.setMultiSelectionEnabled(false);// 禁止多个选择
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
        	File SelectFile = chooser.getSelectedFile();// 获得选择的文件
        	HexFilePath = SelectFile.getAbsolutePath();
        	if(HexFilePath.endsWith(".hex")){
        		HexTextField.setText(HexFilePath);// 显示选择的文件路径
        	}
        	else{
        		JOptionPane.showMessageDialog(null, "请选择Hex文件", "警告",JOptionPane.WARNING_MESSAGE); 
        	}
        }
    }
    
    protected void do_BordName_Action(ActionEvent e){
    	String comandFilePath = System.getProperty("user.dir") + "\\Comand.txt";
    	BordName = BourdInfor.getElementAt(BourdInfor.getIndex());
    	try{
    		FileReader comandReader = new FileReader(comandFilePath);
    		BufferedReader comandBuff = new BufferedReader(comandReader);
    		String c = comandBuff.readLine();
    		while(c != null){
    			if(c.startsWith(BordName)){
    				Comand = comandBuff.readLine();
    				//System.out.println(Comand);
    				break;
    			}
    			c = comandBuff.readLine();
    		}
    		comandBuff.close();
    		comandReader.close();
    	}catch(Exception er){
    		er.printStackTrace();
    	}	
    }
    
    //@SuppressWarnings("static-access")
	protected void do_begin_actionPerformed(ActionEvent e) {
    	//String line;
    	String[] NewCommPort = new String[20];
    	//String portName;
    	String sendComand =null;
    	String result = null;
    	Process process = null;
    	boolean error = false;
    	int percent = 0;
    	int addPercent = 0;
    	
    	bar.setValue(0);
    	bar.setProgressBarColor(Color.blue);
    	
    	for(int i = 0; i < TextArea.length; i++){
    		TextArea[i].setText("");
    		TextArea[i].setForeground(Color.BLACK);
    	}
    	
    	if(BordNumber == 0){
    		JOptionPane.showMessageDialog(null, "请设置板子数目！", "警告",JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	String avrdude_path = System.getProperty("user.dir") + "\\avrdude\\avrdude.exe";
    	File f = new File(avrdude_path);
    	if(f.exists() != true){
    		JOptionPane.showMessageDialog(null, "avrdude 不存在！", "警告",JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	if(HexFilePath == null){
    		JOptionPane.showMessageDialog(null, "Hex文件 路径为空，请设置Hex文件！", "警告",JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	File HexFile = new File(HexFilePath);
    	if(HexFile.exists() != true){
    		JOptionPane.showMessageDialog(null, "HexFilePath 不存在！", "警告",JOptionPane.WARNING_MESSAGE);
    		return;
    	}
		
		int writeSucess = 0;
		int j = 0;
		SerialPort.getCommPortName(NewCommPort);
		//System.out.println(NewCommPort.length);
		addPercent = 100/BordNumber;
		//while(writeSucess < BordNumber){
		while(NewCommPort[j] != null){
			if(NewCommPort[j] != CommNOPro[j]){
				break;
			}
			j++;
		}
		if(NewCommPort[j] == null && CommNOPro[j] == null){
			JOptionPane.showMessageDialog(null, "当前没有可用的串口！", "警告",JOptionPane.WARNING_MESSAGE);
			return;
		}
		int port = 0;
		while(NewCommPort[j] != null){
			sendComand =avrdude_path + Comand + HexFilePath + "\"" + ":i";
			int commIndex = sendComand.indexOf("-P ") +3;
			sendComand = sendComand.substring(0, commIndex) + "\\\\.\\" + NewCommPort[j] 
					+ sendComand.substring(commIndex + 3, sendComand.length());//*/
			
			System.out.println(sendComand);
			if(NewCommPort[j] != null){
				try{
					@SuppressWarnings("unused")
					Runtime rt = Runtime.getRuntime();
					process = Runtime.getRuntime().exec(sendComand);//, null, new File(avrdude_path));
					BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getErrorStream()));
					int count = 0;
					String line = null;
					while((line = stdout.readLine())!= null){
						if(count == 1){
							result = line;
							if(result.equals("avrdude.exe: AVR device initialized and ready to accept instructions")){
								error = false;
							}
							else{
								error = true;
								process.destroy();
							}
							//System.out.println(result);
							break;
						}
						count++;//
					}
					//System.out.println(result);
				}catch(IOException ioe){
					ioe.printStackTrace();
				}
			}
			//SerialPort.selectPort(NewCommPort[j]);
			//SerialPort.startRead(5);
			if(!error){
				TextArea[port].setText(NewCommPort[j]+ " 烧写成功!");
				TextArea[port].setForeground(Color.BLUE);
				writeSucess++;
				percent = writeSucess * addPercent;
				if (writeSucess == BordNumber){
					percent = 100;
				}
				bar.setValue(percent);
			}
			else{
				TextArea[port].setText(NewCommPort[j]+ " 烧写失败!");
				TextArea[port].setForeground(Color.RED);
				bar.setProgressBarColor(Color.red);
				
			}
			sendComand = null;
			port++;
			j++;
		}
		if(port < BordNumber){
			while(port < BordNumber){
				TextArea[port].setText("没有串口！");
				port++;
			}
		}
		
    }

}
