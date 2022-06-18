package com.tdcq.platform.encrypt;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.tdcq.platform.annotation.Decrypt;
import com.tdcq.platform.config.EncryptProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by Enzo Cotter on 2022/6/17.
 */
@EnableConfigurationProperties(EncryptProperties.class)
@ControllerAdvice
public class DecryptRequest extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(Decrypt.class) || methodParameter.hasParameterAnnotation(Decrypt.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(final HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        byte[] body = new byte[inputMessage.getBody().available()];
//        inputMessage.getBody().read(body);
        try {
            //随机生成密钥
            byte[] decrypt = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
            //构建
            SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, decrypt);
            //加密
//            byte[] decrypt = aes.decrypt(body);

//            byte[] decrypt = AESUtils.decrypt(body, key.getBytes());
            final ByteArrayInputStream bais = new ByteArrayInputStream(aes.decrypt(body));
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() {
                    return bais;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }
}
