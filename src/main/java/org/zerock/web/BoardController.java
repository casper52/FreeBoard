package org.zerock.web;

import org.zerock.dao.BoardDAO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.PageDTO;
import org.zerock.domain.PageMaker;
import org.zerock.web.util.Converter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(urlPatterns = "/board/*")
public class BoardController extends AbstractController {

    private BoardDAO dao = new BoardDAO();

    public String modifyGET(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        System.out.println("modifyGET.................");

        int bno = Converter.getInt(req.getParameter("bno"),-1);
        String pageStr = req.getParameter("page");

        req.setAttribute("board",dao.getBoard(bno,false));
        req.setAttribute("page",pageStr);
        return "modify";

    }

    public void modifyPOST(HttpServletRequest req,HttpServletResponse resp)throws Exception{

        req.setCharacterEncoding("UTF-8");
        System.out.println("modifyPOST..............");

        BoardVO vo = new BoardVO();
        int bno = Converter.getInt(req.getParameter("bno"),-1);

        vo.setBno(bno);
        vo.setTitle(req.getParameter("title"));
        vo.setContent(req.getParameter("content"));

        dao.modifyContent(vo);


        req.setAttribute("board",dao.getBoard(bno,false));

        int page = Converter.getInt(req.getParameter("page"),-1);
        req.setAttribute("page",page);
        resp.sendRedirect("/board/read?bno="+bno+"&page="+page);



    }

    public String writeGET(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        System.out.println("writeGET..................");

        return "write";

    }

    public String writePOST(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        System.out.println("writePOST....................");

        req.setCharacterEncoding("UTF-8");
        BoardDAO boardDAO=new BoardDAO();

        BoardVO vo = new BoardVO();
        String title = req.getParameter("title");
        String writer = req.getParameter("writer");
        String content = req.getParameter("content");

        vo.setTitle(title);
        vo.setWriter(writer);
        vo.setContent(content);

        boardDAO.create(vo);

        int page = Converter.getInt(req.getParameter("page"),-1);
        req.setAttribute("page",page);


        return "redirect/list";
    }

    public String listGET(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        System.out.println("listGET..................");

        PageDTO dto = PageDTO.of()
                .setPage(Converter.getInt(req.getParameter("page"),1))
                .setSize(Converter.getInt(req.getParameter("size"),10));

        int total = 320;
        PageMaker pageMaker = new PageMaker(total, dto);

        req.setAttribute("pageMaker", pageMaker);
        req.setAttribute("list",dao.getList(dto));

        return "list";

    }

    public String readGET(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        System.out.println("readGET..................");

        String bnoStr = req.getParameter("bno");
        int bno = Converter.getInt(bnoStr,-1);
        boolean updateable = false;

        if(bno == -1){
            throw new Exception("Invalid data");
        }

        Cookie[] cookies = req.getCookies();
        Cookie viewCookie = null;
        for(Cookie ck : cookies){
            if(ck.getName().equals("views")){
                viewCookie = ck;
                break;
            }
        }
        // 쿠키가 없다면
        if(viewCookie == null){
            Cookie newCookie = new Cookie("views",bnoStr + "%");
            newCookie.setMaxAge(60*60*24);  // 쿠키 유통기한 하루
            viewCookie = newCookie;
            updateable = true;
        }else{  //쿠키가 있다면
            String cookieValue = viewCookie.getValue();
            System.out.println("cookieValue:" + cookieValue);
            updateable = cookieValue.contains(bnoStr+'%') == false;

            if(updateable){
                cookieValue += bnoStr+'%';
                viewCookie.setValue(cookieValue);
            }
            System.out.println("cookieValue after:" + cookieValue);
            System.out.println("updateable:"+ updateable);
        }
        resp.addCookie(viewCookie);

        int page = Converter.getInt(req.getParameter("page"),-1);

        req.setAttribute("page",page);
        req.setAttribute("board",dao.getBoard(bno,updateable));

        return "read";

    }


    public String removePOST(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        System.out.println("removePOST...................");

        int bno = Converter.getInt(req.getParameter("bno"),-1);


        dao.removeContent(bno);

        return "redirect/list";




    }


    public String getBasic(){
        return "/board/";
    }
}
