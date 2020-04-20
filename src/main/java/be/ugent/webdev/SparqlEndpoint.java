package be.ugent.webdev;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.jena.fuseki.server.FusekiConfig;
import org.apache.jena.fuseki.server.SPARQLServer;
import org.apache.jena.fuseki.server.ServerConfig;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.DatasetFactory;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class SparqlEndpoint {
	private final OntModel model;
	
	public static void main(final String[] args) {
		// Silence Log4j
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.ERROR);
		
		// Parse command-line arguments
		final Options options = new Options();
		options.addOption("h", "help", false, "prints this message");
		options.addOption("p", "port", true, "launch the SPARQL endpoint on this port");
		CommandLine arguments = null;
		try {
			arguments = new PosixParser().parse(options, args, false);
		}
		catch (ParseException error) {
			System.out.println(error.getMessage());
			System.exit(1);
		}
		if (arguments.hasOption("help")) {
			final HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("sparql-endpoint [file1 [file2 ...]", options, true);
			System.exit(1);
		}
		
		// Load the files
		final SparqlEndpoint endpoint = new SparqlEndpoint();
		for (final String file : arguments.getArgs()) {
			System.err.format("Reading file %s\n", file);
			try {
				endpoint.loadDataset(file);
			}
			catch (Exception error) {
				System.err.format("Could not load %s: %s\n", file, error.getMessage());
			}
		}
		
		// Start the SPARQL endpoint
		final int port = Integer.parseInt(arguments.getOptionValue("port", "3000"));
		System.err.format("Starting SPARQL endpoint at http://localhost:%d/dataset/sparql\n", port);
		endpoint.start(port);
	}
	
	public SparqlEndpoint() {
		model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
	}
	
	public void loadDataset(final String url) {
		model.read(url);
	}
	
	public void start(final int port) {
		final Dataset dataset = DatasetFactory.create(model);
		ServerConfig config = FusekiConfig.defaultConfiguration("dataset", dataset.asDatasetGraph(), false, true);
		config.port = config.pagesPort = port;
		config.pages = null;
		
		final SPARQLServer server = new SPARQLServer(config);
		server.start();
	}
}
