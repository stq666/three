package com.drink.cornerstone.generator;

import com.drink.cornerstone.generator.config.ControllerSetConfig;
import com.drink.cornerstone.generator.config.GeneratorConfig;
import com.drink.cornerstone.generator.data.*;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import javassist.NotFoundException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成RPC调用相关的Stub和skeleton,以及一些助手类
 * Created by newroc on 13-12-4.
 */
public class RPCGenerator {
    public static final String  DEFAULT_JSONRPC_TEMPLETE_DIR="/jsonrpcftl";
    File file;

    Log logger= LogFactory.getLog(RPCGenerator.class);
    private GeneratorConfig generatorConfig;

    private GeneratorData generatorData=null;

    private List<Map<String,Object>> requestList=new ArrayList<Map<String,Object>>();

    public RPCGenerator() {
		super();
		file = new File(".");
		int pos = file.getAbsolutePath().indexOf("rpcskeleton");
		if(pos > -1){
			file = new File(file.getAbsolutePath().substring(0, pos));
		}
	}

	public GeneratorConfig getGeneratorConfig() {
        return generatorConfig;
    }

    public void setGeneratorConfig(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
    }

    private GeneratorData prepareData() throws Exception {
        generatorData=new GeneratorData();
        List<ControllerSetConfig> controllerSetConfigs=getGeneratorConfig().getControllerSetConfigs();
      List<ControllerSetData> controllerSetDatas=new ArrayList<ControllerSetData>();

        for (int i = 0; i < controllerSetConfigs.size(); i++) {
            ControllerSetData controllerSetData=new ControllerSetData();
            ControllerSetConfig controllerSetConfig= controllerSetConfigs.get(i);
            List<String> controllerClassNames=controllerSetConfig.getControllerClassNames();
            List<ControllerData> controllerDatas=prepareControllerData(controllerClassNames);
            controllerSetData.setControllerDatas(controllerDatas);
            controllerSetDatas.add(controllerSetData);
        }
        generatorData.setControllerSetDatas(controllerSetDatas);
        generatorData.setRpcClientJSFilePath(generatorConfig.getRpcClientJSFilePath());
        return generatorData;
    }

    private  List<ControllerData> prepareControllerData(List<String> controllerClassNames) throws Exception {
        List<ControllerData> controllerDatas=new ArrayList<ControllerData>();
         if(controllerClassNames==null||controllerClassNames.isEmpty()){
             throw new Exception("未指定ControllerClassNames");
         }
        for (int j = 0; j < controllerClassNames.size(); j++) {
           String controllerClassName= controllerClassNames.get(j);
            ControllerData controllerData=new ControllerData();

            Class clazz=Class.forName(controllerClassName);
            Annotation restControllerAnnotation=  clazz.getAnnotation(RequestMapping.class);

            if(restControllerAnnotation==null){
                logger.warn(controllerClassName+"不是RestController");
                throw new RuntimeException(controllerClassName+"不是RestController");
            }else{
                controllerData.setBeanName(((RequestMapping)restControllerAnnotation).value()[0]);
            }
            //Method setContextMethod=ReflectionUtils.findMethod(clazz,"setContext", ControllerContext.class);
            Method setContextMethod=ReflectionUtils.findMethod(clazz,"setContext", clazz.getClass());

            //判断是否需要Context,只要controller类中有setContext(ControllerContext context),就认为该类需要上下文,
            //Controller的每次调用会都会创建新实例,因此该context是线程安全的.
            if(setContextMethod!=null){
                controllerData.setNeedContext(true);
            }
             Method[] rMethods= clazz.getMethods();

            controllerData.setControllerClassName( clazz.getSimpleName());
            controllerData.setPackagename(clazz.getPackage().getName());


            List<ActionData> actions=prepareActionData(rMethods);
            controllerData.setActionDatas(actions);
            controllerDatas.add(controllerData);
        }
        return controllerDatas;
    }

    private List<ActionData>  prepareActionData(Method[] rMethods) throws ClassNotFoundException, NotFoundException {
        List<ActionData> actionDatas=new ArrayList<ActionData>();

        for (int i = 0; i < rMethods.length; i++) {
           Method method= rMethods[i];
            if(isActionMethod(method)){
                ActionData actionData=new ActionData();
                actionData.setActionName(method.getName());
                List<ParameterData> parameterDatas = prepareParameterData(rMethods[i]);
                actionData.setParameters(parameterDatas);
                actionData.setReturnType(rMethods[i].getReturnType().getName());
                actionDatas.add(actionData);
            }
        }

        return actionDatas;
    }

