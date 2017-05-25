package com.bstek.bdf3.dorado.jpa.policy.impl;

import java.lang.reflect.Field;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;

/**
 *@author Kevin.yang
 *@since 2015年5月18日
 */
public class UUIDPolicy implements GeneratorPolicy {

	@Override
	public void apply(Object entity, Field field) {
		
		if (EntityUtils.isEntity(entity)) {
			EntityState state = EntityUtils.getState(entity);
			if (EntityState.NEW.equals(state)) {
				if(field.getType() == String.class && StringUtils.isEmpty(EntityUtils.getString(entity, field.getName()))) {
					EntityUtils.setValue(entity, field.getName(), UUID.randomUUID().toString());	
				}
			}
		} else {
			if(field.getType() == String.class) {
				field.setAccessible(true);
				Object value = ReflectionUtils.getField(field, entity);
				if (value == null) {
					ReflectionUtils.setField(field, entity, UUID.randomUUID().toString());
				}
			}
		}

	}

}
