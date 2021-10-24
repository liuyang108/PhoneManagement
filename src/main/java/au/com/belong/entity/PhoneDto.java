package au.com.belong.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PhoneDto {
	
	@Schema(description = "Active or deactive phone.", example = "true", required = true)
	private boolean active;

}
