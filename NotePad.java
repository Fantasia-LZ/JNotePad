package myclass;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class NotePad extends JFrame{
	private MyText text=new MyText();
	public NotePad(){
		super();
		//text=new MyText();//创建文本域
		this.add(new JScrollPane(text));//加入文本域
		this.getMyText().getStyledDocument().addDocumentListener(this.text);//为文档注册文档监听
		this.setJMenuBar(createMenuBar());//设置菜单栏
		this.setTitle("JNotePad-null");//设置标题
		this.setSize(750,500);//设置窗体大小
		this.setLocation(100,100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	public MyText getMyText(){
		return text;
	}
	
	public static void main(String[] args){
		NotePad notepad=new NotePad();
		notepad.addWindowListener(new WindowAdapter(){//注册窗口监听器
			public void windowClosing(WindowEvent e){
				notepad.getJMenuBar().getMenu(0).getItem(4).doClick();
			}
		});
	}

	public JMenuBar createMenuBar(){//创建菜单栏并为各菜单项注册各自的监听器
		JMenuBar menuBar=new JMenuBar();
		
		JMenu menu=new JMenu("文件");
		menuBar.add(menu);
		JMenuItem menuItem=new JMenuItem("新建");
		menuItem.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));
		menuItem.addActionListener(new Hear1_1(this));
		menu.add(menuItem);
		menuItem=new JMenuItem("打开");
		menuItem.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
		menuItem.addActionListener(new Hear1_2(this));
		menu.add(menuItem);
		menuItem=new JMenuItem("保存");
		menuItem.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
		menuItem.addActionListener(new Hear1_3(this));
		menu.add(menuItem);
		menuItem=new JMenuItem("另存为");
		menuItem.addActionListener(new Hear1_4(this));
		menu.add(menuItem);
		menuItem=new JMenuItem("退出");
		menuItem.addActionListener(new Hear1_5(this));
		menu.add(menuItem);
		
		menu=new JMenu("编辑");
		menuBar.add(menu);
		menuItem=new JMenuItem("复制");
		menuItem.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		menuItem.addActionListener(new Hear2_1(this));
		menu.add(menuItem);
		menuItem=new JMenuItem("剪切");
		menuItem.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		menuItem.addActionListener(new Hear2_2(this));
		menu.add(menuItem);
		menuItem=new JMenuItem("粘贴");
		menuItem.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		menuItem.addActionListener(new Hear2_3(this));
		menu.add(menuItem);
		menuItem=new JMenuItem("查找");
		menuItem.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK));
		menuItem.addActionListener(new Hear2_4(this));
		menu.add(menuItem);
		menuItem=new JMenuItem("替换");
		menuItem.setAccelerator(KeyStroke.getKeyStroke('H', InputEvent.CTRL_MASK));
		menuItem.addActionListener(new Hear2_5(this));
		menu.add(menuItem);
		
		menu=new JMenu("格式");
		menuBar.add(menu);
		menuItem=new JMenuItem("字体设置");
		menuItem.addActionListener(new Hear3_1(this));
		menu.add(menuItem);
		
		menu=new JMenu("帮助");
		menuBar.add(menu);
		menuItem=new JMenuItem("关于");
		menuItem.addActionListener(new Hear4_1());
		menu.add(menuItem);
		menuItem=new JMenuItem("帮助");
		menuItem.addActionListener(new Hear4_2());
		menu.add(menuItem);
		return menuBar;
	}
}

class MyText extends JTextPane implements DocumentListener{
	private File file=null;//当前操作的文件
	private boolean isChanged=false;//标识当前文件修改后是否保存
	
	private HashSet<String> red;
	private HashSet<String> orange;
	private HashSet<String> yellow;
	private HashSet<String> green;
	private HashSet<String> blue;
	private HashSet<String> cyan;
	private HashSet<String> black;
	private HashSet<String> pink;//创建八个不同的哈希表存储不同类型关键词
	private Style redStyle;
	private Style orangeStyle;
	private Style yellowStyle;
	private Style greenStyle;
	private Style blueStyle;
	private Style cyanStyle;
	private Style blackStyle;
	private Style pinkStyle;//对于不同类型个关键字创建对应的文档属性

