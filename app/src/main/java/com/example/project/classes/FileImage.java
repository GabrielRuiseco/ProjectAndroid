package com.example.project.classes;

import java.io.Serializable;

public class FileImage implements Serializable {

    private String _id, fileName, imgsrc_route;
    private int uid;

    public FileImage(String id,String name,String path,int u_id){
        this._id=id;
        this.fileName=name;
        this.imgsrc_route=path;
        this.uid=u_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImgsrc_route() {
        return imgsrc_route;
    }

    public void setImgsrc_route(String imgsrc_route) {
        this.imgsrc_route = imgsrc_route;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
