package com.drink.cornerstone.rest.generator.maven;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.drink.cornerstone.generator.RPCGenerator;
import com.drink.cornerstone.generator.config.ControllerSetConfig;
import com.drink.cornerstone.generator.config.GeneratorConfig;
import org.apache.maven.plugin.AbstractMojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Goal which touches a timestamp file.
 *
 * @goal rpc
 * 
 * @phase generate-sources
 */
public class RestRpcGeneratorMojo
    extends AbstractMojo
{
    /**
     * @parameter
     */
    private List controllerClassNames;

    /**
     * @parameter property="templeteDir"
     */
    private String templeteDir;
    /**
     * @parameter property="rpcClientJSFilePath"
     */
    private String rpcClientJSFilePath;


    public void execute(){
        try {
            ControllerSetConfig mobileGateway=new ControllerSetConfig();
            mobileGateway.setControllerClassNames(controllerClassNames);
            GeneratorConfig rpcGeneratorConfig=new GeneratorConfig();
            List<ControllerSetConfig> list=new ArrayList<ControllerSetConfig>();
            list.add(mobileGateway);
            rpcGeneratorConfig.setControllerSetConfigs(list);
            rpcGeneratorConfig.setTempleteDir(templeteDir);
            rpcGeneratorConfig.setRpcClientJSFilePath(rpcClientJSFilePath);
            RPCGenerator rpcGenerator= new RPCGenerator();
            rpcGenerator.setGeneratorConfig(rpcGeneratorConfig);
            rpcGenerator.generatCode();
        } catch (Exception e) {
            System.out.println("----------------------------------------------");
            e.printStackTrace();
//            getLog().error("生成RPC代码失败",e);

        }
    }
}
