package com.drink.cornerstone.util;

/**
 * Created by xm on 2014/12/16.
 */
public class InitPassword {
    private  static String[] lettersS = new String[]{
            "q","w","e","r","t",
            "y","u","i","o","p",
            "a","s","d","f","g",
            "h","j","k","l","z",
            "x","c","v","b","n", "m"
    };
    private static String[] lettersB = new String[]{
            "Q","W","E","R","T",
            "Y","U","I","O","P",
            "A","S","D","F","G",
            "H","J","K","L","Z",
            "X","C","V","B","N", "M"
    };
    private static String[] number = new String[]{
            "0","1","2","3","4",
            "5","6","7","8","9"
    };

    /**
     *
     * @return
     */
    public static String ceateInitPasswor(Integer length){
        if(length==null ) length=6;
        String password="";
        for(int i=0;i<length;i++){
            int sbn= (int)Math.random()*3;
            String lets="";
            String  letb="";
            String  n="";
            if(sbn==0){
                int letters=(int)Math.random()*26;
                 lets=  lettersS[letters];
            }
            if(sbn==1){
                int letterb=(int)Math.random()*26;
                  letb= lettersB[letterb];
            }
            if(sbn==2){
                int num=(int)Math.random()*26;
                  n= number[num];
            }

            password+=lets+letb+n;

        }
    return password;
    }








}