    private List<ParameterData> prepareParameterData(Method rMethod) throws NotFoundException {
        List<ParameterData> parameterDatas=new ArrayList<ParameterData>();
        Type[] types=  rMethod.getGenericParameterTypes();
        Class[] parameterTypes= rMethod.getParameterTypes();
        for (int i = 0; i < types.length; i++) {
//            System.out.println("参数名："+types[i].getTypeName());
//            if(types[i].getTypeName().contains("HttpServletRequest")){
//                continue;
//            }
            Type type=   types[i];
            ParameterData parameter=new ParameterData();
            parameter.setClassName(getParameterClassName(type));
            parameter.setName("p"+i);
            parameterDatas.add(parameter);
        }
        return parameterDatas;
    }

    private String getParameterClassName(Type type) {
        if(type instanceof ParameterizedType){
           return getParameterizedParameterClassName(type);
        }else if(type instanceof Class){
             Class clazz=(Class) type;
             if(clazz.isArray()){
                 return clazz.getComponentType().getName()+"[]";
             }else{
                 return clazz.getName();
             }
        }else if(type instanceof GenericArrayType){
           return ((GenericArrayType) type).getClass().getName()+"[]";
        }else{
            throw new RuntimeException("不支持的参数类型"+type+"");
        }
    }

   private String getParameterizedParameterClassName(Type type){
       ParameterizedType parameterizedType=(ParameterizedType)type;
       Type[] actualTypeArguments= parameterizedType.getActualTypeArguments();
       StringBuilder sb=new StringBuilder();
       sb.append(getParameterClassName(parameterizedType.getRawType()));
       sb.append("<");
       for (int j = 0; j < actualTypeArguments.length; j++) {
           Type  actualType=actualTypeArguments[j];
           String temp= getParameterClassName(actualType);
           sb.append(temp);
           if(j+1!=actualTypeArguments.length){
               sb.append(",");
           }
       }
       sb.append(">");
       return sb.toString();
    }

    /**
     * 判断该方法是否有RestAction 注解
     * @param method
     * @return
     */
    private boolean isActionMethod( Method method) throws ClassNotFoundException {
        return method.getAnnotation(RequestMapping.class)!=null;
    }

    public void generatCode() throws Exception {
        logger.info("当前目录为:"+new File(".").getAbsoluteFile());

        GeneratorData generatorData;


        String templeteDir;
                if(this.getGeneratorConfig().getTempleteDir()==null||this.getGeneratorConfig().getTempleteDir().equals("")){
                    templeteDir=DEFAULT_JSONRPC_TEMPLETE_DIR;
                }else{
                    templeteDir=this.getGeneratorConfig().getTempleteDir();
                }

//        String mrequestTempleteFileName="Mrequest.ftl";
        String parameterWrapperTempleteFileName="ParameterWrapper.ftl";
        String rpcClientJSTempleteFileName="RpcClientJS.ftl";


             generatorData= prepareData();
            logger.info(generatorData);


        Configuration cfg = initTempleteCfg(templeteDir);
//        Template mrequestTemplete = getTemplate(templeteDir, mrequestTempleteFileName, cfg);
        Template parameterWrapperTemplete = getTemplate(templeteDir, parameterWrapperTempleteFileName, cfg);
        Template rpcClientJSTemplete=getTemplate(templeteDir, rpcClientJSTempleteFileName, cfg);


        Map root=new HashMap<String,Object>();
        root.put("generatorData",generatorData);
        List<ControllerSetData> controllerSetDatas= generatorData.getControllerSetDatas();
        for (ControllerSetData controllerSetData : controllerSetDatas) {
            root.put("controllerSetData",controllerSetData);
          List<ControllerData> controllerDatas= controllerSetData.getControllerDatas();
            for (ControllerData controllerData : controllerDatas) {
                root.put("controllerData", controllerData);
                List<ActionData> actionDatas= controllerData.getActionDatas();
                for (ActionData actionData : actionDatas) {
                    List<ParameterData> parameterDatas= actionData.getParameters();

                    String mrequestClassName=getMrequestClassName(generatorData,controllerSetData,controllerData,actionData);
                    String mrequestPackagename=getMrequestPackagename(generatorData, controllerSetData, controllerData, actionData);
                    root.put("actionData",actionData);
                    root.put("parameterDatas",parameterDatas);
                    root.put("mrequestClassName",mrequestClassName);
                    String parameterWrapperClassName;
                    String parameterWrapperPackagename;
                   String  parameterWrapperFilePath=null;
                    Map value=new HashMap();
                    value.put("key", controllerData.getBeanName()+"."+actionData.getActionName());
                    value.put("beanName", root.get("controllerData.getBeanName()"));
                    value.put("mrequestPackagename", root.get("mrequestPackagename"));
                    value.put("mrequestClassName",root.get("mrequestClassName"));
                    value.put("parameterWrapperClassName",root.get("parameterWrapperClassName"));
                    value.put("parameterWrapperPackagename",root.get("parameterWrapperPackagename"));
                    value.put("controllerClassName",controllerData.getControllerClassName());
                    value.put("controllerPackagename",controllerData.getPackagename());
                    requestList.add(value);
                }
            }
        }
         root.put("requestList",requestList);
        //生成JavaScript文件
       genFile(generatorData.getRpcClientJSFilePath(),root,rpcClientJSTemplete);
    }

