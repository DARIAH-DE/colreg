package eu.dariah.de.colreg.service.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class BaseEntityService implements EntityService {
	@Override
	public Map<Integer, String> getOrderedImageMap(Map<Integer, String> imageMap) {
		if (imageMap!=null) {
			List<Integer> dropImages = new ArrayList<Integer>();
			
			String imageId;
			for (Integer index : imageMap.keySet()) {
				imageId = imageMap.get(index);
				if (imageId==null || imageId.trim().isEmpty()) {
					dropImages.add(index);
				}
			}
			if (dropImages.size()>0) {
				for (Integer dropIndex : dropImages) {
					imageMap.remove(dropIndex.intValue());
				}
			}
			if (imageMap.size()==0) {
				return null;
			}
			
			imageMap = new TreeMap<Integer, String>(imageMap);
			Map<Integer, String> result = new TreeMap<Integer, String>();
			int idxNew = 0;
			for (Integer index : imageMap.keySet()) {
				result.put(idxNew++, imageMap.get(index));
			}
			imageMap = result;
		}
		return imageMap;
	}
}
