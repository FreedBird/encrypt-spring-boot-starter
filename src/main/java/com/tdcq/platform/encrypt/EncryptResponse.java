package com.tdcq.platform.encrypt;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdcq.platform.annotation.Encrypt;
import com.tdcq.platform.config.EncryptProperties;
import com.tdcq.platform.domain.AjaxResult;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by Enzo Cotter on 2022/6/17.
 */
@EnableConfigurationProperties(EncryptProperties.class)
@ControllerAdvice
public class EncryptResponse implements ResponseBodyAdvice<AjaxResult> {

    private final ObjectMapper om = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public AjaxResult beforeBodyWrite(AjaxResult body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            if (body != null) {
                //随机生成密钥
                byte[] encrypt = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
                //构建
                SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, encrypt);
                Object data = body.get("data");

                byte[] bytes = om.writeValueAsBytes(data);
                body.put("data", aes.encrypt(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }
}
