package com.drink.cornerstone.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.drink.cornerstone.context.ApplicationContextUtils;
import com.drink.cornerstone.mongodb.GridFsTemplate;
import com.mongodb.gridfs.GridFSDBFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.bson.types.ObjectId;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 
 * Servlet implementation class FileUploadServlet
 * 
 * @author kimmking 2014-4-1 16:24:56
 * 
 */
@WebServlet("/downLoadServlet")
public class FileDownLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FileDownLoadServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	};

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
        try {
            if(request.getParameter("templet")!=null){
                String templet=request.getParameter("templet");
                InputStream fis=this.getClass().getClassLoader().getResourceAsStream(templet);
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(templet, "utf-8"));
                response.setContentType("application/octet-stream");
                OutputStream os = response.getOutputStream();
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = fis.read(b)) > 0) {
                    os.write(b, 0, len);
                }
                fis.close();
                os.close();
            }else if(request.getParameter("errorFile")!=null){
                String errorFile=request.getParameter("errorFile");
                errorFile=new String(errorFile.getBytes("ISO-8859-1"),"UTF-8");
                ByteArrayInputStream stream = new ByteArrayInputStream(errorFile.getBytes());
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("error.txt", "utf-8"));
                response.setContentType("application/octet-stream");
                OutputStream os = response.getOutputStream();
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = stream.read(b)) > 0) {
                    os.write(b, 0, len);
                }
                stream.close();
                os.close();
            }else if(request.getParameter("fileId")!=null){
                GridFsTemplate gridFsTemplate=(GridFsTemplate) ApplicationContextUtils.getBean("gridFsTemplate");
                String fileId=request.getParameter("fileId");
                GridFSDBFile file=gridFsTemplate.findOne(new ObjectId(fileId));
                String fileName=file.getFilename();
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
                response.setContentType(file.getContentType());
                InputStream fis = file.getInputStream();
                OutputStream os = response.getOutputStream();
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = fis.read(b)) > 0) {
                    os.write(b, 0, len);
                }
                fis.close();
                os.close();
            }else if(request.getParameter("exportOcc")!=null){  //导出职位数据
                String data=request.getParameter("exportData");
                response.reset();
                response.setContentType("application/octet-stream");
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("occ.xls", "utf-8"));
                InputStream fis = this.getInputStream(JSON.parseArray(data));
                OutputStream os = response.getOutputStream();
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = fis.read(b)) > 0) {
                    os.write(b, 0, len);
                }
                fis.close();
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println( "{success:false, message:'"+e.getMessage()+"'}" );
        }
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
            if(request.getParameter("exportOcc")!=null){  //导出职位数据
                String jsonString = readJSONStringFromRequestBody(request);
                jsonString=new String(jsonString.getBytes("ISO-8859-1"),"UTF-8");
                System.out.println(jsonString.substring(jsonString.indexOf("exportData")+11,jsonString.lastIndexOf("------")));
                response.reset();
                response.setContentType("application/octet-stream");
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("occ.xls", "utf-8"));
                InputStream fis = this.getInputStream(JSON.parseArray(jsonString.substring(jsonString.indexOf("exportData")+11,jsonString.lastIndexOf("------"))));
                OutputStream os = response.getOutputStream();
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = fis.read(b)) > 0) {
                    os.write(b, 0, len);
                }
                fis.close();
                os.close();
            }else{
                GridFsTemplate gridFsTemplate=(GridFsTemplate) ApplicationContextUtils.getBean("gridFsTemplate");
                String fileId=request.getParameter("fileId");
                GridFSDBFile file=gridFsTemplate.findOne(new ObjectId(fileId));
                String fileName=file.getFilename();
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
                response.setContentType(file.getContentType());
                InputStream fis = file.getInputStream();
                OutputStream os = response.getOutputStream();
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = fis.read(b)) > 0) {
                    os.write(b, 0, len);
                }
                fis.close();
                os.close();
            /*response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println();*/
            }
		} catch (Exception e) {
			e.printStackTrace();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
			response.getWriter().println( "{success:false, message:'"+e.getMessage()+"'}" );
		}

	}

    private InputStream getInputStream(JSONArray array) {
        if(array==null||array.size()==0)return null;
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("序号");
        cell = row.createCell((short) 1);
        cell.setCellValue("名称");
        cell = row.createCell((short) 2);
        cell.setCellValue("编码");
        cell = row.createCell((short) 3);
        cell.setCellValue("上级职位");
        cell = row.createCell((short) 4);
        cell.setCellValue("状态");
        cell = row.createCell((short) 5);
        cell.setCellValue("负责人");
        JSONObject obj=null;
        for(int i=0;i<array.size();i++){
            obj=array.getJSONObject(i);
            row = sheet.createRow(1);
            cell = row.createCell((short) 0);
            cell.setCellValue(i+1);
            cell = row.createCell((short) 1);
            cell.setCellValue(obj.getString("name"));
            cell = row.createCell((short) 2);
            cell.setCellValue(obj.getString("serialnumber"));
            cell = row.createCell((short) 3);
            cell.setCellValue(obj.getString("parentName"));
            cell = row.createCell((short) 4);
            cell.setCellValue(obj.getIntValue("status")==0?"启用":"停用");
            cell = row.createCell((short) 5);
            cell.setCellValue(obj.getIntValue("ifleader")==0?"是":"否");
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        return is;
    }
    private String readJSONStringFromRequestBody(HttpServletRequest request) throws IOException {
        StringBuffer json = new StringBuffer();
        String line = null;
        // try {
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }
        return json.toString();
    }
}
