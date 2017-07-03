package util;
import java.text.SimpleDateFormat;  
import java.util.Date;  
  
import net.sf.json.JsonConfig;  
import net.sf.json.processors.JsonValueProcessor;  
  
/** 
 * ����һ���Լ���ʱ�����䴦���� 
 * @author Administrator 
 * 
 */  
public class SQLDateProcessor implements JsonValueProcessor{  
  
    private String format = "yyyy-MM-dd hh:mm:ss";//�Զ���ʱ���ʽ������ʽ  
    public SQLDateProcessor() {  
        super();  
        // TODO Auto-generated constructor stub  
    }  
      
    public SQLDateProcessor(String format) {  
        this.format = format;  
    }  
  
    public Object processArrayValue(Object arg0, JsonConfig arg1) {  
        // TODO Auto-generated method stub  
        return arg0;  
    }  
    /** 
     * ��������ֵ 
     * str ��������ǵ�ǰ��Ҫ����������� 
     */  
    public Object processObjectValue(String str, Object obj, JsonConfig arg2) {  
        // TODO Auto-generated method stub  
        String ret = "";  
        if(obj instanceof java.sql.Date){  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            ret = sdf.format(new Date(((java.sql.Date) obj).getTime()));  
        }  
        return ret;  
    }  
  
}  
