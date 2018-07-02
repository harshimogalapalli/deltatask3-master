package com.example.harshit.searchbarlv;

import java.io.Serializable;

public class GOTChar implements Serializable {
    public String name ;
    public String housename ;
    public String imageLink;
    public String pageRank ;
    public String books;
    public String toString() { return name+" "+housename+" "+imageLink+" "+pageRank+" "+books; }
}
