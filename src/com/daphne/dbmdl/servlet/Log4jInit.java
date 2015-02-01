package com.daphne.dbmdl.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jInit extends HttpServlet {
     
	    /**  
	     * serialVersionUID:TODO（用一句话描述这个变量表示什么）  
	     *  
	     * @since Ver 1.1  
	     */  
	    
	private static final long serialVersionUID = -7127228984521927150L;
	static Logger logger = Logger.getLogger(Log4jInit.class);
    public Log4jInit() {
    }

    public void init(ServletConfig config) throws ServletException {
        String prefix = config.getServletContext().getRealPath("/");
        String file = config.getInitParameter("log4j");
        String filePath = prefix + file;
        Properties props = new Properties();
        try {
            FileInputStream istream = new FileInputStream(filePath);
            props.load(istream);
            istream.close();
          
            String logFile = prefix + props.getProperty("log4j.appender.dailyFile.File");
            
           // toPrint(logFile);
            props.setProperty("log4j.appender.dailyFile.File",logFile);//设置路径
            PropertyConfigurator.configure(props);//装入log4j配置信息
        } catch (IOException e) {
            toPrint("Could not read configuration file [" + filePath + "].");
            toPrint("Ignoring configuration file [" + filePath + "].");
            return;
        }
    }

    public static void toPrint(String content) {
        System.out.println(content);
    }
}
