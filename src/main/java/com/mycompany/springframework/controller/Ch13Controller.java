package com.mycompany.springframework.controller;

import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.springframework.dto.Ch13Board;
import com.mycompany.springframework.dto.Ch13Member;
import com.mycompany.springframework.dto.Ch13Pager;
import com.mycompany.springframework.dto.Ch13UpdateBoardForm;
import com.mycompany.springframework.dto.Ch13WriteBoardForm;
import com.mycompany.springframework.interceptor.LoginCheck;
import com.mycompany.springframework.service.Ch13BoardService;
import com.mycompany.springframework.service.Ch13MemberService;
import com.mycompany.springframework.service.Ch13MemberService.JoinResult;
import com.mycompany.springframework.service.Ch13MemberService.LoginResult;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/ch13")
public class Ch13Controller {
	@Autowired
	private Ch13BoardService boardService;
	
	@Resource
	private Ch13MemberService memberService;
	
	@LoginCheck
	@GetMapping("/writeBoardForm")
	public String writeBoardForm(Model model) {
		log.info("실행");
		model.addAttribute("chNum", "ch13");
		return "ch13/writeBoardForm";
	}
	
	/* 
	 *  파일 시스템에 저장
	 * @PostMapping("/writeBoard")
	public String writeBoard(Ch13WriteBoardForm form) throws Exception{
		log.info("실행");
		Ch13Board board = new Ch13Board();
		board.setBtitle(form.getBtitle());
		board.setBcontent(form.getBcontent());
		board.setMid("user");
		MultipartFile battach = form.getBattach();
		if(!battach.isEmpty()) {
			board.setBattachoname(battach.getOriginalFilename());
			board.setBattachtype(battach.getContentType());
			String fileName = new Date().getTime() + "-" + battach.getOriginalFilename();
			board.setBattachsname(fileName);
			String saveDir ="C:\\2024-oti\\worksapce-spring\\uploadFiles";
			File file = new File(saveDir, fileName);
			battach.transferTo(file);
		}
		boardService.writeBoard(board);
		return "redirect:/";
	}
	*/
	
	//DB에 저장
    @PostMapping("/writeBoard")
   public String writeBoard(Ch13WriteBoardForm form, HttpSession session) throws Exception {
    	  log.info("실행");
      Ch13Board board = new Ch13Board();
      board.setBtitle(form.getBtitle());
      board.setBcontent(form.getBcontent());
      //board.setMid("user");
      Ch13Member member = (Ch13Member) session.getAttribute("login");
      board.setMid(member.getMid());
      MultipartFile battach = form.getBattach();
      if(!battach.isEmpty()) {
         board.setBattachoname(battach.getOriginalFilename());
         board.setBattachtype(battach.getContentType());
         board.setBattachdata(battach.getBytes());
      }
      
      boardService.writeBoard(board);
      
      return "redirect:/ch13/boardList";
   }
   
	@LoginCheck
	@GetMapping("/boardList")
	public String boardList(Model model,@RequestParam(defaultValue="1")
			int pageNo, HttpSession session) {
		log.info("실행");
		
		model.addAttribute("chNum", "ch13");
		// 페이징 처리
		int totalRows = boardService.getTotalRows();
		// 10=> 목록 개수, 그룹 개수, 총 행수, 가고 싶은 페이지 번호
		Ch13Pager pager = new Ch13Pager(10, 5, totalRows, pageNo);
		session.setAttribute("pager", pager);
		
		List<Ch13Board> list = boardService.getBoardList(pager);
		model.addAttribute("list", list);
		return "ch13/boardList";
	}
	
	@GetMapping("/detailBoard")
	public String detailBoard(int bno, Model model) {
		log.info("실행");
		Ch13Board board = boardService.getBoard(bno);
		model.addAttribute("chNum", "ch13");
		model.addAttribute("board", board);
		return "ch13/detailBoard";
	}
	
	@GetMapping("/detailBoardAddHitcount")
	public String detailBoardAddHitcount(int bno, Model model) {
		log.info("실행");
		boardService.addHitcount(bno);
		return detailBoard(bno, model);
	}
	
