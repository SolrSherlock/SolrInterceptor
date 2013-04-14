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

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

//import net.sf.json.JSONObject;
import org.json.simple.JSONObject;

import org.apache.solr.common.SolrInputDocument;
//import org.apache.solr.common.SolrInputField;
import org.apache.solr.update.processor.UpdateRequestProcessor;

/**
 * @author park
 * 
 */
public class DocumentProcessor extends UpdateRequestProcessor {
	private int thePort = 0;
	private boolean isRunning = true;
	private Worker thread;
	private String agentTag;
	/**
	 * @param next
	 * @param agentTag TODO
	 */
	public DocumentProcessor(UpdateRequestProcessor next, int port, String agentTag) {
		super(next);
		thePort = port;
		this.agentTag = agentTag;
		thread = new Worker();
	}
	
	/**
	 * For testing only!!!
	 * @param port
	 */
	public DocumentProcessor(int port) {
		super(null);
		thePort = port;
	}
	/**
	 * allows support of testing
	 * @param doc
	 */
	public void acceptDocument(SolrInputDocument doc) {
		System.out.println("DocumentProcessor.acceptDocument "+doc);
		JSONObject j = new JSONObject(doc);
		String json= agentTag+"|"+j.toJSONString();
		System.out.println("JSON "+json);
		serveData(json);
	}
	
	
	public void processAdd(AddUpdateCommand cmd) throws IOException {
//		System.out.println("INTERCEPTOR.ADD "+cmd);
		SolrInputDocument doc = cmd.getSolrInputDocument();
		//acceptDocument(doc);
		thread.addDocument(doc);
	}
	
	/**
	 * Convert <code>doc</code> into a <code>Map<String,Object</code>
	 * @param doc
	 * @return
	 * /
	Map<String,Object> toMap(SolrInputDocument doc) {
		Map<String,Object> result = new HashMap<String,Object>();
		Iterator<SolrInputField> itr = doc.iterator();
		SolrInputField f;
		Object v;
		String key;
		while (itr.hasNext()) {
			f = itr.next();
//			System.out.println(f);
			v = f.getValue();
//			System.out.println("  "+v);
			key = f.getName();
			result.put(key, v);
		}
		return result;
	}
	*/
	
	protected void finalize() throws Exception {
		thread.halt();
	}
	/**
	 * Send the data
	 * @param data
	 */
	void serveData(String data) {
	    try {
	        ServerSocket srvr = new ServerSocket(thePort);
	        System.out.println("DocumentProcessor socket "+srvr);
	        Socket skt = srvr.accept();
	        System.out.print("Server has connected!\n");
	        PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
	        System.out.print("Sending string: '" + data + "'\n");
	        out.print(data);
	        out.close();
	        skt.close();
	        srvr.close();
	    }
	    catch(Exception e) {
	        System.out.print("Whoops! It didn't work!\n");
	        e.printStackTrace();
	        //TODO figure out how to get this into Solr's logging system
	    }
	}
	
	class Worker extends Thread {
		private List<SolrInputDocument>documents;

		Worker() {
			documents = new ArrayList<SolrInputDocument>();
			this.start();
		}
		
		public void halt() {
			synchronized(documents) {
				isRunning = false;
				documents.notify();
			}
		}
		public void addDocument(SolrInputDocument doc) {
			synchronized(documents) {
				documents.add(doc);
				documents.notify();
			}
		}
		public void run() {
			SolrInputDocument theDoc = null;
			while (isRunning) {
				synchronized(documents) {
					if (documents.isEmpty()) {
						try {
							documents.wait();
						} catch (Exception e) {}
					}
					if (isRunning && !documents.isEmpty()) {
						theDoc = documents.remove(0);
					}
				}
				if (isRunning && theDoc != null) {
					acceptDocument(theDoc);
					theDoc = null;
				}
			}
		}
	}
}
