/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author moh
 */
public class LZ77 {

//    public static int check(char c, String s , int j) {
//        boolean flag = false;
//        int index = 0;
//        for (int i = 0; i < j ; i++) {
//            if (c == s.charAt(i)) {
//                flag = true;
//                index = i;
//            }
//        }
//        if (flag == true) {
//            return index;
//        }
//        return -1;
//    }
    public static int existsBefore(String s, StringBuilder substring, int i) {

        String before = s.substring(0, i);
//        System.out.println(before);
//        System.out.println(substring+" : "+before.lastIndexOf(substring.toString()));
        if (before.contains(substring)) {
            return before.lastIndexOf(substring.toString());
        }

        return -1;
    }
//    public static int existsBefore (List <Tag> li,StringBuilder sb)
//    {
//        StringBuilder sb2 = new StringBuilder ();
//       for (Tag t : li){
//           sb2.append(t.next);
//       }
//       if (sb2.toString().contains(sb)){
//           return sb2.lastIndexOf(sb.toString());
//       }
//        return -1 ;
//    }

    public static boolean charBefore(List<Tag> li, char c) {
        boolean flag = false;
        for (Tag t : li) {
            if (t.next == c) {
                flag = true;
                break;
            }
        }
        if (flag == true) {
            return true;
        }
        return false;
    }

//    public static Tag get (String s , List <Tag> list,int j)
//    {
//        StringBuilder sb = new StringBuilder ();
//        sb.append(s.charAt(j));
//        System.out.println("exists before " + existsBefore(list, sb));
//        int index = j - existsBefore(list, sb) ; 
//        
//        for (int i = j+1 ; i < s.length() ; i++ ){
//            sb.append(s.charAt(i));
//            
//            if (existsBefore(list,sb) == -1){
//                break;  
//            } 
//        }
//       System.out.println("index : "+index + " String : " +sb);
//        return new Tag (index,sb.length()-1,sb.charAt(sb.length()-1));
//        
//    }
    public static int getIndex(String s, StringBuilder substring, int i) {

        String before = s.substring(0, i);
        int lastIndex = before.lastIndexOf(substring.substring(0, substring.length() - 1));
        return i - lastIndex;
    }

//    public static boolean containsIndex(int i,StringBuilder sb){
//        if (sb.toString().contains(String.valueOf(i))){
//            return true ; 
//        } 
//        return false ;
//        
//    }
    public static int log2(int value) {
        return Integer.SIZE - Integer.numberOfLeadingZeros(value);
    }

    public static int CompressedDataSize(List<Tag> list) {
        int biggestIndex = list.get(0).index;
        int biggestLength = list.get(0).length;
        for (Tag t : list) {
            if (t.index > biggestIndex) {
                biggestIndex = t.index;
            }
            if (t.length > biggestLength) {
                biggestLength = t.length;
            }
        }
        return log2(biggestLength) + log2(biggestIndex) + 8;
    }

    public static int back(String s, int back) {
        int index = (s.length()) - back;
        return index;
    }

    public static void main(String[] args) throws IOException {
        List<Tag> li = new LinkedList<Tag>();
        String s = "ABAABABAABBBBBBBBBBBBA";
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (count > i) {
                continue;
            }
            if (charBefore(li, s.charAt(i))) {
                sb.append(s.charAt(i));
                count = i + 1;
                while (existsBefore(s, sb, i) != -1 && count < s.length()) {

                    sb.append(s.charAt(count));

                    count++;
                }
                int index = getIndex(s, sb, i);
                Tag t = new Tag(index, sb.length() - 1, sb.charAt(sb.length() - 1));
                li.add(t);
                sb.setLength(0);

            } else {
                Tag t = new Tag(0, 0, s.charAt(i));
                li.add(t);
            }

        }

        for (Tag t : li) {
            System.out.println(t);
        }
        System.out.println("Compressed Data Size is  " + CompressedDataSize(li));
        System.out.println("*******************************************************************************");
        String add = "";
        for (Tag t : li) {
            // if the length and index is 0 add it just add the letter 
            if (t.index == 0 && t.length == 0) {
                add += t.next;
            } else {
                // go back t.index steps and get that string make it your start and add till you reach the specified length 
                int index = back(add, t.index);
                int c1 = 0;
                while (c1 != t.length) {
                    add += add.charAt(index);
                    index++;
                    c1++;
                }
                add += t.next;

            }

        }

        System.out.println("String Before Decompression : " + s + "  ,  String After Decompression : " + add);

    }

}
