package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 进行界面显示并添加监听
 * @author li192
 */
public class Calculator extends JFrame implements ActionListener {
    /**
     * 创建用于输入的文本框
     */
    TextArea text = new TextArea("", 2, 10, TextArea.SCROLLBARS_HORIZONTAL_ONLY);
    /**
     * 卡片布局模板及其控制按钮的创建
     */
    JPanel CardPanel = new JPanel();
    CardLayout cards = new CardLayout();
    JButton record = new JButton("历史记录");
    JButton mermory = new JButton("内存");
    String ReSetrecordArea = "历史记录\n三角函数默认以角度制进行计算";
    TextArea recordArea = new TextArea(ReSetrecordArea, 37, 26);
    TextArea mermoryArea = new TextArea("内存", 37, 26);
    /**存储运算过程*/
    String mathPast = "";//用于存储过去的运算过程
    String mathNow = "";//用于存储现在的运算过程
    String showNow = "";
    /**创建存储变量*/
    private String m;
    private String result = "";
    String temp = "";//用于暂时保留数字
    /**用于判断是否需要清空mathNow进行新的运算*/
    private int equal_flag = 0;
    /**存储三角函数计算的方式，0为角度制，1为弧度制*/
    private int TrigCalculMethod = 0;
    /**创建x的平方，x的三次方，x的y次方，x开y次方按钮*/
    ImageIcon squareimg = new ImageIcon("src\\Calculator\\x的平方.png");   // 创建图片对象
    ImageIcon cubeimg = new ImageIcon("src\\Calculator\\x的三次方.png");    // 创建图片对象
    ImageIcon powerimg = new ImageIcon("src\\Calculator\\x的y次方.png");   // 创建图片对象
    ImageIcon readicationimg = new ImageIcon("src\\Calculator\\x开y次方.png"); // 创建图片对象

    Image squareimage = squareimg.getImage();
    Image cubeimage = cubeimg.getImage();
    Image powerimage = powerimg.getImage();
    Image readicationimage = readicationimg.getImage();

    /**为把它缩小点，先要取出这个Icon的image ,然后缩放到合适的大小*/
    Image squaresmallImage = squareimage.getScaledInstance(25, 25, Image.SCALE_FAST);
    Image cubesmallImage = cubeimage.getScaledInstance(25, 25, Image.SCALE_FAST);
    Image powersmallImage = powerimage.getScaledInstance(25, 25, Image.SCALE_FAST);
    Image readictionsmallImage = readicationimage.getScaledInstance(30, 30, Image.SCALE_FAST);

    /**
     * 再由修改后的Image来生成合适的Icon;
     *Image对象创建ImageIcon对象,可查阅ImageIcon的构造方法
     * */
    ImageIcon smallIcon = new ImageIcon(squaresmallImage);
    ImageIcon cmallIcon = new ImageIcon(cubesmallImage);
    ImageIcon pmallIcon = new ImageIcon(powersmallImage);
    ImageIcon rmallIcon = new ImageIcon(readictionsmallImage);

    JButton squarebuttion = new JButton(smallIcon);
    JButton cubebuttion = new JButton(cmallIcon);
    JButton powerbuttion = new JButton(pmallIcon);
    JButton readictionbuttion = new JButton(rmallIcon);

    public Calculator() {
        super("计算器");
        this.setBounds(400, 250, 900, 680);
        //设置窗体图片
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗体的布局形式
        GridBagLayout gr = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        //设置窗体布局
        this.setLayout(gr);

        //创建模板
        JPanel jp1 = createPanel1();
        JPanel jp2 = createPanel2();

        //对整个窗体进行网格包布局 jp1,jp4
        gc.weightx = 90;
        gc.weighty = 68;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridwidth = 7;
        gc.gridheight = 10;

        gc.gridx = 0;
        gc.gridy = 0;
        gr.setConstraints(jp1, gc);
        this.add(jp1);
        gc.gridwidth = 3;
        gc.gridheight = 10;
        gc.gridx = 7;
        gc.gridy = 0;
        gr.setConstraints(jp2, gc);
        this.add(jp2);
        this.setVisible(true);
    }

