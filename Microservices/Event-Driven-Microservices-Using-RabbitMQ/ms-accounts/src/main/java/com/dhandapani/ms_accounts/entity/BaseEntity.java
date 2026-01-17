package com.dhandapani.ms_accounts.entity;

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

@MappedSuperclass
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@CreatedBy
	@Column(name = "created_by", updatable = false)
	private String createdBy;
	
	/**
	 * We do not have to insert values for this column when a new record is created for the first time
	 */
	@LastModifiedDate
	@Column(name = "updated_at", insertable = false)
	private LocalDateTime updatedAt;
	
	@LastModifiedBy
	@Column(name = "updated_by", insertable = false)
	private String updatedBy;
}
