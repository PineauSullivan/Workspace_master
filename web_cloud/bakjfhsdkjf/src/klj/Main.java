package klj;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.json.simple.JSONObject;

public class Main {

	public static void main(String[] args) throws FileNotFoundException,
			IOException, ParseException {
		update_donnees_json update_data = new update_donnees_json();
		int num_data = update_data.go_update(null);
		System.out.println("ajout de "+num_data+" données.");
		

	}

}
