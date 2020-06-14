package kr.co.assemble.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.assemble.dao.BoardDAO;
import kr.co.assemble.dao.MemReqGroupDAO;
import kr.co.assemble.dao.RequestDAO;
import kr.co.assemble.dto.BoardDTO;
import kr.co.assemble.dto.MemReqGroupDTO;
import kr.co.assemble.dto.RequestDTO;

@Controller
public class RequestController {

   @Autowired
   RequestDAO rdao;

   @Autowired
   BoardDAO dao;

   @Autowired
   MemReqGroupDAO mrgDao;
   
   @Autowired
   PlatformTransactionManager transactionManager;
   
   // 요청 글쓰기 폼 띄우기
   @RequestMapping(value = "/request")
   public String reqForm() {
      return "board/request";
   }

   // 그룹보드에 요청 글쓰기와 동시에 요청테이블 업데이트
   @PostMapping(value = "/requestOk")
   public String request(
         @RequestParam(value = "grNum") int grNum, @RequestParam(value = "cgNum") int cgNum,
         @RequestParam(value = "memNum") int memNum, @RequestParam(value = "response") String response,
         @RequestParam(value = "contents") String contents, Model model) {
      
      TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
      
      BoardDTO dto = new BoardDTO();
      dto.setGroupno(grNum);
      dto.setCategoryno(cgNum);
      dto.setMemberno(memNum);
      dto.setBoardcontents(contents);
      dto.setRequestboolean(1);

      dao.insertRequest(dto);

      
      this.transactionManager.commit(status);
      
      // 요청받는사람 업데이트
      RequestDTO reqdto = new RequestDTO();
      int bno = rdao.newlyReqbno();
      
      reqdto.setBno(bno);
      reqdto.setResponseid(response);
      rdao.updateReq(reqdto);
      
      model.addAttribute("groupno", grNum);

      return "redirect:/wall";
   }
   
   
   
   // 요청 status 변경 0=요청, 1=진행중, 2=완료
   @RequestMapping(value = "/updateStatus")
   public String updateStatus(
         @RequestParam(value = "bno") int bno,
         @RequestParam(value = "groupno") int groupno,
         @RequestParam(value = "status") int status, Model model) {
      
      RequestDTO dto = new RequestDTO();
      
      dto.setBno(bno);
      dto.setReqstatus(status);
      rdao.updateStatus(dto);
      
      model.addAttribute("groupno", groupno);
      
      return "redirect:/wall";
   }
   
   
   
   //내가 받은 요청만 출력
   @RequestMapping(value = "/myRequest")
   public String myreq(
         @RequestParam(value = "memberno") int memberno, Model model) {
      //memberno는 세션의 값
      MemReqGroupDTO dto = new MemReqGroupDTO();
      dto.setMemberno(memberno);
      List<MemReqGroupDTO> list = mrgDao.myReq(dto);
      
      model.addAttribute("list", list);
      
      return "board/myRequest";
   }
   
   
   
   
   
   
   
   
   
   

}