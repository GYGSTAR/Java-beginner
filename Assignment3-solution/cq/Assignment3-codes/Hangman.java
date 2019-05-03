/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

import java.io.*;
import java.io.RandomAccessFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hangman extends ConsoleProgram {

	/***********************************************************
	 *              CONSTANTS                                  *
	 ***********************************************************/
	
	/* The number of guesses in one game of Hangman */
	private static final int N_GUESSES = 7;
	/* The width and the height to make the karel image */
	private static final int KAREL_SIZE = 150;
	/* The y-location to display karel */
	private static final int KAREL_Y = 230;
	/* The width and the height to make the parachute image */
	private static final int PARACHUTE_WIDTH = 300;
	private static final int PARACHUTE_HEIGHT = 130;
	/* The y-location to display the parachute */
	private static final int PARACHUTE_Y = 50;
	/* The y-location to display the partially guessed string */
	private static final int PARTIALLY_GUESSED_Y = 430;
	/* The y-location to display the incorrectly guessed letters */
	private static final int INCORRECT_GUESSES_Y = 460;
	/* The fonts of both labels */
	private static final String PARTIALLY_GUESSED_FONT = "Courier-36";
	private static final String INCORRECT_GUESSES_FONT = "Courier-26";
	
	//����Ϊȫ�ֱ���������ԭ��
	private boolean ending;
	
	private GLabel str2 = null;
	private GLabel str = null;
	
	private String guessCases = "";
    List<String> list = new ArrayList<String>();
	
	
	/***********************************************************
	 *              Instance Variables                         *
	 ***********************************************************/
	
	/* An object that can produce pseudo random numbers */
//	private RandomGenerator rg = new RandomGenerator();
	
	
	
	private GCanvas canvas = new GCanvas();
	
	/***********************************************************
	 *                    Methods                              *
	 ***********************************************************/
	
	
	
	public void init() {
		add(canvas);
	}
	
	public void run() {
		setSize(800, 500);
		
		draw();

    	Hangman content = new Hangman();
        content.initList("HangmanLexicon.txt");
        String con = content.getString();
        String txt = con.replaceAll("[^a-zA-Z]", "");//ȥ������tab���Ϳո�ͻس���
        
		// shall we?
		String guessWord = txt;
		
		String seeCase = "";
		for(int i = 0; i < guessWord.length(); i++)
		{
			seeCase = seeCase + "_ ";
		}
		println("Welcome to Hangman");
		println("Your word now looks like this:" + seeCase);
		
		//��ʽ��ʼ��Ϸ
		play(guessWord, seeCase);
	}
	
	/**
	 * Method: Get Random Word
	 * -------------------------
	 * This method returns a word to use in the hangman game. It randomly 
	 * selects from among 10 choices.
	 */

	//��������������
    public int getRandomNumber(int total) {
        return (int) (Math.random() * total);
    }
	
    //���ж�ȡ�ļ�����
	public void initList(String fileName) {
    	//�쳣����
        try 
        {														//ֻ��
            RandomAccessFile accessFile = new RandomAccessFile(fileName, "r");
            String str = "";
            while (null != (str = accessFile.readLine()))
            {
            	
                list.add(str);
            }
            accessFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	//��������ַ���
    public String getString() {
        if (null != list)
        {
            int line = getRandomNumber(list.size());
            return list.get(line);
        }
        
        return null;
    }
	
	private void draw()
	{
		drawBackground();
		drawUnbralla();
	}

	private void drawBackground() {
		GImage bg = new GImage("background.jpg");
		bg.setSize(canvas.getWidth(), canvas.getHeight());
		canvas.add(bg, 0, 0);
	}
	
	private void drawUnbralla() {
		GImage unbralla = new GImage("parachute.png");
		unbralla.setSize(290, 120);
		canvas.add(unbralla, 60, 60);
	}
	
	private GImage drawKarel() {
		GImage karel = new GImage("karel.png");
		karel.setSize(150, 150);
		canvas.add(karel, 130, 230);
		return karel;
	}
	
	private void drawKarel2() {
		GImage karel = new GImage("karelFlipped.png");
		karel.setSize(150, 150);
		canvas.add(karel, 130, 230);
	}
	
	private GObject[] drawLines() {
		//��ʼ������ֱ�� ���ݲµĴ�������
		int lines = N_GUESSES;
		GObject[] e = new GLine[lines];
		for(int i = 0; i < lines; i++)
		{
			int len = 150 + (100 / lines) * i;  
			e[i] = new GLine(len, 180, 200, 230);
			canvas.add(e[i]);
		}
		return e;
	}

//	private String getRandomWord() {
//		int index = rg.nextInt(10);
//		if(index == 0) return "BUOY";
//		if(index == 1) return "COMPUTER";
//		if(index == 2) return "CONNOISSEUR";
//		if(index == 3) return "DEHYDRATE";
//		if(index == 4) return "FUZZY";
//		if(index == 5) return "HUBBUB";
//		if(index == 6) return "KEYHOLE";
//		if(index == 7) return "QUAGMIRE";
//		if(index == 8) return "SLITHER";
//		if(index == 9) return "ZIRCON";
//		throw new ErrorException("getWord: Illegal index");
//	}
	
	private void play(String guessWord, String seeCase)
	{
		//����
		GObject[] el = drawLines();
		//�����˹�������
		GImage kar = drawKarel();
		ending = false;
		int times = N_GUESSES;
		String inputCase = "";
		while(!ending)
		{
			//���²���
			drawLabel(seeCase);
			//�´ʲ���
			drawLabel2(guessCases);
			
			if(times>0)
			{
				print("Yout guess:");
		
				String cases = readLine();
				char[] caseArr = cases.toLowerCase().toCharArray();
				while(!(caseArr.length<=1&&caseArr.length!=0))
				{
					println("Please input correctly!");
					cases = readLine();
					caseArr = cases.toLowerCase().toCharArray();
				}
					
				inputCase = cases;
					
				times--;
				canvas.remove(str);
				canvas.remove(str2);
				canvas.remove(el[times]);
				//�жϵ���
				seeCase = judgeCase(guessWord, inputCase, seeCase);
					//�ڴ����ڲ´ʳɹ�������,ע������ĵ��ʴ�Сд
				guessCases += cases;
			}
			else
			{
				canvas.remove(kar);
				drawKarel2();
				Gameover(guessWord);
				ending = true;//�������Ҳ�᷵�������ѭ��
			}
		}
	}
	
	private String judgeCase(String correct, String inputCases, String seeCase)
	{
		int count = 0;
		char[] correctCases = correct.toLowerCase().toCharArray();
		char[] inputCase = inputCases.toCharArray();
		char[] seeCases = seeCase.toCharArray();
		boolean doing = false;
		boolean change  = false;
		for(int i = 0; i < correctCases.length; i++)//�ж�
		{
			if(correctCases[i] == inputCase[0])//abcd --> a b c d  
			{								//	 0123	  0123456789
				change = true;
			}
		}
		
		for(int i = 0; i < correctCases.length; i++)
		{
			if(correctCases[i] == inputCase[0])
			{
				//��δ����ظ����ֵ���ĸ?��γ��־Ͷ�θ�ֵ��
				seeCases[2*i]  = correctCases[i];//ע�⸳ֵʱ��ļ��
			}
		}
		
		for(int i = 0; i < seeCases.length; i+=2)
		{
			if(seeCases[i]!='_')//��Ӧ�޸ĵ�λ�ò����»���
				count++;
		}
		
		if(count==correctCases.length)
		{
			doing = true;
		}
		
		if(!doing)
		{
			String out = "";
			if(change)
			{
				println("That guess is correct");
			}
			else
			{
				String outX = inputCase[0] + "";
				println("There are no '" + outX.toUpperCase() + "' in the word.");
			}
		
			print("Your word now looks like this:");
			for(int i = 0; i < seeCases.length; i++)
			{
				out += seeCases[i] + "";
			}
			 
			print(out.toUpperCase());			
			println();
			ending = false;
			
			return out;
		}
		else
		{
			println("That guess is correct.\nYou win.\nThe word was:" + correct);
			ending = true;
			return "";
		}
	}
	
	private void Gameover(String correct)
	{
		println("You're completely hung.");
		println("The word was:" + correct);
	}

	private void drawLabel(String seeCase) {
		str = new GLabel(seeCase.toUpperCase());
		str.setFont("Courier-20");
		canvas.add(str, canvas.getWidth()/2 - str.getWidth()/2, 420);
	}
	
	private void drawLabel2(String cases) {
		str2 = new GLabel(cases);
		str2.setFont("Courier-20");
		canvas.add(str2, canvas.getWidth()/2 - str2.getWidth()/2, 450);
	}
}
