//package Calculator;
package Calculator;

public class ScienCalculator {

    public int angle_metric = 0;    //三角函数计算的方式，0为角度值，1为弧度值

    String fPower(String con_str) {
        while (con_str.contains("^")) {
            //1.中间寻找的点
            int midIndex = con_str.lastIndexOf("^");
            //2.获取左边参数
            double leftNum; //左边的数
            String leftStr; //左边math字符串
            int leftIndex = midIndex - 1;
            if (con_str.charAt(leftIndex) == ')') {        //1.左边是一个表达式，即左边用括号括起来
                int i = leftIndex - 1;
                int LeftBrackSum = 0;
                int RightBrackSum = 1;

                while (LeftBrackSum != RightBrackSum) {
                    if (con_str.charAt(i) == '(') {
                        LeftBrackSum += 1;
                    }
                    if (con_str.charAt(i) == ')') {
                        RightBrackSum += 1;
                    }
                    i--;
                }
                String subLeftMath = con_str.substring(i + 2, leftIndex);
                if (subLeftMath.contains("sin") || subLeftMath.contains("^") || subLeftMath.contains("cos") || subLeftMath.contains("tan") || subLeftMath.contains("ln") ||
                        subLeftMath.contains("log") || subLeftMath.contains("!") || subLeftMath.contains("abs")) {
                    String subLeftMath1 = subLeftMath;
                    leftNum = Double.parseDouble(fScienceCalcuator(subLeftMath1));
                } else {
                    leftNum = Double.parseDouble(fBaseCalcuator(subLeftMath));
                }
                if (leftNum == Double.MAX_VALUE) //每次计算要判断是否出现 math error
                {
                    return String.valueOf(Double.MAX_VALUE);
                }
                leftStr = "(" + subLeftMath + ")";
            } else {                                    //2.左边是一个数
                //注意：判定index范围一定要在左边，否则可能出现IndexOutOfRange异常
                while (leftIndex >= 0 && !isOper(con_str.charAt(leftIndex))) {
                    leftIndex--;
                }
                leftStr = con_str.substring(leftIndex + 1, midIndex);
                leftNum = Double.parseDouble(leftStr);
            }
            //3.获取右边参数
            double rightNum;
            String rightStr;
            int rightIndex = midIndex + 1;
            if (con_str.charAt(rightIndex) == '(') {
                int i = rightIndex + 1;
                int LeftBrackSum = 1;
                int RightBrackSum = 0;
                while (LeftBrackSum != RightBrackSum) {
                    if (con_str.charAt(i) == '(') {
                        LeftBrackSum += 1;
                    }
                    if (con_str.charAt(i) == ')') {
                        RightBrackSum += 1;
                    }
                    i++;
                }
                String subRightMath = con_str.substring(rightIndex + 1, i - 1);
                if (subRightMath.contains("sin") || subRightMath.contains("cos") || subRightMath.contains("^") || subRightMath.contains("tan") || subRightMath.contains("ln") ||
                        subRightMath.contains("log") || subRightMath.contains("!") || subRightMath.contains("abs")) {
                    String subRightMath1 = subRightMath;
                    rightNum = Double.parseDouble(fScienceCalcuator(subRightMath1));
                } else {
                    rightNum = Double.parseDouble(fBaseCalcuator(subRightMath));
                }
                if (rightNum == Double.MAX_VALUE) {
                    return String.valueOf(Double.MAX_VALUE);
                }
                rightStr = "(" + subRightMath + ")";
            } else {
                while (rightIndex < con_str.length() && !isOper(con_str.charAt(rightIndex))) {
                    rightIndex++;
                }
                rightStr = con_str.substring(midIndex + 1, rightIndex);
                rightNum = Double.parseDouble(rightStr);
            }
            //4.得到完整的运算式并运算和替换
            String wholeMath = leftStr + "^" + rightStr;
            double result = Math.pow(leftNum, rightNum);
            con_str = con_str.replace(wholeMath, String.valueOf(result));
        }
        return con_str;
    }


