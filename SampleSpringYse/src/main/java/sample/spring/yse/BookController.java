package sample.spring.yse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;
import java.util.List;

@Controller
public class BookController {
	
	@Autowired
	BookService bookService;
	
	/*
	스프링은 http 메소드가 GET인지 POST인지 상관하지 않고 @RequestMapping 어노테이션이 있으면 무조건 파라미터를 넣어준다. 
	따라서 파라미터 map 안에는 4개 데이터가 다 들어있다. map 데이터 예시는 아래와 같다.
	 * 
	 */
	
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createPost(@RequestParam Map<String, Object> map) {
		
		ModelAndView mav = new ModelAndView();
		String bookId = this.bookService.create(map);
		if(bookId == null) {
			mav.setViewName("redirect:/create");
		}else {
			mav.setViewName("redirect:/detail?bookId="+bookId);
		}
		
		return mav;
	}	
	
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create() {
		return new ModelAndView("book/create");
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam Map<String, Object> map) {
		Map<String, Object> detailMap = this.bookService.detail(map);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("data", detailMap);
		String bookId = map.get("bookId").toString();
		mav.addObject("bookId", bookId);
		mav.setViewName("/book/detail");
		return mav;
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam Map<String, Object> map) {
		Map<String, Object> detailMap = this.bookService.detail(map);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("data", detailMap);
		mav.setViewName("/book/update");
		return mav;
		
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView updatePost(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		
		boolean isUpdateSuccess = this.bookService.edit(map);
		if(isUpdateSuccess) {
			String bookId = map.get("bookId").toString();
			mav.setViewName("redirect:/detail?bookId="+bookId);
		}else {
			mav = this.update(map);
		}
		return mav;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView deletePost(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		
		boolean isDeleteSuccess = this.bookService.remove(map);
		if(isDeleteSuccess) {
			mav.setViewName("redirect:/list");
		}else {
			String bookId = map.get("bookId").toString();
			mav.setViewName("/redirect:/detail?bookId="+bookId);
		}
		return mav;
	}
		
	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam Map<String, Object>map) {
		List<Map<String, Object>> list = this.bookService.list(map);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("data", list);
		
		if(map.containsKey("keyword")){
			mav.addObject("keyword", map.get("keyword"));
		}
		
		mav.setViewName("/book/list");
		return mav;
		
		
	}
	
	
	
	
	
	
	
	
}
