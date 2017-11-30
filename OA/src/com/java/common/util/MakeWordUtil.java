package com.java.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 文件下载工具类
 */
public class MakeWordUtil {
	
	/**
	 * 下载操作
	 * @param map 数据map
	 * @param fileType 文件对应的模板名称
	 * @param fileName 文件名称
	 * @param response 相应
	 * @throws Exception 
	 */
	public static void downLoad(Object map, String fileType, String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
    	HttpSession session = request.getSession();
    	
    	//临时文件保存路径
		String filePath= session.getServletContext().getRealPath(File.separator+"wordtemp"+File.separator);
		
		MakeWordUtil.createWord(map, fileType, filePath, fileName);
		
		response.setContentType("application/msword");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
		ServletOutputStream servletout = response.getOutputStream();
		File outFile = new File(filePath + File.separator + fileName);
		try {
			if (outFile.exists()) {
				FileInputStream in = new FileInputStream(outFile);
				byte bs[] = new byte[in.available()];
				int x = 0;
				int j = 0;
				while ((x = in.read()) != -1) {
					bs[j++] = (byte) x;
				}
				servletout.write(bs);
				in.close();
				MakeWordUtil.deleteFile(new File(filePath));
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			servletout.flush();
			servletout.close();
			response.flushBuffer();
		}
	}
	
	/**
	 * 将模板和数据模型合并生成文件
	 * @param dataMap
	 * @param fileType
	 * @param filePath
	 * @param fileName
	 */
	public static  void createWord(Object map, String fileType,String filePath ,String fileName) {
		try {
			// 创建配置实例
			Configuration configuration = new Configuration();
			// 设置编码
			configuration.setDefaultEncoding("UTF-8");
			// 设置FreeMarker的模版文件位置
			configuration.setClassForTemplateLoading(MakeWordUtil.class,File.separator+"com"+File.separator+"java"+File.separator+"word"+File.separator);
			// 获取模板
			Template template = configuration.getTemplate(fileType); 
			// 输出文件
			File outFile = new File(filePath + File.separator + fileName);
			// 如果输出目标文件夹不存在，则创建
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}
			// 将模板和数据模型合并生成文件
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), "UTF-8"));
			// 生成文件
			template.process(map, out);

			// 关闭流
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 删除word
     * @param file
     */
    public static void deleteFile(File file){ 
		if(file.exists()){                    
		    if(file.isFile()){                    
		    	file.delete();                       
		    }else if(file.isDirectory()){              
		    	File files[] = file.listFiles();               
		    		for(int i=0;i<files.length;i++){            
		    			deleteFile(files[i]);             
		    		} 
		    } 
		    file.delete(); 
		 }
	} 
}
