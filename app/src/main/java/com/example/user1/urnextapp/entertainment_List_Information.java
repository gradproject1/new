package com.example.user1.urnextapp;

// class to store patient information for accept page
public class entertainment_List_Information {


    public String URL;
    public String article;

    public entertainment_List_Information(){
        //this constructor is required
    }

    public entertainment_List_Information(String url,String Article) {

        this.URL = url;
        this.article=Article;
    }



    String getImageURL() {
        return URL;
    }

    public void settime(String url) {
        URL=url;

    }

    String getImageArticle() {
        return article;
    }

    public void setArticle(String art) {
        article=art;

    }


}

