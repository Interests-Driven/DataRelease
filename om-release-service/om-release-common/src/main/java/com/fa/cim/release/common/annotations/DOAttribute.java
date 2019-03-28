package com.fa.cim.release.common.annotations;

import com.fa.cim.common.support.ObjectIdentifier;
import com.fa.cim.release.common.annotations.repeatable.DOAttributes;

import java.lang.annotation.*;

/**
 * description:
 * <p>DOAttribute .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 11:59
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DOAttributes.class)
@Inherited
public @interface DOAttribute {


	/**
	 * the name origin attribute in the dto use prefix "this." to refer to another attribute of the same dto entity;
	 * @return string
	 */
	String fromAttribute () default "";

	/**
	 * the name of the target attribute, use prefix "target.{attributeName}." to refer to the entity attribute in the BO;
	 * @return string
	 */
	String toAttribute ();

	/**
	 * convert the attribute to a specific returnType;
	 * @return DOAttribute.Type
	 */
	Type convertTo () default Type.NONE;

	enum Type {
		NONE (null), // no conversion - default;
		INT (Integer.TYPE), // convert to Integer - number returnType required;
		LONG (Long.TYPE), // convert to Long - number returnType required;
		SHORT (Short.TYPE), // convert to Short - number returnType required;
		BYTE (Byte.TYPE), // convert to Byte - number returnType required;
		DOUBLE (Double.TYPE), // convert to Double - number returnType required;
		FLOAT (Float.TYPE), // convert Float - number returnType required;
		BOOLEAN (Boolean.TYPE), // convert to Boolean - Short required;
		STRING (String.class), // convert to String - call toString () of the Object;
		OBJECT_IDENTIFIER (ObjectIdentifier.class)
		;
		Class<?> classType;

		public Class<?> getClassType () {
			return this.classType;
		}

		Type (Class<?> classType) {
			this.classType = classType;
		}
	}

}
