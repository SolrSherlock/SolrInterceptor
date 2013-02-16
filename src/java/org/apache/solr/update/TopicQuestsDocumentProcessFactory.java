/**
   Copyright [2013] [Jack Park]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.apache.solr.update;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.update.processor.UpdateRequestProcessor;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.update.processor.UpdateRequestProcessorFactory;

/**
 * @author park
 * <p>Adapted from code found at
 * http://knackforge.com/blog/selvam/integrating-solr-and-mahout-classifier
 * </p>
 */
public class TopicQuestsDocumentProcessFactory extends
		UpdateRequestProcessorFactory {
	private int myPort = 0;
	
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
	public UpdateRequestProcessor getInstance(SolrQueryRequest req,
			SolrQueryResponse resp, UpdateRequestProcessor next) {
		//Seems to require a new processor since next changes
		//TODO try testing against a fixed processor
		return new DocumentProcessor(next,myPort);
	}
	
	
}
