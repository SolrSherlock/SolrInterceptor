/**
 * 
 */
package org.apache.solr.update;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.solr.common.util.NamedList;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.update.processor.UpdateRequestProcessor;
import org.apache.solr.update.processor.UpdateRequestProcessorFactory;

/**
 * @author park
 *
 */
public class TopicQuestsHarvestProcessFactory extends
		UpdateRequestProcessorFactory {
	private int myPort = 0;
	private DocumentProcessor processor;
	private final String agentTag = "HarvestDocument";

	/**
	 * Called when the code in solrconfig.xml wants to send in some
	 * initialization parameters, which we ignore
	 * @param args
	 */
	public void init( NamedList args ) {
		int port = 0;
		Properties p = new Properties();
		try {
			//file must be in classpath
			File f = new File("agents.properties");
			FileInputStream fis = new FileInputStream(f);
			p.load(fis);
			fis.close();
			String portx = p.getProperty("port");
			myPort = Integer.parseInt(portx);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.apache.solr.update.processor.UpdateRequestProcessorFactory#getInstance(org.apache.solr.request.SolrQueryRequest, org.apache.solr.response.SolrQueryResponse, org.apache.solr.update.processor.UpdateRequestProcessor)
	 */
	@Override
	public UpdateRequestProcessor getInstance(SolrQueryRequest arg0,
			SolrQueryResponse arg1, UpdateRequestProcessor next) {
		if (processor == null)
			processor = new DocumentProcessor(next,myPort, agentTag);
		return processor;
	}

}
