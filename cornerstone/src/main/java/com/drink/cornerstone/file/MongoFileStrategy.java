package com.drink.cornerstone.file;

import com.drink.cornerstone.mongodb.GridFsTemplate;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSInputFile;
import org.apache.commons.fileupload.FileItem;

import java.io.InputStream;

/**
 * Created by kk on 14-4-3.
 */

public class MongoFileStrategy implements FileStrategy {

    GridFsTemplate gridFsTemplate;

    @Override
    public String save(FileItem item,String organization,String user,String comment) throws Exception {
        InputStream input = item.getInputStream();
        DBObject metaData = new BasicDBObject();
        metaData.put("organization", organization);
        metaData.put("user", user);
        metaData.put("comment", comment);
        GridFSInputFile f = gridFsTemplate.saveFile(input,item.getName(),metaData,null);// TODO: content type
        return f.getId().toString();
    }

    public GridFsTemplate getGridFsTemplate() {
        return gridFsTemplate;
    }

    public void setGridFsTemplate(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }
}
