package com.drink.cornerstone.file;

import org.apache.commons.fileupload.FileItem;

import java.io.File;

public interface FileStrategy {

    // 保存文件，返回保存后的文件标识
    String save(FileItem item,String organization,String user,String comment) throws Exception;
	
	
	public static class DiskFileStrategy implements FileStrategy{

        String targetPath = new File("targetPath").getAbsolutePath();

		@Override
		public String save(FileItem item,String organization,String user,String comment) throws Exception {

            if(! new File(targetPath).exists() )
                new File(targetPath).mkdirs();

            String fileName = item.getName();
            String postfix = fileName.lastIndexOf(".")>-1?fileName.substring(fileName.lastIndexOf(".")+1):null;
            File fullFile = new File(System.currentTimeMillis()+"."+postfix);
            File savedFile = new File(targetPath, fullFile.getName());
            item.write(savedFile);
			return fullFile.getName();
		}
		
	}
	
	
	
	
}
