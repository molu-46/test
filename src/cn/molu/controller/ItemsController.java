package cn.molu.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.molu.pojo.Items;
import cn.molu.service.ItemsService;
import cn.molu.vo.QueryVo;

@Controller
@RequestMapping("/items")
public class ItemsController {
	@Autowired
	private ItemsService itemsService;

	@RequestMapping("/list")
	public ModelAndView itemsList() throws Exception {
		List<Items> list = itemsService.list();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemList", list);
		modelAndView.setViewName("itemList");
		return modelAndView;
	}

	/**
	 * SpringMvc中默认支持的参数类型。可以在controller中加入这些类型，也可以不加入 HttpServletRequest
	 * HttpServletResponse Model HttpSession *
	 */
	/*
	@RequestMapping("itemEdit")
	public String itemEdit(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String idStr = request.getParameter("id");
		Items item = itemsService.findItemsById(Integer.parseInt(idStr));

		// model模型，模型中放入了返回给页面的数据 // model底层就是用request域来传递数据，但是对request域进行了扩展。
		model.addAttribute("item", item); //
		// 如果springMvc返回一个简单的string字符串，那么springMvc就会认为这个字符串就是页面的名称
		return "editItem";
	}
	*/
	/**
	 * rest方式
	 * @PathVariable("id")	可以接受页面传过来的参数
	 * @RequestMapping("itemEdit/{id}")	大括号中参数名字，比如要和@PathVariable("id")	中的参数名称相同
	 */
	@RequestMapping("itemEdit/{id}")
	public String itemEdit(@PathVariable("id") Integer id, HttpServletRequest request, Model model) throws Exception {
		String idStr = request.getParameter("id");
		Items item = itemsService.findItemsById(id);

		/// model模型，模型中放入了返回给页面的数据
		// model底层就是用request域来传递数据，但是对request域进行了扩展。
		model.addAttribute("item", item);
		// 如果springMvc返回一个简单的string字符串，那么springMvc就会认为这个字符串就是页面的名称
		return "editItem";
	}

	/**
	 * springmvc 可以直接接收基本参数类型，包括string，并能自动转换类型 controller
	 * 方法接受参数的变量名称必须要等于页面上input中 name的属性名
	 */
	/*
	 * @RequestMapping("/updateitem") public String updateitem(Integer id,String
	 * name,Float price,String detail) throws Exception{ Items items = new
	 * Items(id, name, price, "", new Date(), detail);
	 * itemsService.updateItems(items);
	 * 
	 * return "success"; }
	 */
	/**
	 * springmvc 可以直接接收基本参数类型，包括string，并能自动转换类型 controller
	 * 方法接受参数的变量名称必须要等于页面上input中 name的属性名
	 * 
	 * @RequestMapping("/updateitem") public String updateitem(Items items)
	 * throws Exception{ itemsService.updateItems(items); return "success"; }
	 */
	@RequestMapping("/deAll")
	public String deAll(QueryVo vo) throws Exception {
		System.err.println(vo + "1231312");
		return "success";
	}

	@RequestMapping("/updateAll")
	public String updateAll(QueryVo vo) throws Exception {
		System.err.println(vo + "1231312");
		return "success";
	}

	// 商品修改提交
	@RequestMapping("/updateitem")
	public String editItemSubmit(Items items, MultipartFile pictureFile) throws Exception {

		// 1. 获图片完成名称
		String pictureFile_name = pictureFile.getOriginalFilename();
		// 2. 使用随机生成的字符串+源图片扩展名组成的新的图片名称，防止重名
		String newFileName = UUID.randomUUID().toString()
				+ pictureFile_name.substring(pictureFile_name.lastIndexOf("."));

		// 3.将图片保存到硬盘中
		File uploadPic = new java.io.File("E:\\image\\" + newFileName);
		// 向磁盘写文件
		pictureFile.transferTo(uploadPic);
		// 4.将图片保存到数据库
		items.setPic(newFileName);
		itemsService.updateItems(items);
		if (!uploadPic.exists()) {
			uploadPic.mkdirs();
		}

		return "redirect:itemEdit/"+items.getId();
	}

	/**
	 * 导入jackson的jar包在controller的方法中可以使用@RequestBody,
	 * 让springMVC将json格式字符串自动转换成java中的pojo（key要等于pojo中的属性名）
	 * 
	 * 同时如果使用@RequestBody 注解，返回Items时，会将items 转换为json
	 * 
	 */
	// 接收json
	//
	@RequestMapping("sendJson")
	public Items sendJson(@RequestBody Items items) throws Exception {
		System.out.println(items);
		return items;
	}

}
