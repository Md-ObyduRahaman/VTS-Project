package nex.vts.backend.Model.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;




@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "DRIVERDETAILS")
public class DriverDetails {

	@Id
	@JsonProperty("driverId")
	private Long DRIVERID;

	@JsonProperty("Father Name")
	private String FATHERNAME;

	@JsonProperty("Mobile Number")
	private String MOBILENUMBER;

	@JsonProperty("License Number")
	@Column(name = "LICENSENUMBER")
	private String LICENSENUMBER;

	@JsonProperty("Address")
	private String ADDRESS;

	@JsonProperty("Name")
	private String NAME;

}