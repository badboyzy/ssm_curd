package com.lvoyee.crud.common;

import java.util.Properties;
import com.lvoyee.crud.utils.AesUtil;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import sun.security.krb5.internal.crypto.Des;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    // 第二种方式
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {

        String key = "AkxfGVoMiKDxUiJM";
        String iv = "1841611841611010";

        try {
            String userKey = "jdbc.user";
            String passwordKey = "jdbc.password";

            String user = props.getProperty(userKey);
            String password = props.getProperty(passwordKey);

            if(StringUtil.isNotEmpty(user)){
                props.setProperty(userKey,AesUtil.decrypt(user,key,iv));//解密user
            }

            if(StringUtil.isNotEmpty(password)){
                props.setProperty(passwordKey,AesUtil.decrypt(password,key,iv));
            }

            super.processProperties(beanFactory, props); //

        }catch (Exception e){
            throw new BeanInitializationException(e.getMessage());
        }


    }







    //  第一种方式
 /*private String[] encryptPropNames = {"jdbc.user", "jdbc.password"};


    @Override
    protected String convertProperty(String propertyName, String propertyValue)
    {
        String key = "AkxfGVoMiKDxUiJM";
        String iv = "1841611841611010";

        //如果在加密属性名单中发现该属性
        if (isEncryptProp(propertyName))
        {
            String decryptValue = AesUtil.decrypt(propertyValue,key,iv);
            System.out.println("--------------------------------"+decryptValue);
            return decryptValue;
        }else {
            return propertyValue;
        }

    }

    private boolean isEncryptProp(String propertyName)
    {
        for (String encryptName : encryptPropNames)
        {
            if (encryptName.equals(propertyName))
            {
                return true;
            }
        }
        return false;
    }*/


}