	MyText(){
		super();
		blackStyle = this.getStyledDocument().addStyle("black", null);
		orangeStyle = this.getStyledDocument().addStyle("orange", null);
		yellowStyle = this.getStyledDocument().addStyle("yellow", null);
		greenStyle = this.getStyledDocument().addStyle("green", null);
		blueStyle = this.getStyledDocument().addStyle("blue", null);
		cyanStyle = this.getStyledDocument().addStyle("cyan", null);
		pinkStyle = this.getStyledDocument().addStyle("pink", null);
		redStyle = this.getStyledDocument().addStyle("red", null);
		StyleConstants.setForeground(blackStyle, Color.black);
		StyleConstants.setForeground(orangeStyle, Color.orange);
		StyleConstants.setForeground(yellowStyle, Color.yellow);
		StyleConstants.setForeground(greenStyle, Color.green);
		StyleConstants.setForeground(blueStyle, Color.blue);
		StyleConstants.setForeground(cyanStyle, Color.cyan);
		StyleConstants.setForeground(pinkStyle, Color.pink);
		StyleConstants.setForeground(redStyle, Color.red);//初始化不同的关键字文档属性

		// 创建不同类型关键字的哈希表
		
		//访问控制
		orange = new HashSet<String>();
		orange.add("public");
		orange.add("protected");
		orange.add("private");
		//类、方法和变量修饰符
		yellow = new HashSet<String>();
		yellow.add("abstract");
		yellow.add("class");
		yellow.add("extends");
		yellow.add("final");
		yellow.add("implements");
		yellow.add("interface");
		yellow.add("native");
		yellow.add("new");
		yellow.add("static");
		yellow.add("strictfp");
		yellow.add("synchronized");
		yellow.add("transient");
		yellow.add("volatile");
		//程序控制语句
		green=new HashSet<String>();
		green.add("break");
		green.add("continue");
		green.add("return");
		green.add("do");
		green.add("while");
		green.add("if");
		green.add("else");
		green.add("for");
		green.add("instanceof");
		green.add("switch");
		green.add("case");
		green.add("default");
		//异常处理
		blue=new HashSet<String>();
		blue.add("catch");
		blue.add("finally");
		blue.add("throw");
		blue.add("throws");
		blue.add("try");
		//包相关
		cyan=new HashSet<String>();
		cyan.add("package");
		cyan.add("import");
		//基本类型
		pink=new HashSet<String>();
		pink.add("boolean");
		pink.add("byte");
		pink.add("char");
		pink.add("double");
		pink.add("float");
		pink.add("int");
		pink.add("long");
		pink.add("short");
		//变量引用
		red=new HashSet<String>();
		red.add("super");
		red.add("this");
		red.add("void");
	}
	public File getFile(){//得到文本域中的文件
		return file;
	}
	public boolean getIsChanged(){//获知文件是否更改
		return isChanged;
	}
	public void setFile(File file){//设置文本域文件
		this.file=file;
	}
	public void setIsChanged(boolean isChanged){//设置文件是否更改
		this.isChanged=isChanged;
	}
	//读写文件
	public void readFile(File file) throws Exception{//从指定文件中读取文本
		BufferedReader reader=new BufferedReader(new FileReader(file));
		StringBuffer str=new StringBuffer();
		String str1;
		while((str1=reader.readLine())!=null)
			str.append(str1+"\n");
		this.setText(str.toString());
		this.isChanged=false;
	}
	
