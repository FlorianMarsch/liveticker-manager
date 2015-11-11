package fussballmanager;

import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import de.fussballmanager.db.entity.club.Club;
import de.fussballmanager.db.entity.trainer.Trainer;

public class BeanUtilsTest {

	@Test
	public void testGetSet() throws IllegalAccessException, InvocationTargetException {
		Trainer tempTrainer = new Trainer();
		BeanUtils.setProperty(tempTrainer, "name", "name");
		Assert.assertEquals("name", tempTrainer.getName());
		
		Club tempClub = new Club();
		Assert.assertNull(tempClub.getExternID());
		BeanUtils.setProperty(tempClub, "externID", "1");
		Assert.assertTrue(tempClub.getExternID().equals(1));
	}
	
	@Test
	public void testGetSimpleProperty() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Trainer tempTrainer = new Trainer();
		
		Class checkValue =PropertyUtils.getPropertyType(tempTrainer, "name");
		Assert.assertTrue(checkValue.equals(String.class));
		System.out.println(checkValue);
	}

}
