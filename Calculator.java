package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator implements ActionListener, KeyListener{
	
	final int DECIMAL_THRESHOLD=4;
	
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
	public int chain=-1;
	
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
		operator[4].setFont(buttonFont2);//sqrt
		
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
		
		System.out.println(Double.parseDouble("10.42")-1.9);
		new Calculator();
	}
	
	public void displayNumber(int i) {
//		if(operatorSet==1 && decimalTwo==false) {
		if(operatorSet==1) {
			textField.setText("");
			operatorSet=0;
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
		if(decimalTwo==false&&chain==1) {
			textField.setText(textField.getText().concat("."));
			decimalTwo=true;
		}
		
	}

	private void squareRoot() {
		n1=Double.parseDouble(textField.getText());
		operation=' ';
		double k=0;
		k=Math.sqrt(n1);
		k=roundNumber(k);
		textField.setText(Double.toString(k));
		operatorSet=1;
		
	}
	
	private void operationOnFirstNumber() {
		if(chain==1)
			equal();
		n1=Double.parseDouble(textField.getText());
		operatorSet=1;
		chain=1;
		decimalTwo=false;
		
	}

	private void addition() {
		
		operationOnFirstNumber();
		operation='+';
		
	}

	private void subtraction() {
		operationOnFirstNumber();
		operation='-';
	}

	private void multiplication() {
		operationOnFirstNumber();
		operation='x';
		
	}

	private void division() {
		operationOnFirstNumber();
		operation='/';
		
	}

	private void equal() {
		if(operatorSet==2) {//repeat last op
			n1=Double.parseDouble(textField.getText());
		}
		else {//normal
			n2=Double.parseDouble(textField.getText());
			operatorSet=2;
//			System.out.println("n1="+n1+ " n2="+n2);
		}
		textField.setText(calculate());
		
		decimalOne=false;
		chain=0;
	}
	

	private double roundNumber(double k) {
		
		k*=Math.pow(10, DECIMAL_THRESHOLD);
		k=(int)k;
		k/=Math.pow(10, DECIMAL_THRESHOLD);
		return k;
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