	public void writeFile(File file) throws Exception{//将文本写入指定文件
		this.write(new FileWriter(file));
		this.isChanged=false;
	}
	//文档监听
	public void insertUpdate(DocumentEvent e){//当插入时触发操作，并且进行关键字的重新高亮
		System.out.println("insert");
		isChanged=true;
		try {
			coloring((StyledDocument) e.getDocument(), e.getOffset(), e.getLength());
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}	
	}
	public void removeUpdate(DocumentEvent e){//当删除时触发操作，并且进行关键字的重新高亮
		System.out.println("remove");
		isChanged=true;
		try {
			// 因为删除后光标紧接着影响的单词两边, 所以长度就不需要了
			coloring((StyledDocument) e.getDocument(), e.getOffset(), 0);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		} 
	}
	public void changedUpdate(DocumentEvent e){
	}
	//设置为不自动换行
	public boolean getScrollableTracksViewportWidth(){
        return false;
    }
    public void setSize(Dimension d) {
        if (d.width < getParent().getSize().width) {
            d.width = getParent().getSize().width;
        }
        d.width += 1;
        super.setSize(d);
    }
	//关键字高亮
	private void coloring(StyledDocument doc, int pos, int len) throws BadLocationException {
		int start = getStart(doc, pos);
		int end = getEnd(doc, pos + len);

		char ch;
		while (start < end) {
			ch = getChar(doc, start);
			if (Character.isLetter(ch) || ch == '_') {
				start = colorKey(doc, start);
			} else {
				SwingUtilities.invokeLater(new ColorAction(doc, start, 1, blackStyle));//普通文档类型
				start++;
			}
		}
	}
	
	private int colorKey(StyledDocument doc, int pos) throws BadLocationException {
		int end = getEnd(doc, pos);
		String word = doc.getText(pos, end - pos);
		// 对于不同类型的关键字进行不同的染色
		if (orange.contains(word)) {
			SwingUtilities.invokeLater(new ColorAction(doc, pos, end - pos, orangeStyle));
		}
		else if(yellow.contains(word)){
			SwingUtilities.invokeLater(new ColorAction(doc, pos, end - pos, yellowStyle));
		}
		else if(green.contains(word))
			SwingUtilities.invokeLater(new ColorAction(doc, pos, end - pos, greenStyle));
		else if(blue.contains(word))
			SwingUtilities.invokeLater(new ColorAction(doc, pos, end - pos, blueStyle));
		else if(cyan.contains(word))
			SwingUtilities.invokeLater(new ColorAction(doc, pos, end - pos, cyanStyle));
		else if(pink.contains(word))
			SwingUtilities.invokeLater(new ColorAction(doc, pos, end - pos, pinkStyle));
		else if(red.contains(word))
			SwingUtilities.invokeLater(new ColorAction(doc, pos, end - pos, redStyle));
		else {
			SwingUtilities.invokeLater(new ColorAction(doc, pos, end - pos, blackStyle));
		}

		return end;
	}
	
	private char getChar(Document doc, int pos) throws BadLocationException {
		return doc.getText(pos, 1).charAt(0);
	}
	private int getStart(Document doc, int pos) throws BadLocationException {
		// 从pos开始向前找到第一个非单词字符.
		for (; pos > 0 && isChar(doc, pos - 1); --pos);
		return pos;
	}
	private int getEnd(Document doc, int pos) throws BadLocationException {
		// 从pos开始向后找到第一个非单词字符.
		for (;isChar(doc, pos); ++pos);
		return pos;
	}
	
	private boolean isChar(Document doc, int pos) throws BadLocationException {
		char ch = getChar(doc, pos);
		if (Character.isLetter(ch) || Character.isDigit(ch) || ch == '_') { return true; }
		return false;
	}
	
	private class ColorAction implements Runnable {//文档变色的主操作
		private StyledDocument doc;
		private Style style;
		private int pos;
		private int len;

		public ColorAction(StyledDocument doc, int pos, int len, Style style) {
			this.doc = doc;
			this.pos = pos;
			this.len = len;
			this.style = style;
		}

		public void run() {
			try {
				doc.setCharacterAttributes(pos, len, style, true);
			} catch (Exception e) {}
		}
	} 
} 

class Hear1_1 implements ActionListener{
	public NotePad notepad;
	public Hear1_1(NotePad notepad){
		this.notepad=notepad;
	}
	
