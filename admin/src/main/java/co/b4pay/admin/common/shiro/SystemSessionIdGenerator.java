package co.b4pay.admin.common.shiro;

import co.b4pay.admin.common.helper.LoginHelper;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class SystemSessionIdGenerator extends JavaUuidSessionIdGenerator {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SystemSessionIdGenerator.class);

    @Override
    public Serializable generateId(Session session) {
        String username = LoginHelper.getUsername();
        if (username != null) {
            // return Base64.encodeToString(principal.toString().getBytes());
            return username.toString();
        }
        return super.generateId(session);
    }

}
