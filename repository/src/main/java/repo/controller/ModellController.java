package repo.controller;

import java.io.File;
import java.io.StringReader;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import javax.sql.*;

import io.swagger.models.Xml;
import javassist.tools.web.BadHttpRequest;

import org.apache.maven.model.Model;
import org.cyberneko.html.parsers.DOMParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import repo.dtos.ModellDto;
import repo.dtos.OverviewDto;
import repo.exception.IdAlreadyUsedException;
import repo.exception.ResourceNotFoundException;
import repo.model.Modell;
import repo.service.ModellService;

import javax.sql.rowset.serial.SerialBlob;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@RestController
@RequestMapping()
public class ModellController {

	private ModellService modellService;

	public ModellController(ModellService modellService) {
		super();
		this.modellService = modellService;
	}

	@PostMapping(path = "/modell", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public String saveModel(@RequestBody String xml){
		Modell modell = new Modell();

		try{

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xml));
			Document document = builder.parse(is);
			document.getDocumentElement().normalize();

			//Prüfen ob ID bereits genutzt wurde
			List<OverviewDto> overviewList = getAllOverviews();
			for (int i = 0; i < overviewList.size(); i++) {
				if(overviewList.get(i).getId().equalsIgnoreCase(document.getElementsByTagName("bpmn:process").item(0).getAttributes().item(1).getNodeValue())){
				throw new IdAlreadyUsedException("Id already used with: ", i, null);
				}
			}
			//DOM-Parser sortiert intern nach alphabet

			modell.setDepartment(document.getElementsByTagName("bpmn:process").item(0).getAttributes().item(0).getNodeValue());
			modell.setId(document.getElementsByTagName("bpmn:process").item(0).getAttributes().item(1).getNodeValue());
			modell.setName(document.getElementsByTagName("bpmn:process").item(0).getAttributes().item(3).getNodeValue());
			modell.setProcessType(document.getElementsByTagName("bpmn:process").item(0).getAttributes().item(5).getNodeValue());


			if(document.getElementsByTagName("bpmn:process").item(0).getAttributes().item(4).getNodeValue() != ""){
			modell.setParentProcess(document.getElementsByTagName("bpmn:process").item(0).getAttributes().item(4).getNodeValue());
			}else{
				modell.setParentProcess(null);
			}
			//startEvent finden
			NodeList startEvent = document.getElementsByTagName("bpmn:startEvent");

			String startEventString = "";
			for(int i=0; i<startEvent.getLength(); i++){
				Node startEventNodeName = startEvent.item(i);
				if(startEventNodeName.getAttributes().item(1).getNodeValue() != "")
				startEventString += startEventNodeName.getAttributes().item(1).getNodeValue() + ";";
			}
			if(startEventString == "")
				modell.setStartKnoten(null);
			else
			modell.setStartKnoten(startEventString);

			//Wenn kein endEvent in der XML gefunden wurde, soll das Feld in der DB null bleiben
			if(document.getElementsByTagName("bpmn:message").getLength() != 0){
				NodeList endEvent = document.getElementsByTagName("bpmn:message");
				String endEventStrings = "";
				for(int i=0; i<endEvent.getLength(); i++){
					Node item1 = endEvent.item(i);
					endEventStrings += item1.getAttributes().item(1).getNodeValue() + ";";
				}
				modell.setEndKnoten(endEventStrings);
			}
//			XML als BLOB einfügen
			modell.setXml(new SerialBlob(xml.getBytes()));

			//Child Prozesse speichern wenn vorhanden
			if(document.getElementsByTagName("bpmn:callActivity").getLength() != 0){
				NodeList callActivity = document.getElementsByTagName("bpmn:callActivity");
				String callActivityString = "";
				for(int i=0; i<callActivity.getLength(); i++){
					Node item1 = callActivity.item(i);
					callActivityString += item1.getAttributes().item(0).getNodeValue() + ";";
				}
				modell.setChildProcess(callActivityString);
			}
			//CO2 summieren
			int co2 = 0;
			if(document.getElementsByTagName("camunda:property").getLength() != 0){
				NodeList task = document.getElementsByTagName("camunda:property");
				for(int i=0; i<task.getLength(); i++) {
					//Es dürfen keine leeren Properties vorkommen.
					if (task.item(i).getAttributes().item(0).getNodeValue().equals("CO2")) {
						co2 += Integer.parseInt(task.item(i).getAttributes().item(1).getNodeValue());
					}
				}
				modell.setEnergySumYear(co2);
			}
			if(document.getElementsByTagName("bpmn:documentation").getLength() != 0){
				NodeList documentation = document.getElementsByTagName("bpmn:documentation");
				modell.setProcessDescription( documentation.item(0).getTextContent() );
			}



			modellService.saveModell(modell);


		}catch(IdAlreadyUsedException e){
			return HttpStatus.CONFLICT.toString();
		}
		catch(Exception e){
			return HttpStatus.BAD_REQUEST.toString();
			
		}
		return HttpStatus.CREATED.toString();
	}

	@GetMapping("/modell/{id}")
	public ResponseEntity<String> getModellById(@PathVariable("id") String modellId) throws SQLException {

		List<Modell> modelle = modellService.getAllModells();

		for(int i=0; i<modelle.size(); i++){
			if(modelle.get(i).getId().equals(modellId)){
				Blob blob = modelle.get(i).getXml();
				String string = new String(blob.getBytes(1, (int) blob.length()));
				return new ResponseEntity<String>(string, HttpStatus.OK);
			}

		}
		return new ResponseEntity<String>("Fehler!", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/overview/{id}")
	public ResponseEntity<OverviewDto> getOverviewById(@PathVariable("id") String overviewId){

		List<Modell> modelle = modellService.getAllModells();
		OverviewDto overviewDto = new OverviewDto();

		for(int i=0; i<modelle.size(); i++){
			if(modelle.get(i).getId().equals(overviewId)){
				overviewDto.setDepartment(modelle.get(i).getDepartment());
				overviewDto.setEndKnoten(modelle.get(i).getEndKnoten());
				overviewDto.setId(modelle.get(i).getId());
				overviewDto.setProcessDescription(modelle.get(i).getProcessDescription());
				overviewDto.setEnergySumYear(modelle.get(i).getEnergySumYear());
				overviewDto.setProcessType(modelle.get(i).getProcessType());
				overviewDto.setStartKnoten(modelle.get(i).getStartKnoten());
				overviewDto.setChildProcess(modelle.get(i).getChildProcess());
				overviewDto.setName(modelle.get(i).getName());
				overviewDto.setParentProcess(modelle.get(i).getParentProcess());
				return new ResponseEntity<OverviewDto>(overviewDto, HttpStatus.OK);

			}
		}
		return new ResponseEntity<OverviewDto>(overviewDto, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/overviews")
	public List<OverviewDto> getAllOverviewsWithOptionalRequestParam(@RequestParam(required = false) String sortBy){
		if(sortBy == null){
			return getAllOverviews();
		}
		else if(sortBy.equals("CO2")) {
			var allOverviews = getAllOverviews();
			allOverviews.sort( (o1, o2) -> o2.getEnergySumYear() - o1.getEnergySumYear());
			return allOverviews;
		}

		return getAllOverviews();

	}

	@GetMapping("/overviews/processType")
	public List<OverviewDto> getAllOverviewsWithProcessTypeCore(@RequestParam(required = false) String type) throws ResourceNotFoundException {

		if(type.equalsIgnoreCase("core")) {
			var allOverviews = getAllOverviews();

			List<OverviewDto> overviewDtos = new ArrayList<>();

			for (int i = 0; i < allOverviews.size(); i++) {
				String processType = allOverviews.get(i).getProcessType();
				if (processType.equalsIgnoreCase("CORE")) {
					overviewDtos.add(allOverviews.get(i));
				}
			}
			return overviewDtos;
		}
		throw new ResourceNotFoundException("Keine Core Prozesse","Fehler", null );

	}


	private List<OverviewDto> getAllOverviews(){

		List<Modell> allModells = modellService.getAllModells();
		List<OverviewDto> overviewDtos = new ArrayList<>();

		for(int i=0; i<allModells.size(); i++){
			OverviewDto overviewDto = new OverviewDto();
			overviewDto.setStartKnoten(allModells.get(i).getStartKnoten());
			overviewDto.setDepartment(allModells.get(i).getDepartment());
			overviewDto.setProcessType(allModells.get(i).getProcessType());
			overviewDto.setId(allModells.get(i).getId());
			overviewDto.setEnergySumYear(allModells.get(i).getEnergySumYear());
			overviewDto.setProcessDescription(allModells.get(i).getProcessDescription());
			overviewDto.setEndKnoten(allModells.get(i).getEndKnoten());
			overviewDto.setChildProcess(allModells.get(i).getChildProcess());
			overviewDto.setName(allModells.get(i).getName());
			overviewDto.setParentProcess(allModells.get(i).getParentProcess());
			overviewDtos.add(overviewDto);

		}
		return overviewDtos;
	}

}
