package com.severson.taskmanager.utilities;

import java.util.Date;

import com.severson.taskmanager.exceptions.FormValidationException;

/**
 * @author andrewseverson
 *
 */
public class FormValidationHelper {

	/**
	 * @param canBeNull
	 * @param maxCharacters
	 * @param field
	 * @return if the field is valid or not based on the provided conditions
	 * @throws FormValidationException 
	 */
	public static String validateStringField(boolean canBeNull, Integer minCharacters, Integer maxCharacters, String field, String entityAndFieldName) throws FormValidationException{
		// if the field cannot be null and the field is null return false
		checkNullFieldsNullValue(canBeNull, field, entityAndFieldName);
		// if the field can be null and it is null return true
		if(canBeNull && field == null){
			return field;
		}
		// last validate the character length and make sure it isn't exceeded
		if(field.length() >= minCharacters && field.length() <= maxCharacters){
			return field;
		}
		throw new FormValidationException(entityAndFieldName + " must be between " + minCharacters + " and " + maxCharacters + " characters in length");
	}
	
	/**
	 * @param canBeNull
	 * @param dateCheckStartTime
	 * @param field
	 * @param entityAndFieldName
	 * @return if field is valid or not based on provided conditions
	 * @throws FormValidationException
	 */
	public static Date validateDateFields(boolean canBeNull, Date dateCheckStartTime, Date field, String entityAndFieldName) throws FormValidationException{
		// if the field cannot be null and the field is null return false
		checkNullFieldsNullValue(canBeNull, field, entityAndFieldName);
		// if the field can be null and it is null return true
		if(canBeNull && field == null){
			return field;
		}
		// validate the input date comes after the min date time
		if(field.getTime() > dateCheckStartTime.getTime()){
			return field;
		}
		throw new FormValidationException(entityAndFieldName + " must be set to a date greater than " + dateCheckStartTime.toString());
	}
	
	/*
	 * Private Helper Methods
	 */


	/**
	 * @param canBeNull
	 * @param field
	 * @param entityAndFieldName
	 * @throws FormValidationException
	 */
	private static void checkNullFieldsNullValue(boolean canBeNull, Object field, String entityAndFieldName)
			throws FormValidationException {
		if(!canBeNull && field == null){
			throw new FormValidationException(entityAndFieldName + " cannot be null");
		}
	}
}
