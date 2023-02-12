package main;
import javax.annotation.processing.RoundEnvironment;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Console;

public class Calculator implements ActionListener, KeyListener{
	
	JFrame frame;
	JTextField textField;
	JPanel panel;
	
	//adding buttons
	JButton[] num = new JButton[10];
	JButton[] operator = new JButton[8];
	JButton del, clr,equ;
//	JButton 
	
	//for operations
	public double n1=0,n2=0;
	public char operation=' ';
	public int operatorSet=0;
	public boolean decimalOne=false,decimalTwo=false;
	
	Calculator(){
		
		frame = new JFrame();
		frame.setSize(300,500);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Font buttonFont = new Font("Serif", Font.BOLD, 14);
		Font textFieldFont = new Font("Serif", Font.BOLD, 30);
		
		textField = new JTextField();
		textField.setBounds(50, 50, 200, 50);
		textField.setEditable(false);
		
		textField.setFont(textFieldFont);
		textField.setText("0");
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		
		del = new JButton("Delete");
		del.setBounds(50, 400, 100, 50);
		clr = new JButton("Clear");
		clr.setBounds(150, 400, 100, 50);
		del.addActionListener(this);
		del.setFocusable(false);
		clr.addActionListener(this);
		clr.setFocusable(false);
		
		equ = new JButton("=");
		equ.setBounds(50, 335, 200, 50);
		equ.addActionListener(this);
		equ.setFocusable(false);
		
		Font equFont = new Font("Serif", Font.BOLD, 80);
		equ.setFont(equFont);
		
		panel = new JPanel();
		
		String[] buttonCharacters= {"/","x","-","+","-/","."};//"-/") is old =
		
		for(int i =0;i<6;i++) {
			operator[i]=new JButton(buttonCharacters[i]);
			operator[i].addActionListener(this);
			operator[i].setFocusable(false);
			operator[i].setFont(buttonFont);
		}
		
		Font buttonFont2 = new Font("Serif", Font.BOLD, 12);
		operator[4].setFont(buttonFont2);
		
		panel.setBounds(50, 125, 200, 200);
		panel.setLayout(new GridLayout(4,4,10,10));	
		
		for(int i =0;i<10;i++) {
			num[i] = new JButton(Integer.toString(i));
			num[i].addActionListener(this);
			num[i].setFocusable(false);
			num[i].setFont(buttonFont);
			
		}
		
			
		//for adding elements in right order
		for(int i =0 ; i <=8; i++)
		{
			
			panel.add(num[(i+7-(i/3)*6)]);
			if((i+1)%3==0)
				panel.add(operator[i/3]);
		}
			
			panel.add(operator[5]);
			panel.add(num[0]);
			panel.add(operator[4]);
			panel.add(operator[3]);
		
		frame.add(panel);
		frame.add(textField);
		frame.add(equ);
		frame.add(del);
		frame.add(clr);
		
		//KeyboardListener key;
		//key = new KeyboardListener(this);
		//frame.addKeyListener(new KeyboardListener(this));
		//addKeyListener(new KeyboardListener(this));
		//frame.
		
		//KB kb = new KB();
		//frame.add(kb);
		
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		
		new Calculator();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for(int i=0;i<10;i++)
			if(e.getSource()==num[i])
			{
				if(operatorSet==1) {
					operatorSet=0;
					textField.setText("");
				}
				
				if(textField.getText().equals("0")) {
					textField.setText(String.valueOf(i));
				}
				else
					textField.setText(textField.getText().concat(String.valueOf(i)));
			}

		
			if(e.getSource()==operator[0]){// / division
		
				if(decimalOne)
					n1=decimalToDouble();
				else {
					n1=Double.parseDouble(textField.getText());
				}
				operation='/';
	//			textField.setText("/");
				operatorSet=1;
			
			}
			if(e.getSource()==operator[1]){// x
		
				if(decimalOne)
					n1=decimalToDouble();
				else {
					n1=Double.parseDouble(textField.getText());
				}
				operation='x';
	//			textField.setText("x");
				operatorSet=1;
			}
			if(e.getSource()==operator[2])// -
			{
				if(decimalOne)
					n1=decimalToDouble();
				else {
					n1=Double.parseDouble(textField.getText());
				}
				operation='-';
	//			textField.setText("-");
				operatorSet=1;
			}
			if(e.getSource()==operator[3])// +
			{
				if(decimalOne)
					n1=decimalToDouble();
				else {
					n1=Double.parseDouble(textField.getText());
				}
				operation='+';
	//			textField.setText("+");
				operatorSet=1;
				
			}
			
			
			if(e.getSource()==operator[4])// SQRT
			{
				n1=Double.parseDouble(textField.getText());
				operation=' ';
				double k=0;
				k=Math.sqrt(n1);
				k=roundNumber(k,6);
				textField.setText(Double.toString(k));
				operatorSet=1;
				

			}
			
			if(e.getSource()==operator[5])// the point .
			{
				if(decimalOne==false) {
					textField.setText(textField.getText().concat("."));
					decimalOne=true;
				}
					
			}
			
			if(e.getSource()==equ)// ===
			{
				operatorSet=0;
				n2=Double.parseDouble(textField.getText());
				textField.setText(calculate());
				operatorSet=1;
				decimalOne=false;
			}
			
			if(e.getSource()==clr)
			{
				n1=n2=0;
				operation=' ';
				operatorSet=0;
				textField.setText("0");
				decimalOne=false;
			}
			if(e.getSource()==del)
			{
				String S = textField.getText();
				if(S.length()==1 || S.equals("Cannot divide by 0.")) {
					textField.setText("0");
				}
				else {
					textField.setText("");
					for(int i = 0;i<S.length()-1;i++)
						textField.setText(textField.getText()+S.charAt(i));
				}
				decimalOne=false;
			
		}
	}
	
	

	private double roundNumber(double k, int i) {
		
		k*=Math.pow(10, i);
		k=(int)k;
		k/=Math.pow(10, i);
		return k;
	}
	private double roundNumber(double k) {
		
		k*=Math.pow(10, 6);
		k=(int)k;
		k/=Math.pow(10, 6);
		return k;
	}

	private double decimalToDouble() {
		
			String tempString=textField.getText();
			int index=tempString.indexOf(".");
			System.out.println("index "+index);
			
			String leftString=tempString.substring(0,index);
			String rightString=tempString.substring(index+1);
//			System.out.println("left is "+leftString+" right is "+rightString);
			int count=rightString.length();
			double result;
			result=Integer.parseInt(leftString);
			result*=Math.pow(10, count);
			result+=Integer.parseInt(rightString);
			result/=Math.pow(10, count);

		return result;
	}

	private String calculate() {
		double res=0;
		switch(operation) {
		case '/':
			if(n2==0)
				return "Cannot divide by 0.";
			res=roundNumber(n1/n2);
			break;
		case 'x':
			res=n1*n2;
			break;
		case '-':
			res=n1-n2;
			break;
		case '+':
			res=n1+n2;
			break;
		default:
			return "0";
		}
		if(res % 1 == 0)//check if res is int
			return Integer.toString((int) res);
		return Double.toString(res);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
		System.out.println(e.getKeyChar());
		switch(e.getKeyCode()) {
		
		case KeyEvent.VK_W:
			System.out.println("7");
			break;
		default:
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		System.out.println(e.getKeyChar());
		switch(e.getKeyCode()) {
		
		case KeyEvent.VK_W:
			System.out.println("7");
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
