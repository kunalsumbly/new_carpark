package com.au.sofico.dto;

import java.util.ArrayList;
import java.util.List;

public class SsaParkingParserResponseDTO extends AbstractParserResponseDTO {

	private List<SsaEmployeeDetailsDTO> ssaEmployeeDetails = new ArrayList<SsaEmployeeDetailsDTO>();

	private List<SsaParkingSpotsDetailsDTO> ssaParkingSpotsDetails = new ArrayList<SsaParkingSpotsDetailsDTO>();

	public List<SsaEmployeeDetailsDTO> getSsaEmployeeDetails() {
		return ssaEmployeeDetails;
	}

	public List<SsaParkingSpotsDetailsDTO> getSsaParkingSpotsDetails() {
		return ssaParkingSpotsDetails;
	}

}
