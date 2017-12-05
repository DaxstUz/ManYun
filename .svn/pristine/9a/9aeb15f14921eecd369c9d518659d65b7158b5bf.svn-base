package com.ch.mhy.activity.book.loadcomic;

public abstract class ThreadPoolTask implements Runnable {  
	  
    protected String url;  
    protected String urllocal;  
      
    public ThreadPoolTask(String url,String urllocal) {  
        this.url = url;  
        this.urllocal=urllocal;
    }  
      
    public abstract void run();  
      
    public String getURL() {  
        return this.url;  
    }

	public String getUrllocal() {
		return urllocal;
	}  
    
    
}  