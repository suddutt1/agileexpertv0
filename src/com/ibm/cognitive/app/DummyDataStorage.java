package com.ibm.cognitive.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dummy in memory data storage in static variable
 * 
 * @author SUDDUTT1
 *
 */
public class DummyDataStorage implements StorageService {

	private static final Map<String, List<Object>> _storage = new HashMap<>();

	@Override
	public List<Object> retrieveObjects(String dataType) {
		// TODO Auto-generated method stub
		return _storage.get(dataType);
	}

	@Override
	public boolean storeObjects(String dataType, Object objToStore) {
		// TODO Auto-generated method stub
		List<Object> objectList = _storage.get(dataType);
		if(objectList==null)
		{
			objectList = new ArrayList<>();
			_storage.put(dataType, objectList);
		}
		objectList.add(objToStore);
		return true;
	}
	

}
