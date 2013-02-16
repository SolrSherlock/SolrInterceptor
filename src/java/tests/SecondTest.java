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
package tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.update.DocumentProcessor;

/**
 * @author park
 * <p>Test serial port communication</p>
 * <p>NOTE: THIS TEST CANNOT WORK: thread contention between two sockets</p>
 * <p>Possible fix: put this test in a thread of its own</p>
 */
public class SecondTest {
	DocumentProcessor _sender;
	int port = 43435;
	boolean isRunning = true;
	/**
	 * 
	 */
	public SecondTest() {
		_sender = new DocumentProcessor(port);
		System.out.println("Starting "+_sender);
		try {
			runTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void runTest() throws Exception {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("locator", "123ABC");
		List<String>x = new ArrayList<String>();
		x.add("Hello");
		x.add("GoodBye");
		doc.addField("list",x);
		send(doc);
		send(doc);
	}
	
	void send(SolrInputDocument doc) {
		StringBuilder buf = new StringBuilder();
		String line;
		_sender.acceptDocument(doc);
	     try {
	         Socket skt = new Socket("localhost", port);
	         System.out.println("SecondTest socket "+skt);
	         BufferedReader in = new BufferedReader(new
	         InputStreamReader(skt.getInputStream()));

	         while (!in.ready()) { if (!isRunning) return;}
	         while ((line = in.readLine()) != null)
	        	 buf.append(line);
	         
	         in.close();
	      }
	      catch(Exception e) {
	         e.printStackTrace();
	         throw new RuntimeException(e);
	      }		
	     System.out.println("GOT: "+buf.toString());
		
	}
}