    String fTraingle(String con_str) {
        while (con_str.contains("sin")
                || con_str.contains("cos")
                || con_str.contains("tan")
                || con_str.contains("ln")
                || con_str.contains("log")
                || con_str.contains("abs")) {
            String tempor = null;       //用于暂时保留三角运算符
            int beginIndex = 0;     //保留运算符号后面括号的索引值
            String temp = "";
            if (con_str.contains("sin")
                    || con_str.contains("cos")
                    || con_str.contains("tan")
                    || con_str.contains("ln")
                    || con_str.contains("log")
                    || con_str.contains("abs")) {
                if (con_str.contains("sin")) {
                    tempor = "sin";
                    beginIndex = con_str.lastIndexOf(tempor) + 3;
                } else if (con_str.contains("cos")) {
                    tempor = "cos";
                    beginIndex = con_str.lastIndexOf(tempor) + 3;
                } else if (con_str.contains("tan")) {
                    tempor = "tan";
                    beginIndex = con_str.lastIndexOf(tempor) + 3;
                } else if (con_str.contains("ln")) {
                    tempor = "ln";
                    beginIndex = con_str.lastIndexOf(tempor) + 2;
                    if (beginIndex - 2 > 0) {
                        if (isNum(con_str.charAt(beginIndex - 3)) || con_str.charAt(beginIndex - 3) == '.') {
                            int i = con_str.lastIndexOf(tempor) - 1;
                            while ((isNum(con_str.charAt(i)) || con_str.charAt(i) == '.') && i != 0) {
                                i--;
                            }
                            //进行字符串的赋值
                            if (i > 0) {
                                temp = con_str.substring(i + 1, con_str.lastIndexOf(tempor));
                            } else {
                                temp = con_str.substring(i, con_str.lastIndexOf(tempor));
                            }
                        }
                        if (con_str.charAt(beginIndex - 3) == ')') {
                            int leftIndex = beginIndex - 3;
                            int i = leftIndex - 1;
                            int LeftBrackSum = 0;
                            int RightBrackSum = 1;

                            while (LeftBrackSum != RightBrackSum) {
                                if (con_str.charAt(i) == '(') {
                                    LeftBrackSum += 1;
                                }
                                if (con_str.charAt(i) == ')') {
                                    RightBrackSum += 1;
                                }
                                i--;
                            }
                            temp = con_str.substring(i + 1, leftIndex + 1);
                            System.out.println(temp + "templn");
                        }
                    }
                } else if (con_str.contains("abs")) {
                    tempor = "abs";
                    beginIndex = con_str.lastIndexOf(tempor) + 3;
                } else if (con_str.contains("log")) {
                    tempor = "log";
                    beginIndex = con_str.lastIndexOf(tempor) + 3;
                    if (beginIndex - 3 > 0) {
                        if (isNum(con_str.charAt(beginIndex - 4)) || con_str.charAt(beginIndex - 4) == '.') {
                            int i = con_str.lastIndexOf(tempor) - 1;
                            while ((isNum(con_str.charAt(i)) || con_str.charAt(i) == '.') && i != 0) {
                                i--;
                            }
                            //进行字符串的赋值
                            if (i > 0) {
                                temp = con_str.substring(i + 1, con_str.lastIndexOf(tempor));
                            } else {
                                temp = con_str.substring(i, con_str.lastIndexOf(tempor));
                            }
                        }
                        if (con_str.charAt(beginIndex - 4) == ')') {
                            int leftIndex = beginIndex - 4;
                            int i = leftIndex - 1;
                            int LeftBrackSum = 0;
                            int RightBrackSum = 1;
                            while (LeftBrackSum != RightBrackSum) {
                                if (con_str.charAt(i) == '(') {
                                    LeftBrackSum += 1;
                                }
                                if (con_str.charAt(i) == ')') {
                                    RightBrackSum += 1;
                                }
                                i--;
                            }
                            temp = con_str.substring(i + 1, leftIndex + 1);
                        }
                    }
                }
            }
            //1.获取()内运算式并计算出结果，此时假设()不再包含复杂的科学运算
            if (con_str.charAt(beginIndex) == '(') {
                int i = beginIndex + 1;
                int LeftBrackSum = 1;
                int RightBrackSum = 0;
                while (LeftBrackSum != RightBrackSum) {
                    if (con_str.charAt(i) == '(') {
                        LeftBrackSum += 1;
                    }
                    if (con_str.charAt(i) == ')') {
                        RightBrackSum += 1;
                    }
                    i++;
                }
                //获取括号内得字符串
                String subMath = con_str.substring(beginIndex + 1, i - 1);
                double subResult;
                if (subMath.contains("sin") || subMath.contains("cos") || subMath.contains("^") || subMath.contains("tan") || subMath.contains("ln") ||
                        subMath.contains("log") || subMath.contains("!") || subMath.contains("abs")) {
                    String subRightMath1 = subMath;
                    subResult = Double.parseDouble(fScienceCalcuator(subRightMath1));
                } else {
                    subResult = Double.parseDouble(fBaseCalcuator(subMath));
                }
                if (subResult == Double.MAX_VALUE) //每次计算要判断是否出现 math error
                {
                    return String.valueOf(Double.MAX_VALUE);
                }
		        /**3.匹配scienceOper进行科学运算，并替换相应部分*/
                String tempMath;
                double tempResult = 0;
                int DEG = 0; //判断角度制
                switch (tempor) {
                    case "sin":
                        tempMath = "sin(" + subMath + ")";
                        if (angle_metric == DEG) {
                            tempResult = Math.sin(subResult / 180 * Math.PI); //将默认的 Rad → Deg
                        } else {
                            tempResult = Math.sin(subResult);
                        }
                        con_str = con_str.replace(tempMath, String.valueOf(tempResult));
                        break;
                    case "cos":
                        tempMath = "cos(" + subMath + ")";
                        if (angle_metric == DEG) {
                            tempResult = Math.cos(subResult / 180 * Math.PI);
                        } else {
                            tempResult = Math.cos(subResult);
                        }
                        con_str = con_str.replace(tempMath, String.valueOf(tempResult));
                        break;
                    case "tan":
                        tempMath = "tan(" + subMath + ")";
                        if (angle_metric == DEG) {
                            if (subResult == 90) {
                                con_str = "error";
                            } else {
                                tempResult = Math.tan(subResult / 180 * Math.PI);
                            }
                        } else {
                            if (subResult == Math.PI / 2) {
                                con_str = "error";
                            } else {
                                tempResult = Math.tan(subResult);
                            }
                        }
                        con_str = con_str.replace(tempMath, String.valueOf(tempResult));
                        break;
                    case "ln":
                        tempMath = temp + "ln(" + subMath + ")";
                        if (!temp.equals("")) {
                            temp = fScienceCalcuator(temp);
                            subResult = Math.pow(subResult, Double.parseDouble(temp));
                        }
                        tempResult = Math.log(subResult);
                        con_str = con_str.replace(tempMath, String.valueOf(tempResult));
                        break;
                    case "log":
                        tempMath = temp + "log(" + subMath + ")";
                        if (!temp.equals("")) {
                            temp = fScienceCalcuator(temp);
                            subResult = Math.pow(subResult, Double.parseDouble(temp));
                        }
                        tempResult = Math.log10(subResult);
                        con_str = con_str.replace(tempMath, String.valueOf(tempResult));
                        break;
                    case "abs":
                        tempMath = "abs(" + subMath + ")";
                        tempResult = Math.abs(subResult);
                        con_str = con_str.replace(tempMath, String.valueOf(tempResult));
                        break;
                    default:
                        break;
                }
            }
        }
        return con_str;
    }

