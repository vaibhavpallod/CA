package com.example.soni;

import android.net.Uri;

/**
 * Created by Oclemy on 8/6/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class PDFDoc {
    String name,path;

    public PDFDoc(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
