package com.ibm.cognitive.app;

import java.util.List;

public interface StorageService {
	
	public List<Object> retrieveObjects(String dataType);
	public boolean storeObjects(String dataType,Object objToStore);
	

}