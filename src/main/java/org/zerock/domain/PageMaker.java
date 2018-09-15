package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageMaker {

    private int total;      // 전체 데이터 개수
    private PageDTO pageDTO;    // 페이지 정보 (현재 페이지 번호와 페이지 사이즈)

    private int start, end;
    private boolean prev, next;

    public PageMaker(int total, PageDTO pageDTO) {
        this.total = total;
        this.pageDTO = pageDTO;

        int tempEnd = (int)(Math.ceil(pageDTO.getPage()/10.0) * 10);
        this.start = tempEnd - 9;

        int realEnd = (int)(Math.ceil(total/(double)pageDTO.getSize()));

        this.end = realEnd < tempEnd? realEnd:tempEnd;

        this.prev = this.start != 1;    // 1이 아니면 true

        this.next = (this.end * pageDTO.getSize()) < total;


    }


}
