package com.au.sofico.parser;

import java.util.List;

import com.au.sofico.dto.AbstractParserRequestDTO;
import com.au.sofico.dto.AbstractParserResponseDTO;
import com.au.sofico.dto.EmployeeParserRequestDTO;
import com.au.sofico.dto.EmployeeParserResponseDTO;

import junit.framework.TestCase;

public class FlatFileParserTest extends TestCase {
	
	private FlatFileParser flatFileParser = new FlatFileParser();

	public void testParse() throws Exception {
		AbstractParserRequestDTO requestDTO = new EmployeeParserRequestDTO() ;
		requestDTO.setFilePath("X:/spring_projects/ssa-carpark/src/main/resources/employee.txt");
		requestDTO.setFileTrfmXmlPath("X:/spring_projects/ssa-carpark/src/main/resources/employee_mapping.xml");
		List<AbstractParserResponseDTO>respDTO = flatFileParser.parse(requestDTO);
		
		for(int i=0; i< respDTO.size();i++){
			EmployeeParserResponseDTO respObj = (EmployeeParserResponseDTO)respDTO.get(i);
			System.out.println(respObj.getEmployeeDTO().getEmployeeId());
			System.out.println(respObj.getEmployeeDTO().getEmployeeName());
			System.out.println(respObj.getEmployeeDTO().getAccount().getAccountName());
			System.out.println(respObj.getEmployeeDTO().getAccount().getAccountNumber());
			//System.out.println(respDTO.get(i));
		}
		
		/*int x = Integer.valueOf("2222222222");
		System.out.println(x);*/
	}

}
