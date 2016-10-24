package com.au.sofico.parser;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.au.sofico.dto.AbstractParserRequestDTO;
import com.au.sofico.dto.AbstractParserResponseDTO;
import com.au.sofico.parser.metadata.ParseMetaObject;



public class ExcelParser extends GenericParser{
	private String DATE_FORMAT="dd/MM/yyyy";
	@Override
	public List<AbstractParserResponseDTO> parse(AbstractParserRequestDTO type) throws Exception {
		List<AbstractParserResponseDTO> responseDto = new ArrayList<AbstractParserResponseDTO>();
		// read the external xml file
		File metaXmlFile = new File(type.getFileTrfmXmlPath());
		File flatFile = new File(type.getFilePath());

		FileInputStream fis = new FileInputStream(flatFile);
		JAXBContext jaxbContext = JAXBContext.newInstance(ParseMetaObject.class);
		ParseMetaObject parseMetaObject = (ParseMetaObject) jaxbContext.createUnmarshaller().unmarshal(metaXmlFile);
		
		//String excelFilePath = "Books.xlsx";
		//FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		Object obj = null;
		Workbook workbook = new XSSFWorkbook(fis);
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet firstSheet = workbook.getSheetAt(i);
			Iterator<Row> iterator = firstSheet.iterator();
			StringBuffer oneLine=new StringBuffer();
			int skipFirstRowCount =0;
			
			while (iterator.hasNext()) {// row iterator
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
							if(skipFirstRowCount == 0 ){
								skipFirstRowCount++;
								continue;
							}
						while (cellIterator.hasNext()) {// cell iterator
							Cell cell = cellIterator.next();
		
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								oneLine.append(cell.getStringCellValue()).append("|~|");
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								oneLine.append(cell.getBooleanCellValue()).append("|~|");
								break;
							case Cell.CELL_TYPE_NUMERIC:
								if(DateUtil.isCellDateFormatted(cell)){
									SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);											
									oneLine.append(sf.format(cell.getDateCellValue())).append("|~|");
								}else{
									oneLine.append(cell.getNumericCellValue()).append("|~|");
								}
								break;
							case Cell.CELL_TYPE_BLANK:
								oneLine.append("NULL").append("|~|");
								break;
							}
							
						}// when all the cells for 1 row have been read
						
						if(obj != null){
							obj =parse(parseMetaObject.getParseFields(), obj.getClass(), obj, oneLine.toString(), 0, type.getParserType(),firstSheet.getSheetName());
						}else{
							obj = parse(parseMetaObject.getParseFields(), null, null, oneLine.toString(), 0, type.getParserType(), firstSheet.getSheetName());
						}
						oneLine.setLength(0);
						
			}
			//responseDto.add((AbstractParserResponseDTO) parse(parseMetaObject.getParseFields(), obj.getClass(), obj, oneLine.toString(), 0, type.getParserType(),firstSheet.getSheetName()));
		}

		
		fis.close();
	
		
		// Construct BufferedReader from InputStreamReader
		/*BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = null;
		JAXBContext jaxbContext = JAXBContext.newInstance(ParseMetaObject.class);
		ParseMetaObject parseMetaObject = (ParseMetaObject) jaxbContext.createUnmarshaller().unmarshal(metaXmlFile);
		List<AbstractParserResponseDTO> responseDtoList = new ArrayList<AbstractParserResponseDTO>();
		if ("reader".equals(parseMetaObject.getType())) {
			while ((line = br.readLine()) != null) {
				if (parseMetaObject.getParseFields() != null) {
					// the return should be the response
					responseDto = (AbstractParserResponseDTO) parse(parseMetaObject.getParseFields(), null, null, line,0,type.getParserType());
					responseDtoList.add(responseDto);
				}
			}
			//System.out.println("the list size "+employeeList.size());
		} else {// writer

		}*/

		// transform it to pojo, using JAXB

		// based on meta XMLFile
		// navigate the structure in XML
		// based on the object type set the property of the target object
		// recursion
		responseDto .add((AbstractParserResponseDTO)obj);
		return responseDto;

	
	}

}
