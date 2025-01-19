public abstract class Functions {
    public static boolean isNumber(String str) {
		if (str == null || str.isEmpty()) {
			return false;
		}
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
    static int math_operation(String operator,int num1,int num2) {
        switch (operator) {
            case "ADD": return num1 + num2;
            case "SUB": return num1 - num2;
            case "MUL": return num1 * num2;
            case "DIV": return num1 / num2;
            default: throw new IllegalArgumentException("Invalid operator: " + operator);
        }
      }
}