    private JPanel createPanel1() {

        //总的面板
        JPanel panel = new JPanel();
        JPanel keyPanel = new JPanel();
        /**设置每个面板的布局*/
        panel.setLayout(new BorderLayout());
        keyPanel.setLayout(new GridLayout(6, 7, 2, 2));

        /**实例化按钮监听对象*/
        ButtonAction ba = new ButtonAction();
        JButton[][] button = new JButton[][]{
                {
                    new JButton("("), new JButton(")"), new JButton("a/b"),
                    new JButton("mc"), new JButton("m+"), new JButton("m-"),
                    new JButton("mr"),

                },
                {
                    squarebuttion, cubebuttion, powerbuttion,
                    new JButton("C"), new JButton("/"), new JButton("*"),
                    new JButton("ms"),
                },
                {
                    new JButton("X!"), new JButton("√"), readictionbuttion,
                    new JButton("7"), new JButton("8"), new JButton("9"),
                    new JButton("←"),
                },
                {
                    new JButton("e"), new JButton("ln"), new JButton("log"),
                    new JButton("4"), new JButton("5"), new JButton("6"),
                    new JButton("-"),
                },
                {
                    new JButton("sin"), new JButton("cos"), new JButton("tan"),
                    new JButton("1"), new JButton("2"), new JButton("3"),
                    new JButton("+"),
                },
                {
                    new JButton("Deg"), new JButton("|x|"), new JButton("π"),
                    new JButton("%"), new JButton("0"), new JButton("."),
                    new JButton("="),
                }
        };
        //设置文本框的字体
        Font font = new Font("仿宋", Font.ROMAN_BASELINE, 56);
        text.setFont(font);
        text.setEditable(false);
        panel.setLayout(new BorderLayout());
        Font keyfont = new Font("Dialog", Font.BOLD, 20);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (j < 3) {
                    button[i][j].setFont(keyfont);
                }
                /**新增，设置按键为透明*/
                button[i][j].setOpaque(false);
                keyPanel.add(button[i][j]);
            }
        }
        panel.add("North", text);
        panel.add("Center", keyPanel);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                button[i][j].addActionListener(ba);
            }
        }
        button[5][0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = e.getActionCommand();
                if (input.equals("Deg")) {
                    button[5][0].setText("Rad");
                    TrigCalculMethod = 1;
                } else if (input.equals("Rad")) {
                    button[5][0].setText("Deg");
                    TrigCalculMethod = 0;
                }
            }
        });
        return panel;
    }

    private JPanel createPanel2() {

        JPanel panel1 = new JPanel();
        /**按钮面板*/
        JPanel panel2 = new JPanel();
        CardPanel.setBackground(Color.pink);
        panel1.setLayout(new BorderLayout());
        panel2.add(record);
        panel2.add(mermory);

        panel1.add("North", panel2);
        panel1.add("Center", CardPanel);

        /**设置历史记录框和保存框的字体*/
        Font font1 = new Font("Verdana", Font.ROMAN_BASELINE, 20);
        recordArea.setFont(font1);
        mermoryArea.setFont(font1);
        recordArea.setEditable(false);
        mermoryArea.setEditable(false);

        CardPanel.setLayout(cards);

        recordArea.setBackground(Color.gray);
        mermoryArea.setBackground(Color.gray);

        CardPanel.add(recordArea, "card1");
        CardPanel.add(mermoryArea, "card2");

        record.addActionListener((ActionListener) this);
        mermory.addActionListener((ActionListener) this);
        return panel1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //如果用户单击nextbutton，执行的语句
        if (e.getSource() == record) {
            //切换cardPanel面板中当前组件之后的一个组件
            //若当前组件为最后添加的组件，则显示第一个组件，即卡片组件显示是循环的。
            cards.first(CardPanel);
        }
        if (e.getSource() == mermory) {
            //切换cardPanel面板中当前组件之前的一个组件
            //若当前组件为第一个添加的组件，则显示最后一个组件，即卡片组件显示是循环的。
            cards.last(CardPanel);
        }
    }

    private class ButtonAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String input = e.getActionCommand();
            //常量运算符号
            if ("0".equals(input) || "1".equals(input) || "2".equals(input) || "3".equals(input) || "4".equals(input) || "5".equals(input) ||
                    "6".equals(input) || "7".equals(input) || "8".equals(input) || "9".equals(input) || ".".equals(input) || "(".equals(input) ||
                    ")".equals(input) || "e".equals(input) || "π".equals(input) || "%".equals(input)) {
                if (equal_flag == 1) {
                    if (".".equals(input)) {
                        mathNow = "0" + input;
                        showNow = "0" + input;
                        temp = "0.";
                    } else {
                        if (!"%".equals(input)) {
                            mathNow = input;
                            showNow = input;
                            temp = input;
                        }
                        if ("%".equals(input)) {
                            mathNow = result + "*0.01";
                            showNow = result + "%";
                            temp = input;
                        }
                    }
                    mathPast = "";
                    result = "";
                    text.setText(showNow);
                    equal_flag = 0;
                } else {
                    if (".".equals(input)) {
                        if (mathNow.length() == 0) {                                //1.mathNow为空，+0.
                            mathNow += "0.";
                            showNow += "0.";
                        } else if (isChar(mathNow.charAt(mathNow.length() - 1))) {  //2.mathNow的最后一个字符为oper，+0.
                            mathNow += "0.";
                            showNow += "0.";
                        } else if (isNum(mathNow.charAt(mathNow.length() - 1)) && (!hasPoint(temp))) {   //3.mathNow的最后一个字符为num，+.
                            mathNow += ".";
                            showNow += ".";
                        } else {                                                    //4.除此之外，不加
                            mathNow += "";
                            showNow += "";
                        }
                        temp += input;
                    } else {
                        if (!input.equals("e") && !input.equals("π") && !input.equals("(") && !input.equals(")")) {
                            temp += input;
                        }
                        //往mathNow里面添加输入的字符
                        if (input.equals("%")) {
                            if (mathNow.equals("")) {
                                mathNow = "";
                                showNow = "";
                            }
                            if (!isChar(mathNow.charAt(mathNow.length() - 1))) {
                                mathNow += "*0.01";
                                showNow += "%";
                            }
                        } else {
                            mathNow += input;
                            showNow += input;
                        }
                    }
                    //向text里添加内容
                    if (input.equals(".") && hasPoint(temp)) {
                        text.setText(showNow);
                    } else {
                        text.append(input);
                    }
                }
            }
            //运算符号
            if (input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/")) {
                temp = "";
                //刚刚进行过一次运算
                if (equal_flag == 1) {
                    mathNow = result + input;
                    showNow = result + input;
                    mathPast = "";
                    result = "";
                    text.setText(showNow);
                    equal_flag = 0;
                } else {
                    if (mathNow.equals("") && input.equals("-")) {
                        mathNow += input;
                        showNow += input;
                    } else if (!mathNow.equals("")) {
                        if (mathNow.charAt(mathNow.length() - 1) == '(' && input.equals("-")) {
                            showNow += input;
                            mathNow += input;
                            text.append(input);
                        }
                        if (isNum(mathNow.charAt(mathNow.length() - 1)) || mathNow.charAt(mathNow.length() - 1) == '!' ||
                                mathNow.charAt(mathNow.length() - 1) == ')' || mathNow.charAt(mathNow.length() - 1) == 'π' ||
                                mathNow.charAt(mathNow.length() - 1) == 'e' || mathNow.charAt(mathNow.length() - 1) == '%') {
                            mathNow += input;
                        }
                        showNow += input;
                    }
                    if (result.equals("")) {
                        if (mathNow.equals("-") && input.equals("-")) {
                            text.setText("-");
                        } else {
                            if (!isChar(text.getText().charAt(text.getText().length() - 1)) && text.getText().charAt(text.getText().length() - 1) != '(') {
                                text.append(input);
                            }
                        }
                    }
                }
            }
            //函数符号
            if (input.equals("1/X") || e.getSource() == squarebuttion || e.getSource() == cubebuttion || e.getSource() == powerbuttion || input.equals("X!") || input.equals("√") ||
                    e.getSource() == readictionbuttion || input.equals("ln") || input.equals("log") || input.equals("sin") || input.equals("cos") || input.equals("tan") ||
                    input.equals("|x|")) {
                temp = "";
                if (equal_flag == 1) {
                    if (input.equals("|x|")) {
                        mathNow = "abs(";
                        showNow = "abs(";
                    } else if (input.equals("1/X") || input.equals("√") || input.equals("X!") || e.getSource() == squarebuttion || e.getSource() == cubebuttion || e.getSource() == powerbuttion ||
                            e.getSource() == readictionbuttion) {
                        mathNow = "";
                        showNow = "";
                    } else {
                        mathNow = input + "(";
                        showNow = input + "(";
                        mathPast = "";
                    }
                    result = "";
                    text.setText(showNow);
                    equal_flag = 0;
                } else {
                    if (input.equals("1/X") && (isNum(mathNow.charAt(mathNow.length() - 1)) || mathNow.charAt(mathNow.length() - 1) == ')')) {
                        mathNow += "^(-1)";
                        showNow += "^(-1)";
                        text.append("^(-1)");
                    } else if (e.getSource() == squarebuttion && (isNum(mathNow.charAt(mathNow.length() - 1)) || mathNow.charAt(mathNow.length() - 1) == ')')) {
                        mathNow += "^(2)";
                        showNow += "^(2)";
                        text.append("^(2)");
                    } else if (e.getSource() == cubebuttion && (isNum(mathNow.charAt(mathNow.length() - 1)) || mathNow.charAt(mathNow.length() - 1) == ')')) {
                        mathNow += "^(3)";
                        showNow += "^(3)";
                        text.append("^(3)");
                    } else if (e.getSource() == powerbuttion && (isNum(mathNow.charAt(mathNow.length() - 1)) || mathNow.charAt(mathNow.length() - 1) == ')')) {
                        mathNow += "^(";
                        showNow += "^(";
                        text.append("^(");
                    } else if (input.equals("X!") && (isNum(mathNow.charAt(mathNow.length() - 1)) || mathNow.charAt(mathNow.length() - 1) == ')')) {
                        mathNow += "!";
                        showNow += "!";
                        text.append("!");
                    } else if (input.equals("√") && (isNum(mathNow.charAt(mathNow.length() - 1)) || mathNow.charAt(mathNow.length() - 1) == ')')) {
                        mathNow += "^(1/2)";
                        showNow += "^(1/2";
                        text.append("^(1/2)");
                    } else if (e.getSource() == readictionbuttion && (isNum(mathNow.charAt(mathNow.length() - 1)) || mathNow.charAt(mathNow.length() - 1) == ')')) {
                        mathNow += "^(1/";
                        showNow += "^(1/";
                        text.append("^(1/");
                    } else if (input.equals("ln")) {
                        mathNow += "ln(";
                        showNow += "ln(";
                        text.append("ln(");
                    } else if (input.equals("log")) {
                        mathNow += "log(";
                        showNow += "log(";
                        text.append("log(");
                    } else if (input.equals("sin")) {
                        mathNow += "sin(";
                        showNow += "sin(";
                        text.append("sin(");
                    } else if (input.equals("cos")) {
                        mathNow += "cos(";
                        showNow += "cos(";
                        text.append("cos(");
                    } else if (input.equals("tan")) {
                        mathNow += "tan(";
                        showNow += "tan(";
                        text.append("tan(");
                    } else if (input.equals("|x|")) {
                        mathNow += "abs(";
                        showNow += "abs(";
                        text.append("abs(");
                    }

                }
            }
            //功能键
            if ("mc".equals(input) || "m+".equals(input) || "m-".equals(input)
                    || "mr".equals(input) || "ms".equals(input) || "←".equals(input) || "=".equals(input) || "deg".equals(input)
                    || "C".equals(input)) {
                if ("C".equals(input)) {
                    fc();
                } else if (input.equals("ms")) {
                    fms();
                } else if (input.equals("mr")) {
                    fmr();
                } else if (input.equals("m+")) {
                    fmadd();
                } else if (input.equals("mc")) {
                    fmc();
                } else if (input.equals("m-")) {
                    fmdecreae();
                } else if (input.equals("←")) {
                    fbtn_del();
                } else if (input.equals("=")) {
                    feual();
                }
            }
        }
    }

    /**出错时调用该函数*/
    private void ferror() {
        if (mathNow.length() < text.getColumns()) {
            text.append("\n");
            for (int i = 0; i < text.getColumns() - 5; i++) {
                text.append("      ");
            }
            text.append("error");
        } else if (mathNow.length() > text.getColumns()) {
            text.append("\n");
            for (int i = 0; i < mathNow.length() - 4; i++) {
                text.append("  ");
            }
            text.append("error");
        }
    }

    /**点击m相应键时调用这些函数*/
    private void fms() {
        //通过计算将结果保存在m中
        m = result;
        mermoryArea.append("m=" + m + "\n");
    }

    private void fmr() {
        mathNow = m;
        text.setText(mathNow);
    }

    private void fmadd() {
        //计算出来的结果加上M的值
        m = String.valueOf(Double.parseDouble(m) + Double.parseDouble(result));
        mermoryArea.append("m=" + m + "\n");
    }

    private void fmdecreae() {
        //m减去计算出的值
        m = String.valueOf(Double.parseDouble(m) - Double.parseDouble(result));
        mermoryArea.append("m=" + m);
    }

    private void fmc() {
        m = "";
        mermoryArea.setText("已刷新1\n");
    }

    private void fc() {
        equal_flag = 0;
        mathNow = "";
        mathPast = "";
        showNow = "";
        text.setText("");
        result = "";
        recordArea.setText(ReSetrecordArea);
        mermoryArea.setText("内存");
    }

    private void fbtn_del() {
        if (mathNow.length() != 0) {
            mathNow = mathNow.substring(0, mathNow.length() - 1);
            showNow = showNow.substring(0, showNow.length() - 1);
            text.setText(showNow);
        }
    }

    private void feual() {
        if (equal_flag == 1) {
            text.append("");
        } else {
            mathNow = fparentthesis(mathNow);
            showNow = fparentthesis(showNow);
            mathNow = fDeltePare(mathNow);
            showNow = fDeltePare(showNow);
            mathPast = mathNow;
            mathNow = "";
            ScienCalculator sr = new ScienCalculator();
            sr.angle_metric = TrigCalculMethod;
            result = sr.fScienceCalcuator(mathPast);
            if (!result.equals("null")) {
                fVisResuable(showNow);
            }
            recordArea.append("\n" + showNow + "=" + result);
            equal_flag = 1;
        }
    }

    private boolean isChar(char con_char) {
        return con_char == '+' || con_char == '-' || con_char == '*'
                || con_char == '/' || con_char == '×' || con_char == '÷';
    }

    /**显示结果函数*/
    void fVisResuable(String con_str) {
        if (con_str.length() < 18) {
            text.append("\n");
            text.append("=" + result);
        } else {
            text.append("\n");

            for (int i = 0; i < con_str.length() - result.length(); i++) {
                text.append("  ");
            }
            text.append("=" + result);
            System.out.println(text.getColumns() + "\n" + con_str.length());
        }
    }

    private boolean isNum(char con_char) {
        return con_char == '0' || con_char == '1' || con_char == '2'
                || con_char == '3' || con_char == '4' || con_char == '5'
                || con_char == '6' || con_char == '7' || con_char == '8' || con_char == '9';
    }

    /**与零进行比较，目的时限制小数再加小数点的情况*/
    private boolean hasPoint(String con_str) {
        return con_str.indexOf('.') >= 0;
    }

    /**删除多余的括号*/
    private String fDeltePare(String con_str) {

        for (int i = 0; i < con_str.length(); i++) {
            int Index = -1;
            String mind_str;
            String temp_str;
            if (con_str.charAt(i) == '(') {
                Index = i;
                if (Index == 0) {
                    int j = Index + 1;
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
                    temp_str = "(" + mind_str + ")";
                    if (mind_str.contains("(")) {
                        mind_str = fDeltePare(mind_str);
                    }
                    if (!mind_str.contains("(") && !mind_str.contains("+") && !mind_str.contains("-") && !mind_str.contains("*") && !mind_str.contains("/") && !mind_str.contains("%")) {

                        con_str = con_str.replace(temp_str, mind_str);
                    }

                } else if (Index != 0 && isChar(con_str.charAt(Index - 1))) {
                    int j = Index + 1;
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

                    temp_str = "(" + mind_str + ")";
                    if (mind_str.contains("(")) {
                        mind_str = fDeltePare(mind_str);
                    }

                    if (!mind_str.contains("(") && !mind_str.contains("+") && !mind_str.contains("-") && !mind_str.contains("*") && !mind_str.contains("/") && !mind_str.contains("%")) {

                        con_str = con_str.replace(temp_str, mind_str);
                    }
                }
            }
        }
        return con_str;
    }

    private String fparentthesis(String con_mathnow) {
        //补全括号
        int leftNum = 0;
        int rightNum = 0;
        for (int i = 0; i < con_mathnow.length(); i++) {
            if (con_mathnow.charAt(i) == '(') {
                leftNum++;
            }
            if (con_mathnow.charAt(i) == ')') {
                rightNum++;
            }
        }
        int missingNum = leftNum - rightNum; //缺失的 ) 数量
        while (missingNum > 0) {
            con_mathnow += ')';
            missingNum--;
        }
        return con_mathnow;
    }

    public static void main(String[] args) {
        Calculator f = new Calculator();


    }
}
