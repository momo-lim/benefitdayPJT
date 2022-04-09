package com.ssafy.recommend.util;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.json.simple.parser.ParseException;

import java.io.*;

public class RunPython {
    public static void makeUserJSON (Long userId,String tags,String userLocation) throws IOException, ParseException {
        System.out.println(userId);
        System.out.println(tags);
        System.out.println(userLocation);
        DefaultExecutor executor=new DefaultExecutor();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        PumpStreamHandler streamHandler=new PumpStreamHandler(baos);
        executor.setStreamHandler(streamHandler);
        CommandLine commandLine=CommandLine.parse("python3");
        commandLine.addArgument("/home/ssafy/python/getRecommendList.py");
        commandLine.addArgument(String.valueOf(-1));
        commandLine.addArgument(tags);
        commandLine.addArgument(userLocation);
        int exitCode=executor.execute(commandLine);
        String result=baos.toString();
        BufferedOutputStream bo=new BufferedOutputStream(new FileOutputStream("/home/ssafy/userJSON/"+userId+".json"));
        bo.write(result.getBytes());
        bo.close();
        baos.close();
    }
}
