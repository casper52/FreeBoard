package org.zerock.domain;

public class PageDTO {

    private int page = 1, size = 10;

   public static PageDTO of (){
       return new PageDTO();
   }


   public PageDTO setPage(int page){
       this.page = page;
       return this;
   }

   public PageDTO setSize(int size){
       this.size = size;
       return this;
   }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "PageDTO{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
