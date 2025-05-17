package com.dhandapani.ms_loans.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Dhandapani Sudhakar
 * 
 * @MapperSuperclass indicates that a class is a superclass and is not associated with a specific database table, 
 * but its fields can be inherited by child entity classes that are associated with tables, 
 * this helps avoid code duplication and ensures a cleaner object model
 */
@MappedSuperclass
@Getter
@Setter
@ToString
/**
 * @EntityListeners(AuditingEntityListener.class) If you annotate an entity class with @EntityListeners, 
 * you can specify one or more listener classes that implement the corresponding JPA callback methods. 
 * These methods will be invoked automatically when specified events occur on the entity, 
 * such as entity creation, update, or deletion.
 * 
 * AuditingEntityListener is a built-in entity listener class provided by Spring Data JPA for auditing purposes. 
 * It is used in conjunction with auditing annotations (@CreatedBy, @LastModifiedBy, @CreatedDate, @LastModifiedDate) 
 * to automatically populate auditing-related fields in entities.
 */
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@CreatedBy
	/**
	 * We do not want to have this column updated everytime any changes happen to the record, The initial value should not be changed
	 */
	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@CreatedDate
	/**
	 * We do not want to have this column updated everytime any changes happen to the record, The initial value should not be changed
	 */
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedBy
	/**
	 * We do not have to insert values for this column when a new record is created for the first time
	 */
	@Column(name = "updated_by", insertable = false)
	private String updatedBy;

	@LastModifiedDate
	/**
	 * We do not have to insert values for this column when a new record is created for the first time
	 */
	@Column(name = "updated_at", insertable = false)
	private LocalDateTime updatedAt;

}
