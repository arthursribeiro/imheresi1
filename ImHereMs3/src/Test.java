import java.util.ArrayList;
import java.util.List;

import principal.Facade;
import easyaccept.EasyAcceptFacade;

public class Test {

	public static void main(String[] args) throws Exception {

		List<String> files = new ArrayList<String>();

		// Put the us1.txt file into the "test scripts" list

		String sep = System.getProperty("file.separator");
		files.add("teste_aceitacao" + sep + "us1.txt");
		files.add("teste_aceitacao" + sep + "us2.txt");
		files.add("teste_aceitacao" + sep + "us3.txt");
		files.add("teste_aceitacao" + sep + "us4.txt");
		files.add("teste_aceitacao" + sep + "us5.txt");
		files.add("teste_aceitacao" + sep + "us6.txt");
		files.add("teste_aceitacao" + sep + "us7_1.txt");
		files.add("teste_aceitacao" + sep + "us7_2.txt");
		files.add("teste_aceitacao" + sep + "us7_3.txt");



		Facade monopolyGameFacade = new Facade();

		// Instantiate EasyAccept façade

		EasyAcceptFacade eaFacade = new EasyAcceptFacade(monopolyGameFacade,
				files);

		// Execute the tests

		eaFacade.executeTests();

		// Print the tests execution results

		System.out.println(eaFacade.getCompleteResults());

	}

}