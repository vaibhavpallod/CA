package com.example.soni;

public class Upload_PDF {

public String url,name;

public Upload_PDF(){



}


    public Upload_PDF(String name,String url) {
        this.url = url;
        this.name = name;
    }


    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
