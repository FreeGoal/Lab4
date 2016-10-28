

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/*

目的：多项式的 输入   求值     求导

输入 :  多项式输入
        1.接收屏幕输入
        2.判断 合法与否
        3.输入扩展 合法判断
                      命令输入
        1.求导
        2.求值
求值 :  1.


 */

class Term {
	public int coefficient;/* fei static */
	public int fuhao;
	public String[] quantity = null;
	public int[] index;

	Term() {
		coefficient = 1;
		quantity = new String[] { "1", "1", "1", "1", "1", "1", "1", "1", "1", "1" };
		index = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		fuhao = 1;
	}
}

public class CALCULATOR {

	public static void main(final String[] args) {
		// TODO Auto-generated method stub
		Scanner scnn = new Scanner(System.in);
		String input = scnn.nextLine();
		// ch.close();
		String str = "";
		Term[] n = new Term[10];
		for (int i = 0; i < 10; i++) { // 构造
			n[i] = new Term();
		}

		while (!Judge(input).equals("Exit")) {
			// System.out.println("k1");
			if (Judge(input).equals("Expression")) {
				System.out.println("Correct Expression!");
				long a = System.currentTimeMillis();
				str = Arrangement_n(input, n);
				str = Arrangement_exp(str, n);
				str = Arrangement_n(str, n);
				if (str.charAt(0) != '-') {
					str = str.substring(1);
				}
				System.out.println(str);
				System.out.println("\r<br>执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
			} else if (Judge(input).equals("Derivative")) {
				// 求导 函数
				// System.out.println("1111111");
				long a = System.currentTimeMillis();
				str = derivative(input, str, n);
				for (int i = 0; i < 10; i++) { // 构造
					n[i] = new Term();
				}
				str = Arrangement_n(str, n); // 得到字符串
				str = Arrangement_exp(str, n);
				str = Arrangement_n(str, n);
				if (str.charAt(0) != '-') {
					str = str.substring(1);
				}
				System.out.println(str);
				System.out.println("\r<br>执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
			} else if (Judge(input).equals("simplify")) {
				// 化简
				long crntm = System.currentTimeMillis();
				str = Simplify(input, str, n);
				System.out.println(str);
				for (int i = 0; i < 10; i++) { // 构造
					n[i] = new Term();
				}
				str = Arrangement_n(str, n); // 得到字符串
				str = Arrangement_exp(str, n);
				str = Arrangement_n(str, n);
				if (str.charAt(0) != '-'){
					str = str.substring(1);
				}	
				System.out.println(str);
				System.out.println("\r<br>执行耗时 : " + (System.currentTimeMillis() - crntm) / 1000f + " 秒 ");
			}

			else if (Judge(input).equals("NULL")) {
				long a = System.currentTimeMillis();
				// str =Arrangement(str, n);
				System.out.println(str);
				System.out.println("\r<br>执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
			} else {
				System.out.println("Wrong Expression!please continue!");
			}
			scnn = new Scanner(System.in);
			input = scnn.nextLine();
			// System.out.println("***************"+input);
		}
		scnn.close();
	}

	// 求导
	public static String derivative(final String input, final String str, final Term[] n) {
		// System.out.println(input);
		// System.out.println(str);
		String strrrr = "";
		String strTemp = input.substring(4).trim();
		boolean flag = false;
		String regex = "[\\+-]";
		String[] arr1 = str.split(regex);
		for (int k1 = 0; k1 < 10; k1++) {
			for (int k2 = 0; k2 < 10; k2++) {
				if (strTemp.equals(n[k1].quantity[k2])) {
					flag = true;
				}
			}
		}

		// System.out.println(str.charAt(str.indexOf(arr1[i1+1])-1));
		if (flag) { // 存在 目标变量
			int i1 = 0; // 项的计数
			for (String s3 : arr1) {
				flag = false;
				for (int i2 = 0; i2 < 10; i2++) {
					if (strTemp.equals(n[i1].quantity[i2])) {
						n[i1].coefficient = n[i1].coefficient * n[i1].index[i2];
						n[i1].index[i2]--;
						flag = true;
						break;
					}
				}
				if (!flag) { // 如果 本项不存在 求导目标
					strrrr = strrrr + "";
				} else { // 如果本项存在 求导目标
					if (n[i1].fuhao == 1) {
						strrrr = strrrr + '+';
					} else{
						strrrr = strrrr + '-';
					}
						
					strrrr = strrrr + n[i1].coefficient;
					for (int i3 = 0; !n[i1].quantity[i3].equals("1"); i3++) {
						for (int i4 = 0; i4 < n[i1].index[i3]; i4++) {
							strrrr = strrrr + "*" + n[i1].quantity[i3];
						}
					}
				}
				i1++;
			}
		} else {
			System.out.println("no variable!");
			return str;
		}
		if (strrrr.equals("")) { // 为 0
			strrrr = "0";
		}
		if (strrrr.charAt(0) != '-')
			strrrr = strrrr.substring(1, strrrr.length());
		return strrrr;
	}
	// 3*4*5*6*m*m*m*v*v*9+6*c+h*9
	// 2*3*4 * 6 * m+ k*p* 1 + lll* 5
	// 5*5+6*6*a+9*d*gg+9
	// !d/d m

	// 判断输入
	public static String Judge(String input) { // 判断

		String regex2;
		if (input.charAt(0) == '!') { // command
			if (input.equals("!Exit")) {
				return "Exit";
			}
			regex2 = "!simplify";
			if (input.matches(regex2)) {
				// System.out.println(input);
				return "NULL";
			}
			regex2 = "(!simplify([ ]*[a-zA-Z]*[ ]*=[ ]*[1-9]\\d*[ ]*)*)";
			if (input.matches(regex2)) {
				// System.out.println("Simplify");
				return "simplify";
			}
			regex2 = "!d/d[ ]*[a-zA-Z]*";
			if (input.matches(regex2)) {
				// System.out.println("Derivative");
				return "Derivative";
			}
			// System.out.println("illegal input!");
			return "Error!";
		}

		else { // expression
			regex2 = "([ ]*-?[ ]*([1-9]\\d*\\.?\\d*|[a-zA-Z]*)[ ]*([\\+-]|[\\*]))*[ ]*(([1-9]\\d*\\.?\\d*)|[a-zA-Z]*[ ]*)";
			if (input.matches(regex2)) {
				return "Expression";
			} else {
				// System.out.println("Wrong Expression");
				return "Wrong Expression";
			}
		}
		// return "Error!";
	}

	// 数值带入化简
	public static String Simplify(String input, String str, Term[] n) {
		String regex = "[a-zA-Z]*[ ]*=[ ]*[1-9]\\d*";
		Pattern p = Pattern.compile(regex);
		Matcher mtch = p.matcher(input);
		while (mtch.find()) {

			String strTemp = mtch.group();
			strTemp = strTemp.replaceAll("[ ]*", "");
			String quantity = strTemp.substring(0, strTemp.indexOf("="));
			String value = strTemp.substring(strTemp.indexOf("=") + 1, strTemp.length());
			if (str.indexOf(quantity) >= 0) {
				str = str.replaceAll(quantity, value);
			} else {
				System.out.println("Sorry no variable!" + ":" + quantity);
			}
		}
		for (int i = 0; i < 10; i++) { // 构造
			n[i] = new Term();
		}
		return str;
	}

	// 整理表达式
	public static String Arrangement_exp(String string, Term[] n) {
		// 合并所有常数项

		int tempChangshu = 0;
		string = string.replaceAll("([\\+-])", " $1"); // 保留每一项的符号
		String regex2 = " ";
		String[] arr2 = string.split(regex2); // 每一项带符号
		String strrrr = "";
		for (int f = 1; f < arr2.length; f++) {
			if ((!arr2[f].matches(".*[A-Za-z]+.*"))) { // 数字即常数项
				String kString = arr2[f].substring(1);
				if (arr2[f].charAt(0) == '+') {
					tempChangshu = tempChangshu + Integer.parseInt(kString);
				} else {
					tempChangshu = tempChangshu - Integer.parseInt(kString);
				}
					
				arr2[f] = "";
			}
			strrrr = strrrr + arr2[f];
		}
		if (tempChangshu != 0) {
			strrrr = tempChangshu + strrrr;
		}
			
		return strrrr;
	}

	// 整理 Term n
	public static String Arrangement_n(String input, Term[] n) { // 存储结构体 化简表达式
		// 整理zheng li
		// System.out.println(input);
		if (input.charAt(0) != '-' && input.charAt(0) != '+'){
			input = "+" + input;
		}
		input = input.replaceAll("[ ]*", ""); // 简化
		input = input.replaceAll("([\\+-])", " $1"); // 保留每一项的符号

		String regex = " ";
		String[] arr1 = input.split(regex); // 每一项带符号

		for (int i = 0; i < 10; i++) { // 构造
			n[i] = new Term();
		}

		int tempCoefficient = 1;
		int tempN1 = 0;
		int tempN2 = 0;
		int pos = 0;
		int m = 0;
		String string = "";
		boolean flag = false;

		for (m = 1; m < arr1.length; m++) { // 存进结构体
			// 遍历每一项
			// ******符号
			String s1 = arr1[m];
			if (s1.charAt(0) == '+') {
				n[m - 1].fuhao = 1;
				string = string + "+";
			} else {
				n[m - 1].fuhao = 0;
				string = string + "-";
			}
			s1 = s1.substring(1);
			regex = "\\*";
			String[] arr2 = s1.split(regex);
			tempCoefficient = 1;
			pos = 0; // 可以存储的位置
			for (String s2 : arr2) {
				// 遍历每个
				String regex1 = "\\d+";
				if (s2.matches(regex1)) { // 数字组合
					tempCoefficient = tempCoefficient * Integer.parseInt(s2);
				} else {
					// 字母组合
					flag = false; // 是否出现过
					tempN2 = 0;
					for (int i = 0; i <= pos; i++) { // 每一项遍历所有未知数
						if (s2.equals(n[tempN1].quantity[i])) {
							n[tempN1].index[tempN2]++;
							flag = true;
							break;
						}
						tempN2++;
					}
					if (!flag) {
						n[tempN1].quantity[pos] = s2;
						n[tempN1].index[pos] = 1;
						pos++;
					}
				}
			}

			// 每一项系数
			n[tempN1].coefficient = tempCoefficient;

			// 准备 字符串
			string = string + n[tempN1].coefficient;

			// 每一项内部无未知数
			for (int i3 = 0; !n[tempN1].quantity[i3].equals("1"); i3++) { // 遍历到最后变量
				for (int i4 = 0; i4 < n[tempN1].index[i3]; i4++) { // 小于该次数
					string = string + "*" + n[tempN1].quantity[i3]; // 加变量和乘号
				}
			}
			// 每一项前添加 + 或 -
			tempN1++;
			tempCoefficient = 1;
		}
		// System.out.println(string);
		return string;
	} // 5*5+6*6*a+9*d*gg+9
}