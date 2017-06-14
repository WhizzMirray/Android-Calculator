package whizz.me.calculator.utils;

import junit.framework.Assert;


public class PasteUtils {

    private PasteUtils(){};

    public static String pastInto(String oldMessage,String newMessage,int index){
        Assert.assertTrue(index != -1);
        return oldMessage.substring(0,index)+newMessage+oldMessage.substring(index);
    }

    public static String removeBefore(String message,int index){
        if(index == 0)
            return message;

        return message.substring(0,index-1)+message.substring(index,message.length());
    }
}
