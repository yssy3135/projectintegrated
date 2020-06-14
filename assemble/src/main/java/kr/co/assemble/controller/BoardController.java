package kr.co.assemble.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.assemble.dao.BoardDAO;
import kr.co.assemble.dao.ComposedDAO;
import kr.co.assemble.dto.BoardDTO;
import kr.co.assemble.dto.Groupboard_Memberinfo_FileDTO;
import kr.co.assemble.dto.searchDTO;
import kr.co.assemble.dto.searchParamDTO;

@Controller
public class BoardController {

	@Autowired
	BoardDAO dao;
	
	
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}


	//글쓰기 폼
	@RequestMapping(value = "/write")
	public String writeForm() {
		return "board/write";
	}
	
	
	//글쓰기 완료 페이지
	@RequestMapping(value = "/writeOk")
	public String writeOk(
			@RequestParam(value = "grNum") int grNum,
			@RequestParam(value = "cgNum") int cgNum,
			@RequestParam(value = "memNum") int memNum,
			@RequestParam(value = "contents") String contents, Model model) {
		
		BoardDTO dto = new BoardDTO();
		
		dto.setGroupno(grNum);
		dto.setCategoryno(cgNum);
		dto.setMemberno(memNum);
		dto.setBoardcontents(contents);
		dao.write(dto);
		
		model.addAttribute("contents", contents);
		model.addAttribute("groupno", grNum);
		
		
		return "redirect:/wall";
	}
	
	
	@Autowired
	ComposedDAO cdao;
	//그룹별 게시글 조회 
	//그룹별 정보, 구성원 정보(Groups_Memberinfo_Composed_DTO)
	@RequestMapping("/assemble.io/{mi_assembleName}/g/{groupno}/wall")
	public String groupBoard(@PathVariable("groupno")int groupno, 
							@PathVariable("mi_assembleName") String assemblename, 
							@RequestParam("categoryno")int categoryno, Model model){
		
		//세션정보 받아서  주소에 assemble이름 넣기
//		assemblename = (String)session.getAttribute("mi_assembleName");
		
		
		//그룹장 이름 출력
		String list = cdao.selectGroupMastername(groupno);
		model.addAttribute("mastername", list);
	
		//그룹장 아이디 출력
		String list1 = cdao.selectGroupMasterId(groupno);
		model.addAttribute("masterid", list1);
		
		//그룹명 출력
		String groupname = cdao.selectGroupName(groupno);
		model.addAttribute("groupname", groupname);

		//insert할 때 필요한 categoryno
		model.addAttribute("categoryno", categoryno);
		
		System.out.println(categoryno);
		
		//그룹별 게시글 출력
		List<Groupboard_Memberinfo_FileDTO> list3 = dao.boardlist(groupno);
		model.addAttribute("thirdlist", list3);
		
		//System.out.println(groupno);-+
		return "board/wall";
	}
	
		
	
	//게시글 수정을 위한 bno로 해당 게시글 정보 조회
		@RequestMapping(value = "/selectBoard")
		public String selectbno(
				@RequestParam(value = "bno") int bno, Model model) {
			System.out.println(bno);
			
			List<BoardDTO> list = dao.selectOne(bno);
			model.addAttribute("list", list);
			
			return "board/modify";
		}
		
		
		//게시글 수정
		@RequestMapping(value = "/modify")
		public String modifyBoard(
				@RequestParam(value = "bno") int bno,
				@RequestParam(value = "contents") String contents,
				@RequestParam(value = "groupno") int groupno, Model model) {
			
			BoardDTO dto = new BoardDTO();
			dto.setBno(bno);
			dto.setBoardcontents(contents);
			
			dao.updateBoard(dto);

					
			model.addAttribute("dto", dto);
			model.addAttribute("groupno", groupno);
			
			return "redirect:/wall";
		}
		
		
		//게시글 삭제 - 파일이랑 연결되있으면 파일에도 bno넘겨서 삭제(트리거 걸어놓음)
		@RequestMapping(value = "/deleteBoard")
		public String deleteBoard(
				@RequestParam(value = "bno") int bno,
				@RequestParam(value = "groupno") int groupno, Model model) {
			
			dao.deleteBoard(bno);
			model.addAttribute("groupno", groupno);
			
			return "redirect:/wall";
		}
	   
	   
	   //북마크 UPDATE
	   
	   
	
	
	   //좋아요 update
	/*
	 * @RequestMapping("/like.do") public String Like(
	 * 
	 * @RequestParam int num1, Model model) throws Exception{
	 * 
	 * BoardDTO dto = new BoardDTO();
	 * 
	 * dto.setBoardlike(num1);
	 * 
	 * dao.updateLike(dto); model.addAttribute("dto", dto);
	 * 
	 * return "board/wall";
	 * 
	 * }
	 */
	   
	   
	   //좋아요 count
	   
	
	
	
	   // 검색
		@RequestMapping(value = "/searchBoard")
		public String searchBoard(@RequestParam(value = "value") String value,
								  @RequestParam(value = "assemble") String assemble,Model model) {
			String value2 = "%"+value+"%";
			
			searchParamDTO dto = new searchParamDTO(value2, assemble);
			
			List<searchDTO> list = dao.searchlist(dto);
			model.addAttribute("searchlist",list);
			model.addAttribute("value", value);
			
			return "search";
			
		}
	
	
	
	
	
	
	
	
	
}