	public void actionPerformed(ActionEvent e){
		if(notepad.getMyText().getIsChanged()==false){
			notepad.getMyText().setText("");
			notepad.getMyText().setIsChanged(false);
			notepad.getMyText().setFile(null);
			notepad.setTitle("JNotePad-null");
			notepad.validate();
		}
		else{
			int xuanXiang=JOptionPane.showConfirmDialog(notepad,"是否保存文件");
			if(xuanXiang==JOptionPane.YES_OPTION) {
					notepad.getJMenuBar().getMenu(0).getItem(2).doClick();
					notepad.getMyText().setText("");
					notepad.getMyText().setIsChanged(false);
					notepad.getMyText().setFile(null);
					notepad.setTitle("JNotePad-null");
					notepad.validate();
				}
			if(xuanXiang==JOptionPane.NO_OPTION) {
				notepad.getMyText().setText("");
				notepad.getMyText().setIsChanged(false);
				notepad.getMyText().setFile(null);
				notepad.setTitle("JNotePad-null");
				notepad.validate();
			}
		}
	}
}

class Hear1_2 implements ActionListener{
	public NotePad notepad;
	public Hear1_2(NotePad notepad){
		this.notepad=notepad;
	}
	
	public void actionPerformed(ActionEvent e){
		if(notepad.getMyText().getIsChanged()==false){
			JFileChooser chooser = new JFileChooser();
			int xuanXiang=chooser.showOpenDialog(new JPanel());
			if(xuanXiang==JFileChooser.APPROVE_OPTION){
				File file=chooser.getSelectedFile();
				notepad.getMyText().setFile(file.getAbsoluteFile());
				if(!file.exists()){
						JOptionPane.showMessageDialog(new JPanel(),"文件不存在","注意",JOptionPane.INFORMATION_MESSAGE);
					}
				else{
					try{
						notepad.getMyText().readFile(file);
					} catch (Exception exc){}
					notepad.setTitle("JNotePad-"+file.getAbsolutePath());
				}
				notepad.validate();
			}
		}
		else{
			int xuanXiang=JOptionPane.showConfirmDialog(notepad,"是否保存已修改的文件");
			if(xuanXiang==JOptionPane.YES_OPTION) {
				notepad.getJMenuBar().getMenu(0).getItem(2).doClick();
				notepad.validate();
				JFileChooser chooser = new JFileChooser();
				int xuanXiang1=chooser.showOpenDialog(new JPanel());
				if(xuanXiang1==JFileChooser.APPROVE_OPTION){
					File file=chooser.getSelectedFile();
					notepad.getMyText().setFile(file.getAbsoluteFile());
					if(!file.exists()){
						JOptionPane.showMessageDialog(new JPanel(),"文件不存在","注意",JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						try{
							notepad.getMyText().readFile(file);
						} catch (Exception exc){}
						notepad.setTitle("JNotePad-"+file.getAbsolutePath());
					}
				}
				notepad.validate();
			}
			if(xuanXiang==JOptionPane.NO_OPTION) {
				JFileChooser chooser = new JFileChooser();
				int xuanXiang2=chooser.showOpenDialog(new JPanel());
				if(xuanXiang2==JFileChooser.APPROVE_OPTION){
					File file=chooser.getSelectedFile();
					notepad.getMyText().setFile(file.getAbsoluteFile());
					try{
						notepad.getMyText().readFile(file);
					}catch (Exception exc){}
					notepad.setTitle("JNotePad-"+file.getAbsolutePath());
				}
				notepad.validate();
			}
		}
	}	
}

class Hear1_3 implements ActionListener {
	public NotePad notepad;
	public Hear1_3(NotePad notepad){
		this.notepad=notepad;
	}
	
