import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 2014/12/3.
 */
public class Test{

    Log log= LogFactory.getLog(Test.class);

    @org.junit.Test
    public void sendMail(){
        try {
            List list=new ArrayList<String>();
            list.add(0,"1");
            list.add(0,"2");
            list.add(0,"3");
            for(int i=0;i<list.size();i++){
                System.out.println(list.get(i).toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}