package com.drink.cornerstone.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.drink.cornerstone.context.ApplicationContextUtils;
import com.drink.cornerstone.file.FileStrategy;
import com.drink.cornerstone.mongodb.GridFsTemplate;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSInputFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.bson.types.ObjectId;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Servlet implementation class FileUploadServlet
 *
 * @author kimmking 2014-4-1 16:24:56
 */
@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    WebApplicationContext ctx = null;
    // max file size default is 30M.
    long maxFileSize = 30 * 1024 * 1024;
    //
    String tempPath = new File("tempPath").getAbsolutePath();

    String[] allowTypes = "jpg,bmp,png,gif,zip,rar,docx,pdf,xlsx,pptx,doc,ppt,xls,rtf,txt".split(",");

    FileStrategy fileStrategy;// = new FileStrategy.DiskFileStrategy();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        ServletContext servletContext = this.getServletContext();

        ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        fileStrategy = ctx.getBean(FileStrategy.class);

        // 配置其他参数
        // such as maxFileSize,allTypes
        // 创建临时目录
        if (!new File(tempPath).exists())
            new File(tempPath).mkdirs();

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("This is " + this.getServletName() + " for File Uploading&Downloading by kimmking.");
        // 可以不实现文件下载，直接从Nginx GridFS Module来获取文件
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        try {
            String flag=request.getParameter("flag");
            if(flag!=null&&flag.equals("1")){  //editor manageFile
                    PrintWriter out=response.getWriter();
                    //根目录路径，可以指定绝对路径，比如 /var/www/attached/
                    String rootPath = request.getServletContext().getRealPath("/") + "upload/";
                    System.out.println("FileManagerAction:"+rootPath);
                    //根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
                    String rootUrl  = request.getContextPath() + "/upload/";
                    //图片扩展名
                    String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

                    String dirName = request.getParameter("dir");
                    if (dirName != null) {
                        if(!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
                            out.println("Invalid Directory name.");
                        }
                        rootPath += dirName + "/";
                        rootUrl += dirName + "/";
                        File saveDirFile = new File(rootPath);
                        if (!saveDirFile.exists()) {
                            saveDirFile.mkdirs();
                        }
                    }
                    //根据path参数，设置各路径和URL
                    String path = request.getParameter("path") != null ? request.getParameter("path") : "";
                    String currentPath = rootPath + path;
                    String currentUrl = rootUrl + path;
                    String currentDirPath = path;
                    String moveupDirPath = "";
                    if (!"".equals(path)) {
                        String str = currentDirPath.substring(0, currentDirPath.length() - 1);
                        moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
                    }

                    //排序形式，name or size or type
                    String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

                    //不允许使用..移动到上一级目录
                    if (path.indexOf("..") >= 0) {
                        out.println("Access is not allowed.");
                    }
                    //最后一个字符不是/
                    if (!"".equals(path) && !path.endsWith("/")) {
                        out.println("Parameter is not valid.");
                    }
                    //目录不存在或不是目录
                    File currentPathFile = new File(currentPath);
                    if(!currentPathFile.isDirectory()){
                        out.println("Directory does not exist.");
                    }

                    //遍历目录取的文件信息
                    List<Hashtable> fileList = new ArrayList<Hashtable>();
                    if(currentPathFile.listFiles() != null) {
                        for (File file : currentPathFile.listFiles()) {
                            Hashtable<String, Object> hash = new Hashtable<String, Object>();
                            String fileName = file.getName();
                            if(file.isDirectory()) {
                                hash.put("is_dir", true);
                                hash.put("has_file", (file.listFiles() != null));
                                hash.put("filesize", 0L);
                                hash.put("is_photo", false);
                                hash.put("filetype", "");
                            } else if(file.isFile()){
                                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                                hash.put("is_dir", false);
                                hash.put("has_file", false);
                                hash.put("filesize", file.length());
                                hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                                hash.put("filetype", fileExt);
                            }
                            hash.put("filename", fileName);
                            hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                            fileList.add(hash);
                        }
                    }

                    if ("size".equals(order)) {
                        Collections.sort(fileList, new SizeComparator());
                    } else if ("type".equals(order)) {
                        Collections.sort(fileList, new TypeComparator());
                    } else {
                        Collections.sort(fileList, new NameComparator());
                    }
                    JSONObject result = new JSONObject();
                    result.put("moveup_dir_path", moveupDirPath);
                    result.put("current_dir_path", currentDirPath);
                    result.put("current_url", currentUrl);
                    result.put("total_count", fileList.size());
                    result.put("file_list", fileList);

                    response.setContentType("application/json; charset=UTF-8");
                    out.println(result.toJSONString());
            }else if(flag!=null&&flag.equals("2")){  //editor uploadFile
                //文件保存目录路径
                String savePath = request.getServletContext().getRealPath("/") + "upload/";
                //文件保存目录URL
                String saveUrl  = request.getContextPath() + "/upload/";
                //定义允许上传的文件扩展名
                HashMap<String, String> extMap = new HashMap<String, String>();
                extMap.put("image", "gif,jpg,jpeg,png,bmp");
                extMap.put("flash", "swf,flv");
                extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
                extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
                //最大文件大小
                long maxSize = 1000000;
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out=response.getWriter();
                if(!ServletFileUpload.isMultipartContent(request)){
                    out.println(getError("请选择文件。"));
                    return;
                }
                //检查目录
                File uploadDir = new File(savePath);
                if(!uploadDir.isDirectory()){
                    out.println(getError("上传目录不存在。"));
                    return;
                }
                //检查目录写权限
                if(!uploadDir.canWrite()){
                    out.println(getError("上传目录没有写权限。"));
                    return;
                }
                String dirName = request.getParameter("dir");
                if (dirName == null) {
                    dirName = "image";
                }
                if(!extMap.containsKey(dirName)){
                    out.println(getError("目录名不正确。"));
                    return;
                }
                //创建文件夹
                savePath += dirName + "/";
                saveUrl += dirName + "/";
                File saveDirFile = new File(savePath);
                if (!saveDirFile.exists()) {
                    saveDirFile.mkdirs();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String ymd = sdf.format(new Date());
                savePath += ymd + "/";
                saveUrl += ymd + "/";
                File dirFile = new File(savePath);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setHeaderEncoding("UTF-8");
                List items = upload.parseRequest(request);
                Iterator itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    String fileName = item.getName();
                    long fileSize = item.getSize();
                    if (!item.isFormField()) {
                        //检查文件大小
                        if(item.getSize() > maxSize){
                            out.println(getError("上传文件大小超过限制。"));
                            return;
                        }
                        //检查扩展名
                        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                        if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
                            out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
                            return;
                        }
                        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
                        try{
                            File uploadedFile = new File(savePath, newFileName);
                            item.write(uploadedFile);
                        }catch(Exception e){
                            out.println(getError("上传文件失败。"));
                            return;
                        }
                        JSONObject obj = new JSONObject();
                        obj.put("error", 0);
                        obj.put("url", saveUrl + newFileName);
                        out.println(obj.toJSONString());
                    }
                }
            }else{
                long begin = System.currentTimeMillis();
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println(uploadToFS(request, response));
                long end = System.currentTimeMillis();
                System.out.println("保存文件时间：" + (end - begin));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("{success:false, message:'" + e.getMessage() + "'}");
        }

    }

    public String uploadToFS(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        GridFsTemplate gridFsTemplate = (GridFsTemplate) ApplicationContextUtils.getBean("gridFsTemplate");
        String message = null;
        try {
            String id = request.getParameter("id");
            if (id != null && !id.equals("")) {
                gridFsTemplate.remove(new ObjectId(id));
            }
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // Set factory constraints
            factory.setSizeThreshold(8192);
            factory.setRepository(new File(tempPath));

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            // Set overall request size constraint
            upload.setSizeMax(this.maxFileSize);

            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> i = items.iterator();
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                String fileName = fi.getName();
                if (fi.getFieldName() != null && fi.getFieldName().equals("fileId") && fi.getString() != null && !fi.getString().equals("")) {
                    gridFsTemplate.remove(new ObjectId(fi.getString()));
                } else if (fileName != null) {
                    String postfix = fileName.lastIndexOf(".") > -1 ? fileName.substring(fileName.lastIndexOf(".") + 1) : null;
                    if (!check(postfix))
                        throw new Exception("扩展名" + postfix + "不合法:");
                    //System.out.println(fileStrategy.getClass().getCanonicalName());
                    //String realName = fileStrategy.save(fi,"wy","test","测试文件，请修改实现。");
                    try {
                        InputStream input = fi.getInputStream();
                        DBObject metaData = new BasicDBObject();
                        /*metaData.put("owner", "tmpFile");
                        metaData.put("uploader", "user2");
                        metaData.put("desc", "");*/
                        long begin = System.currentTimeMillis();
                        GridFSInputFile fsInputFile = gridFsTemplate.saveFile(input, fileName, metaData, fi.getContentType());
                        long end = System.currentTimeMillis();
                        System.out.println("save to mongo：" + (end - begin));
                        System.out.println(fsInputFile);
                        message = "{\"success\":true, \"fileName\":\"" + fileName + "\", \"fileId\":\"" + fsInputFile.getId() + "\"}";
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.print("upload succeed");
        } catch (Exception e) {
            e.printStackTrace();
            message = "{\"success\":false,\"message\":\"" + e.getMessage() + "\"}";
        }
        return message;
    }

    private boolean check(String postfix) {

        if (allowTypes == null) return true;
        if (postfix == null) return false;

        for (String t : allowTypes) {
            if (postfix.equalsIgnoreCase(t)) {
                return true;
            }
        }

        return false;
    }
    private String getError(String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", message);
        return obj.toJSONString();
    }

}