	public void actionPerformed(ActionEvent e){
		if(notepad.getMyText().getFile()!=null){
			try{
				notepad.getMyText().writeFile(notepad.getMyText().getFile());
			}catch(Exception exc){}
		}
		else{
			JFileChooser chooser = new JFileChooser();
			int xuanXiang=chooser.showSaveDialog(new JPanel());
			if(xuanXiang==JFileChooser.APPROVE_OPTION){
				File file=chooser.getSelectedFile();
				if(file.exists()){
					int xuanXiang1=JOptionPane.showConfirmDialog(chooser,"文件已存在，是否覆盖");
					if(xuanXiang1==JOptionPane.YES_OPTION){
						notepad.getMyText().setFile(file.getAbsoluteFile());
						try{
							notepad.getMyText().writeFile(file);
						}catch(Exception exc){}
						notepad.setTitle("JNotePad"+file.getAbsolutePath());
					}
				}
				else{
						notepad.getMyText().setFile(file.getAbsoluteFile());
						try{
							notepad.getMyText().writeFile(file);
						}catch(Exception exc){}
						notepad.setTitle("JNotePad"+file.getAbsolutePath());
				}
			}
		}
		notepad.validate();
	}
}

class Hear1_4 implements ActionListener{
	public NotePad notepad;
	
	public Hear1_4(NotePad notepad){
		this.notepad=notepad;
	}
	
	public void actionPerformed(ActionEvent e){
		JFileChooser chooser = new JFileChooser();
		int xuanXiang=chooser.showDialog(new JPanel(),"另存为");
		if(xuanXiang==JFileChooser.APPROVE_OPTION){
			File file=chooser.getSelectedFile();
			if(file.exists()){
					int xuanXiang1=JOptionPane.showConfirmDialog(chooser,"文件已存在，是否覆盖");
					if(xuanXiang1==JOptionPane.YES_OPTION){
						notepad.getMyText().setFile(file.getAbsoluteFile());
						try{
							notepad.getMyText().writeFile(file);
						}catch(Exception exc){}
					}
					notepad.setTitle("JNotePad"+file.getAbsolutePath());
				}
			else{
					notepad.getMyText().setFile(file.getAbsoluteFile());
					try{
						notepad.getMyText().writeFile(file);
					}catch(Exception exc){}
					notepad.setTitle("JNotePad-"+file.getAbsolutePath());
			}
		}
		notepad.validate();
	}
}
class Hear1_5 implements ActionListener{
	private NotePad notepad;
	public Hear1_5(NotePad notepad){
		this.notepad=notepad;
	}
	public void actionPerformed(ActionEvent e){
		if(notepad.getMyText().getIsChanged()==false) System.exit(0);//notepad.dispose();
		else{
			int xuanXiang=JOptionPane.showConfirmDialog(notepad,"是否保存文件");
			if(xuanXiang==JOptionPane.YES_OPTION) {
				notepad.getJMenuBar().getMenu(0).getItem(2).doClick();
				//notepad.dispose();
				System.exit(0);
			}
			if(xuanXiang==JOptionPane.NO_OPTION) System.exit(0);//notepad.dispose();
		}
	}
}
class Hear2_1 implements ActionListener{
	public NotePad notepad;
	public Hear2_1(NotePad notepad){
		this.notepad=notepad;
	}
	public void actionPerformed(ActionEvent e){
		notepad.getMyText().copy();
	}
}
class Hear2_2 implements ActionListener{
	public NotePad notepad;
	public Hear2_2(NotePad notepad){
		this.notepad=notepad;
	}
	public void actionPerformed(ActionEvent e){
		notepad.getMyText().cut();
	}
}
class Hear2_3 implements ActionListener{
	public NotePad notepad;
	public Hear2_3(NotePad notepad){
		this.notepad=notepad;
	}
	public void actionPerformed(ActionEvent e){
		notepad.getMyText().paste();
	}
}
class Hear2_4 implements ActionListener{
	public NotePad notepad;
	public JDialog dialog;
	public Hear2_4(NotePad notepad){
		this.notepad=notepad;
		dialog=getDialog();
	}
	public void actionPerformed(ActionEvent e){
		dialog.setVisible(true);
	}
	private JDialog getDialog(){
		JDialog dialog=new JDialog(notepad,"查找");
		dialog.setLayout(new FlowLayout());
		JLabel label1=new JLabel("查找内容");
		JTextField textField=new JTextField(10);
		JButton button1=new JButton("向下查找下一个");
		button1.addActionListener(new HearFind(notepad,textField));
		JButton button2=new JButton("取消");
		button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dialog.setVisible(false);
			}
		});
		dialog.add(label1);
		dialog.add(textField);
		dialog.add(button1);
		dialog.add(button2);
		dialog.setLocation(200,200);
		dialog.pack();
		dialog.setResizable(false);
		return dialog;
	}
	
	class HearFind implements ActionListener{
		int lastIndex=-1;
		NotePad notepad;
		JTextField textField;
		HearFind(NotePad notepad,JTextField textField){
			this.notepad=notepad;
			this.textField=textField;
		}
		public void actionPerformed(ActionEvent e){
			
			if(textField.getText().isEmpty()) JOptionPane.showMessageDialog(new JPanel(),"未输入查找内容","注意",JOptionPane.INFORMATION_MESSAGE);
			else{
				StringBuffer str=new StringBuffer(notepad.getMyText().getText());
				for(int i=0;i<str.length();i++){
					if(str.charAt(i)=='\r') str.deleteCharAt(i);
				}
				int index=str.indexOf(textField.getText(),lastIndex+1);
				if(index==-1){
					JOptionPane.showMessageDialog(new JPanel(),"已到文件尾，再次点击将从头查找","注意",JOptionPane.INFORMATION_MESSAGE);
					lastIndex=-1;
				}
				else{
					 notepad.getMyText().select(index,index+textField.getText().length());
					 lastIndex=index;
				}
			}
		}
	}
}