    private String getAndroidProxyClassName(GeneratorData generatorData, ControllerSetData controllerSetData, ControllerData controllerData) {
        return controllerData.getControllerName();
    }

    private String getActionInvokerClassName(GeneratorData generatorData, ControllerSetData controllerSetData, ControllerData controllerData, ActionData actionData) {
        return "ActionInvoker"+actionData.getActionName();

    }

    private String getActionInvokerPackagename(GeneratorData generatorData, ControllerSetData controllerSetData, ControllerData controllerData, ActionData actionData) {
       return controllerData.getPackagename()+"."+controllerData.getControllerClassName().toLowerCase();
    }

    /**
     * 生成文件
     * @param filePath
     * @param root
     * @param template
     * @throws IOException
     * @throws TemplateException
     */
    private void   genFile(String filePath,Object root,Template template) throws IOException, TemplateException {
        logger.info("genFile.filePath:" + filePath);
       if(filePath==null||filePath.equals("")){
           logger.warn("未指定文件名");
           return;
       }
        File file = genereFile(filePath);
        FileUtils.touch(file);
        /* 将模板和数据模型合并 */
        Writer out = new OutputStreamWriter(new FileOutputStream(file));
        template.process(root, out);
        out.flush();
        logger.info("生成" + file.getAbsoluteFile());
    }
    
    private File genereFile(String path){
    	return new File(this.file,path);
    }

    /**
     * 子类可以覆盖此方法来改变ParameterWrapperPackagename的生成规则
     * @param generatorData
     * @param controllerSetData
     * @param controllerData
     * @param actionData
     * @return
     */
    private String getParameterWrapperPackagename(GeneratorData generatorData, ControllerSetData controllerSetData, ControllerData controllerData, ActionData actionData) {
        return controllerData.getPackagename()+"."+controllerData.getControllerClassName().toLowerCase();
    }

    /**
     * 子类可以覆盖此方法来改变ParameterWrapperClassName的生成规则
     * @param generatorData
     * @param controllerSetData
     * @param controllerData
     * @param actionData
     * @return
     */
    private String getParameterWrapperClassName(GeneratorData generatorData, ControllerSetData controllerSetData, ControllerData controllerData, ActionData actionData) {
        return "PW"+actionData.getActionName();
    }

    /**
     * 子类可以覆盖此方法来改变MrequestPackagename的生成规则
     * @param generatorData
     * @param controllerSetData
     * @param controllerData
     * @param actionData
     * @return
     */
    private String getMrequestPackagename(GeneratorData generatorData, ControllerSetData controllerSetData, ControllerData controllerData, ActionData actionData) {
        return controllerData.getPackagename()+"."+controllerData.getControllerClassName().toLowerCase();
    }

    /**
     * 子类可以覆盖此方法来改变MrequestClassName的生成规则
     * @param generatorData
     * @param controllerSetData
     * @param controllerData
     * @param actionData
     * @return
     */
   public String getMrequestClassName(GeneratorData generatorData, ControllerSetData controllerSetData, ControllerData controllerData, ActionData actionData) {
        return "MRequest"+actionData.getActionName();
    }

    private Template getTemplate(String templeteDir, String mrequestTempleteFileName, Configuration cfg) {
    /* 在整个应用的生命周期中,这个工作你可以执行多次 */
         /* 获取或创建模板*/
        Template mrequestTemplete = null;
        try {
            mrequestTemplete = cfg.getTemplate(mrequestTempleteFileName);
        } catch (IOException e) {
            logger.error("未找模板templeteDir=" +templeteDir+"mrequestTempleteFileName"+mrequestTempleteFileName, e);

        }
        return mrequestTemplete;
    }

    private Configuration initTempleteCfg(String templeteDir) {
    /* 在整个应用的生命周期中,这个工作你应该只做一次。 */
        /* 创建和调整配置。 */
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), templeteDir);
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        return cfg;
    }

}
