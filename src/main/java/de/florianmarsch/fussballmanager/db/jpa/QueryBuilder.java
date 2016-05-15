package de.florianmarsch.fussballmanager.db.jpa;

import javax.persistence.EntityManager;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.PathMetadataFactory;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.StringPath;

import de.florianmarsch.fussballmanager.db.entity.AbstractEntity;

public class QueryBuilder {

	private EntityManager em;
	private EntityPathBase<? extends AbstractEntity> source;
	
	public QueryBuilder(EntityManager aEntityManager, EntityPathBase<? extends AbstractEntity> aFrom){
		em = aEntityManager;
		source = aFrom;
	}
	
	public JPAQuery select(BooleanBuilder condition){
		
		JPAQuery tempQuery = new JPAQuery(em).from(source);
		tempQuery.where(condition);
		return tempQuery;
	}

}
