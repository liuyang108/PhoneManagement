package au.com.belong.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

	@Schema(description = "Time stamp.", example = "2021-10-24 07:52:58")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime timestamp;
	
	@Schema(description = "Response code. 400 or 404", example = "400")
	private int status;
	
	@Schema(description = "Error description.", example = "Can not found any phone.")
	private String error;

}
