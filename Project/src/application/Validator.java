package application;

public class Validator {
	
	private static int countChar(String str, char c) {
		int len = str.length();
		int count = 0;
		for (int i=0; i < len; ++i) {
			if (str.charAt(i) == c) {
				count++;
			}
		}
		return count;
	}
	
	private static String splice(String str, int a, int b) {
		StringBuilder s = new StringBuilder("");
		int len = str.length();
		for (int i=a; i <= b && i < len; ++i) {
			s.append(str.charAt(i));
		}
		return s.toString();
	}
	
	public static boolean emailFormat(String email) {
		if (email.startsWith(" ") || email.endsWith(" ")) return false;
		
		int len = email.length();
		if (len < 5) return false;
		if (countChar(email, ' ') != 0) return false;
		if (countChar(email, '@') != 1) return false;
		
		int at = email.indexOf('@');
		String a = splice(email, 0, at-1);
		String b = splice(email, at+1, len-1);
		
		if (a.isEmpty() || b.isEmpty()) return false;
		if (a.startsWith(".") || a.endsWith(".") || b.startsWith(".") || b.endsWith(".")) return false;
		if (b.contains(".") == false) return false;
		
		return true;
	}
	
	private static boolean charIsAlphabet(char c) {
		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <='Z')) {
			return true;
		}
		return false;
	}
	
	private static boolean charIsNumber(char c) {
		if (c >= '0' && c <= '9') {
			return true;
		}
		return false;
	}
	
	public static boolean alphanumeric(String password) {
		int len = password.length();
		boolean flag1 = false, flag2 = false;
		
		for (int i=0; i < len; ++i) {
			char c = password.charAt(i);
			if (charIsAlphabet(c)) flag1 = true;
			else if (charIsNumber(c)) flag2 = true;
			if (flag1 && flag2) return true;
		}
		
		return false; 
	}
}
