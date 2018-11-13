package com.aksky.music;

public class ArticleList {
    private String id;
    private String typeid;
    private String title;
    public String writer;
    public void setId(String id){
        this.id =id;
    }
    public void setTypeid(String typeid){
        this.typeid = typeid;
    }
    public void setTitle(String title){
        this.title =title;
    }
    public void setWriter(String creditrating){this.writer=writer;}
    public String getId(){
        return  id;
    }
    public String getTypeid(){
        return typeid;
    }

   public  String getTitle(){
        return title;
   }
   public String getWriter(){return writer;}

}
