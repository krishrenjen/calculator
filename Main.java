import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;

// 3((3 + (5 -3)) +9+(3*2)) / 4 = 15
// (4 + (5+3)) / 5 + ((3+2) + 5) = 12.4

class Main {
  public static void main(String[] args) {
    Scanner reader = new Scanner(System.in);
    while (true) {
      String input = reader.nextLine();
      try{
        String result = calc(input);
        System.out.print(result + "\n\n");
      }
      catch(Exception E){
        System.out.print("Unknown Error\n\n");
      }
    }
  }

  public static String calc(String exp){
    if (exp.matches("^[0-9.!()+*\\-\\/\\^ ]+$")){
      exp = exp.replace(" ", "");
      exp = exp.replaceAll("(?<=[0-9])\\(", "*(");
      exp = exp.replaceAll("\\)(?=[0-9])", ")*");
      exp = exp.replaceAll("\\)\\(", ")*(");

      if(countChar(exp, '(') != countChar(exp, ')'))
        return "Syntax Error";

      //System.out.println(exp);
      int place = 0;

      while(exp.indexOf('(') != -1){
        if (exp.charAt(place) == ')'){
          int temp1 = place;
          while(exp.charAt(place) != '(')
            place--;
          int temp2 = place;
          String temp3 = exp.substring(temp2 + 1, temp1);
          //System.out.println(temp3);
          temp3 = String.valueOf(operation(temp3));
          if (temp1 == (exp.length() - 1)){
            exp = exp.substring(0, temp2) + temp3;
            place = 0;
          }
          else{
            exp = exp.substring(0, temp2) + temp3 + exp.substring(temp1+1);
            place = temp2+1;
          }
          //System.out.println(exp);
        }
        else{
          if(place == (exp.length() - 1))
            place = 0;
          else
            place++;
        }
      }
      return(String.valueOf(operation(exp)));

    }

    else return "Invalid Characters";
  }

  public static double operation(String exp){
    String[] operators = {"\\+", "\\-", "\\/", "\\*"};
    for(int i = 0; i < operators.length; i++)
      exp = exp.replaceAll("(?<=[0-9])" + operators[i], " " + operators[i] + " ");

    String[] splitExp = exp.split(" ");
    ArrayList<String> exp2 = new ArrayList<String>();
    for(int i = 0; i < splitExp.length; i++)
      exp2.add(splitExp[i]);
    
    //System.out.println(exp2);
    
    // multiplication & division
    while(exp2.contains("*") || exp2.contains("/")){
      if(exp2.size() <= 2) break;

      int a = exp2.indexOf("*");
      int b = exp2.indexOf("/");
      int i = b;
      if((a < b || b==-1) && a!=-1) i = a;

      double left = Double.parseDouble(exp2.get(i-1));
      double right = Double.parseDouble(exp2.get(i+1));
      double result = 0;

      if(exp2.get(i).equals("*"))
        result = left * right;
      
      else if(exp2.get(i).equals("/"))
        result = left / right;

      exp2.set(i, String.valueOf(result));
      exp2.remove(i-1); exp2.remove(i);
    }

    // addition & subtraction
    while(exp2.contains("+") || exp2.contains("-")){
      if(exp2.size() <= 2) break;

      int a = exp2.indexOf("+");
      int b = exp2.indexOf("-");
      int i = b;
      if((a < b || b==-1) && a!=-1) i = a;

      double left = Double.parseDouble(exp2.get(i-1));
      double right = Double.parseDouble(exp2.get(i+1));
      double result = 0;

      if(exp2.get(i).equals("+"))
        result = left + right;
      
      else if(exp2.get(i).equals("-"))
        result = left - right;

      exp2.set(i, String.valueOf(result));
      exp2.remove(i-1); exp2.remove(i);
    }

    return Double.valueOf(exp2.get(0));
  }

  public static int countChar(String str, char c){
    int count = 0;

    for(int i=0; i < str.length(); i++)
    {    if(str.charAt(i) == c)
            count++;
    }

    return count;
}
}
