package dts.logic;

import java.util.List;

import dts.OperationBoundary;

public interface EnhancedOperationService extends OperationsService {
	
	public List<OperationBoundary> getAllOperationsBoundaries(String adminSpace, String adminEmail, int size, int page);
	
}
