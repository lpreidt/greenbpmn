package repo.model;

import javax.persistence.*;

import lombok.Data;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;

@Data
@Entity
@Table(name="modell")
public class Modell {

	@Id
	private String id;

	@Lob
	private Blob xml;

	@Column(name = "name")
	private String name;

	@Column(name = "childProcess")
	private String childProcess;

	@Column(name = "parentProcess")
	private String parentProcess;

	@Column(name = "startKnoten")
	private String startKnoten;

	@Column(name = "endKnoten")
	private String endKnoten;

	@Column(name = "energySumYear")
	private int energySumYear;

	@Column(name = "processType")
	private String processType;

	@Column(name = "department")
	private String department;

	@Column(name = "processDescription")
	private String processDescription;






}
