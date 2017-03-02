
package lab2;

public class Lab2 {    
   
    public static void main(String[] args) {
        // TODO code application logic here     
       
        String[] keys= {"?","r","w"};
        
        ComLineParser start = new ComLineParser(keys);
        
        start.Parse(args);
        
        String[] result = "-wOutfileName -rInfileName".split("\\-\\w");
        for (String result1 : result) {
            System.out.println(result1);
        }
    }      
    
}
