package com.tdcq.platform;

import com.tdcq.platform.config.EncryptProperties;
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

//        return RespBean.success(encryptProperties.getDefaultKey());
        return builder.toString();
    }

    private String format(Integer num) {
        if (num > 9) {
            return String.valueOf(num);
        }
        return "0" + num;
    }
}
