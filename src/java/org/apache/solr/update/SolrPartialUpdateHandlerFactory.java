/**
 * 
 */
package org.apache.solr.update;

import org.apache.solr.common.util.NamedList;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.update.processor.UpdateRequestProcessor;
import org.apache.solr.update.processor.UpdateRequestProcessorFactory;

/**
 * @author park
 *
 */
public class SolrPartialUpdateHandlerFactory extends
		UpdateRequestProcessorFactory {
	private int myPort = 0;
	private DocumentProcessor processor;

	/**
	 * Called when the code in solrconfig.xml wants to send in some
	 * initialization parameters, which we ignore
	 * @param args
	 */
	public void init( NamedList args ) {
		//TODO
	}
	/* (non-Javadoc)
	 * @see org.apache.solr.update.processor.UpdateRequestProcessorFactory#getInstance(org.apache.solr.request.SolrQueryRequest, org.apache.solr.response.SolrQueryResponse, org.apache.solr.update.processor.UpdateRequestProcessor)
	 */
	@Override
	public UpdateRequestProcessor getInstance(SolrQueryRequest arg0,
			SolrQueryResponse arg1, UpdateRequestProcessor arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
