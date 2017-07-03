package util;

import java.util.Iterator;  
import java.util.Map;  
  
import net.sf.json.JSONObject;  
import net.sf.json.JsonConfig;  
import net.sf.json.processors.JsonValueProcessor;  
/** 
 * JSON��ʽת���� 
 * @author Administrator 
 * 
 */  
public class JSONUtil {  
    /** 
     * ��һ������ֱ��ת��Ϊһ��JSONObject���� 
     * ͬ���ʺ���JSON��ʽ���ַ��� 
     * �����������java.sql.Date����java.sql.Timestampʱ���ʽ����������һ��toJsonת������ 
     * @param obj 
     * @return 
     */  
    public static JSONObject toJson(Object obj) {  
        return JSONObject.fromObject(obj);  
    }  
      
    /** 
     *  
     * @param obj ��Ҫת���Ĳ��� 
     * @param processors ����ת�����ļ���,������һ��Map���ϣ���������Ҫת�����͵�ȫ·����ֵ������ת���� 
     * @return 
     * @throws ClassNotFoundException 
     */  
    public static JSONObject toJson(Object obj,Map<String,JsonValueProcessor> processors) throws ClassNotFoundException{  
        //����һ��JSONConfig���󣬸ö�������ƶ�һ��ת������  
        JsonConfig config = new JsonConfig();  
        if(processors != null && !processors.isEmpty()){  
            Iterator<java.util.Map.Entry<String, JsonValueProcessor>> it = processors.entrySet().iterator();  
            while (it.hasNext()) {  
                Map.Entry<java.lang.String, net.sf.json.processors.JsonValueProcessor> entry = (Map.Entry<java.lang.String, net.sf.json.processors.JsonValueProcessor>) it  
                        .next();  
                String key = entry.getKey();  
                JsonValueProcessor processor = processors.get(key);  
                //�����ȡ����Ҫת��������  
                Class<?> cls = Class.forName(key);  
                config.registerJsonValueProcessor(cls, processor);  
            }  
        }  
        return JSONObject.fromObject(obj, config);  
    }  
}  
