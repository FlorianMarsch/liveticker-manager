package de.fussballmanager.db.service;

import java.util.List;
import java.util.Map;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.PathMetadataFactory;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.StringPath;

import de.fussballmanager.db.AccessLayerException;
import de.fussballmanager.db.entity.AbstractEntity;

public abstract class AbstractService<E extends AbstractEntity> extends AbstractServiceCache<E> {

	private Path<String> idPath;
	
	public AbstractService(EntityPathBase<E> aSource){
		source = aSource;
		PathMetadata<String> forProperty = PathMetadataFactory.forProperty(source, "id");
		idPath = new StringPath(forProperty);
	}
	
	public List<E> getAll() {
		try {
			return super.getAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessLayerException("A Call Problem occured", e);
		}
	}
	
	public  Map<String,E> getAllAsMap() {
		try {
			return super.getAllOrderedInMap(idPath );
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessLayerException("A Call Problem occured", e);
		}
	}

	public <K> Map<K,E> getAllOrderedInMap(Path<K> aKey) {
		try {
			return super.getAllOrderedInMap(aKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessLayerException("A Call Problem occured", e);
		}
	}
	
	
	public void save(E aEntity) {
		try {
			super.save(aEntity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessLayerException("A Call Problem occured", e);
		}
	}
	
	public abstract E getNewInstance();

}
