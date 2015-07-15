package com.jiuquanlife.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class UploadUtils {
	private final static String PREFIX = "--";
	  private final static String MUTIPART_FORMDATA = "multipart/form-data";
	  private final static String CHARSET = "utf-8";
	  private final static String CONTENTTYPE = "application/octet-stream";
	
	public static String upload(String hostUrl,String filePath,  HashMap<String, String> values)  
    {  
		String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        File file = new File(filePath);   // 要上传的文件  
        StringBuffer sb = new StringBuffer();
        sb.append(hostUrl);
        if(values!=null) {
        	 Set<String> keys = values.keySet();
             Iterator<String> iterator = keys.iterator();
             while(iterator.hasNext()) {
             	String paramKey = iterator.next();
             	sb.append("&" + paramKey+"=" + values.get(paramKey));
             }
        }
        String host = sb.toString();
        try   
        {  
            // 构造URL和Connection
//        	String hosturl = "http://www.5ijq.cn/hongliu/mobcent/app/web/index.php?r=forum/sendattachmentex&fid=52&module=forum&packageName=com.appbyme.app139447&accessToken=37962a4df3146779145b569d973d9&albumId=0&apphash=1626a3c2&forumType=7&imei=A0000038955AFDB&sortId=0&platType=1&accessSecret=e901eadd8fd6dc50deead72478c82&type=image&sdkVersion=2.4.0&appName=????&forumKey=Ir8mv2RUsHewrQiiyJ&imsi=460030721378921";
        	String hosturl = "http://www.5ijq.cn/App/Index/testUpload?message=hello&name=orlando";
        	URL url = new URL(host);
        	HttpURLConnection con = (HttpURLConnection) url.openConnection();
        	/* Output to the connection. Default is false,
        	set to true because post method must write something to the connection */
        	con.setDoOutput(true);
        	/* Read from the connection. Default is true.*/
        	con.setDoInput(true);
        	/* Post cannot use caches */
        	con.setUseCaches(false);
        	/* Set the post method. Default is GET*/
        	con.setRequestMethod("POST");
        	/* 设置请求属性 */
        	con.setRequestProperty("Connection", "Keep-Alive");
        	con.setRequestProperty("Charset", "UTF-8");
        	con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        	DataOutputStream ds = new DataOutputStream(con.getOutputStream());
        	ds.writeBytes(twoHyphens + boundary + end);
        	ds.writeBytes("Content-Disposition: form-data; " +
        	    "name=\"uploadFile\";filename=\"" +
        	    file.getName() + "\"" + end);
        	ds.writeBytes("Content-Type: image/jpeg");
        	ds.writeBytes(end);
        	ds.writeBytes(end);
        	/* 取得文件的FileInputStream */
        	FileInputStream fStream = new FileInputStream(file);
        	/* 设置每次写入8192bytes */
        	int bufferSize = 8192;
        	byte[] buffer = new byte[bufferSize];   //8k
        	int length = -1;
        	/* 从文件读取数据至缓冲区 */
        	while ((length = fStream.read(buffer)) != -1)
        	{
        		ds.write(buffer, 0, length);
        	}
        	ds.writeBytes(end);
        	ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
        	/* 关闭流，写入的东西自动生成Http正文*/
        	fStream.close();
        	/* 关闭DataOutputStream */
        	ds.close();
        	InputStream is = con.getInputStream();  //input from the connection 正式建立HTTP连接
        	int ch;
        	StringBuffer b = new StringBuffer();
        	while ((ch = is.read()) != -1)
        	{	
        		b.append((char) ch);
        	}
        	return b.toString();
        }  
        catch (MalformedURLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return null;
    }
	
	public static String upload(String hostUrl,String filePath, String fileName,  HashMap<String, String> values)  
    {  
		String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        File file = new File(filePath);   // 要上传的文件  
        StringBuffer sb = new StringBuffer();
        sb.append(hostUrl);
        if(values!=null) {
        	 Set<String> keys = values.keySet();
             Iterator<String> iterator = keys.iterator();
             while(iterator.hasNext()) {
             	String paramKey = iterator.next();
             	sb.append("&" + paramKey+"=" + values.get(paramKey));
             }
        }
        String host = sb.toString();
        try   
        {  
            // 构造URL和Connection
//        	String hosturl = "http://www.5ijq.cn/hongliu/mobcent/app/web/index.php?r=forum/sendattachmentex&fid=52&module=forum&packageName=com.appbyme.app139447&accessToken=37962a4df3146779145b569d973d9&albumId=0&apphash=1626a3c2&forumType=7&imei=A0000038955AFDB&sortId=0&platType=1&accessSecret=e901eadd8fd6dc50deead72478c82&type=image&sdkVersion=2.4.0&appName=????&forumKey=Ir8mv2RUsHewrQiiyJ&imsi=460030721378921";
        	String hosturl = "http://www.5ijq.cn/App/Index/testUpload?message=hello&name=orlando";
        	URL url = new URL(host);
        	HttpURLConnection con = (HttpURLConnection) url.openConnection();
        	/* Output to the connection. Default is false,
        	set to true because post method must write something to the connection */
        	con.setDoOutput(true);
        	/* Read from the connection. Default is true.*/
        	con.setDoInput(true);
        	/* Post cannot use caches */
        	con.setUseCaches(false);
        	/* Set the post method. Default is GET*/
        	con.setRequestMethod("POST");
        	/* 设置请求属性 */
        	con.setRequestProperty("Connection", "Keep-Alive");
        	con.setRequestProperty("Charset", "UTF-8");
        	con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        	DataOutputStream ds = new DataOutputStream(con.getOutputStream());
        	ds.writeBytes(twoHyphens + boundary + end);
        	ds.writeBytes("Content-Disposition: form-data; " +
        	    "name=\""+fileName+"\";filename=\"" +
        	    file.getName() + "\"" + end);
        	ds.writeBytes("Content-Type: image/jpeg");
        	ds.writeBytes(end);
        	ds.writeBytes(end);
        	/* 取得文件的FileInputStream */
        	FileInputStream fStream = new FileInputStream(file);
        	/* 设置每次写入8192bytes */
        	int bufferSize = 8192;
        	byte[] buffer = new byte[bufferSize];   //8k
        	int length = -1;
        	/* 从文件读取数据至缓冲区 */
        	while ((length = fStream.read(buffer)) != -1)
        	{
        		ds.write(buffer, 0, length);
        	}
        	ds.writeBytes(end);
        	ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
        	/* 关闭流，写入的东西自动生成Http正文*/
        	fStream.close();
        	/* 关闭DataOutputStream */
        	ds.close();
        	InputStream is = con.getInputStream();  //input from the connection 正式建立HTTP连接
        	int ch;
        	StringBuffer b = new StringBuffer();
        	while ((ch = is.read()) != -1)
        	{	
        		b.append((char) ch);
        	}
        	return b.toString();
        }  
        catch (MalformedURLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return null;
    }
	
}