class Hear2_5 implements ActionListener{
	public NotePad notepad;
	public JDialog dialog;
	public Hear2_5(NotePad notepad){
		this.notepad=notepad;
		dialog=getDialog();
	}
	public void actionPerformed(ActionEvent e){
		dialog.setVisible(true);
	}
	
	private JDialog getDialog(){
		JDialog dialog=new JDialog(notepad,"替换");
		dialog.setLayout(new FlowLayout());
		JLabel label1=new JLabel("查找内容");
		JTextField textField1=new JTextField(10);
		JLabel label2=new JLabel(" 替换为 ");
		JTextField textField2=new JTextField(10);
		JButton button1=new JButton("向下查找下一个");
		JButton button2=new JButton("替换");
		button2.setEnabled(false);
		button1.addActionListener(new HearFind(notepad,textField1,button2));
		button2.addActionListener(new HearReplace(notepad,textField2));
		JButton button3=new JButton("取消");
		button3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dialog.setVisible(false);
			}
		});
		dialog.add(label1);
		dialog.add(textField1);
		dialog.add(label2);
		dialog.add(textField2);
		dialog.add(button1);
		dialog.add(button2);
		dialog.add(button3);
		dialog.setLocation(200,200);
		dialog.setResizable(false);
		dialog.pack();
		return dialog;
	}
	
	class HearFind implements ActionListener{
		int lastIndex=-1;
		NotePad notepad;
		JTextField textField;
		JButton button;
		HearFind(NotePad notepad,JTextField textField,JButton button){
			this.notepad=notepad;
			this.textField=textField;
			this.button=button;
		}
		public void actionPerformed(ActionEvent e){
			if(textField.getText().isEmpty()) JOptionPane.showMessageDialog(new JPanel(),"未输入查找内容","注意",JOptionPane.INFORMATION_MESSAGE);
			else{
				StringBuffer str=new StringBuffer(notepad.getMyText().getText());
				for(int i=0;i<str.length();i++){
					if(str.charAt(i)=='\r') str.deleteCharAt(i);
				}
				int index=str.indexOf(textField.getText(),lastIndex+1);
				if(index==-1){
					JOptionPane.showMessageDialog(new JPanel(),"已到文件尾，再次点击将从头查找","注意",JOptionPane.INFORMATION_MESSAGE);
					lastIndex=-1;
				}
				else{
					 notepad.getMyText().select(index,index+textField.getText().length());
					 button.setEnabled(true);
					 lastIndex=index;
				}
			}
		}
	}
	class HearReplace implements ActionListener{
		NotePad notepad;
		JTextField textField;
		HearReplace(NotePad notepad,JTextField textField){
			this.notepad=notepad;
			this.textField=textField;
		}
		public void actionPerformed(ActionEvent e){
			if(textField.getText().isEmpty()) JOptionPane.showMessageDialog(new JPanel(),"未输入替换内容","注意",JOptionPane.INFORMATION_MESSAGE);
			else{
					notepad.getMyText().replaceSelection(textField.getText());
					((JButton)e.getSource()).setEnabled(false);
			}
		}
	}
}

