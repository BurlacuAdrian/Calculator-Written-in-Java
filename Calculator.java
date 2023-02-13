package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
		
		textField.addKeyListener(this);
		
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		
		System.out.println();
		new Calculator();
	}
	
	public void displayNumber(int i) {
		if(operatorSet==1 && decimalTwo==false) {
			textField.setText("");
		}
		
		if(textField.getText().equals("0")) {
			textField.setText(String.valueOf(i));
		}
		else
			if(textField.getText().length()<8)//LIMIT NUMBERS
				textField.setText(textField.getText().concat(String.valueOf(i)));
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for(int i=0;i<10;i++)
			if(e.getSource()==num[i])
				displayNumber(i);

			if(e.getSource()==operator[0])// / division
				division();

			if(e.getSource()==operator[1])// x
				multiplication();

			if(e.getSource()==operator[2])// -
				subtraction();

			if(e.getSource()==operator[3])// +
				addition();

			if(e.getSource()==operator[4])// SQRT
				squareRoot();
			
			if(e.getSource()==operator[5])// decimal point .
				decimalPoint();
			
			if(e.getSource()==equ)// ===
				equal();
	
			if(e.getSource()==clr)
				clear();
			if(e.getSource()==del)
				del();
	
	}
	
	private void del() {
		String bufferString = textField.getText();
		//checks if textField only has one number, that is deleted or 
		//if previous division was by 0
		if(bufferString.length()==1 || bufferString.equals("Cannot divide by 0.")) {
			textField.setText("0");
		}
		else {
			textField.setText("");
			for(int i = 0;i<bufferString.length()-1;i++)
				textField.setText(textField.getText()+bufferString.charAt(i));
		}
		//check if it deleted decimal
		if(bufferString.charAt(bufferString.length()-1)=='.') {
			//check which number it came from
			if(decimalTwo)
				decimalTwo=false;
			else {
				decimalOne=false;
			}
		}
		
	}

	private void clear() {
		n1=n2=0;
		operation=' ';
		operatorSet=0;
		textField.setText("0");
		decimalOne=decimalTwo=false;
		
		
	}

	private void decimalPoint() {
		if(decimalOne==false) {
			textField.setText(textField.getText().concat("."));
			decimalOne=true;
		}else
		if(decimalTwo==false) {
			textField.setText(textField.getText().concat("."));
			decimalTwo=true;
		}
		
	}

	private void squareRoot() {
		n1=Double.parseDouble(textField.getText());
		operation=' ';
		double k=0;
		k=Math.sqrt(n1);
		k=roundNumber(k,6);
		textField.setText(Double.toString(k));
		operatorSet=1;
		
	}

	private void addition() {
		if(decimalOne)
			n1=decimalToDouble();
		else {
			n1=Double.parseDouble(textField.getText());
		}
		operation='+';
		operatorSet=1;
		
	}

	private void subtraction() {
		if(decimalOne)
			n1=decimalToDouble();
		else {
			n1=Double.parseDouble(textField.getText());
		}
		operation='-';
		operatorSet=1;
		
	}

	private void multiplication() {
		if(decimalOne)
			n1=decimalToDouble();
		else {
			n1=Double.parseDouble(textField.getText());
		}
		operation='x';
		operatorSet=1;
		
	}

	private void division() {
		if(decimalOne)
			n1=decimalToDouble();
		else {
			n1=Double.parseDouble(textField.getText());
		}
		operation='/';
		operatorSet=1;
		
	}

	private void equal() {
		if(operatorSet==1) {
			n2=Double.parseDouble(textField.getText());
			textField.setText(calculate());
			operatorSet=2;
			decimalOne=false;
		}
		else 
			if(operatorSet==2) {
				n1=Double.parseDouble(textField.getText());
				textField.setText(calculate());
				
			}
		
		decimalOne=false;
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
		
	    double result;
	    String tempString=textField.getText();
	    
		int index=tempString.indexOf(".");
		if(index==tempString.length()-1){//if decimal is last
		    result=Integer.parseInt(tempString.substring(0,index));
		}
		else
		{
		    	String leftString=tempString.substring(0,index);
		String rightString=tempString.substring(index+1);
		int count=rightString.length();
		
		result=Integer.parseInt(leftString);
		result*=Math.pow(10, count);
		result+=Integer.parseInt(rightString);
		result/=Math.pow(10, count);
		}

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
		return Double.toString(roundNumber(res));
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int key =e.getKeyChar();
		if(key>=48 && key< 58)
			displayNumber(key-48);
		else
			switch(key) {
			
			case KeyEvent.VK_ENTER:
				equal();
				break;
			case (int)'+':
				addition();
				break;
			case (int)'-':
				subtraction();
				break;
			case (int)'*':
				multiplication();
				break;
			case (int)'/':
				division();
				break;
			case (int)'.':
				decimalPoint();
				break;
			default:
				break;
			}

	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