	@GetMapping("/attachDownload")
	public void attachDownload(int bno, HttpServletResponse response) throws Exception{
		log.info("실행");
		Ch13Board board = boardService.getBoardAttach(bno);
		//첨부가 포함된 게시물 전부를 가져옴
		
		//응답 헤더에 들어가는 Content-Type 내용 설정
	      String contentType = board.getBattachtype();
	      response.setContentType(contentType);
	      
	      //파일로 저장하기 위한 설정
	      String fileName = board.getBattachoname();
	      String encodingfileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
	      response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingfileName + "\"");
	      
	      //응답 본문에 파일 데이터를 출력
	      OutputStream out = response.getOutputStream();
	      out.write(board.getBattachdata());
	      out.flush();
	      out.close();
	}
	
	@GetMapping("/updateBoardForm")
	public String updateBoardForm(int bno, Model model) {
		log.info("실행");
		model.addAttribute("chNum", "ch13");
		Ch13Board board = boardService.getBoard(bno);
		model.addAttribute("board", board);
		return "ch13/updateBoardForm";
	}
	
	@PostMapping("/updateBoard")
	public String upadatBoard(Ch13UpdateBoardForm form) throws Exception{
		log.info("실행");
		Ch13Board board = new  Ch13Board();
		board.setBno(form.getBno());
		board.setBtitle(form.getBtitle());
		board.setBcontent(form.getBcontent());
		MultipartFile mf = form.getBattach();
		if(!mf.isEmpty()) {
			board.setBattachoname(mf.getOriginalFilename());
			board.setBattachtype(mf.getContentType());
			board.setBattachdata(mf.getBytes());
		}
		boardService.updateBoard(board);
		return "redirect:/ch13/detailBoard?bno=" + form.getBno();
	}
	
	@GetMapping("/deleteBoard")
	public String deleteBoard(int bno, HttpSession session) {
		log.info("실행");
		boardService.deleteBoard(bno);
		Ch13Pager pager = (Ch13Pager) session.getAttribute("pager");
		int pageNo = pager.getPageNo();
		return "redirect:/ch13/boardList?pageNo=" + pageNo;
	}
	
	@GetMapping("/joinForm")
	public String joinForm(Model model) {
		model.addAttribute("chNum", "ch13");
		log.info("실행");
		return "ch13/joinForm";
	}
	
	@PostMapping("/join")
	public String join(Ch13Member member, Model model) {
		member.setMenabled(true);
		log.info(member.toString());
		JoinResult joinResult = memberService.join(member);
		if(joinResult == JoinResult.FAIL_DUPLICATED_MID) {
			String errorMessage = "중복 아이디임";
			model.addAttribute("errorMessage", errorMessage);
			return "ch13/joinForm";
		}else { //Success
			return "redirect:/ch13/loginForm";
		}
	}
	
	@GetMapping("/loginForm")
	public String loginForm(Model model) {
		model.addAttribute("chNum", "ch13");
		log.info("실행");
		return "ch13/loginForm";
	}
	
	@PostMapping("/login")
	public String login(Ch13Member member, Model model, HttpSession session) {
		log.info("실행");

		LoginResult loginResult = memberService.login(member);
		if(loginResult == LoginResult.FAIL_MID) {
			model.addAttribute("chNum", "ch13");
			model.addAttribute("errorMid", "아이디가 없슈.");
			return "ch13/loginForm";
		}else if(loginResult == LoginResult.FAIL_MPASSWORD) {
			model.addAttribute("chNum", "ch13");
			model.addAttribute("errorMpassword", "비밀번호가 틀려유.");
			return "ch13/loginForm";
		}else if(loginResult == LoginResult.FAIL_ENABLED) {
			return "redirect:/";	
		}else {//Success
			session.setAttribute("login", member);
			return "redirect:/";			
		}
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		log.info("실행");
		session.removeAttribute("login");
		return "redirect:/ch13/loginForm";		
	}
	
	
}