class Hear3_1 implements ActionListener{
	public NotePad notepad;
	public JDialog dialog;
	public Hear3_1(NotePad notepad){
		this.notepad=notepad;
		dialog=getFontDialog();
	}
	public void actionPerformed(ActionEvent e){
		dialog.setVisible(true);
	}
	private JDialog getFontDialog(){
		JDialog dialog=new JDialog(notepad,"字体设置",true);
		dialog.setLayout(new FlowLayout());
		
		dialog.setLocation(200,200);
		JLabel label1=new JLabel("字体");
		JLabel label2=new JLabel("样式");
		JLabel label3=new JLabel("大小");
		
		dialog.add(label1);
		List list1=new List(5);
		Font[] fonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();  
		for(int i=0;i<fonts.length;i++){
			list1.add(fonts[i].getFontName());
			if(notepad.getMyText().getFont().getFontName().equals(fonts[i].getFontName())) list1.select(i);
		}
		dialog.add(list1);
		
		dialog.add(label2);
		List list2=new List();
		list2.add("常规");
		list2.add("粗体");
		list2.add("斜体");
		dialog.add(list2);
		
		dialog.add(label3);
		List list3=new List();
		list3.add("10");
		list3.add("15");
		list3.add("20");
		list3.add("25");
		list3.add("30");
		list3.add("35");
		list3.add("40");
		list3.add("50");
		list3.select(1);
		dialog.add(list3);
		
		JButton button1=new JButton("确定");
		button1.addActionListener(new HearApprove(notepad,dialog,list1,list2,list3));
		JButton button2=new JButton("取消");
		button2.addActionListener(new HearQuit(dialog));
		dialog.add(button1);
		dialog.add(button2);
		dialog.pack();
		return dialog;
	}
	class HearApprove implements ActionListener{
		NotePad notepad;
		Font font;
		List list1;
		List list2;
		List list3;
		JDialog dialog;
		public HearApprove(NotePad notepad,JDialog dialog,List list1,List list2,List list3){
			this.list1=list1;
			this.list2=list2;
			this.list3=list3;
			this.notepad=notepad;
			this.dialog=dialog;
		}
		public void actionPerformed(ActionEvent e){
			int style=Font.PLAIN;
			if(list2.getSelectedItem()=="粗体") style=Font.BOLD;
			else if(list2.getSelectedItem()=="斜体") style=Font.ITALIC;
			else if(list2.getSelectedItem()=="常规") style=Font.PLAIN;
			else {}
			font = new Font(list1.getSelectedItem(),style,Integer.parseInt(list3.getSelectedItem()));
			notepad.getMyText().setFont(font);
			dialog.setVisible(false);
		}
	}
	class HearQuit implements ActionListener{
		private JDialog dialog;
		public HearQuit(JDialog dialog){
			this.dialog=dialog;
		}
		public void actionPerformed(ActionEvent e){
			dialog.setVisible(false);
		}
	}
}

class Hear4_1 implements ActionListener{
	public Hear4_1(){}
	public void actionPerformed(ActionEvent e){
		JOptionPane.showMessageDialog(new JPanel(),"3013216095出品","关于",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("resource\\flag.jpg"));
	}
}

class Hear4_2 implements ActionListener{
	public Hear4_2(){}
	public void actionPerformed(ActionEvent e){
		JOptionPane.showMessageDialog(new JPanel(),"点击文件——新建或打开开始","帮助",JOptionPane.INFORMATION_MESSAGE);
	}
}
