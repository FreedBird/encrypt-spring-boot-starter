package com.tdcq.platform;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.tdcq.platform.annotation.Decrypt;
import com.tdcq.platform.annotation.Encrypt;
import com.tdcq.platform.config.EncryptProperties;
import com.tdcq.platform.domain.AjaxResult;
import com.tdcq.platform.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
public class MainController {

    @Autowired
    private EncryptProperties encryptProperties;

    @Value("${spring.profiles.active}")
    private String active;

    @GetMapping(value = {"/", ""})
    @Decrypt
    public String test() {
        Calendar now = Calendar.getInstance();
        StringBuilder builder = new StringBuilder(active);
        builder.append("\n我是中文");
        builder.append("\n");
        builder.append(now.get(Calendar.YEAR)).append("-");
        builder.append(format(now.get(Calendar.MONTH) + 1)).append("-");
        builder.append(format(now.get(Calendar.DAY_OF_MONTH))).append(" ");
        builder.append(format(now.get(Calendar.HOUR_OF_DAY))).append(":");
        builder.append(format(now.get(Calendar.MINUTE))).append(":");
        builder.append(format(now.get(Calendar.SECOND)));
        return builder.toString();
    }

    private String format(Integer num) {
        if (num > 9) {
            return String.valueOf(num);
        }
        return "0" + num;
    }

    @GetMapping("/user")
    @Encrypt
    public AjaxResult getUser() {
        String content = "test中文";
        // 随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        // 构建
        AES aes = SecureUtil.aes(key);
        // 加密
        byte[] encrypt = aes.encrypt(content);
        // 解密
//        byte[] decrypt = aes.decrypt(encrypt);
        // 加密为16进制表示
        String encryptHex = aes.encryptHex(encrypt);
        // 解密为字符串
//        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);

        User user = new User();
        user.setId((long) 99);
        user.setUsername("javaboy");
        return AjaxResult.success(user);
    }

//    @PostMapping("/user")
//    public RespBean addUser(@RequestBody @Decrypt User user) {
//        System.out.println("user = " + user);
//        return RespBean.ok("ok", user);
//    }

}
