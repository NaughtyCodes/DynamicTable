package com.naughty.codes.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	
	public JSONObject execiteQuery(String query) throws ParseException{
		
		JSONObject selectRow ;
		JSONObject selectColumn ;
		List<JSONObject> columnObjects = new ArrayList<JSONObject>();
		List<JSONObject> rowObjects = new ArrayList<JSONObject>();
		JSONObject responseObject = new JSONObject();
		boolean buildCollDefinition = true;
	    
		try{
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mifos","root","root1234"); 
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery(query);  
				
			rowObjects = new ArrayList<JSONObject>();
			
			ResultSetMetaData metaData = rs.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			
			if (count == 0){
				return responseObject;
			} 
			
			JSONParser parser = new JSONParser();

			
			while(rs.next()){  
				
				selectRow = new JSONObject();
				
				for (int i = 1; i <= count; i++)
				{
					if(buildCollDefinition){
						selectColumn = new JSONObject();
						selectColumn = (JSONObject) parser.parse(
								 "{\"field\": \"display_name\",\"width\": \"15%\"}" 
								);
						selectColumn.put("field", metaData.getColumnLabel(i));
						columnObjects.add(selectColumn);
					}
					switch(metaData.getColumnType(i)){
					
						case Types.VARCHAR:
							selectRow.put(metaData.getColumnLabel(i),rs.getString(i));
							break;
						case Types.INTEGER:
							selectRow.put(metaData.getColumnLabel(i),rs.getInt(i));
							break;
						case Types.BIGINT:
							selectRow.put(metaData.getColumnLabel(i),rs.getInt(i));
							break;
						case Types.SMALLINT:
							selectRow.put(metaData.getColumnLabel(i),rs.getInt(i));
							break;
						case Types.TINYINT:
							selectRow.put(metaData.getColumnLabel(i),rs.getInt(i));
							break;
						default:
							selectRow.put(metaData.getColumnLabel(i),rs.getString(i));
					}
					
				}
				
				buildCollDefinition = false;
			    rowObjects.add(selectRow);
			}
			con.close();
			for(JSONObject row : rowObjects){
					//System.out.println(row.toJSONString());
			}
			
			responseObject.put("columnDefs",columnObjects);
			responseObject.put("data",rowObjects);
			
		}catch(Exception e){ 
			System.out.println(e);
		}
		
		return responseObject;  
	}
	
	
	@RequestMapping(value = { "/api/dtable" }, method = RequestMethod.GET)
	public JSONObject getDtable() throws ParseException {

		String query;
		query = "SELECT * FROM customer";
		ApiController ac = new ApiController();
		return ac.execiteQuery(query);
	}
	@RequestMapping(
			value = { "/api/post-dtable" },
			method = RequestMethod.POST
			//headers = "Content-Type=application/x-www-form-urlencoded"
			)
	public JSONObject postDtable(@RequestBody Data data) throws ParseException {
		
		System.out.println(data.toString());
		String query = data.getSql();
		//query = "SELECT * FROM customer";
		System.out.println(query);
		ApiController ac = new ApiController();
		return ac.execiteQuery(query);
	}
	
}
