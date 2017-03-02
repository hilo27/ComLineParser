
package lab2;

public class ComLineParser {    
    private String[] keys;
    private String[] delimeters;
            
    public enum SwitchStatus {NoError, Error, ShowUsage };
          
    public  ComLineParser(String[] keys){
       this(keys, new String[] { "/", "-" }); 
    }
      
    public ComLineParser(String[] keys, String[] delimeters) {           
        this.keys = keys;
        this.delimeters = delimeters;       
    }
      
    public void onUsage(String errorKey){        
        if (errorKey != null) {
            System.out.println("Неверный ключ:" + errorKey);
            System.out.println("формат ком.строки: имяПрограммы [-r<input-fileName>] [-w<output-fileName>]");
            System.out.println("   -?  показать Help файл");
            System.out.println("   -r  задать имя входного файла");
            System.out.println("   -w  выполнить вывод в указанный файл");
        }  
    }
    
    public SwitchStatus onSwitch(String key, String keyValue){
        System.out.println(key+" "+keyValue);
        return SwitchStatus.NoError;
    }
    
   public boolean Parse(String[] args){
        //установил SwitchStatus в NoError 
        SwitchStatus switchStatus = SwitchStatus.NoError; 
        
        int argNum;
        for (argNum = 0; (switchStatus == SwitchStatus.NoError) && (argNum < args.length); argNum++){
            
            // провера наличия правильного разделителя
            boolean isDelimeter = false;
            for (int n = 0; !isDelimeter && (n < delimeters.length); n++){
                isDelimeter = args[argNum].regionMatches(0,delimeters[n], 0, 1);  
            }
            
            if (isDelimeter) {
               // проверка наличия правильного ключа
               boolean isKey=false; 
               int i;
               for (i = 0; !isKey && (i < keys.length); i++) {
                   //isKey =  args[argNum].regionMatches(0,keys[i], 0, 1);  
                   isKey = args[argNum].toUpperCase().regionMatches(1, keys[i].toUpperCase(), 0, keys[i].length());
                   if (isKey){break;}                  
               }
               if (!isKey){
                   switchStatus= SwitchStatus.Error;  
                   break;
                   
               //очень долго разбирался почему ничего не выводится когда ключи верные, 
               //оказалось что в onSwitch ничего не передается
               // т.к отсутствовал этот else
               }else {switchStatus = onSwitch(keys[i].toLowerCase(), args[argNum].substring(1 + keys[i].length()));}        
            }
             else {
                switchStatus= SwitchStatus.Error;
                break;
            }       
        }
        // завершение разбора командной строки
        if (switchStatus == SwitchStatus.ShowUsage){
            onUsage(null);
        }
        if (switchStatus == SwitchStatus.Error){
            onUsage((argNum == args.length) ? null : args[argNum]);
        }
        
        return switchStatus == SwitchStatus.NoError;
   }       
        
}
