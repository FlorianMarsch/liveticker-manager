package de.fussballmanager.db.jpa;

import javax.persistence.EntityManager;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.PathMetadataFactory;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.StringPath;

import de.fussballmanager.db.entity.AbstractEntity;

public class QueryBuilder {

	private EntityManager em;
	private EntityPathBase<? extends AbstractEntity> source;
	
	public QueryBuilder(EntityManager aEntityManager, EntityPathBase<? extends AbstractEntity> aFrom){
		em = aEntityManager;
		source = aFrom;
	}
	
	public JPAQuery select(BooleanBuilder condition){
		
		JPAQuery tempQuery = new JPAQuery(em).from(source);
		tempQuery.where(buildCondition(condition));
		return tempQuery;
	}

	private BooleanBuilder buildCondition(BooleanBuilder condition) {
		BooleanBuilder finalCondition = null;
		BooleanBuilder schemeCondition = new BooleanBuilder();
		
		PathMetadata<String> forProperty = PathMetadataFactory.forProperty(source, "schemaName");
		   
		
		schemeCondition.and(new StringPath(forProperty).eq( System.getenv("SCHEME")));
		if(condition!= null && condition.hasValue()){
			BooleanBuilder joinCondition = new BooleanBuilder();
			joinCondition.and(condition);
			joinCondition.and(schemeCondition);
			finalCondition = joinCondition;
		}else{
			finalCondition = schemeCondition;
		}
		return finalCondition;
	}
}
