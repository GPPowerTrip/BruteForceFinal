package org.powertrip.excalibot.common.plugins.bruteforce;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Created by theOthers on 13/01/2016.
 */

public class SSHConn {
    private Session session = null;
    private String host=null;
    private String password=null;

    public SSHConn(String pass,String host,String username) throws JSchException {
        this.password = pass;
        this.host=host;
        JSch jsch = new JSch();
        session = jsch.getSession(username, host, 22);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.setPassword(password);
        session.connect();

    }
}
