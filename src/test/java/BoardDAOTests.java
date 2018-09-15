import org.junit.Test;
import org.zerock.dao.BoardDAO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.PageDTO;
import org.zerock.domain.PageMaker;

import static junit.framework.TestCase.assertNotNull;

public class BoardDAOTests {

    private BoardDAO boardDAO = new BoardDAO();

    @Test
    public void testInsert() throws Exception{
        BoardVO vo = new BoardVO();
        vo.setTitle("가익아 톰캣 실행 해야지");
        vo.setContent("제곧내");
        vo.setWriter("톰캣");
        boardDAO.create(vo);
    }
    @Test
    public void test1(){

        assertNotNull(boardDAO);
        System.out.println("test1...................");

        PageDTO pageDTO = PageDTO.of().setSize(20).setPage(5);  // 지정하고 싶으면 지정하고 안하고 싶으면 안해도 되는 유연한 코드 (생성자 있기 때문)


        System.out.println(pageDTO);
    }

    @Test
    public void testList() throws Exception{

        boardDAO.getList(PageDTO.of().setPage(2).setSize(100)).forEach(vo-> System.out.println(vo));
    }

    @Test
    public void testPageMaker(){
        PageDTO dto = PageDTO.of().setPage(11).setSize(10);
        int total = 123;

        PageMaker pageMaker = new PageMaker(total,dto);

        System.out.println(pageMaker);
    }

    @Test
    public void testRead() throws Exception{
        int bno = 5701641;

        System.out.println(boardDAO.getBoard(bno,false));
    }
}
