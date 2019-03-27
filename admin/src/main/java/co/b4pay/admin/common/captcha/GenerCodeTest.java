package co.b4pay.admin.common.captcha;

import java.io.IOException;
import java.util.Date;

public class GenerCodeTest {

    public static void main(String[] args) {
        GenerCode vCode = new GenerCode(120, 40, 5, 100);
        try {
            String path = "D:/" + new Date().getTime() + ".png";
            // System.out.println(vCode.getCode() + " >" + path);
            vCode.write(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
