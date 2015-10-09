package com.jiuquanlife.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class CrashHandler implements UncaughtExceptionHandler{
	 public static final String TAG = "CrashHandler";  
     
	    //ϵͳĬ�ϵ�UncaughtException������   
	    private Thread.UncaughtExceptionHandler mDefaultHandler;  
	    //CrashHandlerʵ��  
	    private static CrashHandler INSTANCE = new CrashHandler();  
	    //�����Context����  
	    private Context mContext;  
	    //�����洢�豸��Ϣ���쳣��Ϣ  
	    private Map<String, String> infos = new HashMap<String, String>();  
	  
	    //���ڸ�ʽ������,��Ϊ��־�ļ�����һ����  
	    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");  
	  
	    /** ��ֻ֤��һ��CrashHandlerʵ�� */  
	    private CrashHandler() {  
	    }  
	  
	    /** ��ȡCrashHandlerʵ�� ,����ģʽ */  
	    public static CrashHandler getInstance() {  
	        return INSTANCE;  
	    }  
	  
	    /** 
	     * ��ʼ�� 
	     *  
	     * @param context 
	     */  
	    public void init(Context context) {  
	        mContext = context;  
	        //��ȡϵͳĬ�ϵ�UncaughtException������  
	        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();  
	        //���ø�CrashHandlerΪ�����Ĭ�ϴ�����  
	        Thread.setDefaultUncaughtExceptionHandler(this);  
	    }  
	  
	    /** 
	     * ��UncaughtException����ʱ��ת��ú��������� 
	     */  
	    @Override  
	    public void uncaughtException(Thread thread, Throwable ex) {  
	    	ex.printStackTrace();
	        if (!handleException(ex) && mDefaultHandler != null) {  
	            //����û�û�д�������ϵͳĬ�ϵ��쳣������������  
	            mDefaultHandler.uncaughtException(thread, ex);  
	        } else {  
	            try {  
	                Thread.sleep(3000);  
	            } catch (InterruptedException e) {  
	                Log.e(TAG, "error : ", e);  
	            }  
	            //�˳�����  
	            android.os.Process.killProcess(android.os.Process.myPid());  
	            System.exit(1);  
	        }  
	    }  
	  
	    /** 
	     * �Զ��������,�ռ�������Ϣ ���ʹ��󱨸�Ȳ������ڴ����. 
	     *  
	     * @param ex 
	     * @return true:��������˸��쳣��Ϣ;���򷵻�false. 
	     */  
	    private boolean handleException(Throwable ex) {  
	        if (ex == null) {  
	            return false;  
	        }  
	        //ʹ��Toast����ʾ�쳣��Ϣ  
	        new Thread() {  
	            @Override  
	            public void run() {  
	                Looper.prepare();  
	                Toast.makeText(mContext, "�ܱ�Ǹ,��������쳣,�����˳�.", Toast.LENGTH_LONG).show();  
	                Looper.loop();  
	            }  
	        }.start();  
	       //������־�ļ�   
	        saveCrashInfo2File(ex);  
	        return true;  
	    }  
	      
	   
	    /** 
	     * ���������Ϣ���ļ��� 
	     *  
	     * @param ex 
	     * @return  �����ļ�����,���ڽ��ļ����͵������� 
	     */  
	    private String saveCrashInfo2File(Throwable ex) {  
	          
	        StringBuffer sb = new StringBuffer();  
	        for (Map.Entry<String, String> entry : infos.entrySet()) {  
	            String key = entry.getKey();  
	            String value = entry.getValue();  
	            sb.append(key + "=" + value + "\n");  
	        }  
	          
	        Writer writer = new StringWriter();  
	        PrintWriter printWriter = new PrintWriter(writer);  
	        ex.printStackTrace(printWriter);  
	        Throwable cause = ex.getCause();  
	        while (cause != null) {  
	            cause.printStackTrace(printWriter);  
	            cause = cause.getCause();  
	        }  
	        printWriter.close();  
	        String result = writer.toString();  
	        sb.append(result);  
	        try {  
	            long timestamp = System.currentTimeMillis();  
	            String time = formatter.format(new Date());  
	            String fileName = "crash-" + time + "-" + timestamp + ".log";  
	            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
	                String path =  Environment.getExternalStorageDirectory().getPath()+"/crash/";  
	                File dir = new File(path);  
	                if (!dir.exists()) {  
	                    dir.mkdirs();  
	                }  
	                FileOutputStream fos = new FileOutputStream(path + fileName);  
	                fos.write(sb.toString().getBytes());  
	                fos.close();  
	            }  
	            return fileName;  
	        } catch (Exception e) {  
	            Log.e(TAG, "an error occured while writing file...", e);  
	        }  
	        return null;  
	    }  

}
