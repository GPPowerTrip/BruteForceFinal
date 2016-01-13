package org.powertrip.excalibot.common.plugins.bruteforce;

import org.powertrip.excalibot.common.com.SubTask;
import org.powertrip.excalibot.common.com.SubTaskResult;
import org.powertrip.excalibot.common.plugins.KnightPlug;
import org.powertrip.excalibot.common.plugins.interfaces.knight.ResultManagerInterface;

import java.util.concurrent.Semaphore;

/**
 * Created by theOthers on 04/01/2016.
 * 04:12
 */
public class Bot extends KnightPlug {

    Semaphore s1 = new Semaphore(0);
    String correctPass = "";


    public Bot(ResultManagerInterface resultManager) {
        super(resultManager);
    }

    @Override
    public boolean run(SubTask subTask) {


        SubTaskResult result = subTask.createResult();
        String host = subTask.getParameter("host");
        String username = subTask.getParameter("username");
        BruteForce panzerKnacker = new BruteForce(host, username, correctPass, s1);
        panzerKnacker.start();
        try {
            s1.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long currentTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis() - currentTime;
        result
                .setSuccessful(true)
                .setResponse("correctPass", correctPass).setResponse("elapsedTime", String.valueOf(currentTime));


        return true;
    }
}
