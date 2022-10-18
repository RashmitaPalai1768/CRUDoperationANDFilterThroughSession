package com.txt.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.txt.Repo.DemoSessionRepo;
import com.txt.entity.DemoSession;

@Controller
public class DemoSessionController {

	@Autowired
	DemoSessionRepo DemoSessionRepo;
	
	@RequestMapping("/")
	public String home(Model model) {
		System.out.println("home...........");
		model.addAttribute("ss",DemoSessionRepo.findAll());			
		return "index";
	}
	
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public String save(Model model,@ModelAttribute DemoSession demoSession,
			@RequestParam(value = "name1", required = false) String name,
			@RequestParam(value = "salary1", required = false) Double salary
			) {
		System.out.println("save...........");
		
		demoSession.setName(name);
		demoSession.setSalary(salary);
		DemoSessionRepo.save(demoSession);
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/ExcelReport.htm",method = RequestMethod.POST)
	public String excel(Model model,HttpServletResponse response,HttpSession session) {
		System.out.println("Excel...........");
		//List<DemoSession> findAll = DemoSessionRepo.findAll();
		List<DemoSession> findAll=(List<DemoSession>)session.getAttribute("ss1");
		try {
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Country Report");

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.BIG_SPOTS);

			Font newFont = workbook.createFont();
			newFont.setBold(true);
			newFont.setColor(IndexedColors.WHITE.getIndex());
			newFont.setFontHeightInPoints((short) 15);
			newFont.setItalic(true);

			headerCellStyle.setFont(newFont);
			Row headerRow = sheet.createRow(6);
			Row rows = sheet.createRow(0);
			Row rows1 = sheet.createRow(2);

			Cell heading = rows.createCell(0);
			heading.setCellStyle(headerCellStyle);
			heading.setCellValue("Demo Report");

			Cell genCell = rows1.createCell(0);
			genCell.setCellStyle(headerCellStyle);
			genCell.setCellValue("Generated On:");
			rows1.createCell(1).setCellValue(new SimpleDateFormat("dd-MMM-yyyy").format(new Date()));

			String[] columns = { "Sl. No.", "Name", "Salary" };
			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(headerCellStyle);
			}
			int rowNum = 7;
			int count = 0;
			for (DemoSession allEntity1 : findAll) {

				Row row = sheet.createRow(rowNum++);
				if (allEntity1.getClass() != null) {
					count = count + 1;
					String s = String.valueOf(count);
					row.createCell(0).setCellValue(s.trim());

					if (allEntity1.getName() != null) {
						row.createCell(1).setCellValue(allEntity1.getName().trim());
					} else {
						row.createCell(1).setCellValue("-NA-");
					}if (allEntity1.getSalary() != null) {
						row.createCell(2).setCellValue(allEntity1.getSalary());
					} else {
						row.createCell(2).setCellValue("-NA-");
					}
					
				}
			}

			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}

			response.setContentType("application/vnd.ms-excel");

			String FILENAME = "DemoExcelReport.xls";
			response.addHeader("Content-Disposition", "attachment; filename=" + FILENAME);
			try {
				workbook.write(response.getOutputStream());
				response.getOutputStream().flush();
				response.getOutputStream().close();
				workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	@RequestMapping(value = "/SearchExcelReport.htm",method = RequestMethod.POST)
	public String SearchExcel(Model model,HttpServletResponse response,HttpSession session,
			@RequestParam(value = "name",required = false)String name,
			@RequestParam(value = "salary",required = false)Double salary) {
		System.out.println("ExcelSearch...........");
		List<DemoSession> all;
		if(name != null && !name.equals("") && salary == null) {
			System.out.println("Through name");
			all=DemoSessionRepo.getByName(name);
		}else if((name ==null || name.equals("")) && salary != null) {
			System.out.println("Through salary");
			all=DemoSessionRepo.getBySalary(salary);
		}else {
			System.out.println("Through All");
			all=DemoSessionRepo.findAll();
		}
		
		//System.out.println(byName);
		session.removeAttribute("ss1");//session deactivate
		
		session.setAttribute("ss1",all);	
		model.addAttribute("ss",all);	
		return "index";
	}
}