    private String fBrackAddZero(String con_str) {
        for (int i = 0; i < con_str.length(); i++) {
            String mind_str;
            String temp_str;
            if (con_str.charAt(i) == '(') {
                int Index = i;
                int j = Index + 1;
                if (con_str.charAt(j) == '-') {
                    int LeftBrackSum = 1;
                    int RightBrackSum = 0;
                    while (LeftBrackSum != RightBrackSum) {
                        if (con_str.charAt(j) == '(') {
                            LeftBrackSum += 1;
                        }
                        if (con_str.charAt(j) == ')') {
                            RightBrackSum += 1;
                        }
                        j++;
                    }
                    mind_str = con_str.substring(Index + 1, j - 1);
                    con_str = con_str.replace(mind_str, "0" + mind_str);
                }
            }
        }
        return con_str;
    }

    private String fBaseCalcuator(String con_str) {
        BaseCalculator sc = new BaseCalculator();
        con_str = fBrackAddZero(con_str);
        sc.math = con_str;
        sc.fCalcuate();
        String result = sc.restult;
        BaseCalculator.data.clear();
        BaseCalculator.stack.clear();
        return result;
    }

    public String fScienceCalcuator(String con_str) {
        con_str = con_str.replace(" ", "");   //去掉math中的所有空格
        con_str = con_str.replaceAll("π", String.valueOf(Math.PI));   //替换π
        con_str = con_str.replaceAll("e", String.valueOf(Math.exp(1))); //替换自然指数e
        while (con_str.contains("^") || con_str.contains("sin") || con_str.contains("!") || con_str.contains("cos") || con_str.contains("tan") || con_str.contains("ln")
                || con_str.contains("log") || con_str.contains("abs")) {
            if (con_str.contains("!")) {
                con_str = fpreactorial(con_str);
            }
            con_str = fTraingle(con_str);
            con_str = fPower(con_str);
        }
        return fBaseCalcuator(con_str);
    }

