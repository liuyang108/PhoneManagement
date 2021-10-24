package au.com.belong.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "phone")
public class Phone {
	
    @Schema(description = "Unique identifier of the Phone.", example = "1", required = true)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
    @Schema(description = "Phone number.", example = "040-315-0243", required = true)
    @Pattern(regexp ="^\\d{3}-\\d{3}-\\d{4}$", message = "Phone number")
    @Size(max = 12)
	@Column(name = "number")
	private String number;
	
	@JsonIgnoreProperties(value = {"name", "address", "email"})
	@ManyToOne
	@JoinColumn(name="customer_id", nullable=false)
	private Customer customer;
	
    @Schema(description = "Phone has been active or not", example = "true", required = true)
	@Column(name = "active")
	private boolean active;

}
