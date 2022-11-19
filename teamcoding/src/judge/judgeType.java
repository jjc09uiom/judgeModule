package judge;
public class judgeType {
    //输入类型判断
    /*
        设置变量inputs用于存放前端输入的数据
        函数setInput()用于设置inputs变量
        设置变量flags用于区分不同类型数据
        设置judgeX()用于判断inputs数据类型并返回flags的值
        flags = 0 /小写数字
                1 /大写数字
     */
    private String inputs;
    public int flags = -2;
    public static char[] basNum = {'零','壹','贰','叁','肆','伍','陆','柒','捌','玖','拾','佰','仟','万','亿','元','整'};
    public static char[] basNum1 = {'壹','贰','叁','肆','伍','陆','柒','捌','玖'};
    public static char[] basNum2 = {'拾','佰','仟','万','亿','元','整'};
    public static char[] basNum3 = {'拾','佰','仟','万','亿'};


    private void setInput(String input){//设置inputs值
        inputs = input;
    }

    public static boolean isNumeric(String str){//判断是否全是数字
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }


    public boolean contain(char temp){//判断字符是否处于合法字典
        int i;
        int flag = -1;
        int len = basNum.length;
        for(i = 0;i < len;i++){
            if(temp == basNum[i])
                flag = 0;
        }
        return flag != -1;
    }
    public boolean isBas(char temp){//判断字符是否为1~9
        for (char c : basNum1) {
            if (temp == c)
                return true;
        }
        return false;
    }
    public boolean Next(){//判断1~9是否存在相连
        int i;
        int len = inputs.length();
        for(i = 0 ; i < len-1; i++){
            char temp1 = inputs.charAt(i);
            if(isBas(temp1)){
                char temp2 = inputs.charAt(i+1);
                if(isBas(temp2))
                    return false;
            }
        }
        return true;
    }
    public int judgeX(String input){ //判断函数，用于判断大小写数字
        setInput(input);
        if(isNumeric(inputs)){//判断是否只含有数字
            if(inputs.charAt(0) == '0'){ //判断首位是否为0
                flags = -1;
                return flags;
            }
            flags = 0;//是小写
        }
        else{//判断大写
            if(inputs.contains(" ")){//判断是否有空格
                flags = -1;
                return flags;
            }
            else{
                int len = inputs.length();
                for(int i = 0;i < len;i++){
                    char temp = inputs.charAt(i);
                    if(!contain(temp)){//判断inputx中的汉字是否在basNum中
                        flags = -1;
                        return flags;
                    }
                    if(i == 0){
                        if(temp=='零'){//判断首位是否为零
                            flags = -1;
                            return flags;
                        }else{//判断是否由单位开头
                            for (char c : basNum2) {
                                if (temp == c) {
                                    flags = -1;
                                    return flags;
                                }
                            }
                        }
                    }
                    else if(temp == '零'){//判断零位置是否正确
                        int s1=0,s2=0;
                        for (char c : basNum3)
                            if (inputs.charAt(i - 1) == c){
                                s1  = 1;
                                break;
                            }

                        for(char c : basNum1)
                            if(inputs.charAt(i + 1 )== c){
                                s2 = 1;
                                break;
                            }

                        if((s1==0&&s2==0)||s1!=s2){
                            flags = -1;
                            return flags;
                        }
                    }
                    for( char c : basNum3){ //单位重复
                        if(temp == c&&i!=len-1){
                                if(inputs.charAt(i+1) == temp){
                                    flags = -1;
                                    return flags;
                                }
                        }
                    }
                }
            }
            if(!Next()){//判断1~9是否相连
                flags = -1;
                return flags;
            }
            flags = 1;//是大写
        }
        return flags;
    }
}
/*

    已知且尚未解决的bug：
        1.大写数字间本应添零的未考虑
            ex：壹亿贰--->100000002
        2.单位间顺序考虑
            ex:壹拾贰佰---->210
        3.不应添零的位置添上后不报错
               ex:捌拾捌万零玖仟陆佰伍拾---->889650
 */