    private boolean isOper(char con_char) {
        return con_char == '+' || con_char == '-' || con_char == '*' || con_char == '/';
    }

    private boolean isNum(char con_char) {
        return con_char == '0' || con_char == '1' || con_char == '2' || con_char == '3' || con_char == '4' || con_char == '5' || con_char == '6' || con_char == '7' ||
                con_char == '8' || con_char == '9';
    }

    private String fpreactorial(String con_str) {
        while (con_str.contains("!")) {
            int Index = con_str.lastIndexOf("!");
            int RightIndex = Index - 1;
            String PreactorialResult;
            String left_str;
            if (con_str.charAt(RightIndex) == ')') {
                //左边是式子
                int i = RightIndex - 1;
                int LeftBrackSum = 0;
                int RightBrackSum = 1;
                String LeftSum;
                while (LeftBrackSum != RightBrackSum) {
                    if (con_str.charAt(i) == '(') {
                        LeftBrackSum += 1;
                    }
                    if (con_str.charAt(i) == ')') {
                        RightBrackSum += 1;
                    }
                    i--;
                }
                String subLeftMath = con_str.substring(i + 2, RightIndex);
                System.out.println(subLeftMath + "开头处理");
                left_str = "(" + subLeftMath + ")" + "!";
                if (con_str.contains("^") || con_str.contains("sin") || con_str.contains("cos") || con_str.contains("tan") || con_str.contains("!") || con_str.contains("ln")
                        || con_str.contains("log")) {
                    LeftSum = fScienceCalcuator(subLeftMath);
                } else {
                    LeftSum = fBaseCalcuator(subLeftMath);
                }
                if (LeftSum == String.valueOf(Double.MAX_VALUE)) {
                    return String.valueOf(Double.MAX_VALUE);
                }
                PreactorialResult = factorial(LeftSum);
                con_str = con_str.replace(left_str, PreactorialResult);
            } else if (isNum(con_str.charAt(RightIndex))) {             //左边是一个数
                System.out.println(con_str + " " + RightIndex);
                int i = RightIndex;
                String leftMath = null;
                while (i >= 0 && !isOper(con_str.charAt(i))) {
                    i--;
                }
                leftMath = con_str.substring(i + 1, Index);
                System.out.println(leftMath + "阶乘开头处理");
                left_str = leftMath + "!";
                PreactorialResult = factorial(leftMath);
                con_str = con_str.replace(left_str, PreactorialResult);
            }
        }
        return con_str;
    }

    private String factorial(String con_str) {
        if (con_str.contains("^") || con_str.contains("sin") || con_str.contains("cos") || con_str.contains("tan") || con_str.contains("ln")
                || con_str.contains("log") || con_str.contains("abs")) {
            con_str = fScienceCalcuator(con_str);
        } else {
            con_str = fBaseCalcuator(con_str);
        }
        if (Double.parseDouble(con_str) % 1 != 0) {
            return "error";
        } else {
            if (con_str.equals("0")) {
                return "1";
            } else {

                double sum = 1;
                for (int i = 1; i <= Double.parseDouble(con_str); i++) {
                    sum = sum * i;
                }
                return String.valueOf(sum);
            }
        }
    }
}