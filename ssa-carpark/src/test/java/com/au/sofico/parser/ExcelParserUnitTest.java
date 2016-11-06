package com.au.sofico.parser;

import java.util.List;

import com.au.sofico.dto.AbstractParserRequestDTO;
import com.au.sofico.dto.AbstractParserResponseDTO;
import com.au.sofico.dto.SsaParkingParserRequestDTO;
import com.au.sofico.dto.SsaParkingParserResponseDTO;
import com.au.sofico.parser.ExcelParser;

import junit.framework.TestCase;

public class ExcelParserUnitTest extends TestCase {
	
	private ExcelParser excelParser = new ExcelParser();

	public void testParseAbstractParserRequestDTO() throws Exception {
		AbstractParserRequestDTO requestDTO = new SsaParkingParserRequestDTO() ;
		requestDTO.setFilePath("C:/Users/kusu/git/ssa-carpark/ssa-carpark/src/main/resources/employee_parking_details.xlsx");
		requestDTO.setFileTrfmXmlPath("C:/Users/kusu/git/ssa-carpark/ssa-carpark/src/main/resources/employee_parking_mapping.xml");
		requestDTO.setParserType("excel");
		List<AbstractParserResponseDTO>respDTO = excelParser.parse(requestDTO);
		
		for(int i=0; i< respDTO.size();i++){
			SsaParkingParserResponseDTO resChildpObj = (SsaParkingParserResponseDTO)respDTO.get(i);
			for(int k=0; k < resChildpObj.getSsaEmployeeDetails().size();k++){
				System.out.println(resChildpObj.getSsaEmployeeDetails().get(k).getEmployeeName());
				System.out.println(resChildpObj.getSsaEmployeeDetails().get(k).getDateOfJoining());
			}
			
			for(int j=0;j<resChildpObj.getSsaParkingSpotsDetails().size();j++){
				System.out.println(resChildpObj.getSsaParkingSpotsDetails().get(j).getParkingNumber());
				System.out.println(resChildpObj.getSsaParkingSpotsDetails().get(j).getComments());
			}
			
			
			//System.out.println(respDTO.get(i));
		}
		
	}

}
