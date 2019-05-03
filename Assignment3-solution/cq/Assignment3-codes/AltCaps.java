/*
 * File: AltCaps.java
 * ------------------
 * A sandcastle warmup for assgignment 4
 */

import acm.program.*;

public class AltCaps extends ConsoleProgram {

	/**
	 * Method: AltCaps
	 * ---------------
	 * Takes in an input string and returns the same string,
	 * except that the capitalization of the letters is changed
	 * to be alternating.
	 * Example usage:
	 * altCaps("aaaaa") -> "aAaAaA"
	 * altCaps("hello world") -> "hElLo WoRlD"
	 */
	private String altCaps(String input) {
		String output0= "";
		String output = "";
		// TODO: implement altCaps!
		//��Ҫʵ�ֵĹ��ܣ����Կ��ַ�����ȫ�����Сд����д��Сд����д�ĸ�ʽ
		String input0 = input.toLowerCase();
		char[] a = input0.toCharArray();
		for(int i = 0; i < a.length; i++)
		{
			if(!((a[i] >= 'a'&&a[i] <= 'z') || (a[i] >= 'A'&&a[i] <= 'B')))
				continue;
			else
				output0 = output0 + a[i];
//				if(!Character.isLowerCase(a[i]))
//					 a[i] = Character.toLowerCase(a[i]);
//				if(!Character.isUpperCase(a[i]))
//					a[i] = Character.toUpperCase(a[i]);
			
		}
		String output1 = output0.toLowerCase();
		String output2 = output0.toUpperCase();
		char[] b = output1.toCharArray();//�뵽���ǣ�������ĸ�ַ�ȥ���Ժ�õ�һ��ȫ����ĸ�����飬
		char[] c = output2.toCharArray();
		//Ȼ�������ĸ��Сת���Ժ��ٺϲ��ַ�
		for(int i = 1; i < b.length; i+=2)
		{
			b[i] = c[i];
		}
		//��ʱ��b�����Ѿ����ɼ����ĸչʾ
		//�ж��ַ���������ַ����������Ǿ�ȫ���滻
		for(int i = 0, j = 0; i < a.length; i++,j++)
		{
			if(!((a[i] >='a'&& a[i] <='z') || (a[i] >= 'A'&&a[i] <= 'B')))//���������ĸ��ע���Ǵ��ڵ��ڻ�����С�ڵ��ڣ�
			{
				j--;
			}
			else
			{
				a[i] = b[j];
			}
		}
		for(int i = 0; i < a.length; i++)
		{
			output = output + a[i];
		}
		return output;
	}

	/****************************************************
	 *                  STARTER CODE                    *
	 * You can read this code, but you should not edit  *
	 * It (except to add more tests, if you so desire)  *
	 ****************************************************/

	// an instance variable which keeps track of how many tests 
	// have been run.
	private int testIndex = 0;
	
	// This run method executes a barrage of tests.
	public void run() {
		runTest("aaaaaa", "aAaAaA");
		runTest("bbbbbb", "bBbBbB");
		runTest("hello", "hElLo");
		runTest("hello world", "hElLo WoRlD");
		runTest("i love the beach", "i LoVe ThE bEaCh");
		runTest("----altj----", "----aLtJ----");
	}

	/**
	 * Method: Run Test
	 * ----------------
	 * Takes in an input and an expected output, and checks
	 * if the method altCap produces the correct output! Each
	 * call runs exactly one test.
	 */
	private void runTest(String input, String expectedOutput) {
		// call the altCaps method!
		String output = altCaps(input);
		
		// print out the results
		println("Test #:   " + testIndex);
		println("Input:    " + input);
		println("Output:   " + output);
		println("Expected: " + expectedOutput);
		
		// don't forget to use .equals when comparing strings
		if(expectedOutput.equals(output)) {
			println("Test passed");
		} else {
			println("Test failed");
		}
		
		// this adds a blank line so each test looks like a
		// paragraph of text
		println("");
		
		// keep track of how many tests have been run
		testIndex++;
	}
	
	private void replace(String str, char content, int index)
	{
		//���أ�			��ͷ��ʼ��ָ���ַ�λ�õ��ַ�����ע�ⲻ�������棬Ȼ�������ӵ��ַ������Ӻ���
		str = str.substring(0, index) + content + str.substring(index+1);
	}


}
